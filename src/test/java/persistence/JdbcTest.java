package persistence;

import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.DriverManager;

@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
@ContextConfiguration("file:web/WEB-INF/applicationContext.xml")
public class JdbcTest {

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnection() {
        try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "book_ex", "book_ex")) {
            log.info(con);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
