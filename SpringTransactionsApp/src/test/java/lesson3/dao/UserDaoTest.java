package lesson3.dao;

import lesson3.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class UserDaoTest {

    private UserDaoWithTransactionTemplate dao;

    @Before
    public void setUp() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .generateUniqueName(true)
                .addScript("dao/data.sql")
                .build();
        dao = new UserDaoWithTransactionTemplate(new JdbcTemplate(db));
    }

    @Test
    public void read() throws Exception {
        List<User> list = dao.findAll();
        assertEquals(1, list.size());
        assertEquals("EmbeddedUserName", list.get(0).getName());

    }

}