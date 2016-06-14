package @grails.codegen.defaultPackage@

import grails.transaction.Transactional

@Transactional(readOnly = true)
class PersonController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Person.list(params), model: [personCount: Person.count()]
    }

    def show(Person person) {
        if (person == null) {
            notFound()
            return
        }

        respond person
    }

    def create() {
        respond new Person(params)
    }

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

    def edit(Person person) {
        respond person
    }

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
