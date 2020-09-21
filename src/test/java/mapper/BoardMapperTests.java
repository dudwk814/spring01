package mapper;

import domain.BoardVO;
import domain.Criteria;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:web/WEB-INF/applicationContext.xml")
@Log4j
public class BoardMapperTests {

    @Setter(onMethod_ = {@Autowired})
    private BoardMapper boardMapper;

    @Test
    public void testGetList() {

        boardMapper.getList().forEach(board -> log.info(board));
    }

    @Test
    public void testInsert() {
        BoardVO board = new BoardVO();
        board.setTitle("새로 작성하는 글");
        board.setContent("새로 작성하는 내용");
        board.setWriter("newbie");

        boardMapper.insert(board);

        log.info(board);
    }

    @Test
    public void testInsertSelectKey() {
        BoardVO board = new BoardVO();
        board.setTitle("새로 작성하는 글");
        board.setContent("새로 작성하는 내용");
        board.setWriter("newbie");

        boardMapper.insertSelectKey(board);

        log.info(board);
    }

    @Test
    public void testRead() {
        Long bno = 9L;

        BoardVO read = boardMapper.read(bno);

        log.info(read);
    }

    @Test
    public void testDelete() {

        Long bno = 9L;

        log.info("DELETE COUNT : " + boardMapper.delete(bno));
    }

    @Test
    public void testUpdate() {

        BoardVO board = new BoardVO();
        board.setBno(5L);
        board.setTitle("수정된 제목");
        board.setContent("수정된 내용");
        board.setWriter("user00");

        int COUNT = boardMapper.update(board);

        log.info("UPDATE COUNT : " + COUNT);
    }

    @Test
    public void testGetListWithPaging() {
        Criteria cri = new Criteria();
        // 10개씩 3페이지
        cri.setPageNum(3);
        cri.setAmount(10);

        List<BoardVO> list = boardMapper.getListWithPaging(cri);

        list.forEach(boardVO -> log.info(boardVO));
    }
}
