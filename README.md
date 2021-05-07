# Student Management System
This is basic student management app demonstrating JPA and JUnit skills. A valid user can log in with an email and password and choose courses to enroll in.

## Notes
* I have made several changes to SMSRunner both to make it work with my implementation and also to make it a bit more robust. 
* I have not made use of either of the other two "hint" files.
* The app creates 4 tables in the database: "Course" and "Student" for the entities, a join table "Student_Course" and an ID generation table, "SMS_SEQUENCE".
* In addition to the required classes, I have added a package-info.java file in the entitymodels package for the purpose of implementing a Hibernate generator for ID generation at the package level. I also have a package env with a helper class FactoryProvider to hold the EntityManagerFactory instead of having it in the Runner classes. Finally, there are two Runner classes. SteveSMSRunner is my own implementation. SMSRunner is the given "hint" implementation. Either one works, but I like mine better...

## Requirements
JVM 11+, Maven, and an SQL database. Currently setup for MariaDB on port 3306. Settings can be adjusted in persistence.xml.

## Setup
1. If database is not set up, create a schema named 'SMS'.
2. Set property hibernate.hbm2ddl.auto to "create" in src/main/resources/META-INF/persistence.xml.
3. Run either SteveSMSRunner or SMSRunner and attempt to login. App will fail, but tables should be created in the database.
4. Change property hibernate.hbm2ddl.auto to "update".
5. Run the two SQL scripts Course.sql and Student.SQL using the SMS schema.

## Run
1. Run SteveSMSRunner to use my implementation of the UI or SMSRunner to use the given UI.
2. Adjust terminal window a bit wider than 80 columns (~120 should be fine) if tables display incorrectly. 
