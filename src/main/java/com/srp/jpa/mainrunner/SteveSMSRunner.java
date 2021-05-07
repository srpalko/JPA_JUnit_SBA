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
public class SteveSMSRunner {
    public static void main(String[] args) {

        /*Services and Scanner for app*/
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        Scanner input = new Scanner(System.in);

        System.out.println("Student Management System");
        System.out.println("---------------------");

        boolean validEmail;
        String email;
        String password;
        Student student;

        /*Login to the system---------------------------------
        * User is asked to enter their email or q to quit.
        * User is then asked for password. Credentials are checked against the database.
        * A valid login results in the program continuing, invalid login results in a loop.*/
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

        /*Course registration-----------------------------------
        * Current course enrollment is displayed for the current user and options are displayed.
       */
        while (true) {
            System.out.println("Current courses for " + student.getsName());
            System.out.println("---------------------------------");
            List<Course> studentCourses = studentService.getStudentCourses(email);
            displayList(studentCourses);
            int choice;
            /*User must choose an option*/
            do {
                System.out.print("Options: (1)Register for course (2)Logout: ");
                choice = input.nextInt();
            } while (choice < 1 || choice > 2);

            /*All available courses are displayed. User enters their choice.*/
            if (choice == 1) {
                System.out.println("\n");
                List<Course> allCourses = courseService.getAllCourses();
                allCourses.removeAll(studentCourses);
                displayList(allCourses);
                System.out.print("Which course would you like to enroll in?: ");
                int courseNum = input.nextInt();
                Course course = courseService.getCourseById(courseNum);
                /*Check if the choice is a valid course*/
                if (course == null) {
                    System.out.println("\n\nInvalid course ID\n\n");
                    continue;
                }
                /*Check if the student has already enrolled in the course
                * registerStudentToCourse() will return false if it was unable to enroll due
                * to duplicate enrollment.*/
                boolean isNowEnrolled = studentService.registerStudentToCourse(email, courseNum);
                if (!isNowEnrolled) {
                    System.out.println("\n\nYou are already enrolled in that course.");
                    System.out.println();
                    continue;
                }
                /*Successful enrollment. Menu loops until user quits.*/
                System.out.println("\nSuccessful enrollment in " + course.getcName());
                System.out.println();
            } else {
                System.out.println("\n\nHave a nice day!\n\n");
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
