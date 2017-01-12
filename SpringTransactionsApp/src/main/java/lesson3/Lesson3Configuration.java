package lesson3;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class Lesson3Configuration {

    @Autowired
    private DataSource datasource;

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(datasource);
    }
}
