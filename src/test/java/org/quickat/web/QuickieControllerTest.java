package org.quickat.web;

import com.jayway.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quickat.QuickatApplication;
import org.quickat.da.Quickie;
import org.quickat.da.builder.QuickieBuilder;
import org.quickat.repository.QuickiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfiguration.class)
@WebAppConfiguration()
@IntegrationTest("server.port:0")
public class QuickieControllerTest {

    @Value("${local.server.port}")
    int port;

    Quickie springBootQuickie;

    @Autowired
    public QuickiesRepository quickiesRepository;

    @Before
    public void setUp() throws Exception {
        quickiesRepository.deleteAll();
        springBootQuickie = QuickieBuilder.aQuickie().withQuickieDate(new Date()).
                withTitle("Spring Boot").
                build();
        Quickie scalaQuickie = QuickieBuilder.aQuickie().withQuickieDate(new Date()).
                withTitle("Scala").
                build();
        Quickie vagrantQuickie = QuickieBuilder.aQuickie().withQuickieDate(new Date()).
                withTitle("Vagrant").
                build();
        quickiesRepository.save(Arrays.asList(scalaQuickie, springBootQuickie, vagrantQuickie));

        RestAssured.port = port;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetQuickies() throws Exception {

        when().get("/quickies").
                then().
                statusCode(HttpStatus.SC_OK).
                body("title", Matchers.hasItems("Vagrant", "Scala", "Spring Boot"));

    }

    @Test
    public void testGetQuickie() throws Exception {

        Integer springBootId = Integer.valueOf(springBootQuickie.getId().toString());
        when().get("/quickies/{id}", springBootQuickie.getId()).
                then().
                statusCode(HttpStatus.SC_OK).
                body("title", is("Spring Boot")).body("id", is(springBootId));

    }

    @Test
    public void testCreateQuickie() throws Exception {

    }

    /*@Autowired   // 5
            CharacterRepository repository;

    Character mickey;
    Character minnie;
    Character pluto;

    @Value("${local.server.port}")   // 6
            int port;

    @Before
    public void setUp() {
        // 7
        mickey = new Character("Mickey Mouse");
        minnie = new Character("Minnie Mouse");
        pluto = new Character("Pluto");

        // 8
        repository.deleteAll();
        repository.save(Arrays.asList(mickey, minnie, pluto));

        // 9
        RestAssured.port = port;
    }

    // 10
    @Test
    public void canFetchMickey() {
        Integer mickeyId = mickey.getId();

        when().
                get("/characters/{id}", mickeyId).
                then().
                statusCode(HttpStatus.SC_OK).
                body("name", Matchers.is("Mickey Mouse")).
                body("id", Matchers.is(mickeyId));
    }

    @Test
    public void canFetchAll() {
        when().
                get("/characters").
                then().
                statusCode(HttpStatus.SC_OK).
                body("name", Matchers.hasItems("Mickey Mouse", "Minnie Mouse", "Pluto"));
    }

    @Test
    public void canDeletePluto() {
        Integer plutoId = pluto.getId();

        when()
                .delete("/characters/{id}", plutoId).
                then().
                statusCode(HttpStatus.SC_NO_CONTENT);
    }*/
}

@Configuration
@Import({NoSecurityConfig.class,QuickatApplication.class})
class TestConfiguration {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource testDataSource = new DriverManagerDataSource();
        testDataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        testDataSource.setUrl("jdbc:hsqldb:file:hsqldb");
        testDataSource.setUsername("sa");
        testDataSource.setPassword("sa");
        Properties connectionProperties = new Properties();
        connectionProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        testDataSource.setConnectionProperties(connectionProperties);
        return testDataSource;

    }

    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.HSQLDialect");
        hibernateJpaVendorAdapter.setShowSql(true);
        return hibernateJpaVendorAdapter;
    }

}
@Configuration
@Order(1)
class NoSecurityConfig extends WebSecurityConfigurerAdapter {

    //https://github.com/rwinch/spring-boot-openid/blob/master/src/main/java/demo/MyController.java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll();
    }


}

