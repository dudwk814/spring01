package mapper;

import domain.Criteria;
import domain.ReplyVO;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.stream.IntStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:web/WEB-INF/applicationContext.xml")
@Log4j
public class ReplyMapperTests {

    @Setter(onMethod_ = {@Autowired})
    private ReplyMapper mapper;

    private Long[] bnoArr = {9733710L, 9733709L, 9733708L, 9733707L, 9733706L};
    @Test
    public void testMapper() {
        log.info(mapper);
    }

    @Test
    public void testInsert() {

        IntStream.rangeClosed(1, 10).forEach(i -> {

            ReplyVO vo = new ReplyVO();

            // 게시물 번호
            vo.setBno(bnoArr[i%5]);
            vo.setReply("댓글 테스트 " + i);
            vo.setReplyer("replyer " + i);

            mapper.insert(vo);
        });
    }

    @Test
    public void testRead() {

        Long targetRno = 5L;

        ReplyVO vo = mapper.read(targetRno);

        log.info(vo);
    }

    @Test
    public void testDelete() {

        Long targetRno = 11L;

        mapper.delete(targetRno);
    }

    @Test
    public void testUpdate() {

        Long targetRno = 10L;

        ReplyVO replyVO = mapper.read(targetRno);

        replyVO.setReply("Update Reply");

        int count = mapper.update(replyVO);

        log.info("UPDATE COUNT : " + count);
    }

    @Test
    public void testList() {

        Criteria cri = new Criteria();

        // 9733709L
        List<ReplyVO> replies = mapper.getListWithPaging(cri, bnoArr[1]);

        replies.forEach(reply -> log.info(reply));

    }
}
