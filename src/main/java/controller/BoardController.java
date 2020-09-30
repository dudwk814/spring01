package controller;

import domain.BoardAttachVO;
import domain.BoardVO;
import domain.Criteria;
import domain.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.BoardService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@Log4j
@RequestMapping("/board/*")
/*@RequiredArgsConstructor*/
public class BoardController {

    @Setter(onMethod_ = {@Autowired})
    private /*final*/ BoardService boardService;

    @GetMapping("/list")
    public void list(Criteria cri, Model model) {

        log.info("list : " + cri);

        /* List<BoardVO> list = boardService.getList();
         */
        model.addAttribute("list", boardService.getList(cri));
        /*model.addAttribute("pageMaker", new PageDTO(cri, 123));*/

        int total = boardService.getTotal(cri);

        log.info("total : " + total);

        model.addAttribute("pageMaker", new PageDTO(cri, total));
    }

    @GetMapping("/register")
    @PreAuthorize("isAuthenticated()")
    public void register() {

    }

    @PostMapping("/register")
    @PreAuthorize("isAuthenticated()")
    public String register(BoardVO board, RedirectAttributes rttr) {

        log.info("=====================================");

        log.info("register");

        if (board.getAttachList() != null) {
            board.getAttachList().forEach(attach -> log.info(attach));
        }

        log.info("=====================================");

        boardService.register(board);

        rttr.addFlashAttribute("result", board.getBno());

        return "redirect:/board/list";
    }

    @GetMapping({"/get", "/modify"})
    public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {

        log.info("/get or modify");

        BoardVO board = boardService.get(bno);

        model.addAttribute("board", board);
    }

    @PreAuthorize("principal.username == #board.writer")
    @PostMapping("/modify")
    public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {

        log.info("modify : " + board);

        if (boardService.modify(board)) {
            rttr.addFlashAttribute("result", "success");
        }

        /*rttr.addAttribute("pageNum", cri.getPageNum());
        rttr.addAttribute("amount", cri.getAmount());
        rttr.addAttribute("type", cri.getType());
        rttr.addAttribute("keyword", cri.getKeyword());*/
        return "redirect:/board/list" + cri.getListLink();
    }

    @PreAuthorize("principal.username == #writer")
    @PostMapping("/remove")
    public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr, String writer) {

        log.info("remove........" + bno);

        List<BoardAttachVO> attachList = boardService.getAttachList(bno);

        if (boardService.remove(bno)) {

            // delete Attach Files
            deleteFiles(attachList);

            rttr.addFlashAttribute("result", "success");
        }

        /*rttr.addAttribute("pageNum", cri.getPageNum());
        rttr.addAttribute("amount", cri.getAmount());
        rttr.addAttribute("type", cri.getType());
        rttr.addAttribute("keyword", cri.getKeyword());*/

        return "redirect:/board/list" + cri.getListLink();
    }

    @GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno) {

        log.info("getAttachList : " + bno );

        return new ResponseEntity<>(boardService.getAttachList(bno), HttpStatus.OK);
    }

    private void deleteFiles(List<BoardAttachVO> attachList) {

        if (attachList == null || attachList.size() == 0) {
            return;
        }

        log.info("delete attach files..................");
        log.info(attachList);

        attachList.forEach(attach -> {
            try {
                Path file = Paths.get("C:\\upload\\" + attach.getUploadPath() + "\\" + attach.getUuid() + "_" + attach.getFileName());

                Files.deleteIfExists(file);

                if (Files.probeContentType(file).startsWith("image")) {

                    Path thumbnail = Paths.get("C:\\upload\\" + attach.getUploadPath() + "\\s_" + attach.getUuid() + "_" + attach.getFileName());

                    Files.delete(thumbnail);
                }
            } catch (Exception e) {
                log.error("delete file error : " + e.getMessage());
            } // end catch
        }); //end foreachd
    }
}
