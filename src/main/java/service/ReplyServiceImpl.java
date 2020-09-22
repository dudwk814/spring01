package service;

import domain.Criteria;
import domain.ReplyVO;
import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import mapper.ReplyMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyMapper replyMapper;

    @Override
    public int register(ReplyVO vo) {

        log.info("register........." + vo);
        return replyMapper.insert(vo);
    }

    @Override
    public ReplyVO get(Long rno) {

        log.info("get..........." + rno);
        return replyMapper.read(rno);
    }

    @Override
    public int modify(ReplyVO vo) {

        log.info("modify........." + vo);
        return replyMapper.update(vo);
    }

    @Override
    public int remove(Long rno) {

        log.info("remove..........." + rno);
        return replyMapper.delete(rno);
    }

    @Override
    public List<ReplyVO> getList(Criteria cri, Long bno) {

        log.info("get Reply List of a Board " + bno);
        return replyMapper.getListWithPaging(cri, bno);
    }
}
