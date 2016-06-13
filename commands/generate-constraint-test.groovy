import org.grails.cli.interactive.completers.DomainClassCompleter
import java.lang.reflect.Field

//
// Helper
//

def isDomainClass = { Class targetClass ->
    targetClass.genericInterfaces.any { it.typeName == "grails.artefact.DomainClass" }
}

def parseConstrainedProperties = { Class targetClass ->
    def constraintsField = targetClass.declaredFields.find { it.name == "constraints" }
    constraintsField.accessible = true
    def constraintsClosure = constraintsField.get(targetClass)

    def aggregator = new ConstraintAggregator(targetClass)
    constraintsClosure.delegate = aggregator
    constraintsClosure.call()
    return aggregator.props
}

class ConstrainedProperty {
    String name
    Class type
    def constraints = [:]
}

class ConstraintAggregator {
    def typeMap
    def props = []

    ConstraintAggregator(Class targetClass) {
        typeMap = targetClass.declaredFields.collectEntries { Field field -> [field.name, field.type] }
    }

    void methodMissing(String name, args) {
        if (args.size() == 0) {
            props << new ConstrainedProperty(name: name, type: typeMap[name], constraints: [:])
            return
        }
        if (args.size() == 1 && args[0] instanceof Map) {
            props << new ConstrainedProperty(name: name, type: typeMap[name], constraints: args[0])
            return
        }
        throw new IllegalArgumentException("should specify a single map argument or empty for each property in constraints: name=$name, args=$args")
    }
}

