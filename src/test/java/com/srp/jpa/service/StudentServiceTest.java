package com.srp.jpa.service;

import com.srp.jpa.entitymodels.Student;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StudentServiceTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private StudentService service;
    private Student expected;

    @BeforeAll
    static void connect() {
        entityManagerFactory = Persistence.createEntityManagerFactory("SMS");
    }

    @AfterAll
    static void close() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    @BeforeEach
    void setUp() {
        try {
            entityManager = entityManagerFactory.createEntityManager();
            service = new StudentService();
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("select s from Student s where s.sEmail = 'stephenpalko@gmail.com'");
            Student retreived = (Student) query.getSingleResult();
            expected = new Student(retreived.getSName(), retreived.getSEmail(), retreived.getSPass());
            expected.getSCourses().addAll(retreived.getSCourses());
        } catch (HibernateException exception) {
            System.out.println(exception.getMessage());
        }

    }

    @AfterEach
    void tearDown() {
        entityManager.getTransaction().commit();
        if (entityManager.isOpen())
            entityManager.close();
    }

    /*Test disabled as count of students unknown for different databases.*/
    @Disabled
    @Test
    void getAllStudents() {
        entityManager.getTransaction().begin();
        List<Student> students = service.getAllStudents();
        entityManager.getTransaction().commit();
        assertEquals(students.size(), 11);
    }

    @Disabled
    @Test
    void getStudentByEmail() {
        Student actual = service.getStudentByEmail("stephenpalko@gmail.com");
        assertEquals(actual, expected);
        Student nullStudent = service.getStudentByEmail("LALALALALA");
        assertNull(nullStudent);
    }

    @Test
    void validateStudent() {
    }

    @Test
    void registerStudentToCourse() {
    }

    @Test
    void getStudentCourses() {
    }
}