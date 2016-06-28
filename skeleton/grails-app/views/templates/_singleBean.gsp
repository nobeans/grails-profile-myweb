<%
  if (!bean) {
    throw new IllegalArgumentException("対象のドメインクラスインスタンスをbeanに指定してください。")
  }
  if (editable == null) {
    throw new IllegalArgumentException("参照用ならばfalse、編集用ならばtrueをeditableに指定してください。")
  }
%>

<g:set var="domainProperties" value="${properties ?: @grails.codegen.defaultPackage@.common.DomainClassUtil.getConstrainedPropertyNames(bean)}"/>
<g:set var="domainClassName" value="${@grails.codegen.defaultPackage@.common.DomainClassUtil.getDomainClassPropertyName(bean)}"/>

<div class="form-horizontal">

  <g:each in="${domainProperties}" var="propName">
    <div class="form-group">
      <label for="${propName}" class="col-xs-3 control-label"><g:message code="${domainClassName}.${propName}.label"/></label>

      <div class="col-xs-9">
        <g:if test="${Boolean.valueOf(editable)}">
          <f:widget bean="${bean}" property="${propName}" class="form-control"/>
        </g:if>
        <g:else>
          <span class="form-control display"><f:displayWidget bean="${bean}" property="${propName}"/></span>
        </g:else>
      </div>
    </div>
  </g:each>

</div>
