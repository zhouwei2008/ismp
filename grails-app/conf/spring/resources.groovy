// Place your Spring DSL code here
import org.apache.commons.dbcp.BasicDataSource
import org.codehaus.groovy.grails.commons.ConfigurationHolder

beans = {
    dataSource_ismp(BasicDataSource) { bean ->
        bean.destroyMethod = 'close'
        driverClassName=ConfigurationHolder.config.dataSource.ismp.driverClassName
        url=ConfigurationHolder.config.dataSource.ismp.url
        username=ConfigurationHolder.config.dataSource.ismp.username
        password=DESCodec.decode(ConfigurationHolder.config.dataSource.ismp.password)

        maxActive =ConfigurationHolder.config.dataSource.dbcp.maxActive
        maxIdle =ConfigurationHolder.config.dataSource.dbcp.maxIdle
        minIdle =ConfigurationHolder.config.dataSource.dbcp.minIdle
        initialSize =ConfigurationHolder.config.dataSource.dbcp.initialSize
        minEvictableIdleTimeMillis =ConfigurationHolder.config.dataSource.dbcp.minEvictableIdleTimeMillis
        timeBetweenEvictionRunsMillis =ConfigurationHolder.config.dataSource.dbcp.timeBetweenEvictionRunsMillis
        numTestsPerEvictionRun =ConfigurationHolder.config.dataSource.dbcp.numTestsPerEvictionRun
        testOnBorrow =false
        testWhileIdle =true
        testOnReturn =false
        validationQuery =ConfigurationHolder.config.dataSource.dbcp.validationQuery
        maxWait =ConfigurationHolder.config.dataSource.dbcp.maxWait
    }

    dataSource_account(BasicDataSource) { bean ->
        bean.destroyMethod = 'close'
        driverClassName=ConfigurationHolder.config.dataSource.account.driverClassName
        url=ConfigurationHolder.config.dataSource.account.url
        username=ConfigurationHolder.config.dataSource.account.username
        password=DESCodec.decode(ConfigurationHolder.config.dataSource.account.password)

        maxActive =ConfigurationHolder.config.dataSource.dbcp.maxActive
        maxIdle =ConfigurationHolder.config.dataSource.dbcp.maxIdle
        minIdle =ConfigurationHolder.config.dataSource.dbcp.minIdle
        initialSize =ConfigurationHolder.config.dataSource.dbcp.initialSize
        minEvictableIdleTimeMillis =ConfigurationHolder.config.dataSource.dbcp.minEvictableIdleTimeMillis
        timeBetweenEvictionRunsMillis =ConfigurationHolder.config.dataSource.dbcp.timeBetweenEvictionRunsMillis
        numTestsPerEvictionRun =ConfigurationHolder.config.dataSource.dbcp.numTestsPerEvictionRun
        testOnBorrow =false
        testWhileIdle =true
        testOnReturn =false
        validationQuery =ConfigurationHolder.config.dataSource.dbcp.validationQuery
        maxWait =ConfigurationHolder.config.dataSource.dbcp.maxWait
    }

    dataSource_boss(BasicDataSource) { bean ->
        bean.destroyMethod = 'close'
        driverClassName=ConfigurationHolder.config.dataSource.boss.driverClassName
        url=ConfigurationHolder.config.dataSource.boss.url
        username=ConfigurationHolder.config.dataSource.boss.username
        password=DESCodec.decode(ConfigurationHolder.config.dataSource.boss.password)

        maxActive =ConfigurationHolder.config.dataSource.dbcp.maxActive
        maxIdle =ConfigurationHolder.config.dataSource.dbcp.maxIdle
        minIdle =ConfigurationHolder.config.dataSource.dbcp.minIdle
        initialSize =ConfigurationHolder.config.dataSource.dbcp.initialSize
        minEvictableIdleTimeMillis =ConfigurationHolder.config.dataSource.dbcp.minEvictableIdleTimeMillis
        timeBetweenEvictionRunsMillis =ConfigurationHolder.config.dataSource.dbcp.timeBetweenEvictionRunsMillis
        numTestsPerEvictionRun =ConfigurationHolder.config.dataSource.dbcp.numTestsPerEvictionRun
        testOnBorrow =false
        testWhileIdle =true
        testOnReturn =false
        validationQuery =ConfigurationHolder.config.dataSource.dbcp.validationQuery
        maxWait =ConfigurationHolder.config.dataSource.dbcp.maxWait
    }

    dataSource_dsf(BasicDataSource) { bean ->
        bean.destroyMethod = 'close'
        driverClassName=ConfigurationHolder.config.dataSource.dsf.driverClassName
        url=ConfigurationHolder.config.dataSource.dsf.url
        username=ConfigurationHolder.config.dataSource.dsf.username
        password=DESCodec.decode(ConfigurationHolder.config.dataSource.dsf.password)

        maxActive =ConfigurationHolder.config.dataSource.dbcp.maxActive
        maxIdle =ConfigurationHolder.config.dataSource.dbcp.maxIdle
        minIdle =ConfigurationHolder.config.dataSource.dbcp.minIdle
        initialSize =ConfigurationHolder.config.dataSource.dbcp.initialSize
        minEvictableIdleTimeMillis =ConfigurationHolder.config.dataSource.dbcp.minEvictableIdleTimeMillis
        timeBetweenEvictionRunsMillis =ConfigurationHolder.config.dataSource.dbcp.timeBetweenEvictionRunsMillis
        numTestsPerEvictionRun =ConfigurationHolder.config.dataSource.dbcp.numTestsPerEvictionRun
        testOnBorrow =false
        testWhileIdle =true
        testOnReturn =false
        validationQuery =ConfigurationHolder.config.dataSource.dbcp.validationQuery
        maxWait =ConfigurationHolder.config.dataSource.dbcp.maxWait
    }
}
