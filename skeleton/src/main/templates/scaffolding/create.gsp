<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main"/>
  <g:set var="entityName" value="\${message(code: '${propertyName}.label')}"/>
  <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav" role="navigation">
  <ul>
    <li><a class="home" href="\${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
    <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
  </ul>
</div>

<div id="create-${propertyName}" class="content scaffold-create" role="main">
  <h1><g:message code="default.create.label" args="[entityName]"/></h1>

  <g:render template="/messages" model="[bean: this.${propertyName}]"/>

  <g:form action="save">
    <fieldset class="form">
      <f:all bean="${propertyName}"/>
    </fieldset>
    <fieldset class="buttons">
      <g:submitButton name="create" class="save" value="\${message(code: 'default.button.create.label')}"/>
    </fieldset>
  </g:form>
</div>
</body>
</html>
