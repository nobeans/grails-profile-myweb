<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main"/>
  <g:set var="entityName" value="\${message(code: '${propertyName}.label')}"/>
  <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<g:render template="/templates/toolbar"/>

<div id="create-${propertyName}" class="content scaffold-create" role="main">
  <h1 class="page-header"><g:message code="default.create.label" args="[entityName]"/></h1>

  <g:render template="/templates/messages" model="[bean: ${propertyName}]"/>

  <g:form action="save" method="POST">
    <fieldset class="form">
      <g:render template="/templates/singleBean" model="\${[bean: ${propertyName}, editable: true]}"/>
    </fieldset>

    <fieldset class="buttons">
      <div class="col-xs-offset-3 col-xs-9">
        <g:submitButton name="create" class="save btn btn-primary" value="\${message(code: 'default.button.create.label')}"/>
      </div>
    </fieldset>
  </g:form>
</div>
</body>
</html>
