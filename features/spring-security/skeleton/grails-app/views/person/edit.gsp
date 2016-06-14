<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <g:set var="entityName" value="${message(code: 'person.label')}"/>
        <title>
            <g:message code="default.edit.label" args="[entityName]"/>
        </title>
    </head>
    <body>
        <div id="edit-person" class="content scaffold-edit" role="main">
            <h1 class="page-header">
                <g:message code="default.edit.label" args="[entityName]"/>
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

            <g:form resource="${this.person}" method="PUT" class="form-horizontal">
                <g:hiddenField name="version" value="${this.person?.version}"/>
                <div class="form-group">
                    <label for="loginName" class="col-md-2 control-label"><g:message code="person.loginName.label"/></label>
                    <div class="col-sm-4">
                        <g:textField name="loginName" class="form-control" id="loginName" value="${person.loginName}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="realName" class="col-md-2 control-label"><g:message code="person.realName.label"/></label>
                    <div class="col-sm-4">
                        <g:textField name="realName" class="form-control" id="realName" value="${person.realName}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-md-2 control-label"><g:message code="person.password.label"/></label>
                    <div class="col-sm-4">
                        <g:passwordField name="password" class="form-control" id="password" value="${person.password}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="passwordToConfirm" class="col-md-2 control-label"><g:message code="person.passwordToConfirm.label"/></label>
                    <div class="col-sm-4">
                        <g:passwordField name="passwordToConfirm" class="form-control" id="passwordToConfirm" value="${person.passwordToConfirm}"/>
                    </div>
                </div>
                <fieldset class="buttons">
                    <input class="save btn btn-primary" type="submit"
                           value="${message(code: 'default.button.edit.label')}"/>
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
