package service;

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

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:web/WEB-INF/applicationContext.xml")
@Log4j
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testExist() {

        log.info(boardService);

        assertNotNull(boardService);
    }

    @Test
    public void testRegister() {
        BoardVO board = new BoardVO();
        board.setTitle("새로 작성하는 글");
        board.setContent("새로 작성하는 내용");
        board.setWriter("newbie");

        boardService.register(board);

        log.info("생성된 게시글의 번호 : " + board.getBno());
    }

    @Test
    public void testGetList() {

        Criteria cri = new Criteria();
        boardService.getList(cri).forEach(boardVO -> log.info(boardVO));
    }

    @Test
    public void testGet() {

        //given
        Long bno = 10L;

        //when
        BoardVO boardVO = boardService.get(bno);

        //then
        log.info(boardVO);
    }

    @Test
    public void testModify() {
        BoardVO boardVO = boardService.get(10L);

        if (boardVO == null) {
            return;
        }

        boardVO.setTitle("제목 수정");

        log.info("MODIFY RESULT : " + boardService.modify(boardVO));

    }

    @Test
    public void testRemove() {

        log.info("REMOVE RESULT : " + boardService.remove(10L));
    }

}
