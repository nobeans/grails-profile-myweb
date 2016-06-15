<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main"/>
  <g:set var="entityName" value="${message(code: 'person.label')}"/>
  <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<g:render template="/templates/toolbar"/>

<div id="list-person" class="content scaffold-list" role="main">
  <h1 class="page-header"><g:message code="default.list.label" args="[entityName]"/></h1>

  <g:render template="/templates/messages"/>

  <g:render template="/templates/beanList" model="${[collection: personList, properties: ['loginName', 'realName', 'dateCreated']]}"/>

  <nav class="text-center">
    <ul class="pagination">
      <g:paginate total="${personCount ?: 0}"/>
    </ul>
  </nav>
</div>
</body>
</html>
