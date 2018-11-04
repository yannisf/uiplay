package fraglab;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * <p>ApplicationConfiguration class.</p>
 *
 * @author yannis
 * @version $Id: $Id
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@EnableCaching
@ComponentScan(basePackages = {"fraglab"})
@EnableJpaRepositories(basePackages = "fraglab")
@PropertySource("jdbc.properties")
public class ApplicationConfiguration implements WebMvcConfigurer {

    @Value("${jdbc.driver}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    /**
     * <p>dataSource.</p>
     *
     * @return a DataSource object.
     */
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setMaximumPoolSize(10);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * <p>entityManagerFactory.</p>
     *
     * @param dataSource a DataSource object.
     * @return a {@link org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean} object.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        return entityManagerFactoryBean;
    }

    /**
     * <p>transactionManager.</p>
     *
     * @param entityManagerFactory a {@link javax.persistence.EntityManagerFactory} object.
     * @return a {@link org.springframework.transaction.PlatformTransactionManager} object.
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager platformTransactionManager = new JpaTransactionManager();
        platformTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return platformTransactionManager;
    }

    /**
     * <p>cacheManager.</p>
     *
     * @return a {@link org.springframework.cache.CacheManager} object.
     */
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("authors");
    }

    /**
     * <p>dozerMapper.</p>
     *
     * @return a {@link com.github.dozermapper.core.Mapper} object.
     */
    @Bean
    public Mapper dozerMapper() {
        return DozerBeanMapperBuilder.buildDefault();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }

}
