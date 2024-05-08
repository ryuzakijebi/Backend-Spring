package jebi.hendardi.student.service;

import jebi.hendardi.student.model.Student;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    private static final String[] NAME_PARTS = {"Sa", "Ku", "Ra", "Ma", "Na", "Ta", "I", "Yu", "Chi", "Ri", "Ka", "O", "E", "No", "Fu", "Me"};

    public static List<Student> generateStudents() {
        List<Student> students = new ArrayList<>();
        Random random = new Random();

        String[] departments = {"Informatics Engineering", "Computer Engineering", "Information Systems", "Software Engineering", "Digital Business"};
        for (String department : departments) {
            for (int i = 0; i < 200000; i++) {
                Student student = new Student();
                String firstName = generateName(random);
                String lastName = generateName(random);
                String fullName = capitalizeFirstLetter(firstName) + " " + capitalizeFirstLetter(lastName);
                student.setName(fullName);
                student.setStudentID(generateRandomStudentID());
                student.setEmail(generateRandomEmail(firstName, lastName));
                student.setDepartment(department);
                students.add(student);
            }
        }

        return students;
    }

    private static String generateName(Random random) {
        StringBuilder name = new StringBuilder();
        String firstPart = NAME_PARTS[random.nextInt(NAME_PARTS.length)];
        String secondPart = NAME_PARTS[random.nextInt(NAME_PARTS.length)];
        name.append(capitalizeFirstLetter(firstPart));
        name.append(secondPart.toLowerCase());
        return name.toString();
    }

    public static void writeStudentsToCSV(List<Student> students) throws IOException {
        FileWriter writer = new FileWriter("src/main/resources/students.csv");
        writer.append("Name,StudentID,Email,Department\n");
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
        int min = 10000000;
        int max = 999999999;
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

    private static String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
