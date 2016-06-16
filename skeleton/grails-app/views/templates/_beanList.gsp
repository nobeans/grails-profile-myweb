<g:if test="${collection}">

  <g:set var="domainProperties" value="${properties ?: @grails.codegen.defaultPackage@.common.DomainClassUtil.getConstrainedPropertyNames(collection.first())}"/>
  <g:set var="domainClassName" value="${@grails.codegen.defaultPackage@.common.DomainClassUtil.getDomainClassPropertyName(collection.first())}"/>

  <table class="table table-striped table-hover">
    <thead>
    <tr>
      <g:each in="${domainProperties}" var="propName" status="i">
        <g:sortableColumn property="${propName}" title="${message(code: "${domainClassName}.${propName}.label")}"/>
      </g:each>
    </tr>
    </thead>
    <tbody>
    <g:each in="${collection}" var="bean" status="i">
      <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
        <g:each in="${domainProperties}" var="propName" status="j">
          <g:if test="${j == 0}">
            <td><g:link method="GET" resource="${bean}"><f:displayWidget bean="${bean}" property="${propName}"/></g:link></td>
          </g:if>
          <g:else>
            <td><f:displayWidget bean="${bean}" property="${propName}"/></td>
          </g:else>
        </g:each>
      </tr>
    </g:each>
    </tbody>
  </table>

</g:if>
