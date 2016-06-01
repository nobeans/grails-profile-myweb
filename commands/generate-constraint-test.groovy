import org.grails.cli.interactive.completers.DomainClassCompleter

//
// Helper
//

def parseConstraints = { Class targetClass ->
    def constraintsField = targetClass.declaredFields.find { it.name == "constraints" }
    constraintsField.accessible = true
    def constraintsClosure = constraintsField.get(targetClass)

    def aggregator = new ConstraintAggregator()
    constraintsClosure.delegate = aggregator
    constraintsClosure.call()
    return aggregator.found
}

class ConstraintAggregator {
    def found = [:].withDefault { [:] }

    void methodMissing(String name, args) {
        if (args.size() == 0) return
        if (!(args.size() == 1 && args[0] instanceof Map)) {
            throw new IllegalArgumentException("should specify a single map argument or empty for each property in constarints")
        }
        found[name] = args[0]
    }
}

def resolveWherePatterns = { constraintsMap ->
    def wherePatterns = []
    constraintsMap.each { field, constraints ->
        if (!constraints*.key.contains('nullable')) {
            wherePatterns << [field, "nullable", "null"]
        }
        constraints.each { constraint, value ->
            if (constraint == "nullable") {
                if (value) {
                    wherePatterns << [field, "valid", "null"]
                } else {
                    wherePatterns << [field, "nullable", "null"]
                }
            }
            if (constraint == "blank") {
                wherePatterns << [field, "blank", '""']
            }
            if (constraint == "minSize") { // support only for String
                wherePatterns << [field, "minSize.notmet", /"x" * ${value - 1}/]
                wherePatterns << [field, "valid", /"x" * ${value}/]
            }
            if (constraint == "maxSize") { // support only for String
                wherePatterns << [field, "valid", /"x" * ${value}/]
                wherePatterns << [field, "maxSize.exceeded", /"x" * ${value + 1}/]
            }
            if (constraint == "size") { // support only for String
                wherePatterns << [field, "size.toosmall", /"x" * ${value.from - 1}/]
                wherePatterns << [field, "valid", /"x" * ${value.from}/]
                wherePatterns << [field, "valid", /"x" * ${value.to}/]
                wherePatterns << [field, "size.toobig", /"x" * ${value.to + 1}/]
            }
            if (constraint == "min") {
                wherePatterns << [field, "min.notmet", value - 1]
                wherePatterns << [field, "valid", value]
            }
            if (constraint == "max") {
                wherePatterns << [field, "valid", value]
                wherePatterns << [field, "max.exceeded", value + 1]
            }
            if (constraint == "range") {
                wherePatterns << [field, "range.toosmall", value.from - 1]
                wherePatterns << [field, "valid", value.from]
                wherePatterns << [field, "valid", value.to]
                wherePatterns << [field, "range.toobig", value.to + 1]
            }
            if (constraint == "inList") {
                value.each { validItem ->
                    wherePatterns << [field, "valid", /"${validItem}"/]
                }
                wherePatterns << [field, "not.inList", '"NOT_IN_LIST"']
            }
            if (constraint == "notEqual") { // support only for String
                wherePatterns << [field, "valid", '"NOT_EQUAL"']
                wherePatterns << [field, "notEqual", /"${value}"/]
            }
            if (constraint == "matches") {
                wherePatterns << [field, "matches.invalid", $/"FIXME" // FIXME for "$value"/$]
            }
            if (constraint == "validator") {
                wherePatterns << [field, "validator.invalid", $/"FIXME" // FIXME/$]
            }
            if (constraint == "unique") {
                wherePatterns << [field, "unique", $/"FIXME" // FIXME for "$value"/$]
            }
            if (constraint == "creditCard" && value) {
                wherePatterns << [field, "creditCard.invalid", '"NOT_CREDIT_CARD"']
            }
            if (constraint == "email" && value) {
                wherePatterns << [field, "email.invalid", '"NOT_EMAIL"']
            }
            if (constraint == "url" && value) {
                wherePatterns << [field, "url.invalid", '"NOT_URL"']
            }
        }
    }
    wherePatterns.collect { new WherePattern(it[0], it[1], it[2]) }
}

@groovy.transform.TupleConstructor
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
    boolean isDomainClass = targetClass.genericInterfaces.any { it.typeName == "grails.artefact.DomainClass" }
    def wherePatterns = resolveWherePatterns(parseConstraints(targetClass))

    def model = model(sourceClassResource)

    render template: template("src/test/groovy/ConstraintSpec.groovy"),
        destination: file("src/test/groovy/${model.packagePath}/${model.convention('ConstraintSpec')}.groovy"),
        model: model.asMap() + [isDomainClass: isDomainClass, wherePatterns: wherePatterns],
        overwrite: shouldOverwrite

    addStatus "Generation completed for ${projectPath(sourceClassResource)}"

    info "[注意事項] 自動生成されたテストのwhereブロックはあくまで参照実装です。必ずチェックした上で適切に修正してください。"
}
