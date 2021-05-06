package com.srp.jpa.service;

import com.srp.jpa.dao.StudentDAO;
import com.srp.jpa.entitymodels.Course;
import com.srp.jpa.entitymodels.Student;
import com.srp.jpa.env.FactoryProvider;
import org.hibernate.HibernateException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class StudentService implements StudentDAO {
    @Override
    public List<Student> getAllStudents() {

        EntityManager em = FactoryProvider.EMF.createEntityManager();

        try {
            em.getTransaction().begin();
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

    @Override
    public Student getStudentByEmail(String sEmail) {

        EntityManager em = FactoryProvider.EMF.createEntityManager();

        try {
            em.getTransaction().begin();
            Student s = em.find(Student.class, sEmail);
            em.getTransaction().commit();
            return s;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return null;
    }

    @Override
    public boolean validateStudent(String sEmail, String sPassword) {

        EntityManager em = FactoryProvider.EMF.createEntityManager();

        try {
            em.getTransaction().begin();
            Student s = em.find(Student.class, sEmail);
            em.getTransaction().commit();
            if (s == null) {
                return false;
            }
            return s.getsPass().equals(sPassword);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return false;
    }

    @Override
    public void registerStudentToCourse(String sEmail, int cId) {

        EntityManager em = FactoryProvider.EMF.createEntityManager();

        try {
            em.getTransaction().begin();
            Student student = em.find(Student.class, sEmail);
            Course course = em.find(Course.class, cId);
            if (!student.getsCourses().contains(course)) {
                student.getsCourses().add(course);
            }
            em.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Course> getStudentCourses(String sEmail) {

        EntityManager em = FactoryProvider.EMF.createEntityManager();

        try {
            em.getTransaction().begin();
            Student student = em.find(Student.class, sEmail);
            em.getTransaction().commit();
            return student.getsCourses();
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
