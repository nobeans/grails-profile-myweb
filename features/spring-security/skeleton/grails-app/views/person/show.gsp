<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main"/>
  <g:set var="entityName" value="${message(code: 'person.label')}"/>
  <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<nav class="navbar toolbar">
  <div class="navbar-text navbar-left">
    <g:link class="list" action="index"><span class="fa fa-angle-double-left">&nbsp;</span><g:message code="default.list.label" args="[entityName]"/></g:link>
  </div>
  <div class="navbar-form navbar-right">
    <g:link class="create" action="create" class="btn btn-primary"><g:message code="default.new.label" args="[entityName]"/></g:link>
  </div>
</nav>

<div id="show-person" class="content scaffold-show" role="main">
  <h1 class="page-header"><g:message code="default.show.label" args="[entityName]"/></h1>

  <g:render template="/messages"/>

  <%-- 現状でf:displayではorder, except, propertiesは使えない --%>
  <f:display bean="person" order="realName, loginName, dateCreated"/>

  <g:form resource="${this.person}" method="DELETE">
    <fieldset class="buttons">
      <div class="col-xs-offset-2 col-xs-10">
        <g:link class="edit btn btn-primary" action="edit" resource="${this.person}"><g:message code="default.button.edit.label"/></g:link>
        <input class="delete btn btn-danger" type="submit" value="${message(code: 'default.button.delete.label')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message')}');" />
      </div>
    </fieldset>
  </g:form>
</div>
</body>
</html>
