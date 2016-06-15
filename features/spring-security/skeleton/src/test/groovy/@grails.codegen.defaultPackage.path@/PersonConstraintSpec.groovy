package @grails.codegen.defaultPackage@

import grails.test.mixin.TestFor
import spock.lang.Unroll
import test.ConstraintUnitSpec

@TestFor(Person)
class PersonConstraintSpec extends ConstraintUnitSpec {

    Person person = new Person()

    def setup() {
        new Person(
            loginName: 'existed_login_name',
            realName: 'EXISTED_REAL_NAME',
            password: 'TEST_PASSWORD',
            passwordToConfirm: 'TEST_PASSWORD',
        ).save(failOnError: true, flush: true)
    }

    @Unroll
    def "validate: #field is #error when value is #value.inspect()"() {
        given:
        bind(person, field, value)
        person.passwordToConfirm = person.password

        expect:
        validateConstraints(person, field, error)

        where:
        field               | error               | value
        "loginName"         | "nullable"          | null
        "loginName"         | "blank"             | ""
        "loginName"         | "size.toosmall"     | "x" * 3
        "loginName"         | "valid"             | "x" * 4
        "loginName"         | "valid"             | "x" * 20
        "loginName"         | "size.toobig"       | "x" * 21
        "loginName"         | "matches.invalid"   | "あいうえおかきくけこ"
        "loginName"         | "unique"            | "existed_login_name"
        "realName"          | "nullable"          | null
        "realName"          | "blank"             | ""
        "realName"          | "valid"             | "x" * 100
        "realName"          | "maxSize.exceeded"  | "x" * 101
        "password"          | "nullable"          | null
        "password"          | "blank"             | ""
        "password"          | "size.toosmall"     | "x" * 7
        "password"          | "valid"             | "x" * 8
        "password"          | "valid"             | "x" * 60
        "password"          | "size.toobig"       | "x" * 61
        "password"          | "matches.invalid"   | "あいうえおかきくけこ"
    }

    def "validate: should cause error when password and passwordToConfirm doesn't match"() {
        when:
        person.password = 'test-password'
        person.passwordToConfirm = 'miss-password'

        then:
        !person.validate()
        person.errors['password'].code == 'validator.invalid'
    }

    def "passwordToConfirm should be set from password on loading"() {
        when:
        def person = Person.first()

        then:
        person.password == person.passwordToConfirm
    }
}
