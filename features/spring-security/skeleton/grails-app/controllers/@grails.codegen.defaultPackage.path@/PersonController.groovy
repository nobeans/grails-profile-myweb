package @grails.codegen.defaultPackage@

import grails.transaction.Transactional
import grails.util.Holders

/**
 * {@code Person}管理用のコントローラです。
 */
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
        if (person.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond person.errors, view: 'create'
            return
        }

        person.addToAuthorities(Role.admin).save flush: true

        flash.message = message(code: 'default.created.message', args: [message(code: 'person.label'), person.loginName])
        redirect person
    }

    /**
     * 編集画面を表示します。
     *
     * @param person
     * @return
     */
    def edit(Person person) {
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

        flash.message = message(code: 'default.updated.message', args: [message(code: 'person.label'), person.loginName])
        redirect person
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
            render view: 'show', model: [person: person, errorMessage: message(code: 'person.not.deleted.error')]
            return
        }

        person.delete flush: true

        flash.message = message(code: 'default.deleted.message', args: [message(code: 'person.label'), person.loginName])
        redirect action: "index", method: "GET"
    }

    private boolean isLastAdminPerson() {
        Person.list(lock: true).count { it.admin } == 1
    }

    protected void notFound() {
        flash.errorMessage = message(code: 'default.not.found.message', args: [message(code: 'person.label'), params.id])
        redirect action: 'index'
    }
}
