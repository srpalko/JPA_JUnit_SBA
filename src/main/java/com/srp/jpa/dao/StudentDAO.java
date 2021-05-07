package com.srp.jpa.dao;

import com.srp.jpa.entitymodels.Course;
import com.srp.jpa.entitymodels.Student;

import java.util.List;

/**
 * Interface for methods used to manipulate Student entities
 */
public interface StudentDAO {

    List<Student> getAllStudents();

    Student getStudentByEmail(String sEmail);

    boolean validateStudent(String sEmail, String sPassword);

    boolean registerStudentToCourse(String sEmail, int cId); // I changed this return to boolean.

    List<Course> getStudentCourses(String sEmail);

}
