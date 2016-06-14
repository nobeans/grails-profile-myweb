grails {
    plugin {
        springsecurity {
            // 起動時に出力される以下のメッセージを抑止する。
            //   Configuring Spring Security Core ...
            //   finished configuring Spring Security Core
            printStatusMessages = false

            // このアプリケーションにおけるクラス名やプロパティを指定する。
            userLookup {
                userDomainClassName = '@grails.codegen.defaultPackage@.Person'
                usernamePropertyName = 'loginName'
            }
            authority.className = '@grails.codegen.defaultPackage@.Role'

            // URLベースでのアクセス制御を設定する。
            // アノテーションベースでは開発ポータル(/)へのアクセス制御ができないため、
            // InterceptUrlMapによるアクセス制御を採用する。
            securityConfigType = "InterceptUrlMap"
            interceptUrlMap = [
                [pattern: '/',               access: ['permitAll']],
                [pattern: '/error',          access: ['permitAll']],
                [pattern: '/dbconsole/**',   access: ['permitAll']],
                [pattern: '/assets/**',      access: ['permitAll']],
                [pattern: '/**/js/**',       access: ['permitAll']],
                [pattern: '/**/css/**',      access: ['permitAll']],
                [pattern: '/**/images/**',   access: ['permitAll']],
                [pattern: '/**/fonts/**',    access: ['permitAll']],
                [pattern: '/**/favicon.ico', access: ['permitAll']],
                [pattern: '/login/**',       access: ['permitAll']],
                [pattern: '/logout/**',      access: ['permitAll']],
                [pattern: '/person/**',      access: ['hasRole("ROLE_ADMIN")']],
            ]

            // 管理者は、一般ユーザの権限を兼ねる。
            roleHierarchy = 'ROLE_ADMIN > ROLE_USER'

            // GETでログアウトできるようにする。
            logout.postOnly = false
        }
    }
}
