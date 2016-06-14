package @grails.codegen.defaultPackage@

import grails.util.GrailsUtil

class PersonAndRoleFixture {

    void setup() {
        // (存在しなければ)ロールを登録する。
        def adminRole = Role.findOrSaveByAuthority("ROLE_ADMIN")
        def userRole = Role.findOrSaveByAuthority("ROLE_USER")

        // 初期管理者ユーザを登録する。
        if (!Person.count()) {
            new Person(realName: 'Default Admin', loginName: 'admin', password: 'password', passwordToConfirm: 'password').addToAuthorities(adminRole).save(flush: true, failOnError: true)
        }

        // 開発時のみ一般ユーザを登録する。
        if (GrailsUtil.developmentEnv) {
            new Person(realName: 'Test User', loginName: 'user', password: 'password', passwordToConfirm: 'password').addToAuthorities(userRole).save(flush: true, failOnError: true)
        }
    }
}
