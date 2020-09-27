package service;

import domain.BoardAttachVO;
import domain.BoardVO;
import domain.Criteria;
import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import mapper.BoardAttachMapper;
import mapper.BoardMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper mapper;
    private final BoardAttachMapper boardAttachMapper;

    @Transactional
    @Override
    public void register(BoardVO board) {
        log.info("register......" + board);

        mapper.insertSelectKey(board);

        if (board.getAttachList() == null || board.getAttachList().size() <= 0) {
            return;
        }

        board.getAttachList().forEach(attach ->{
            attach.setBno(board.getBno());
            boardAttachMapper.insert(attach);
        });
    }

    @Override
    public BoardVO get(Long bno) {
        log.info("get........" + bno);

        return mapper.read(bno);
    }

    @Transactional
    @Override
    public boolean modify(BoardVO board) {

        log.info("modify........" + board);
        boardAttachMapper.deleteAll(board.getBno());

        boolean modifyResult = mapper.update(board) == 1;

        if (modifyResult && board.getAttachList() != null && board.getAttachList().size() > 0) {

            board.getAttachList().forEach(attach -> {
                attach.setBno(board.getBno());
                boardAttachMapper.insert(attach);
            });
        }
        return modifyResult;
    }

    @Override
    public List<BoardAttachVO> getAttachList(Long bno) {

        log.info("get Attach list by bno : " + bno);
        return boardAttachMapper.findByBno(bno);
    }

    @Transactional
    @Override
    public boolean remove(Long bno) {

        log.info("remove........." + bno);

        boardAttachMapper.deleteAll(bno);

        return mapper.delete(bno) == 1;
    }

    @Override
    public List<BoardVO> getList(Criteria cri) {

        log.info("get List with criteria : " + cri);

        return mapper.getListWithPaging(cri);
    }

    @Override
    public int getTotal(Criteria cri) {

        log.info("get total count");

        return mapper.getTotalCount(cri);
    }
}
