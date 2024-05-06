package jebi.hendardi.student.service;


import jebi.hendardi.student.model.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    public static List<Student> generateStudents() {
        List<Student> students = new ArrayList<>();
        Random random = new Random();

        // Generate unique names
        String[] firstNames = {"John", "Emma", "Michael", "Sophia", "William", "Olivia", "James", "Ava", "Robert", "Mia"};
        String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez"};

        // Generate 500 students for each department
        String[] departments = {"Informatics Engineering", "Computer Engineering", "Information Systems", "Software Engineering", "Digital Business"};
        for (String department : departments) {
            for (int i = 0; i < 500; i++) {
                Student student = new Student();
                String firstName = firstNames[random.nextInt(firstNames.length)];
                String lastName = lastNames[random.nextInt(lastNames.length)];
                String fullName = firstName + " " + lastName;
                student.setName(fullName);
                student.setStudentID(generateRandomStudentID());
                student.setEmail(generateRandomEmail(firstName, lastName));
                student.setDepartment(department);
                students.add(student);
            }
        }

        return students;
    }

    public static void writeStudentsToCSV(List<Student> students) throws IOException {
        FileWriter writer = new FileWriter("src/main/resources/students.csv");
        writer.append("Name,Student ID,Email,Department\n");
        for (Student student : students) {
            writer.append(student.getName()).append(",");
            writer.append(student.getStudentID()).append(",");
            writer.append(student.getEmail()).append(",");
            writer.append(student.getDepartment()).append("\n");
        }
        writer.flush();
        writer.close();
    }

    private static String generateRandomStudentID() {
        Random random = new Random();
        int min = 10000000; // 8 digit minimum
        int max = 999999999; // 10 digit maximum
        int studentID = random.nextInt(max - min + 1) + min;
        return String.valueOf(studentID);
    }

    private static String generateRandomEmail(String firstName, String lastName) {
        Random random = new Random();
        String[] emailProviders = {"gmail.com", "yahoo.com", "ymail.com", "outlook.com"};
        String emailProvider = emailProviders[random.nextInt(emailProviders.length)];
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@" + emailProvider;
        return email;
    }
}
