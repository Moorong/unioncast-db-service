package com.unioncast.db.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "author")
public class RedisSettings {

	private String name;
	/**
	 * 星座
	 */
	private String constellation;
	/**
	 * 生肖
	 */
	private String anyOfTheTwelveAnimals;
	
	/**
	 * 区别生产环境和开发环境
	 */
	private String environment;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	public String getAnyOfTheTwelveAnimals() {
		return anyOfTheTwelveAnimals;
	}

	public void setAnyOfTheTwelveAnimals(String anyOfTheTwelveAnimals) {
		this.anyOfTheTwelveAnimals = anyOfTheTwelveAnimals;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

}
