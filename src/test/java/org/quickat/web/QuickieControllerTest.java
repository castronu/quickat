package org.quickat.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quickat.QuickatApplication;
import org.quickat.da.Quickie;
import org.quickat.da.builder.QuickieBuilder;
import org.quickat.repository.QuickiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;
import java.util.Date;
import java.util.Properties;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfiguration.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class QuickieControllerTest {

    @Autowired
    public QuickiesRepository quickiesRepository;

    @Before
    public void setUp() throws Exception {
        Quickie springBootQuickie = QuickieBuilder.aQuickie().withDate(new Date()).
                withTitle("Spring Boot").
                build();
        quickiesRepository.save(springBootQuickie);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetQuickies() throws Exception {

    }

    @Test
    public void testGetQuickie() throws Exception {

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
@Import(QuickatApplication.class)
class TestConfiguration {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource testDataSource = new DriverManagerDataSource();
        testDataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        testDataSource.setUrl("jdbc:hsqldb:file:hsqldb");
        testDataSource.setUsername("sa");
        testDataSource.setPassword("sa");
        Properties connectionProperties = new Properties();
        connectionProperties.setProperty("hibernate.hbm2ddl.auto","create-drop");
        testDataSource.setConnectionProperties(connectionProperties);
        return testDataSource;

    }
    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter(){
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter=new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.HSQLDialect");
        hibernateJpaVendorAdapter.setShowSql(true);
        return hibernateJpaVendorAdapter;
    }

}