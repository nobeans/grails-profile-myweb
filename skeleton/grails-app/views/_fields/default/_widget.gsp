<g:if test="${required}">
  <g:textField id="${property}" name="${property}" class="form-control" value="${value}" required=""/>
</g:if>
<g:else>
  <g:textField id="${property}" name="${property}" class="form-control" value="${value}"/>
</g:else>
