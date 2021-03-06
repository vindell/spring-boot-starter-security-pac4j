package org.springframework.security.boot;

import org.pac4j.core.config.Config;
import org.pac4j.core.context.JEEContext;
import org.pac4j.core.engine.DefaultLogoutLogic;
import org.pac4j.core.engine.LogoutLogic;
import org.pac4j.spring.boot.Pac4jAutoConfiguration;
import org.pac4j.spring.boot.Pac4jProperties;
import org.pac4j.springframework.security.web.Pac4jEntryPoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.boot.pac4j.authorizer.Pac4jExtEntryPoint;

// http://blog.csdn.net/change_on/article/details/76302161
@Configuration
@AutoConfigureAfter(Pac4jAutoConfiguration.class)
@AutoConfigureBefore(SecurityAutoConfiguration.class)
@ConditionalOnProperty(prefix = SecurityPac4jProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Pac4jProperties.class })
public class SecurityPac4jAutoConfiguration {
  
    @Bean
    @ConditionalOnMissingBean
    public LogoutLogic<Object, JEEContext> logoutLogic(){
		return new DefaultLogoutLogic<Object, JEEContext>();
	}
    
    @Bean
    @ConditionalOnMissingBean
    public Pac4jEntryPoint pac4jEntryPoint(Config config, Pac4jProperties pac4jProperties){
		return new Pac4jExtEntryPoint(config, pac4jProperties.getDefaultClientName(), pac4jProperties.getClientParameterName());
	}

}
