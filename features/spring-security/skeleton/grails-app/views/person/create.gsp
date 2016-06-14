<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}"/>
        <title>
            <g:message code="default.create.label" args="[entityName]"/>
        </title>
    </head>
    <body>
        <div id="create-person" class="content scaffold-create" role="main">
            <h1 class="page-header">
                <g:message code="default.create.label" args="[entityName]"/>
            </h1>

            <g:hasErrors bean="${this.person}">
                <div class="alert alert-danger" role="alert">
                    <ul>
                        <g:eachError bean="${this.person}" var="error">
                            <li><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </div>
            </g:hasErrors>

            <g:form action="save" class="form-horizontal">
                <div class="form-group">
                    <label for="loginName" class="col-md-2 control-label"><g:message code="person.loginName.label"/></label>
                    <div class="col-sm-4">
                        <g:textField name="loginName" class="form-control" id="loginName" value="${params.loginName}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="realName" class="col-md-2 control-label"><g:message code="person.realName.label"/></label>
                    <div class="col-sm-4">
                        <g:textField name="realName" class="form-control" id="realName" value="${params.realName}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-md-2 control-label"><g:message code="person.password.label"/></label>
                    <div class="col-sm-4">
                        <g:passwordField name="password" class="form-control" id="password" value="${params.password}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="passwordToConfirm" class="col-md-2 control-label"><g:message code="person.passwordToConfirm.label"/></label>
                    <div class="col-sm-4">
                        <g:passwordField name="passwordToConfirm" class="form-control" id="passwordToConfirm" value="${params.passwordToConfirm}"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-7">
                        <g:submitButton name="create" class="btn btn-primary" value="${message(code: 'default.button.create.label', default: 'Create')}"/>
                    </div>
                </div>
            </g:form>
        </div>
    </body>
</html>
