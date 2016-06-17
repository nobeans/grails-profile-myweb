<!doctype html>
<html>
<head>
  <title><g:message code="default.error.label"/><g:if env="development">(for Development)</g:if></title>
  <meta name="layout" content="main">
  <g:if env="development"><asset:stylesheet src="errors.css"/></g:if>
</head>

<body>
<div class="error">
  <g:if env="development">
    <g:if test="${Throwable.isInstance(exception)}">
      <g:renderException exception="${exception}"/>
    </g:if>
    <g:elseif test="${request.getAttribute('javax.servlet.error.exception')}">
      <g:renderException exception="${request.getAttribute('javax.servlet.error.exception')}"/>
    </g:elseif>
    <g:else>
      <h1><g:message code="default.error.label"/></h1>
      <ul class="errors">
        <g:if test="${status}"><li>Status: ${status}</li></g:if>
        <g:if test="${message}"><li>Message: ${message}</li></g:if>
        <g:if test="${path}"><li>Path: ${path}</li></g:if>
        <g:if test="${exception}"><li>Exception: ${exception}</li></g:if>
      </ul>
    </g:else>
  </g:if>
  <g:else>
    <h1><g:message code="default.error.label"/></h1>
    <ul class="errors">
      <g:if test="${status}"><li>Status: ${status}</li></g:if>
      <g:if test="${message}"><li>Message: ${message}</li></g:if>
    </ul>
  </g:else>
</div>
</body>
</html>
