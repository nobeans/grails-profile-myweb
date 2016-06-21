package @grails.codegen.defaultPackage@

import grails.plugin.springsecurity.SpringSecurityService
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * ログインユーザを表すドメインクラスです。
 */
@EqualsAndHashCode(includes = 'loginName')
@ToString(includes = 'loginName', includeNames = true, includePackage = false)
class Person {

    SpringSecurityService springSecurityService

    /** ログイン名 */
    String loginName

    /** 氏名 */
    String realName

    /** パスワード */
    String password

    /** パスワード(確認) */
    String passwordToConfirm

    /** 作成日時 */
    Date dateCreated

    /** 最終更新日時 */
    Date lastUpdated

    // TODO アカウント有効・無効の仕組みを使う場合はfinalをはずして可変にすること。
    final boolean enabled = true
    final boolean accountExpired = false
    final boolean accountLocked = false
    final boolean passwordExpired = false

    static hasMany = [authorities: Role]

    static transients = ['springSecurityService', 'passwordToConfirm']

    /**
     * 管理者かどうかを返します。
     *
     * @return
     */
    boolean isAdmin() {
        authorities.count { it.authority == Role.AUTHORITY_ADMIN } > 0
    }

    def afterLoad() {
        passwordToConfirm = password
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
        // ハッシュ化した後にバリデーションが実行される場合を考慮してpasswordToConfirmにもハッシュ化後のパスワードを設定する。
        passwordToConfirm = password
    }

    static constraints = {
        loginName blank: false, size: 4..20, matches: /[0-9a-z_-]+/, unique: true
        realName blank: false, maxSize: 100
        password blank: false, size: 8..60, matches: /[\x21-\x7e]+/, validator: { value, self ->
            value == self.passwordToConfirm
        }
        passwordToConfirm bindable: true
    }

    static mapping = {
        password column: '`password`'
    }
}
