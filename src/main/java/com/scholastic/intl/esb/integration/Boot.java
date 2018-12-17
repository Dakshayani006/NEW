package com.scholastic.intl.esb.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;

@SpringBootApplication
@EnableAutoConfiguration
public class Boot {

	private static final Logger logger = LoggerFactory.getLogger(Boot.class);
	
	@Bean
	public MetricRegistry metricRegistry() {
		return new MetricRegistry();
	}
	
	@Bean(initMethod = "start", destroyMethod = "stop")
	public JmxReporter jmxReporter(MetricRegistry metricRegistry) {
		return JmxReporter.forRegistry(metricRegistry).build();
	}
	
    public static void main(String[] args) {
    	logger.info(" *** Starting Customer application  ***");
        SpringApplication.run(Boot.class, args);
    }
    
}
