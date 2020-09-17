package controller;

import domain.SampleDTO;
import domain.SampleDTOList;
import domain.TodoDTO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {

    @InitBinder()
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping("")
    public void basic() {
        log.info("basic..................");
    }

    @RequestMapping(value = "/basic", method = RequestMethod.GET)
    public void basicGet() {
        log.info("basicGet........");
    }

    @GetMapping("/basicOnlyGet")
    public void basicGet2() {
        log.info("basicOnlyGet........");
    }

    @GetMapping("/ex01")
    public String ex01(SampleDTO dto) {
        log.info("" + dto);

        return "ex01";
    }

    @GetMapping("/ex02")
    public String ex02(@RequestParam("name") String name, @RequestParam("age") int age, Model model) {
        log.info("name : " + name);
        log.info("age : " + age);

        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "ex02";
    }

    @GetMapping("/ex02List")
    public String ex02List(@RequestParam("ids") ArrayList<String> ids) {
        log.info("ids : " + ids);
        return "ex02List";
    }

    @GetMapping("/ex02Array")
    public String ex02Array(@RequestParam("ids") String[] ids) {
        log.info("Array ids : " + Arrays.toString(ids));
        return "ex02Array";
    }

    @GetMapping("/ex02Bean")
    public String ex02Bean(SampleDTOList list) {
        log.info("list dtos : " + list);

        return "ex02Bean";
    }

    @GetMapping("/ex03")
    public String ex03(TodoDTO todoDTO) {
        log.info("todo : " + todoDTO);

        return "ex03";
    }

    @GetMapping("/ex04")
    public String ex04(SampleDTO dto, int page) {
        log.info("dto : " + dto);
        log.info("page : " + page);

        return "sample/ex04";
    }

    @GetMapping("/ex05")
    public @ResponseBody
    SampleDTO ex05() {
        log.info("/ex05........");
        SampleDTO dto = new SampleDTO();
        dto.setName("박찬양");
        dto.setAge(26);

        return dto;
    }
}
