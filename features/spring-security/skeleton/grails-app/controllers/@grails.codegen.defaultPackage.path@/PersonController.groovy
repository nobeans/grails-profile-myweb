package @grails.codegen.defaultPackage@

import grails.transaction.Transactional

/**
 * {@code Person}管理用のコントローラです。
 */
@Transactional(readOnly = true)
class PersonController {

    static scaffold = Person

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

        if (lastAdminPerson) {
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
