<!doctype html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title><g:message code="app.title" args="[layoutTitle(default: message(code: 'app.name'))]"/></title>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <asset:stylesheet src="application.css"/>
  <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
  <g:layoutHead/>
</head>

<body>
<header class="navbar navbar-fixed-top">
  <div class="container-fluid">
    <g:render template="/layouts/header"/>
  </div>
</header>

<article>
  <div class="container-fluid">
    <g:layoutBody/>
  </div>
</article>

<footer class="navbar navbar-fixed-bottom">
  <div class="container-fluid">
    <g:render template="/layouts/footer"/>
  </div>
</footer>

<asset:javascript src="application.js"/>
</body>
</html>
