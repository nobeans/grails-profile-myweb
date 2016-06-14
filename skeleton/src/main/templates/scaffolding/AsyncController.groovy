<%=packageName ? "package ${packageName}" : ''%>


import grails.util.Holders

import static org.springframework.http.HttpStatus.*
import org.springframework.transaction.TransactionStatus

/**
 * {@code ${className}}管理用のコントローラです。
 */
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
        ${className}.async.task {
            [${propertyName}List: list(params), count: count() ]
        }.then { result ->
            respond result.${propertyName}List, model:[${propertyName}Count: result.count]
        }
    }

    /**
     * 詳細画面を表示します。
     *
     * @param ${propertyName}
     * @return
     */
    def show(Long id) {
        ${className}.async.get(id).then { ${propertyName} ->
            respond ${propertyName}
        }
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
    def save(${className} ${propertyName}) {
        ${className}.async.withTransaction { TransactionStatus status ->
            if (${propertyName} == null) {
                status.setRollbackOnly()
                notFound()
                return
            }

            if(${propertyName}.hasErrors()) {
                status.setRollbackOnly()
                respond ${propertyName}.errors, view:'create' // STATUS CODE 422
                return
            }

            ${propertyName}.save flush:true
            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.created.message', args: [message(code: '${propertyName}.label'), ${propertyName}.id])
                    redirect ${propertyName}
                }
                '*' { respond ${propertyName}, [status: CREATED] }
            }
        }
    }

    /**
     * 編集画面を表示します。
     *
     * @param ${propertyName}
     * @return
     */
    def edit(Long id) {
        ${className}.async.get(id).then { ${propertyName} ->
            respond ${propertyName}
        }
    }

    /**
     * 更新します。
     *
     * @param ${propertyName}
     * @return
     */
    def update(Long id) {
        ${className}.async.withTransaction { TransactionStatus status ->
            def ${propertyName} = ${className}.get(id)
            if (${propertyName} == null) {
                status.setRollbackOnly()
                notFound()
                return
            }

            ${propertyName}.properties = params
            if( !${propertyName}.save(flush:true) ) {
                status.setRollbackOnly()
                respond ${propertyName}.errors, view:'edit' // STATUS CODE 422
                return
            }

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.updated.message', args: [message(code: '${className}.label'), ${propertyName}.id])
                    redirect ${propertyName}
                }
                '*'{ respond ${propertyName}, [status: OK] }
            }
        }
    }

    /**
     * 削除します。
     *
     * @param ${propertyName}
     * @return
     */
    def delete(Long id) {
        ${className}.async.withTransaction { TransactionStatus status ->
            def ${propertyName} = ${className}.get(id)
            if (${propertyName} == null) {
                status.setRollbackOnly()
                notFound()
                return
            }

            ${propertyName}.delete flush:true

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: '${className}.label'), ${propertyName}.id])
                    redirect action:"index", method:"GET"
                }
                '*'{ render status: NO_CONTENT }
            }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: '${propertyName}.label'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
