<dl class="property-list ${domainClass.propertyName}">
  <g:each in="${domainClass.persistentProperties}" var="p">
    <div class="row">
      <dt id="${p.name}-label" class="col-xs-2 property-label"><g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}"/></dt>
      <dd class="col-xs-2 property-value" aria-labelledby="${p.name}-label">${body(p)}</dd>
    </div>
  </g:each>
</dl>
