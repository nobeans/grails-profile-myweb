<div class="form-group">
  <label for="${property}" class="col-xs-2 control-label"><g:message code="${org.apache.commons.lang.StringUtils.uncapitalize(bean.class.simpleName)}.${property}.label"/></label>
  <div class="col-xs-4">
    <f:widget bean="${bean}" property="${property}" class="form-control"/>
  </div>
</div>
