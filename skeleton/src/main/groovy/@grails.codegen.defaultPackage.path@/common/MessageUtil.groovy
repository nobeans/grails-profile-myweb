package @grails.codegen.defaultPackage@.common

import grails.util.Holders
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.context.NoSuchMessageException
import org.springframework.validation.ObjectError

/**
 * Grailsのi18n機構によるメッセージ解決をどこからでも使えるようにするためのユーティリティクラスです。
 */
class MessageUtil {

    /**
     * エラーメッセージを返します。
     *
     * @param error
     */
    static String message(ObjectError error) {
        try {
            def messageSource = Holders.applicationContext.messageSource
            return messageSource.getMessage(error, locale)
        } catch (NoSuchMessageException e) {
            // キーに対応するメッセージが存在しない場合は例外がスローされる。
            // エラーのデフォルトメッセージ文字列を返す。
            return error.defaultMessage
        }
    }

    /**
     * コードに対応するメッセージを返します。
     *
     * @param code
     * @param args
     * @param defaultMessage
     */
    static String message(String code, List args, String defaultMessage = null) {
        try {
            def messageSource = Holders.applicationContext.messageSource
            return messageSource.getMessage(code, args as Object[], locale)
        } catch (NoSuchMessageException e) {
            // キーに対応するメッセージが存在しない場合は例外がスローされる。
            // ここでは、g:messageと同様にキーそのものを返す。
            // ただし、デフォルトメッセージが指定されている場合は、指定された文字列を返す。
            return defaultMessage ?: code
        }
    }

    /** エンドユーザから指定されたロケールを返します。langリクエストパラメータにも対応しています。*/
    private static Locale getLocale() {
        GrailsWebRequest.lookup()?.locale ?: Locale.default
    }
}
