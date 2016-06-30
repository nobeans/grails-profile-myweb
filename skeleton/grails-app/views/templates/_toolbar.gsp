<nav class="navbar toolbar">
  <g:if test="${actionName != 'index'}">
    <div class="navbar-text navbar-left">
      <g:link class="list" action="index"><span class="fa fa-angle-double-left">&nbsp;</span><g:message code="default.list.label" args="[entityName]"/></g:link>
    </div>
  </g:if>
  <g:if test="${!(actionName in ['create', 'save', 'edit', 'update'])}">
    <div class="navbar-form navbar-right">
      <g:link class="create btn btn-primary" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link>
    </div>
  </g:if>
</nav>
