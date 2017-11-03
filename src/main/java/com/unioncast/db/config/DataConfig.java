package com.unioncast.db.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataConfig {

	private static final Logger LOG = LogManager.getLogger(DataConfig.class);

	/**
	 * unioncast_common_manager数据源
	 * 
	 * @author 琚超超
	 * @date 2016年9月28日 上午10:59:49
	 *
	 * @return
	 */
	@Bean(name = "commonDB")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.mysql_common")
	public DataSource commonDB() {
		LOG.info("初始化commonDB数据源");
		return DataSourceBuilder.create().type(BasicDataSource.class).build();
	}

	/**
	 * unioncast_common_manager数据源的jdbc模板
	 * 
	 * @author 琚超超
	 * @date 2016年9月28日 上午11:00:07
	 *
	 * @param dataSource
	 * @return
	 */
	@Bean(name = "commonJdbcTemplate")
	public JdbcTemplate commonJdbcTemplate(@Qualifier("commonDB") DataSource dataSource) {
		LOG.info("初始化commonJdbcTemplate");
		// return new JdbcTemplate(dataSource);

		return new JdbcTemplate(dataSource, false);
	}

	/**
	 * unioncast_adx_manager数据源
	 * 
	 * @author 琚超超
	 * @date 2016年9月28日 上午11:00:51
	 *
	 * @return
	 */
	@Bean(name = "adxDB")
	@ConfigurationProperties(prefix = "spring.datasource.mysql_adx")
	public DataSource adxDB() {
		LOG.info("初始化adxDB数据源");
		return DataSourceBuilder.create().type(BasicDataSource.class).build();
	}

	/**
	 * unioncast_adx_manager数据源的jdbc模板
	 * 
	 * @author 琚超超
	 * @date 2016年9月28日 上午11:01:07
	 *
	 * @param dataSource
	 * @return
	 */
	@Bean(name = "adxJdbcTemplate")
	public JdbcTemplate adxJdbcTemplate(@Qualifier("adxDB") DataSource dataSource) {
		LOG.info("初始化adxJdbcTemplate");
		return new JdbcTemplate(dataSource);
	}

	/**
	 * unioncast_dsp_manager数据源
	 * 
	 * @author 琚超超
	 * @date 2016年9月28日 上午11:01:38
	 *
	 * @return
	 */
	@Bean(name = "dspDB")
	@ConfigurationProperties(prefix = "spring.datasource.mysql_dsp")
	public DataSource dspDB() {
		LOG.info("初始化dspDB数据源");
		return DataSourceBuilder.create().type(BasicDataSource.class).build();
	}

	/**
	 * unioncast_dsp_manager数据源的jdbc模板
	 * 
	 * @author 琚超超
	 * @date 2016年9月28日 上午11:02:28
	 *
	 * @param dataSource
	 * @return
	 */
	@Bean(name = "dspJdbcTemplate")
	public JdbcTemplate dspJdbcTemplate(@Qualifier("dspDB") DataSource dataSource) {
		LOG.info("初始化dspJdbcTemplate");
		return new JdbcTemplate(dataSource);
	}

	/**
	 * unioncast_ssp_manager的数据源
	 * 
	 * @author 琚超超
	 * @date 2016年9月28日 上午11:03:59
	 *
	 * @return
	 */
	@Bean(name = "sspDB")
	@ConfigurationProperties(prefix = "spring.datasource.mysql_ssp")
	public DataSource sspDB() {
		LOG.info("初始化sspDB数据源");
		return DataSourceBuilder.create().type(BasicDataSource.class).build();
	}

	/**
	 * unioncast_ssp_manager数据源的jdbc模板
	 * 
	 * @author 琚超超
	 * @date 2016年9月29日 下午5:12:51
	 *
	 * @param dataSource
	 * @return
	 */
	@Bean(name = "sspJdbcTemplate")
	public JdbcTemplate sspJdbcTemplate(@Qualifier("sspDB") DataSource dataSource) {
		LOG.info("初始化sspJdbcTemplate");
		return new JdbcTemplate(dataSource);
	}

	/**
	 * unioncast_ssp_report的数据源
	 * @return
	 */
	@Bean(name = "sspReportDB")
	@ConfigurationProperties(prefix = "spring.datasource.mysql_ssp_report")
	public DataSource sspReportDB() {
		LOG.info("初始化sspReportDB数据源");
		return DataSourceBuilder.create().type(BasicDataSource.class).build();
	}



}
