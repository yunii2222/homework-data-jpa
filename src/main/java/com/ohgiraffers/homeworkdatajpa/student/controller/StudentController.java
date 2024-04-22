package com.ohgiraffers.homeworkdatajpa.student.controller;

import com.ohgiraffers.homeworkdatajpa.common.PageNation;
import com.ohgiraffers.homeworkdatajpa.common.PagingButton;
import com.ohgiraffers.homeworkdatajpa.student.dto.StudentDTO;
import com.ohgiraffers.homeworkdatajpa.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/student")
@RequiredArgsConstructor // -> final필드 의존성 주입
public class StudentController {

    private final StudentService studentService;

    /* Id로 특정학생 조회 */
    @GetMapping("/{studentCode}")
    public String findStudentByCode(@PathVariable int studentCode, Model model){

        StudentDTO resultStudent = studentService.findStudentByStudentCode(studentCode);

        model.addAttribute("student", resultStudent);

        return "student/detail";
    }

    /* 학생 전체 조회 */
    @GetMapping("/list")
    public String findStudentList(Model model, @PageableDefault Pageable pageable){

        log.info("pageable : {}", pageable);

        Page<StudentDTO> studentList = studentService.findStudentList(pageable);
        /* 어떤 정보를 가지고 오는지 확인*/
        log.info("조회한 내용 목록 : {}", studentList.getContent());
        log.info("총 페이지 수 : {}", studentList.getTotalPages());
        log.info("총 메뉴 수 : {}", studentList.getTotalElements());
        log.info("해당 페이지에 표시 될 요소 수 : {}", studentList.getSize());
        log.info("해당 페이지에 실제 요소 수:{}",studentList.getNumberOfElements());
        log.info("첫 페이지 여부 : {}", studentList.isFirst());
        log.info("마지막 페이지 여부 : {}", studentList.isLast());
        log.info("정렬 방식 : {}", studentList.getSort());
        log.info("여러 페이지 중 현재 인덱스 : {}", studentList.getNumber());

        PagingButton paging = PageNation.getPagingButtonInfo(studentList);

        model.addAttribute("studentList", studentList);
        model.addAttribute("paging", paging);

        return "student/list";
    }

    /* 쿼리 메소드 */
    @GetMapping("/querymethod")
    public void querymethodPage(){}

    @GetMapping("/search")
    public String findByStudentName(@RequestParam String studentName, Model model){
        List<StudentDTO> studentList = studentService.findByStudentName(studentName);

        model.addAttribute("studentList", studentList);
        return "student/searchResult";
    }

    /* Native를 활용한 전체 조회 */
    @GetMapping("/all")
    public String findStudentList(Model model){
        List<StudentDTO> studentList = studentService.findAllStudent();
        model.addAttribute("studentList", studentList);
        return "student/all";
    }

    /* 학생 신규 등록 */

    @GetMapping("/regist")
    public void registPage(){}
    @PostMapping("/regist")
    public String registNewStudent(@ModelAttribute StudentDTO studentDTO){

        studentService.registStudent(studentDTO);
        return "redirect:/student/list";
    }

    /* 학생 이름 수정 */
    @GetMapping("/modify")
    public void modifyPage() {}

    @PostMapping("/modify")
    public String  modifyMenu(@ModelAttribute StudentDTO studentDTO){
        studentService.modifyStudent(studentDTO);
        return "redirect:/student/" + studentDTO.getStudentCode();
    }

    /* 메뉴 삭제 */
    @GetMapping("/delete")
    public void deletePage() {}

    @PostMapping("/delete")
    public String deleteMenu(Integer studentCode){
        studentService.deleteStudent(studentCode);
        return "redirect:/student/list";
    }

}
