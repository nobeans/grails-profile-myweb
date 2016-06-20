<g:set var="formatPattern" value="${@grails.codegen.defaultPackage@.common.DomainClassUtil.getDateFormat(bean.class, property)}"/>
<g:set var="isOnlyDate" value="${formatPattern ==~ '[yMd/ -]+'}"/>
<g:set var="formattedDate" value="${formatDate(date: value, format: formatPattern)}"/>
<g:if test="${isOnlyDate}">
  <div class="input-group date">
    <span class="input-group-addon input-group-calendar"><i class="fa fa-calendar"></i></span>
    <g:if test="${required}">
      <g:textField id="${property}" name="${property}" class="form-control datepicker" value="${params[property] ?: formattedDate}" data-format="${formatPattern}" required=""/>
    </g:if>
    <g:else>
      <g:textField id="${property}" name="${property}" class="form-control datepicker" value="${params[property] ?: formattedDate}" data-format="${formatPattern}"/>
    </g:else>
  </div>
</g:if>
<g:else>
  <g:if test="${required}">
    <g:textField id="${property}" name="${property}" class="form-control datetime" value="${params[property] ?: formattedDate}" placeholder="${message(code: 'default.placeholder.format', args: [new Date().format(formatPattern)])}" required=""/>
  </g:if>
  <g:else>
    <g:textField id="${property}" name="${property}" class="form-control datetime" value="${params[property] ?: formattedDate}" placeholder="${message(code: 'default.placeholder.format', args: [new Date().format(formatPattern)])}"/>
  </g:else>
</g:else>
