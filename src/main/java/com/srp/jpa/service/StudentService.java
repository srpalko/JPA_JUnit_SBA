package com.srp.jpa.service;

import com.srp.jpa.dao.StudentDAO;
import com.srp.jpa.entitymodels.Course;
import com.srp.jpa.entitymodels.Student;
import com.srp.jpa.env.FactoryProvider;
import org.hibernate.HibernateException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Collection of methods for manipulating Student entities in the database.
 */
public class StudentService implements StudentDAO {

    /**
     * Gets a list of all students in the database
     *
     * @return List of all persisted Student entities
     */
    @Override
    public List<Student> getAllStudents() {

        EntityManager em = FactoryProvider.EMF.createEntityManager();

        try {
            em.getTransaction().begin();
            /*Typed query avoids casting. Named query is defined in Student class.*/
            TypedQuery<Student> query = em.createNamedQuery("getAllStudents", Student.class);
            List<Student> result = query.getResultList();
            em.getTransaction().commit();
            return result;
        } catch (HibernateException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return null;
    }

    /**
     * Finds persisted Student entity by email
     *
     * @param sEmail email of student to find
     * @return A Student entity or null if not found
     */
    @Override
    public Student getStudentByEmail(String sEmail) {

        EntityManager em = FactoryProvider.EMF.createEntityManager();

        try {
            em.getTransaction().begin();
            Student student = em.find(Student.class, sEmail);
            em.getTransaction().commit();
            return student;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return null;
    }

    /**
     * Validates the login credentials of a student.
     *
     * @param sEmail    email of student
     * @param sPassword password of student
     * @return true if email and password match in the database, false otherwise
     */
    @Override
    public boolean validateStudent(String sEmail, String sPassword) {

        EntityManager em = FactoryProvider.EMF.createEntityManager();

        try {
            em.getTransaction().begin();
            Student s = em.find(Student.class, sEmail);
            em.getTransaction().commit();
            /*If no record of student, return false*/
            if (s == null) {
                return false;
            }
            /*If given password matched persisted password, return true otherwise false*/
            return s.getSPass().equals(sPassword);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return false;
    }

    /**
     * Registers a student to a course.
     * PLEASE NOTE: The return type is changed from the requirements from void to boolean to
     * accommodate checking for a successful enrollment. Logic for checking a duplicate
     * enrollment is contained in this method.
     *
     * @param sEmail email of student to register
     * @param cId    ID of course to register student to
     * @return true if student was enrolled by the method. false if student was previously enrolled.
     */
    @Override
    public boolean registerStudentToCourse(String sEmail, int cId) {

        EntityManager em = FactoryProvider.EMF.createEntityManager();

        try {
            em.getTransaction().begin();
            Student student = em.find(Student.class, sEmail);
            Course course = em.find(Course.class, cId);
            if (!student.getSCourses().contains(course)) {
                student.getSCourses().add(course);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().commit();
                return false;
            }

        } catch (HibernateException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return false;
    }

    /**
     * Gets a list of all courses that a given student is enrolled in.
     *
     * @param sEmail email of student
     * @return List of Course entities or null if no courses are enrolled??
     */
    // TODO: 5/6/2021 CHECK RETURN OF EMPTY COURSE LIST 
    @Override
    public List<Course> getStudentCourses(String sEmail) {

        EntityManager em = FactoryProvider.EMF.createEntityManager();

        try {
            em.getTransaction().begin();
            Student student = em.find(Student.class, sEmail);
            em.getTransaction().commit();
            return student.getSCourses();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return null;
    }
}
