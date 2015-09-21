import org.codehaus.groovy.grails.commons.*
import dsf.AgentLoaderService

datasources = {
    datasource(name: 'ismp') {
        domainClasses([
                ismp.CmApplicationInfo,
                ismp.CmCorporationInfo,
                ismp.CmCustomer,
                ismp.CmCustomerAccountMapping,
                ismp.CmCustomerBankAccount,
                ismp.CmCustomerOperator,
                ismp.CmLoginCertificate,
                ismp.CmLoginLog,
                ismp.CmPersonalInfo,
                ismp.CmDynamicKey,
                ismp.TradeAccountCommandSaf,
                ismp.TradeBase,
                ismp.TradeRefund,
                ismp.TradeWithdrawn,
                ismp.TradeCharge,
                ismp.TradeFrozen,
                ismp.TradePayment,
                ismp.TradeTransfer,
                ismp.TradeUnfrozen,
                ismp.RefundAuth,
                ismp.RefundBatch,
                ismp.RefundHistory,
                ismp.RoyaltyBinding,
                ismp.BindingMoney,
                ismp.CmOpLog,
                ismp.CmOpRelation,
                gateway.GwOrder,
                role.Role,
                model.Model,
                ismp.CmOpLog,
                ismp.BalanceOfAccountLog
        ])
        readOnly(Boolean.parseBoolean(ConfigurationHolder.config.dataSource.ismp.readOnly == null ? 'false' : ConfigurationHolder.config.dataSource.ismp.readOnly))
        driverClassName(ConfigurationHolder.config.dataSource.ismp.driverClassName)
        url(ConfigurationHolder.config.dataSource.ismp.url)
        username(ConfigurationHolder.config.dataSource.ismp.username)
        println "password:"+DESCodec.decode(ConfigurationHolder.config.dataSource.ismp.password)
        password(DESCodec.decode(ConfigurationHolder.config.dataSource.ismp.password))
        dbCreate(ConfigurationHolder.config.dataSource.ismp.dbCreate)
        pooled(Boolean.parseBoolean(ConfigurationHolder.config.dataSource.ismp.pooled == null ? 'false' : ConfigurationHolder.config.dataSource.ismp.pooled))
        logSql(Boolean.parseBoolean(ConfigurationHolder.config.dataSource.ismp.logSql == null ? 'false' : ConfigurationHolder.config.dataSource.ismp.logSql))
        dialect(ConfigurationHolder.config.dataSource.ismp.dialect)
        hibernate {
            cache {
                use_second_level_cache(false)
                use_query_cache(false)
                provider_class('net.sf.ehcache.hibernate.EhCacheProvider')
            }
        }
    }

    datasource(name: 'settle') {
        domainClasses([
                settle.FtFoot,
                settle.FtLiquidate,
                settle.FtTrade,
                settle.FtSrvFootSetting,
                settle.FtSrvType,
                settle.FtSrvTradeType,
                settle.FtTradeFee,
                settle.FtTradeFeeUpdate
        ])
        readOnly(Boolean.parseBoolean(ConfigurationHolder.config.dataSource.settle.readOnly == null ? 'false' : ConfigurationHolder.config.dataSource.settle.readOnly))
        driverClassName(ConfigurationHolder.config.dataSource.settle.driverClassName)
        url(ConfigurationHolder.config.dataSource.settle.url)
        username(ConfigurationHolder.config.dataSource.settle.username)
        password(DESCodec.decode(ConfigurationHolder.config.dataSource.settle.password))
        println "setttle is password:"+DESCodec.decode(ConfigurationHolder.config.dataSource.settle.password)
        dbCreate(ConfigurationHolder.config.dataSource.settle.dbCreate)
        pooled(Boolean.parseBoolean(ConfigurationHolder.config.dataSource.settle.pooled == null ? 'false' : ConfigurationHolder.config.dataSource.settle.pooled))
        logSql(Boolean.parseBoolean(ConfigurationHolder.config.dataSource.settle.logSql == null ? 'false' : ConfigurationHolder.config.dataSource.settle.logSql))
        dialect(ConfigurationHolder.config.dataSource.settle.dialect)
        hibernate {
            cache {
                use_second_level_cache(false)
                use_query_cache(false)
                provider_class('net.sf.ehcache.hibernate.EhCacheProvider')
            }
        }
    }

    datasource(name: 'account') {
        domainClasses([
                account.AcAccount,
                account.AcSequential,
                account.AcTransaction
        ])
        readOnly(Boolean.parseBoolean(ConfigurationHolder.config.dataSource.account.readOnly == null ? 'false' : ConfigurationHolder.config.dataSource.account.readOnly))
        driverClassName(ConfigurationHolder.config.dataSource.account.driverClassName)
        url(ConfigurationHolder.config.dataSource.account.url)
        username(ConfigurationHolder.config.dataSource.account.username)
        password(DESCodec.decode(ConfigurationHolder.config.dataSource.account.password))
        println "account is password:"+DESCodec.decode(ConfigurationHolder.config.dataSource.account.password)
        dbCreate(ConfigurationHolder.config.dataSource.account.dbCreate)
        pooled(Boolean.parseBoolean(ConfigurationHolder.config.dataSource.account.pooled == null ? 'false' : ConfigurationHolder.config.dataSource.account.pooled))
        logSql(Boolean.parseBoolean(ConfigurationHolder.config.dataSource.account.logSql == null ? 'false' : ConfigurationHolder.config.dataSource.account.logSql))
        dialect(ConfigurationHolder.config.dataSource.account.dialect)
        hibernate {
            cache {
                use_second_level_cache(false)
                use_query_cache(false)
                provider_class('net.sf.ehcache.hibernate.EhCacheProvider')
            }
        }
    }

    datasource(name: 'boss') {
        domainClasses([
                boss.BoBankDic,
                boss.BoAcquirerAccount,
                boss.BoCustomerService,
                boss.BoMerchant,
                boss.BoInnerAccount,
                boss.BoOperator,
                boss.BoNews,
                boss.BoRefundModel
        ])
        readOnly(Boolean.parseBoolean(ConfigurationHolder.config.dataSource.boss.readOnly == null ? 'false' : ConfigurationHolder.config.dataSource.boss.readOnly))
        driverClassName(ConfigurationHolder.config.dataSource.boss.driverClassName)
        url(ConfigurationHolder.config.dataSource.boss.url)
        username(ConfigurationHolder.config.dataSource.boss.username)
        password(DESCodec.decode(ConfigurationHolder.config.dataSource.boss.password))
        println "boss is password:"+DESCodec.decode(ConfigurationHolder.config.dataSource.boss.password)
        dbCreate(ConfigurationHolder.config.dataSource.boss.dbCreate)
        pooled(Boolean.parseBoolean(ConfigurationHolder.config.dataSource.boss.pooled == null ? 'false' : ConfigurationHolder.config.dataSource.boss.pooled))
        logSql(Boolean.parseBoolean(ConfigurationHolder.config.dataSource.boss.logSql == null ? 'false' : ConfigurationHolder.config.dataSource.boss.logSql))
        dialect(ConfigurationHolder.config.dataSource.boss.dialect)
        hibernate {
            cache {
                use_second_level_cache(false)
                use_query_cache(false)
                provider_class('net.sf.ehcache.hibernate.EhCacheProvider')
            }
        }
    }
    datasource(name: 'dsf') {
        domainClasses([
                dsf.TbAgentpayInfo,
                dsf.TbAgentpayDetailsInfo,
                dsf.TbAgentpayDetailsChildInfo,
                dsf.TbBizCustomer

        ])
        services([AgentLoaderService])
        driverClassName(ConfigurationHolder.config.dataSource.ismp.driverClassName)
        url(ConfigurationHolder.config.dataSource.dsf.url)
        username(ConfigurationHolder.config.dataSource.dsf.username)
        password(DESCodec.decode(ConfigurationHolder.config.dataSource.dsf.password))
        dbCreate(ConfigurationHolder.config.dataSource.dsf.dbCreate)
        println "dsf is password:"+DESCodec.decode(ConfigurationHolder.config.dataSource.dsf.password)
        pooled(Boolean.parseBoolean(ConfigurationHolder.config.dataSource.dsf.pooled == null ? 'false' : ConfigurationHolder.config.dataSource.dsf.pooled))
        logSql(Boolean.parseBoolean(ConfigurationHolder.config.dataSource.dsf.logSql == null ? 'false' : ConfigurationHolder.config.dataSource.dsf.logSql))
        dialect(org.hibernate.dialect.Oracle10gDialect)
        hibernate {
            cache {
                use_second_level_cache(false)
                use_query_cache(false)
                provider_class('net.sf.ehcache.hibernate.EhCacheProvider')
            }
        }
    }
}
