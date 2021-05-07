package com.srp.jpa.entitymodels;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entity that represents Students
 */
@Entity
@Table(name = "Student")
@NamedQueries({
        @NamedQuery(name="getAllStudents", query = "select s from Student s"),
        @NamedQuery(name ="getStudentByEmail", query = "select s from Student s where s.sEmail = :email")
})
public class Student {

    /* FIELDS */
    @Id
    @Column(name = "email", nullable = false, length = 50) // Set NOT NULL and VARCHAR(50)
    private String sEmail;

    @Column(name = "name", nullable = false, length = 50)
    private String sName;

    @Column(name = "password", nullable = false, length = 50)
    private String sPass;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    private List<Course> sCourses;


    /* CONSTRUCTORS */
    public Student() {
        sCourses = new ArrayList<>();
    }

    public Student(String sEmail, String sName, String sPass) {
        this();
        this.sEmail = sEmail;
        this.sName = sName;
        this.sPass = sPass;
    }


    /* GETTERS/SETTERS */
    public String getSEmail() {
        return sEmail;
    }

    /* Email should not be changed after creation as it is the primary key in the database.*/
/*    public void setSEmail(String sEmail) {
        this.sEmail = sEmail;
    }*/

    public String getSName() {
        return sName;
    }

    public void setSName(String sName) {
        this.sName = sName;
    }

    public String getSPass() {
        return sPass;
    }

    public void setSPass(String sPass) {
        this.sPass = sPass;
    }

    public List<Course> getSCourses() {
        return sCourses;
    }

    public void setSCourses(List<Course> sCourses) {
        this.sCourses = sCourses;
    }

    @Override
    public String toString() {
        return String.format("Student: %-50s | Email: %-50s | Password: %-50s", sName, sEmail, sPass);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return sEmail.equals(student.sEmail) && sName.equals(student.sName) && sPass.equals(student.sPass) && sCourses.equals(student.sCourses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sEmail, sName, sPass, sCourses);
    }
}
