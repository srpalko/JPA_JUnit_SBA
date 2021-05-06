package com.srp.jpa.dao;

import com.srp.jpa.entitymodels.Course;

import java.util.List;

public interface CourseDAO {

    List<Course> getAllCourses();

    Course getCourseById(int id);
}
