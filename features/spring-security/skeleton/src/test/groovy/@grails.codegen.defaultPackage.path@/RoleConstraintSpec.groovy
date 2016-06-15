package @grails.codegen.defaultPackage@

import grails.test.mixin.TestFor
import spock.lang.Unroll
import test.ConstraintUnitSpec

@TestFor(Role)
class RoleConstraintSpec extends ConstraintUnitSpec {

    Role role = new Role()

    @Unroll
    def "validate: #field is #error when value is #value.inspect()"() {
        given:
        new Role(authority: "ROLE_EXISTED").save(failOnError: true, flush: true)

        and:
        bind(role, field, value)

        expect:
        validateConstraints(role, field, error)

        where:
        field       | error             | value
        "authority" | "nullable"        | null
        "authority" | "blank"           | ""
        "authority" | "valid"           | "ROLE_ADMIN"
        "authority" | "valid"           | "ROLE_USER"
        "authority" | "matches.invalid" | "NOT_STARTED_WITH_ROLE_XXX"
        "authority" | "unique"          | "ROLE_EXISTED"
    }
}
