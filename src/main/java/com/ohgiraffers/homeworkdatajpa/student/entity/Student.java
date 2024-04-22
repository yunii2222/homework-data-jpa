package com.ohgiraffers.homeworkdatajpa.student.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_student")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // -> 기본생성자(접근제한으로 변경)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentCode;
    private String studentName;
    private String studentPhone;
    private int studentGrade;
    private String password;
    private String studentStatus;
    private int majorSubject;
    private int tutorProfessor;

    /* 학생 이름 수정 */
    public void modifyByStudentName(String studentName) {
        this.studentName = studentName;
    }
}
