package @grails.codegen.defaultPackage@.common

import grails.artefact.DomainClass
import grails.core.GrailsDomainClassProperty
import grails.util.Holders
import grails.validation.Validateable
import org.apache.commons.lang.StringUtils
import org.grails.validation.ConstrainedPropertyBuilder
import org.grails.validation.DomainClassPropertyComparator

import static grails.util.GrailsClassUtils.getStaticPropertyValue

/**
 * ドメインクラスに関するユーティリティクラスです。
 * <p>
 * コマンドオブジェクトもサポートしています。
 */
class DomainClassUtil {

    /**
     * 制約が適用されたプロパティ名群を返します。
     *
     * @param domainClass
     * @return
     */
    static List<String> getConstrainedPropertyNames(domainClass) {
        if (domainClass instanceof DomainClass) {
            return resolvePersistentProperties(domainClass.class).collect { it.name }
        }
        if (domainClass instanceof Validateable) {
            return new ConstrainedPropertyBuilder(domainClass).getConstrainedProperties().collect { it.key }
        }
        throw new IllegalArgumentException("ドメインクラスまたはコマンドオブジェクトではありません。: ${domainClass.class}")
    }

    /**
     * ドメインクラスのプロパティ方式における名前を返します。
     *
     * @param domainClass
     * @return
     */
    static String getDomainClassPropertyName(Object domainClass) {
        if (domainClass instanceof DomainClass || domainClass instanceof Validateable) {
            return StringUtils.uncapitalize(domainClass.class.simpleName)
        }
        throw new IllegalArgumentException("ドメインクラスまたはコマンドオブジェクトではありません。: ${domainClass.class}")
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
