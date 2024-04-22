package com.ohgiraffers.homeworkdatajpa.student.service;

import com.ohgiraffers.homeworkdatajpa.student.dto.StudentDTO;
import com.ohgiraffers.homeworkdatajpa.student.entity.Student;
import com.ohgiraffers.homeworkdatajpa.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;

@Service
@RequiredArgsConstructor // -> final필드 의존성 주입
public class StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public StudentDTO findStudentByStudentCode(int studentCode) {
        Student foundStudent =  studentRepository.findById(studentCode).orElseThrow(IllegalArgumentException::new);
        return modelMapper.map(foundStudent, StudentDTO.class);
    }

    public Page<StudentDTO> findStudentList(Pageable pageable) {

        pageable = PageRequest.of(
                /* 0부터 시작하는 페이지 처리 (번호 1개 줄이기) */
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() -1, // pageNumber
                pageable.getPageSize(), // pageSize : 그대로 사용
                Sort.by("studentCode").descending()  // Sort
        );

        Page<Student> studentList =  studentRepository.findAll(pageable);
        return studentList
                .map(student -> modelMapper
                        .map(student, StudentDTO.class));
    }

    public List<StudentDTO> findByStudentName(String studentName) {
        List<Student> studentList = studentRepository.findByStudentNameLike(studentName);
        System.out.println("studentList = " + studentList);

        return studentList.stream() // 가공하기위해 stream형태로 먼저 변환시키고,
                .map(student -> modelMapper // 하나하나의 menu엔티티를 modelMapper를 사용해
                        .map(student, StudentDTO.class)) // MenuDTO로 가공해주고
                .toList(); // 리스트로 만든다.
    }

    public List<StudentDTO> findAllStudent() {
        List<Student> studentList = studentRepository.findAllStudent();
        return studentList.stream()
                .map(student -> modelMapper
                        .map(student, StudentDTO.class))
                .toList();
    }
    @Transactional
    public void registStudent(StudentDTO studentDTO) {
        studentRepository.save(modelMapper.map(studentDTO, Student.class));
    }



    @Transactional
    public void modifyStudent(StudentDTO studentDTO) {

        Student foundStudent = studentRepository.findById(studentDTO.getStudentCode()).orElseThrow(IllegalArgumentException::new);

        foundStudent.modifyByStudentName(studentDTO.getStudentName());
    }
    @Transactional
    public void deleteStudent(Integer studentCode) {
        studentRepository.deleteById(studentCode);
    }
}
