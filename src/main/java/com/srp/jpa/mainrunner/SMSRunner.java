/*
 * Filename: SMSRunner.java
 * Author: Stefanski
 * 02/25/2020
 */
package com.srp.jpa.mainrunner;

import com.srp.jpa.entitymodels.Course;
import com.srp.jpa.entitymodels.Student;
import com.srp.jpa.env.FactoryProvider;
import com.srp.jpa.service.CourseService;
import com.srp.jpa.service.StudentService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.lang.System.out;


/**
 * I've altered to code a bit to make it more robust and to make it function with the other classes in the
 * application. Mainly, I've added some try-catch blocks to avoid uncaught exceptions from bad input. It's
 * not 100% great. I have also implemented my own UI (SteveSMSRunner) that runs a bit more robustly.
 * Either class will run the app.
 *
 * @author Harry
 */
public class SMSRunner {

    private Scanner sin;
    private StringBuilder sb;

    private CourseService courseService;
    private StudentService studentService;
    private Student currentStudent;

    public SMSRunner() {
        sin = new Scanner(System.in);
        sb = new StringBuilder();
        courseService = new CourseService();
        studentService = new StudentService();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        SMSRunner sms = new SMSRunner();
        sms.run();
        FactoryProvider.shutdownFactory(); // ADDED LINE TO MAKE SURE FACTORY IS SHUT DOWN.
        out.println("Shutting down...");
    }

    private void run() {
        // Login or quit
        switch (menu1()) {
            case 1:
                if (studentLogin()) {
                    registerMenu();
                }
                break;
            case 2:
                out.println("Goodbye!");
                break;

            default:

        }
    }

    /**
     * PLEASE NOTE: Added try-catch to avoid uncaught InputMismatchException. Currently it forces a
     * return of 2 to quit.  It should maybe be in a loop though.
     *
     * @return int representing menu choice.
     */
    private int menu1() {
        try {
            sb.append("\n1.Student Login\n2. Quit Application\nPlease Enter Selection: ");
            out.print(sb);
            sb.delete(0, sb.length());
            return sin.nextInt();
        } catch (InputMismatchException e) {
            out.println("Invalid entry");
        }
        return 2;
    }

    /**
     * The logic for this method is already implemented in the StudentService.validateStudent method. I added
     * it here and commented out the remainder of the method.
     *
     * @return true if email and password are valid.
     */
    private boolean studentLogin() {
        /*boolean retValue = false;*/
        out.print("Enter your email address: ");
        String email = sin.next();
        out.print("Enter your password: ");
        String password = sin.next();
        boolean retValue = studentService.validateStudent(email, password); // ADDED LINE

        Student student = studentService.getStudentByEmail(email);
        if (student != null && retValue) {
            currentStudent = student;
            Stream<Course> courseStream = currentStudent.getSCourses().stream();
            courseStream.forEach(out::println);
        }

/*		if (currentStudent != null && currentStudent.getsPass().equals(password)) {
			List<Course> courses = studentService.getStudentCourses(email);
			out.println("MyClasses");
			for (Course course : courses) {
				out.println(course);
			}
			retValue = true;
		} else {
			out.println("User Validation failed. GoodBye!");
		}*/
        if (!retValue) {
            out.println("User Validation failed. GoodBye!");
        }
        return retValue;
    }

    private void registerMenu() {
        sb.append("\n1.Register a class\n2. Logout\nPlease Enter Selection: ");
        out.print(sb);
        sb.delete(0, sb.length());

        try {
            switch (sin.nextInt()) {
                case 1:
                    List<Course> allCourses = courseService.getAllCourses();
                    List<Course> studentCourses = studentService.getStudentCourses(currentStudent.getSEmail());
                    allCourses.removeAll(studentCourses);
                    out.printf("%5s%15S%15s\n", "ID", "Course", "Instructor");
                    for (Course course : allCourses) {
                        out.println(course);
                    }
                    out.println();
                    out.print("Enter Course Number: ");
                    int number = sin.nextInt();
                    Course newCourse = courseService.getCourseById(number);

                    if (newCourse != null) {
                        studentService.registerStudentToCourse(currentStudent.getSEmail(), number);
                        Student temp = studentService.getStudentByEmail(currentStudent.getSEmail());

                        List<Course> sCourses = studentService.getStudentCourses(currentStudent.getSEmail());


                        out.println("MyClasses");
                        for (Course course : sCourses) {
                            out.println(course);
                        }
                    }
                    break;
                case 2:
                    out.println("Goodbye!");
                    break;
                default:
                    throw new InputMismatchException();
            }
        } catch (InputMismatchException e) {
            out.println("Invalid Input");
        }
        out.println();
    }
}
