<g:if test="${flash.message}">
    <div class="alert alert-success" role="alert">${flash.message}</div>
</g:if>
<g:if test="${flash.errorMessage}">
    <div class="alert alert-danger" role="alert">${flash.errorMessage}</div>
</g:if>
<g:if test="${flash.errors}">
    <div class="alert alert-danger" role="alert">
        <g:each in="${flash.errors}" var="error">
            <li><g:message error="${error}"/></li>
        </g:each>
    </div>
</g:if>
<g:if test="${errorMessage}">
    <div class="alert alert-danger" role="status">${errorMessage}</div>
</g:if>