def resolveWherePatterns = { constrainedProperties ->
    def wherePatterns = []
    constrainedProperties.each { ConstrainedProperty prop ->
        // 推奨されない型が指定された場合は警告して無視する。
        if (prop.type == Object) {
            warn "ドメインクラスやコマンドオブジェクトではObject型のプロパティは制約対象外です。: ${prop.name}"
            return
        }
        if (prop.type.primitive) {
            warn "ドメインクラスやコマンドオブジェクトではプリミティブ型のプロパティは推奨されません。入力の有無をnullとして識別できるラッパー型の採用を検討してください。: ${prop.name}"
            return
        }

        if (!prop.constraints*.key.contains('nullable')) {
            wherePatterns << [prop.name, "nullable", "null"]
        }
        prop.constraints.each { constraint, value ->
            if (constraint == "nullable") {
                if (value) {
                    wherePatterns << [prop.name, "valid", "null"]
                } else {
                    wherePatterns << [prop.name, "nullable", "null"]
                }
            }
            if (constraint == "blank") {
                wherePatterns << [prop.name, "blank", '""']
            }
            if (constraint == "minSize") { // support only for String
                wherePatterns << [prop.name, "minSize.notmet", /"x" * ${value - 1}/]
                wherePatterns << [prop.name, "valid", /"x" * ${value}/]
            }
            if (constraint == "maxSize") { // support only for String
                wherePatterns << [prop.name, "valid", /"x" * ${value}/]
                wherePatterns << [prop.name, "maxSize.exceeded", /"x" * ${value + 1}/]
            }
            if (constraint == "size") { // support only for String
                wherePatterns << [prop.name, "size.toosmall", /"x" * ${value.from - 1}/]
                wherePatterns << [prop.name, "valid", /"x" * ${value.from}/]
                wherePatterns << [prop.name, "valid", /"x" * ${value.to}/]
                wherePatterns << [prop.name, "size.toobig", /"x" * ${value.to + 1}/]
            }
            if (constraint == "min") {
                wherePatterns << [prop.name, "min.notmet", value - 1]
                wherePatterns << [prop.name, "valid", value]
            }
            if (constraint == "max") {
                wherePatterns << [prop.name, "valid", value]
                wherePatterns << [prop.name, "max.exceeded", value + 1]
            }
            if (constraint == "range") {
                wherePatterns << [prop.name, "range.toosmall", value.from - 1]
                wherePatterns << [prop.name, "valid", value.from]
                wherePatterns << [prop.name, "valid", value.to]
                wherePatterns << [prop.name, "range.toobig", value.to + 1]
            }
            if (constraint == "inList") {
                value.each { validItem ->
                    wherePatterns << [prop.name, "valid", /"${validItem}"/]
                }
                wherePatterns << [prop.name, "not.inList", '"NOT_IN_LIST"']
            }
            if (constraint == "notEqual") { // support only for String
                wherePatterns << [prop.name, "valid", '"NOT_EQUAL"']
                wherePatterns << [prop.name, "notEqual", /"${value}"/]
            }
            if (constraint == "matches") {
                wherePatterns << [prop.name, "matches.invalid", $/"FIXME" // FIXME for "$value"/$]
            }
            if (constraint == "validator") {
                wherePatterns << [prop.name, "validator.invalid", $/"FIXME" // FIXME/$]
            }
            if (constraint == "unique") {
                wherePatterns << [prop.name, "unique", $/"FIXME" // FIXME for "$value"/$]
            }
            if (constraint == "creditCard" && value) {
                wherePatterns << [prop.name, "creditCard.invalid", '"NOT_CREDIT_CARD"']
            }
            if (constraint == "email" && value) {
                wherePatterns << [prop.name, "email.invalid", '"NOT_EMAIL"']
            }
            if (constraint == "url" && value) {
                wherePatterns << [prop.name, "url.invalid", '"NOT_URL"']
            }
        }

        // String以外の場合はtypeMismatchエラーの観点を追加する。
        if (prop.type != String) {
            if (Number.isAssignableFrom(prop.type)) {
                wherePatterns << [prop.name, "typeMismatch", '"NOT_NUMBER"']
            } else if (prop.type == Boolean) {
                // Booleanの場合は更に規定の真偽文字列か否かの観点を追加する。
                wherePatterns << [prop.name, "valid", '"true"']
                wherePatterns << [prop.name, "valid", '"TRUE"']
                wherePatterns << [prop.name, "valid", '"false"']
                wherePatterns << [prop.name, "valid", '"FALSE"']
                wherePatterns << [prop.name, "typeMismatch", '"t"']
                wherePatterns << [prop.name, "typeMismatch", '"F"']
                wherePatterns << [prop.name, "typeMismatch", '"NOT_BOOLEAN"']
            } else if (prop.type == Date) {
                wherePatterns << [prop.name, "typeMismatch", '"NOT_DATE"']
            } else {
                wherePatterns << [prop.name, "typeMismatch", '"INVALID_TYPE"']
            }
        }
    }
    wherePatterns.collect { new WherePattern(field: it[0], error: it[1], value: it[2]) }
}

class WherePattern {
    String field
    String error
    Object value
}

//
// Main
//

description("Generates a ConstraintSpec") {
    usage "grails generate-constraint-test [DOMAIN CLASS]"
    argument name: 'Domain Class', description: "The name of the domain class", required: true
    completer DomainClassCompleter
    flag name: 'force', description: "Whether to overwrite existing files"
}

if (!args) {
    error "No domain class specified"
    return
}

boolean shouldOverwrite = flag('force')

def classNames = args
if (args[0] == '*') {
    classNames = resources("file:grails-app/domain/**/*.groovy").collect { className(it) }
}

for (arg in classNames) {
    def sourceClassResource = source(arg)
    if (!sourceClassResource) {
        error "Domain class not found for name $arg"
        continue
    }
    Class targetClass = new GroovyClassLoader().parseClass(sourceClassResource.file)

    def model = model(sourceClassResource)

    render template: template("src/test/groovy/ConstraintSpec.groovy"),
        destination: file("src/test/groovy/${model.packagePath}/${model.convention('ConstraintSpec')}.groovy"),
        model: model.asMap() + [
            isDomainClass: isDomainClass(targetClass),
            wherePatterns: resolveWherePatterns(parseConstrainedProperties(targetClass)),
        ],
        overwrite: shouldOverwrite

    addStatus "Generation completed for ${projectPath(sourceClassResource)}"

    info "[注意事項] 自動生成されたテストのwhereブロックはあくまで参照実装です。必ずチェックした上で適切に修正してください。"
}
