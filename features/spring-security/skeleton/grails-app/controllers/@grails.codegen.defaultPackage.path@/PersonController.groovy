package @grails.codegen.defaultPackage@

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import grails.util.Holders

import static org.springframework.http.HttpStatus.*

/**
 * {@code Person}管理用のコントローラです。
 */
@Secured(["hasRole('ROLE_ADMIN')"])
@Transactional(readOnly = true)
class PersonController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    /**
     * 一覧画面を表示します。
     *
     * @param max
     * @return
     */
    def index(Integer max) {
        params.max = Math.min(max ?: Holders.config.grails.controller.maxDefault, Holders.config.grails.controller.maxLimit)
        respond Person.list(params), model: [personCount: Person.count()]
    }

    /**
     * 詳細画面を表示します。
     *
     * @param person
     * @return
     */
    def show(Person person) {
        if (person == null) {
            notFound()
            return
        }

        respond person
    }

    /**
     * 新規作成画面を表示します。
     *
     * @return
     */
    def create() {
        respond new Person(params)
    }

    /**
     * 新規作成します。
     *
     * @param person
     * @return
     */
    @Transactional
    def save(Person person) {
        if (person == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (person.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond person.errors, view: 'create'
            return
        }

        person.addToAuthorities(Role.admin).save flush: true

        withFormat {
            json xml { respond person, [status: CREATED] }
            '*' {
                flash.message = message(code: 'default.created.message', args: [message(code: 'person.label')])
                redirect person
            }
        }
    }

    /**
     * 編集画面を表示します。
     *
     * @param person
     * @return
     */
    def edit(Person person) {
        if (person == null) {
            notFound()
            return
        }

        respond person
    }

    /**
     * 更新します。
     *
     * @param person
     * @return
     */
    @Transactional
    def update(Person person) {
        if (person == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (person.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond person.errors, view: 'edit'
            return
        }

        person.save flush: true

        withFormat {
            json xml { respond person, [status: OK] }
            '*' {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'person.label')])
                redirect person
            }
        }
    }

    /**
     * 削除します。
     * <p>
     * ただし、最後の管理者ユーザの場合はその後の運用が不可能になるため削除できません。
     *
     * @param person
     * @return
     */
    @Transactional
    def delete(Person person) {
        if (person == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (person.admin && lastAdminPerson) {
            transactionStatus.setRollbackOnly()
            def model = [person: person, errorMessage: message(code: 'person.not.deleted.error')]
            withFormat {
                json xml { respond(model, status: BAD_REQUEST) }
                '*' { render view: 'show', model: model }
            }
            return
        }

        person.delete flush: true

        withFormat {
            json xml { render status: NO_CONTENT }
            '*' {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'person.label')])
                redirect action: "index", method: "GET"
            }
        }
    }

    private boolean isLastAdminPerson() {
        Person.list(lock: true).count { it.admin } == 1
    }

    protected void notFound() {
        withFormat {
            json xml { respond([:], status: NOT_FOUND) }
            '*' {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'person.label'), params.id])
                redirect action: "index", method: "GET"
            }
        }
    }
}
