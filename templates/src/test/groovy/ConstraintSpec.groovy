<%= packageName ? "package ${packageName}" : '' %>

<%= isDomainClass ? "import grails.test.mixin.TestFor${System.lineSeparator()}" : ''
%>import spock.lang.Unroll
import test.ConstraintUnitSpec

<%= isDomainClass ? "@TestFor(${className})${System.lineSeparator()}" : ''
%>class ${className}ConstraintSpec extends ConstraintUnitSpec {

    ${className} ${propertyName} = new ${className}()

    @Unroll
    def "validate: #field is #error when value is #value.inspect()"() {
        given:
        bind(${propertyName}, field, value)

        expect:
        validateConstraints(${propertyName}, field, error)

        where: // TODO 必ずチェックした上で適切に修正すること<%
        def fieldMaxSize = [wherePatterns.collect { it.field.size() }.max() + 2, 'field'.size()].max()
        def errorMaxSize = [wherePatterns.collect { it.error.size() }.max() + 2, 'error'.size()].max()
        %>
        ${'field'.padRight(fieldMaxSize)} | ${'error'.padRight(errorMaxSize)} | value<%
        wherePatterns.each { pattern -> %>
        ${('"' + pattern.field + '"').padRight(fieldMaxSize)} | ${('"' + pattern.error + '"').padRight(errorMaxSize)} | ${pattern.value}<% } %>
    }
}
