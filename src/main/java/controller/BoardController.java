package controller;

import domain.BoardVO;
import domain.Criteria;
import domain.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.BoardService;

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
    public void register() {

    }

    @PostMapping("/register")
    public String register(BoardVO board, RedirectAttributes rttr) {

        log.info("register");

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

    @PostMapping("/modify")
    public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {

        log.info("modify : " + board);

        if (boardService.modify(board)) {
            rttr.addFlashAttribute("result", "success");
        }

        rttr.addAttribute("pageNum", cri.getPageNum());
        rttr.addAttribute("amount", cri.getAmount());
        return "redirect:/board/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {

        log.info("remove........" + bno);

        if (boardService.remove(bno)) {
            rttr.addFlashAttribute("result", "success");
        }

        rttr.addAttribute("pageNum", cri.getPageNum());
        rttr.addAttribute("amount", cri.getAmount());

        return "redirect:/board/list";
    }
}
