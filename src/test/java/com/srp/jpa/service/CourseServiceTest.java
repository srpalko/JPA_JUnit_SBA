package com.srp.jpa.service;

import com.srp.jpa.entitymodels.Course;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * These tests are based on the assumption of specific data being present in the database. If your database
 * is different, the tests won't pass.
 */
class CourseServiceTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private CourseService cs;

    @BeforeAll
    static void setUp() {
        emf = Persistence.createEntityManagerFactory("SMS");
    }

    @AfterAll
    static void shutDown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @BeforeEach
    void getEM() {
        em = emf.createEntityManager();
        cs = new CourseService();
    }

    @Disabled
    @Test
    void getAllCourses() {
        int numberOfCourses = 10;
        try {
            em.getTransaction().begin();
            List<Course> courseList = cs.getAllCourses();
            assertEquals(10, courseList.size());
            em.getTransaction().commit();
        } catch (HibernateException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }

    }

    @Disabled
    @Test
    void getCourseById() {
        /*Maybe not the absolute best way to do a test. But I'm learning!*/
        String courseName = "English";
        String instructor = "Anderea Scamaden";
        int id = 1;
        try {
            em.getTransaction().begin();
            Course actual = cs.getCourseById(1);
            assertAll(() -> assertEquals(courseName, actual.getCName()),
                    () -> assertEquals(instructor, actual.getCInstructorName()),
                    () -> assertEquals(id, actual.getCId()));
            em.getTransaction().commit();
        } catch (HibernateException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @AfterEach
    void closeEM() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}