<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main"/>
  <g:set var="entityName" value="${message(code: 'person.label')}"/>
  <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<g:render template="/templates/toolbar"/>

<div id="create-person" class="content scaffold-create" role="main">
  <h1 class="page-header"><g:message code="default.create.label" args="[entityName]"/></h1>

  <g:render template="/templates/messages" model="[bean: person]"/>

  <g:form action="save" method="POST">
    <fieldset class="form">
      <g:render template="/templates/singleBean" model="${[bean: person, properties: ['loginName', 'realName', 'password', 'passwordToConfirm'], editable: true]}"/>
    </fieldset>

    <fieldset class="buttons">
      <div class="col-xs-offset-2 col-xs-10">
        <g:submitButton name="create" class="save btn btn-primary" value="${message(code: 'default.button.create.label')}"/>
      </div>
    </fieldset>
  </g:form>
</div>
</body>
</html>
