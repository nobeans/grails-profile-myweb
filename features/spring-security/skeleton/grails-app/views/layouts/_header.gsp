<g:link uri="/" class="navbar-brand"><g:message code="app.name"/></g:link>
<sec:ifLoggedIn>
  <div class="navbar-form navbar-right header__menu">
    <span class="header__user-name"><span class="fa fa-user"></span>&nbsp;<sec:loggedInUserInfo field="username"/></span>
    <g:form controller="logout" class="logout-form">
      <button type="submit" class="btn btn-inverted-white btn-default logout-button" title="${message(code: 'springSecurity.logout.tooltip.label')}">
        <span class="fa fa-sign-out"/>
      </button>
    </g:form>
  </div>
</sec:ifLoggedIn>
