package com.asksahayak;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by srikanthchebrolu on 1/24/18.
 */
@SpringBootApplication
public class AskSahayakServicesApplication extends SpringBootServletInitializer{

    @Autowired
    private Environment env;

    private static final Logger log = LoggerFactory.getLogger(AskSahayakServicesApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        final SpringApplication app = new SpringApplication(AskSahayakServicesApplication.class);
        final ConfigurableEnvironment env = app.run(args).getEnvironment();
        final String port = env.getProperty("server.port");
        final String contextPath = env.getProperty("server.contextPath");
        final String securityEnabled = env.getProperty("securityEnabled");
        log.info("Access URLs:\n********************************************************************\n\t"
                + "Local: \t\thttp://127.0.0.1:{}" + contextPath + "\n\t" + "External: \thttp://{}:{}" + contextPath + "\n\t"
                + "Is security enabled?: " + securityEnabled
                + "\n********************************************************************", port, InetAddress.getLocalHost().getHostAddress(), port);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasenames(new String[]{"classpath:i18n/messages", "classpath:build-and-version-info"});
        source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AskSahayakServicesApplication.class);
    }

    @Component
    @Primary
    public class CustomObjectMapper extends ObjectMapper {
        public CustomObjectMapper() {
            setSerializationInclusion(JsonInclude.Include.NON_NULL);
            configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            enable(SerializationFeature.INDENT_OUTPUT);
        }
    }

}
