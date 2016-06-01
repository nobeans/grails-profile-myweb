package myweb.sample

import grails.util.GrailsUtil

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        // 開発用ポータルページ
        if (GrailsUtil.developmentEnv) {
            "/"(view: "/index")
        }

        // エラー系
        [
            handle400: [400, 401, 402, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413, 414, 415, 416, 417, 418, 421, 422, 423, 424, 426, 428, 429, 431],
            handle403: [403],
            handle500: [500, 501, 502, 503, 504, 505, 506, 507, 508, 510, 511]
        ].each { action, statusCodes ->
            statusCodes.each { statusCode ->
                "${statusCode}"(controller: "error", action: action)
            }
        }
    }
}
