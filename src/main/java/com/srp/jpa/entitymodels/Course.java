package com.srp.jpa.entitymodels;

import javax.persistence.*;

@Entity
@Table(name = "Course")
@NamedQuery(name="getAllCourses", query="select c from Course c")
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
    public int getcId() {
        return cId;
    }

    // Setter for cId should not be accessible as cId is auto-generated.
/*    public void setcId(int cId) {
        this.cId = cId;
    }*/

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcInstructorName() {
        return cInstructorName;
    }

    public void setcInstructorName(String cInstructorName) {
        this.cInstructorName = cInstructorName;
    }

    @Override
    public String toString() {
        return String.format("Course ID: %-11d | Name: %-50s | Instructor: %-50s", cId, cName, cInstructorName);
    }
}
