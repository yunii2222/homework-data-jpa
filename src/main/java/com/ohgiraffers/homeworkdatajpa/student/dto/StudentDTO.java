package com.ohgiraffers.homeworkdatajpa.student.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StudentDTO {
    private int studentCode;
    private String studentName;
    private String studentPhone;
    private int studentGrade;
    private String password;
    private String studentStatus;
    private int majorSubject;
    private int tutorProfessor;
}
