// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require webjars/jquery/2.2.1/jquery.min
//= require webjars/bootstrap/3.3.6/js/bootstrap.min
//= require_tree global
//= require_self

(function ($) {
    // 最初の入力要素にフォーカスをあてる。
    $('input:visible[type!="button"][type!="submit"],textarea:visible').first().focus();
})(jQuery);
