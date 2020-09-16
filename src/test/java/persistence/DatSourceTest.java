package persistence;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import java.sql.Connection;

import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
@ContextConfiguration("file:web/WEB-INF/applicationContext.xml")
public class DatSourceTest {

    @Setter(onMethod_ = {@Autowired})
    private DataSource dataSource;

    @Setter(onMethod_ = {@Autowired})
    private SqlSessionFactory sqlSessionFactory;

    @Test
    public void testMyBatis() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(); Connection con = sqlSession.getConnection();){
            log.info(sqlSession);
            log.info(con);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    @Test
    public void testConnection() {
        try (Connection con = dataSource.getConnection()){
            log.info(con);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
