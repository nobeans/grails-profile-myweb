<%=packageName ? "package ${packageName}" : ''%>

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.util.Holders

/**
 * {@code ${className}}管理用のコントローラです。
 */
@Transactional(readOnly = true)
class ${className}Controller {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    /**
     * 一覧画面を表示します。
     *
     * @param max
     * @return
     */
    def index(Integer max) {
        params.max = Math.min(max ?: Holders.config.grails.controller.maxDefault, Holders.config.grails.controller.maxLimit)
        respond ${className}.list(params), model: [${propertyName}Count: ${className}.count()]
    }

    /**
     * 詳細画面を表示します。
     *
     * @param ${propertyName}
     * @return
     */
    def show(${className} ${propertyName}) {
        if (${propertyName} == null) {
            notFound()
            return
        }

        respond ${propertyName}
    }

    /**
     * 新規作成画面を表示します。
     *
     * @return
     */
    def create() {
        respond new ${className}(params)
    }

    /**
     * 新規作成します。
     *
     * @param ${propertyName}
     * @return
     */
    @Transactional
    def save(${className} ${propertyName}) {
        if (${propertyName} == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (${propertyName}.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond ${propertyName}.errors, view: 'create'
            return
        }

        ${propertyName}.save flush: true

        withFormat {
            json xml { respond ${propertyName}, [status: CREATED] }
            '*' {
                flash.message = message(code: 'default.created.message', args: [message(code: '${propertyName}.label')])
                redirect ${propertyName}
            }
        }
    }

    /**
     * 編集画面を表示します。
     *
     * @param ${propertyName}
     * @return
     */
    def edit(${className} ${propertyName}) {
        if (${propertyName} == null) {
            notFound()
            return
        }

        respond ${propertyName}
    }

    /**
     * 更新します。
     *
     * @param ${propertyName}
     * @return
     */
    @Transactional
    def update(${className} ${propertyName}) {
        if (${propertyName} == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (${propertyName}.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond ${propertyName}.errors, view: 'edit'
            return
        }

        ${propertyName}.save flush: true

        withFormat {
            json xml { respond ${propertyName}, [status: OK] }
            '*' {
                flash.message = message(code: 'default.updated.message', args: [message(code: '${propertyName}.label')])
                redirect ${propertyName}
            }
        }
    }

    /**
     * 削除します。
     *
     * @param ${propertyName}
     * @return
     */
    @Transactional
    def delete(${className} ${propertyName}) {
        if (${propertyName} == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        ${propertyName}.delete flush: true

        withFormat {
            json xml { render status: NO_CONTENT }
            '*' {
                flash.message = message(code: 'default.deleted.message', args: [message(code: '${propertyName}.label')])
                redirect action: "index", method: "GET"
            }
        }
    }

    protected void notFound() {
        withFormat {
            json xml { respond([:], status: NOT_FOUND) }
            '*' {
                flash.message = message(code: 'default.not.found.message', args: [message(code: '${propertyName}.label'), params.id])
                redirect action: "index", method: "GET"
            }
        }
    }
}
