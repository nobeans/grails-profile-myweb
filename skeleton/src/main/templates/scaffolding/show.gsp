<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main"/>
  <g:set var="entityName" value="\${message(code: '${propertyName}.label')}"/>
  <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav" role="navigation">
  <ul>
    <li><a class="home" href="\${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
    <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
    <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link></li>
  </ul>
</div>

<div id="show-${propertyName}" class="content scaffold-show" role="main">
  <h1><g:message code="default.show.label" args="[entityName]"/></h1>

  <g:render template="/messages"/>

  <f:display bean="${propertyName}"/>
  <g:form resource="\${this.${propertyName}}" method="DELETE">
    <fieldset class="buttons">
      <g:link class="edit" action="edit" resource="\${this.${propertyName}}"><g:message code="default.button.edit.label"/></g:link>
      <input class="delete" type="submit" value="\${message(code: 'default.button.delete.label')}" onclick="return confirm('\${message(code: 'default.button.delete.confirm.message')}');" />
    </fieldset>
  </g:form>
</div>
</body>
</html>
