package @grails.codegen.defaultPackage@.common

import grails.artefact.DomainClass
import grails.core.GrailsDomainClassProperty
import grails.databinding.BindingFormat
import grails.util.Holders
import grails.validation.Validateable
import org.apache.commons.lang.StringUtils
import org.grails.validation.ConstrainedPropertyBuilder
import org.grails.validation.DomainClassPropertyComparator

import static grails.util.GrailsClassUtils.*

/**
 * ドメインクラスに関するユーティリティクラスです。
 * <p>
 * コマンドオブジェクトもサポートしています。
 */
class DomainClassUtil {

    /**
     * 制約が適用されたプロパティ名群を返します。
     *
     * @param domainClassInstance
     * @return
     */
    static List<String> getConstrainedPropertyNames(Object domainClassInstance) {
        if (domainClassInstance instanceof DomainClass) {
            return resolvePersistentProperties(domainClassInstance.class).collect { it.name }
        }
        if (domainClassInstance instanceof Validateable) {
            return new ConstrainedPropertyBuilder(domainClassInstance).getConstrainedProperties().collect { it.key }
        }
        throw new IllegalArgumentException("ドメインクラスまたはコマンドオブジェクトではありません。: domainClass=${domainClassInstance.class}")
    }

    /**
     * ドメインクラスのプロパティ方式における名前を返します。
     *
     * @param domainClassInstance
     * @return
     */
    static String getDomainClassPropertyName(Object domainClassInstance) {
        if (domainClassInstance instanceof DomainClass || domainClassInstance instanceof Validateable) {
            return StringUtils.uncapitalize(domainClassInstance.class.simpleName)
        }
        throw new IllegalArgumentException("ドメインクラスまたはコマンドオブジェクトではありません。: domainClass=${domainClassInstance.class}")
    }

    /**
     * ドメインクラスの{@java.util.Date}型のプロパティに対するフォーマット形式を返します。
     *
     * @param domainClass
     * @param propertyName {@java.util.Date}型のプロパティ名
     * @return
     */
    static String getDateFormat(Class domainClass, String propertyName) {
        def field = domainClass.getDeclaredField(propertyName)
        if (!field) {
            throw new IllegalArgumentException("指定されたプロパティは存在しません。: domainClass=${domainClass}, propertyName=${propertyName}")
        }
        if (field.type != Date) {
            throw new IllegalArgumentException("指定されたプロパティはjava.util.Date型ではありません。: domainClass=${domainClass}, propertyName=${propertyName}, type=${field.type}")
        }

        def bindingFormat = field.getAnnotation(BindingFormat)
        if (bindingFormat?.value()) {
            return bindingFormat.value()
        }
        if (bindingFormat?.code()) {
            return MessageUtil.getMessage(bindingFormat.code())
        }
        return MessageUtil.getMessage('default.date.format')
    }

    private static List<GrailsDomainClassProperty> resolvePersistentProperties(Class clazz) {
        def domainClass = Holders.grailsApplication.getDomainClass(clazz.name)
        def properties = domainClass.persistentProperties as List
        def blacklist = ['dateCreated', 'lastUpdated']
        def scaffoldProp = getStaticPropertyValue(domainClass.clazz, 'scaffold')
        if (scaffoldProp) {
            blacklist.addAll(scaffoldProp.exclude)
        }
        properties.removeAll { it.name in blacklist }
        properties.removeAll { !it.domainClass.constrainedProperties[it.name]?.display }
        properties.removeAll { it.derived }

        Collections.sort(properties, new DomainClassPropertyComparator(domainClass))
        return properties
    }
}
