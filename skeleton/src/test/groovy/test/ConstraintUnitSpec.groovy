package test

import grails.databinding.DataBinder
import grails.databinding.SimpleMapDataBindingSource
import grails.web.databinding.GrailsWebDataBinder
import org.grails.config.CodeGenConfig
import spock.lang.Specification

abstract class ConstraintUnitSpec extends Specification {

    static final String VALID = 'valid'

    DataBinder dataBinder

    def setup() {
        dataBinder = new GrailsWebDataBinder(null)

        // application.ymlでの設定を反映する。
        def config = loadConfig()
        if (config.containsKey('grails.databinding.convertEmptyStringsToNull')) {
            dataBinder.convertEmptyStringsToNull = config['grails.databinding.convertEmptyStringsToNull']
        }
        if (config.containsKey('grails.databinding.trimStrings')) {
            dataBinder.trimStrings = config['grails.databinding.trimStrings']
        }
    }

    void bind(obj, String field, Object value) {
        dataBinder.bind(obj, [(field): value] as SimpleMapDataBindingSource)
    }

    void validateConstraints(obj, String field, String error) {
        def valid = obj.validate()
        if (error && error != VALID) {
            assert !valid
            assert obj.errors[field]
            assert error == obj.errors[field].code
        } else {
            assert !obj.errors[field]
        }
    }

    private static CodeGenConfig loadConfig() {
        def config = new CodeGenConfig()
        def file = new File("grails-app/conf/application.yml")
        if (file.exists()) {
            config.loadYml(file)
            println "Loaded configuration file: $file"
        }
        return config
    }
}
