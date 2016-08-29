package @grails.codegen.defaultPackage@

import grails.plugin.springsecurity.annotation.Secured
import grails.util.GrailsUtil
import org.springframework.http.HttpStatus

@Secured(["permitAll"])
class ErrorController {

    def handle403() {
        log.warn "403: ${request["javax.servlet.error.request_uri"]}"
        // 開発時以外はセキュリティ向上のため404 NOT_FOUNDとしてユーザにみせる
        def status = (GrailsUtil.developmentEnv) ? HttpStatus.FORBIDDEN : HttpStatus.NOT_FOUND
        render view: '/error', model: [status: status.value(), path: request["javax.servlet.error.request_uri"]]
    }

    def handle400() {
        render view: '/error', model: [status: response.status, path: request["javax.servlet.error.request_uri"]]
    }

    def handle500() {
        render view: '/error', model: [status: response.status, path: request["javax.servlet.error.request_uri"], exception: request.exception]
    }
}
