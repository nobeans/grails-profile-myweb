// https://grails-plugins.github.io/grails-spring-security-core/v3/index.html
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

            // アノテーション以外の静的認可ルールを設定する。
            // CVE-2016-5007対策のため、アノテーションベースで認可設定をする。
            // ただし、静的ファイルやポータルページ、プラグインのコントローラなど、
            // アプリとしてアノテーションを付与できないものは、ここで静的ルールとして設定する。
            // 自前のコントローラについては原則アノテーションによって認可設定すること。
            controllerAnnotations.staticRules = [
                [pattern: '/',               access: ['permitAll']],
                [pattern: '/error',          access: ['permitAll']],
                [pattern: '/index',          access: ['permitAll']],
                [pattern: '/index.gsp',      access: ['permitAll']],
                [pattern: '/shutdown',       access: ['permitAll']],
                [pattern: '/dbconsole/**',   access: ['permitAll']],
                [pattern: '/assets/**',      access: ['permitAll']],
                [pattern: '/**/js/**',       access: ['permitAll']],
                [pattern: '/**/css/**',      access: ['permitAll']],
                [pattern: '/**/images/**',   access: ['permitAll']],
                [pattern: '/**/favicon.ico', access: ['permitAll']],
            ]

            // 静的ファイルへの認可は不要でフルアクセスなので、無駄にフィルタがかからないように除外する。
            filterChain.chainMap = [
                [pattern: '/assets/**',      filters: 'none'],
                [pattern: '/**/js/**',       filters: 'none'],
                [pattern: '/**/css/**',      filters: 'none'],
                [pattern: '/**/images/**',   filters: 'none'],
                [pattern: '/**/favicon.ico', filters: 'none'],
                [pattern: '/**/fonts/**',    filters: 'none'],
                [pattern: '/**',             filters: 'JOINED_FILTERS']
            ]

            // 管理者は、一般ユーザの権限を兼ねる。
            roleHierarchy = 'ROLE_ADMIN > ROLE_USER'

            // GETでログアウトできるようにする。
            logout.postOnly = false
        }
    }
}
