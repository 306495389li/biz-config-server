package com.flaginfo.lightning.common.config;

import java.util.Properties;
import javax.sql.DataSource;
import javax.annotation.Resource;
import org.apache.ibatis.plugin.Interceptor;
import javax.persistence.EntityManagerFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import com.github.miemiedev.mybatis.paginator.OffsetLimitInterceptor;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class MybatisConfig {
//    private static final Logger logger = LoggerFactory.getLogger(MybatisConfig.class);
    
    @Resource
    public DataSource dataSource;

    public OffsetLimitInterceptor offsetLimitInterceptor() {
        OffsetLimitInterceptor o = new OffsetLimitInterceptor();
//        o.setDialectClass("com.github.miemiedev.mybatis.paginator.dialect.MySQLDialect");
        o.setDialectClass("com.github.miemiedev.mybatis.paginator.dialect.OracleDialect");
        return o;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/com/flaginfo/lightning/config/mapper/*.xml"));

        Interceptor[] plugins = new Interceptor[1];
        plugins[0] = offsetLimitInterceptor();
        sqlSessionFactoryBean.setPlugins(plugins);

        Properties sqlSessionFactoryProperties = new Properties();
        sqlSessionFactoryProperties.put("logImpl", "LOG4J");
        sqlSessionFactoryBean.setConfigurationProperties(sqlSessionFactoryProperties);
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        return sqlSessionFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory factory) {
//        return new DataSourceTransactionManager(dataSource);
        return new JpaTransactionManager(factory);
    }
    
    /**
     * 测试事务管理器是哪个
     * @param platformTransactionManager
     * @return
     */
//    @Bean
//    public Object testBean(PlatformTransactionManager platformTransactionManager) {
//        logger.debug(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
//        return new Object();
//    }
}
