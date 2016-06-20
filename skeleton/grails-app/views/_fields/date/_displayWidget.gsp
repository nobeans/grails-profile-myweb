<g:set var="formatPattern" value="${@grails.codegen.defaultPackage@.common.DomainClassUtil.getDateFormat(bean.class, property)}"/>
<g:set var="formattedDate" value="${formatDate(date: value, format: formatPattern)}"/>
${formattedDate}

