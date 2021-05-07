package com.srp.jpa.dao;

import com.srp.jpa.entitymodels.Course;

import java.util.List;

/**
 * Interface for methods used to manipulate Course entities
 */
public interface CourseDAO {

    List<Course> getAllCourses();

    Course getCourseById(int id); // I ADDED THIS ONE
}
