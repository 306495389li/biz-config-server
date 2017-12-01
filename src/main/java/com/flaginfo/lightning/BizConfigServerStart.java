package com.flaginfo.lightning;

import org.slf4j.Logger;
import java.util.Properties;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.core.env.Environment;
import org.springframework.boot.SpringApplication;
import com.github.diamond.client.PropertiesConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.flaginfo.lightning.common.diamond.SuperDiamondConfigurationListener;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@EnableEurekaClient
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
@MapperScan("com.flaginfo.lightning.*.mapper")
@RestController
@EnableTransactionManagement
public class BizConfigServerStart extends SpringBootServletInitializer {
    private static Logger logger = LoggerFactory.getLogger(BizConfigServerStart.class);
    @Resource
    private Environment env;
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BizConfigServerStart.class);
    }

    public static void main(String[] args) {
//        SpringApplication.run(BizConfigServerStart.class, args);
        
        SpringApplication app = new SpringApplication(BizConfigServerStart.class);
        app.addInitializers((ApplicationContextInitializer) applicationContext -> {
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            
            loadSuperDiamond(environment, new SuperDiamondConfigurationListener(environment));
        });
        
        app.run(args);
    }
    
    public static void loadSuperDiamond(ConfigurableEnvironment enviroment, 
                                        SuperDiamondConfigurationListener listener) {
        PropertiesConfiguration config = 
                new PropertiesConfiguration(enviroment.getProperty("superdiamond.host"),
                                            Integer.parseInt(enviroment.getProperty("superdiamond.port")),
                                            enviroment.getProperty("superdiamond.proj", "superdiamond.proj"),
                                            enviroment.getProperty("superdiamond.profile", "superdiamond.profile"), 
                                            enviroment.getProperty("superdiamond.modules", "superdiamond.modules"));
        config.addConfigurationListener(listener);
        
        Properties properties = config.getProperties();
        enviroment.getPropertySources().addLast(new PropertiesPropertySource("superdiamond", properties));
        
    }
    
    @RequestMapping(value = "/test")
    public String test() {
        logger.info("------ 9100 test start---ok-----");
        return "9100  ok----";
    }
    
    @RequestMapping(value = "/env/{name}")
    public String env(@PathVariable String name) {
        return env.getProperty(name);
    }
    
}