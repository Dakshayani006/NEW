package com.scholastic.intl.esb.integration.resource;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
// @EnableConfigurationProperties
@PropertySource("classpath:nzProperties.properties")
public class NzConfigMapper {

	@Value("#{${properties.map}}")
	Map<String, String> palmItemProps;

	@Value("#{${properties.preferences.map}}")
	Map<String, String> preferencesMap;

	public Map<String, String> getPalmItemProps() {
		return palmItemProps;
	}

	public void setPalmItemProps(Map<String, String> palmItemProps) {
		this.palmItemProps = palmItemProps;
	}

	public Map<String, String> getPreferencesMap() {
		return preferencesMap;
	}

	public void setPreferencesMap(Map<String, String> preferencesMap) {
		this.preferencesMap = preferencesMap;
	}

}
