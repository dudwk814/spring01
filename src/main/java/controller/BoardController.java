package controller;

import domain.BoardVO;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public void list(Model model) {

        log.info("list");

        /* List<BoardVO> list = boardService.getList();
         */
        model.addAttribute("list", boardService.getList());
    }

    @PostMapping("/register")
    public String register(BoardVO board, RedirectAttributes rttr) {

        log.info("register");

        boardService.register(board);

        rttr.addAttribute("result", board.getBno());

        return "redirect:/board/list";
    }

    @GetMapping("/get")
    public void get(Long bno, Model model) {

        log.info("/get");

        BoardVO board = boardService.get(bno);

        model.addAttribute("board", board);
    }

    @PostMapping("/modify")
    public String modify(BoardVO board, RedirectAttributes rttr) {
        log.info("modify : " + board);

        if (boardService.modify(board)) {
            rttr.addAttribute("result", "success");
        }
        return "redirect:/board/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {

        log.info("remove........" + bno);

        if (boardService.remove(bno)) {
            rttr.addAttribute("result", "success");
        }

        return "redirect:/board/list";
    }
}
