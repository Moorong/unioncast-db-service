package com.unioncast.db.config;

/**
 * @auther wangyao
 * @date 2017-05-12 15:36
 */

import com.unioncast.db.rdbms.core.dao.ssp.repository.SspAdRegionDayReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import javax.persistence.EntityManager;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackageClasses  = SspAdRegionDayReportRepository.class)
public class RepositoryConfig {
	@Autowired
	private JpaProperties jpaProperties;

	@Autowired
	@Qualifier("sspReportDB")
	private DataSource sspReportDB;

	@Bean(name = "entityManager")
	@Primary
	public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
		return entityManagerFactory(builder).getObject().createEntityManager();
	}

	@Bean(name = "entityManagerFactory")
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(sspReportDB).properties(getVendorProperties(sspReportDB))
				.packages("com.unioncast.common.ssp.model.report") // 设置实体类所在位置
				.persistenceUnit("persistenceUnit").build();
	}

	private Map<String, String> getVendorProperties(DataSource dataSource) {
		return jpaProperties.getHibernateProperties(dataSource);
	}

	@Bean(name = "transactionManager")
	@Primary
	PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
		return new JpaTransactionManager(entityManagerFactory(builder).getObject());
	}
}
