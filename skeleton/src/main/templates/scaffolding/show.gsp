<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main"/>
  <g:set var="entityName" value="\${message(code: '${propertyName}.label')}"/>
  <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<g:render template="/templates/toolbar"/>

<div id="show-${propertyName}" class="content scaffold-single scaffold-show" role="main">
  <h1 class="page-header"><g:message code="default.show.label" args="[entityName]"/></h1>

  <g:render template="/templates/messages"/>

  <g:render template="/templates/singleBean" model="\${[bean: ${propertyName}, editable: false]}"/>

  <g:form resource="\${${propertyName}}" method="DELETE">
    <fieldset class="buttons">
      <div class="col-xs-offset-3 col-xs-9">
        <g:link class="edit btn btn-primary" action="edit" resource="\${${propertyName}}"><g:message code="default.button.edit.label"/></g:link>
        <input class="delete btn btn-danger" type="submit" value="\${message(code: 'default.button.delete.label')}" onclick="return confirm('\${message(code: 'default.button.delete.confirm.message')}');" />
      </div>
    </fieldset>
  </g:form>
</div>
</body>
</html>
