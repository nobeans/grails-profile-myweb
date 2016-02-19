import grails.util.Environment

//
// Appenders
//

def FILE_LOG_PATTERN = '%d{yyyy-MM-dd HH:mm:ss.SSS}\t%thread\t%level\t%logger:%line\t%m%n'
def CONSOLE_LOG_PATTERN = /%d{HH:mm:ss.SSS} [%thread] %highlight(%level) %cyan(\(%logger{39}:%line\)) %m%n/

def logDir = 'logs'

appender('STDOUT', ConsoleAppender) {
    withJansi = true
    encoder(PatternLayoutEncoder) {
        pattern = CONSOLE_LOG_PATTERN
    }
}

appender("FILE", RollingFileAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = FILE_LOG_PATTERN
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${logDir}/application.%d{yyyy-MM-dd}.log"
    }
}

appender("FULL_STACKTRACE", RollingFileAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = FILE_LOG_PATTERN
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${logDir}/stacktrace.%d{yyyy-MM-dd}.log"
    }
}


//
// Helpers
//

def error = { logger it, ERROR, ['STDOUT', 'FILE'], false }
def warn =  { logger it, WARN,  ['STDOUT', 'FILE'], false }
def info =  { logger it, INFO,  ['STDOUT', 'FILE'], false }
def debug = { logger it, DEBUG, ['STDOUT', 'FILE'], false }
def trace = { logger it, TRACE, ['STDOUT', 'FILE'], false }


//
// Configurations
//

root INFO, ['STDOUT', 'FILE']

logger "StackTrace", ERROR, ['FULL_STACKTRACE'], false

warn 'org.grails'
info 'grails.app'
warn 'grails.plugin'
warn 'grails.plugins'
warn 'org.springframework'
warn 'org.hibernate'
error 'net.sf.ehcache.hibernate'
error 'org.hibernate.hql.internal.ast.HqlSqlWalker'
error 'net.sf.ehcache.config.ConfigurationFactory'
warn 'org.apache.coyote'
warn 'org.apache.tomcat'
warn 'org.apache.catalina'
//info 'YOUR_APP_PACKAGE' // FIXME

Environment.executeForCurrentEnvironment {
    development {
        //debug 'YOUR_APP_PACKAGE' // FIXME
        //debug 'grails.app.controllers.YOUR_APP_PACKAGE' // FIXME
        trace 'org.hibernate.type.descriptor.sql.BasicBinder'
        trace 'org.hibernate.type.EnumType'
        debug 'org.hibernate.SQL'
        debug 'groovy.sql.Sql'
        trace 'grails.app.controllers.request.tracelog.RequestTracelogInterceptor'
    }
    test {
        //debug 'YOUR_APP_PACKAGE' // FIXME
        //debug 'grails.app.controllers.YOUR_APP_PACKAGE' // FIXME
        trace 'org.hibernate.type.descriptor.sql.BasicBinder'
        trace 'org.hibernate.type.EnumType'
        debug 'org.hibernate.SQL'
        debug 'groovy.sql.Sql'
    }
}