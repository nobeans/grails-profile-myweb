import @grails.codegen.defaultPackage@.PersonAndRoleFixture

class BootStrap {

    def init = { servletContext ->
        new PersonAndRoleFixture().setup()
    }

    def destroy = {
    }
}
