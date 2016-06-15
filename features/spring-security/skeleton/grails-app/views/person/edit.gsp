<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main"/>
  <g:set var="entityName" value="${message(code: 'person.label')}"/>
  <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<nav class="navbar toolbar">
  <div class="navbar-text navbar-left">
    <g:link class="list" action="index"><span class="fa fa-angle-double-left">&nbsp;</span><g:message code="default.list.label" args="[entityName]"/></g:link>
  </div>
</nav>

<div id="edit-person" class="content scaffold-edit" role="main">
  <h1 class="page-header"><g:message code="default.edit.label" args="[entityName]"/></h1>

  <g:render template="/templates/messages" model="[bean: person]"/>

  <g:form resource="${person}" method="PUT">
    <g:hiddenField name="version" value="${person?.version}"/>

    <fieldset class="form">
      <g:render template="/templates/singleBean" model="${[bean: person, properties: ['realName', 'loginName', 'password', 'passwordToConfirm'], editable: true]}"/>
    </fieldset>

    <fieldset class="buttons">
      <div class="col-xs-offset-2 col-xs-10">
        <input class="save btn btn-primary" type="submit" value="${message(code: 'default.button.update.label')}"/>
      </div>
    </fieldset>
  </g:form>
</div>
</body>
</html>
