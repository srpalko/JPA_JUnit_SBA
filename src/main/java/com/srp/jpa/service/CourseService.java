package com.srp.jpa.service;

import com.srp.jpa.dao.CourseDAO;
import com.srp.jpa.entitymodels.Course;
import com.srp.jpa.env.FactoryProvider;
import org.hibernate.HibernateException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Collection of methods for manipulating Course entities in the database.
 */
public class CourseService implements CourseDAO {
    /**
     * Gets a list of all currently offered courses in the database.
     *
     * @return A List of all courses or null if no courses are found.
     */
    @Override
    public List<Course> getAllCourses() {

        EntityManager em = FactoryProvider.EMF.createEntityManager();

        try {
            em.getTransaction().begin();
            TypedQuery<Course> query = em.createNamedQuery("getAllCourses", Course.class);
            List<Course> result = query.getResultList();
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
     * PLEASE NOTE: Extra added method for retrieving a course by its ID number. Used to check for a valid choice in
     * the enrollment menu.
     *
     * @param id ID of course to find
     * @return Course entity matching ID or null if not found.
     */
    @Override
    public Course getCourseById(int id) {

        EntityManager em = FactoryProvider.EMF.createEntityManager();

        try {
            em.getTransaction().begin();
            Course course = em.find(Course.class, id);
            em.getTransaction().commit();
            return course;
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
}
