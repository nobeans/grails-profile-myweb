<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main"/>
  <g:set var="entityName" value="${message(code: 'person.label')}"/>
  <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div class="row">
  <div class="col-sm-16 col-md-16">
    <a href="${createLink(controller: 'person', action: 'create')}" class="btn btn-primary pull-right">
      <g:message code="default.new.label" args="[message(code: 'person.label')]"/>
    </a>
  </div>
</div>

<div class="row">
  <div class="col-sm-16 col-md-16">
    <h1 class="page-header"><g:message code="default.list.label" args="[message(code: 'person.label')]"/></h1>

    <g:render template="/messages"/>

    <table class="table table-striped table-hover">
      <thead>
      <tr>
        <g:sortableColumn property="loginName" titleKey="person.loginName.label"/>
        <g:sortableColumn property="realName" titleKey="person.realName.label"/>
        <g:sortableColumn property="dateCreated" titleKey="person.dateCreated.label"/>
      </tr>
      </thead>
      <tbody>
      <g:each var="person" in="${personList}">
        <tr>
          <td><a href="${createLink(controller: 'person', action: 'show', params: [id: person.id])}">${person.loginName}</a></td>
          <td>${person.realName}</td>
          <td><g:formatDate date="${person.dateCreated}"/></td>
        </tr>
      </g:each>
      </tbody>
    </table>
    <nav class="text-center">
      <ul class="pagination">
        <g:paginate total="${personCount ?: 0}"/>
      </ul>
    </nav>
  </div>
</div>
</body>
</html>
