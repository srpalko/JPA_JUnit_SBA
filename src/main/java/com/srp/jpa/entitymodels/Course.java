package com.srp.jpa.entitymodels;

import javax.persistence.*;
import java.util.Objects;

/**
 * Entity representing Courses
 */
@Entity
@Table(name = "Course")
@NamedQuery(name = "getAllCourses", query = "select c from Course c")
public class Course {

    /* FIELDS */
    @Id
    /*The generator used here can be found in the package-info.java file in this package. This generator uses
     * a sequence for generating id's if supported by the database, and a simulated sequence if not. */
    @GeneratedValue(generator = "ID_GENERATOR")
    @Column(name = "id")
    private int cId;

    // Sets NOT NULL in DB and sets VARCHAR to (50) instead of (255)
    @Column(name = "name", nullable = false, length = 50)
    private String cName;

    @Column(name = "instructor", nullable = false, length = 50)
    private String cInstructorName;


    /* CONSTRUCTORS */
    public Course() {
        cName = "";
        cInstructorName = "";
    }

    /* Instructions indicated an all args constructor, however this would not work with an auto-generated id.*/
    public Course(String cName, String cInstructorName) {
        this.cName = cName;
        this.cInstructorName = cInstructorName;
    }

    /* GETTERS/SETTERS*/
    public int getCId() {
        return cId;
    }

    // Setter for cId should not be accessible as cId is auto-generated. But it is here!
/*    public void setCId(int cId) {
        this.cId = cId;
    }*/

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public String getCInstructorName() {
        return cInstructorName;
    }

    public void setCInstructorName(String cInstructorName) {
        this.cInstructorName = cInstructorName;
    }

    @Override
    public String toString() {
        return String.format("Course ID: %-11d | Name: %-50s | Instructor: %-50s", cId, cName, cInstructorName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return cId == course.cId && Objects.equals(cName, course.cName) && Objects.equals(cInstructorName, course.cInstructorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cId, cName, cInstructorName);
    }
}
