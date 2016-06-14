package @grails.codegen.defaultPackage@

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * ログインユーザのロールを表すドメインクラスです。
 */
@EqualsAndHashCode(includes = 'authority')
@ToString(includes = 'authority', includeNames = true, includePackage = false)
class Role {

    static final String AUTHORITY_ADMIN = "ROLE_ADMIN"
    static final String AUTHORITY_USER = "ROLE_USER"

    /** 権限 */
    String authority

    static constraints = {
        authority blank: false, unique: true
    }

    static Role getAdmin() {
        Role.findByAuthority(AUTHORITY_ADMIN)
    }

    static Role getUser() {
        Role.findByAuthority(AUTHORITY_USER)
    }
}
