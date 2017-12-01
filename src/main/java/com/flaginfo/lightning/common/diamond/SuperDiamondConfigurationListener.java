package com.flaginfo.lightning.common.diamond;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.diamond.client.event.ConfigurationEvent;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.ConfigurableEnvironment;
import com.github.diamond.client.event.ConfigurationListener;
import org.springframework.core.env.PropertiesPropertySource;

public class SuperDiamondConfigurationListener implements ConfigurationListener {
    
    private static final Logger logger = LoggerFactory.getLogger(SuperDiamondConfigurationListener.class);
    
	private ConfigurableEnvironment environment;
	
	public SuperDiamondConfigurationListener(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    public ConfigurableEnvironment getEnvironment() {
        return environment;
    }

    public void setEnvironment(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @Override
	public void configurationChanged(ConfigurationEvent event) {
	    logger.info("{} {} {}", event.getType().name(), event.getPropertyName(), event.getPropertyValue());
		
		MutablePropertySources propertySources = environment.getPropertySources();
		PropertiesPropertySource ps = (PropertiesPropertySource)propertySources.get("superdiamond");
        if (ps != null) {
            if (ps.getSource().containsKey(event.getPropertyName())) {
                ps.getSource().put(event.getPropertyName(), event.getPropertyValue());
                
                propertySources.addLast(ps);
            }
        }
		
	}
	
}
