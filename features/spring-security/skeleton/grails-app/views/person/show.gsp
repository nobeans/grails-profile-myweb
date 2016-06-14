<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <h1 class="page-header"><g:message code="default.show.label" args="[entityName]"/></h1>
        <g:render template="/messages"/>
        <br>
        <div>
            <dl class="dl-horizontal">
                <dt><g:message code="person.loginName.label"/></dt>
                <dd>${person?.loginName}</dd>
                <dt><g:message code="person.realName.label"/></dt>
                <dd>${person?.realName}</dd>
                <dt><g:message code="person.dateCreated.label"/></dt>
                <dd><g:formatDate date="${person?.dateCreated}"/></dd>
            </dl>
            <g:form resource="${this.person}" method="DELETE">
                <div class="form-group">
                    <div class="col-sm-10">
                        <g:link class="edit btn btn-primary" action="edit" resource="${this.person}"><g:message code="default.button.edit.label" default="Edit"/></g:link>
                        <input class="delete btn btn-danger" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
                    </div>
                </div>
            </g:form>
        </div>
    </body>
</html>
