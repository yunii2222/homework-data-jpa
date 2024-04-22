package com.ohgiraffers.homeworkdatajpa.student.repository;
import com.ohgiraffers.homeworkdatajpa.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    /* Native Query 사용 */
    @Query(
            value = "SELECT student_code, student_name, student_phone, student_grade, password, student_status, major_subject, tutor_professor FROM tb_student ORDER BY student_code",
            nativeQuery = true
    )
    List<Student> findAllStudent();

    List<Student> findByStudentNameLike(String studentName);
}
