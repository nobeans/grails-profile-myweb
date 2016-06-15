<%
  if (!bean) {
    throw new IllegalArgumentException("対象のドメインクラスインスタンスをbeanに指定してください。")
  }
  if (editable == null) {
    throw new IllegalArgumentException("参照用ならばfalse、編集用ならばtrueをeditableに指定してください。")
  }
%>

<g:set var="domainProperties" value="${properties ?: @grails.codegen.defaultPackage@.common.DomainClassUtil.getConstrainedPropertyNames(bean, actionName)}"/>
<g:set var="domainClassName" value="${@grails.codegen.defaultPackage@.common.DomainClassUtil.getDomainClassPropertyName(bean)}"/>

<div class="form-horizontal">

  <g:each in="${domainProperties}" var="propName">
    <div class="form-group">
      <label for="${propName}" class="col-xs-2 control-label"><g:message code="${domainClassName}.${propName}.label"/></label>

      <div class="col-xs-4">
        <g:if test="${Boolean.valueOf(editable)}">
          <f:widget bean="${bean}" property="${propName}"/>
        </g:if>
        <g:else>
          <f:displayWidget bean="${bean}" property="${propName}"/>
        </g:else>
      </div>
    </div>
  </g:each>

</div>
