package com.srp.jpa.mainrunner;

import com.srp.jpa.entitymodels.Course;
import com.srp.jpa.entitymodels.Student;
import com.srp.jpa.env.FactoryProvider;
import com.srp.jpa.service.CourseService;
import com.srp.jpa.service.StudentService;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Main application class. EntityManagerFactory is handled by the FactoryProvider class in the env package.
 * Basic school management system where students can register for courses and view the courses assigned
 * to them.
 */
public class SMSRunner {
    public static void main(String[] args) {

        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        Scanner input = new Scanner(System.in);

        System.out.println("Student Management System");
        System.out.println("---------------------");
        boolean validEmail = false;
        String email;
        String password;
        Student student;

        do {
            System.out.print("Enter email or 'q' to quit: ");
            email = input.nextLine();
            if (email.charAt(0) == 'q') {
                System.out.println("Goodbye");
                FactoryProvider.shutdownFactory();
                System.exit(0);
            }
            System.out.print("Enter password: ");
            password = input.nextLine();
            validEmail = studentService.validateStudent(email, password);
            if (!validEmail) {
                System.out.println("Invalid credentials");
            }
        } while (!validEmail);
        student = studentService.getStudentByEmail(email);
        System.out.println("Login successful");
        System.out.println();
        System.out.println("Current courses for " + student.getsName());
        displayList(studentService.getStudentCourses(email));

        while (true) {

            int choice;
            do {
                System.out.print("Options: (1)Register for course (2)Logout: ");
                choice = input.nextInt();
            } while (choice < 1 || choice > 2);

            if (choice == 1) {
                displayList(courseService.getAllCourses());
                System.out.print("Which course would you like to enroll in?: ");
                int courseNum = input.nextInt();
                Course course = courseService.getCourseById(courseNum);
                studentService.registerStudentToCourse(email, courseNum);
                // TODO: 5/6/2021 FIX EXTRA DUPLICATE ENROLLMENTS 
/*                if (student.getsCourses().contains()) {
                }*/
                System.out.println("Successful enrollment in " + course.getcName());
                displayList(studentService.getStudentCourses(email));
            } else {
                System.out.println("Have a nice day!");
                FactoryProvider.shutdownFactory();
                System.exit(0);
            }
        }
    }


    /**
     * Displays result lists to the console.
     *
     * @param list List of items to display.
     */
    private static void displayList(List<?> list) {
        Stream<?> stream = list.stream();
        stream.forEach(System.out::println);
    }
}
