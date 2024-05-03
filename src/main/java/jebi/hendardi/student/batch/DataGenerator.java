package jebi.hendardi.student.batch;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Component;

import jebi.hendardi.student.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component 
@StepScope
public class DataGenerator {

    private final List<String> departments = List.of(
            "Computer Engineering",
            "Software Engineering",
            "Digital Business",
            "Information Systems",
            "Computer Science"
    );

    private final Random random = new Random();

    public List<Student> generateRandomData() {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            Student student = new Student();
            student.setName(generateRandomName());
            student.setStudentID(generateRandomStudentID());
            student.setEmail(generateRandomEmail(student.getName()));
            student.setDepartment(departments.get(random.nextInt(departments.size())));
            students.add(student);
        }
        return students;
    }

    private String generateRandomName() {
        String[] firstNames = {"John", "Emma", "Michael", "Olivia", "James", "Sophia", "Liam", "Ava", "Noah", "Isabella", "Lucas", "Mia", "William", "Ella", "Benjamin", "Lily", "Christopher", "Victoria", "Andrew", "Sophie", "Logan", "Nathan", "Hannah", "Isaac", "Gabriel", "Madison", "Edward", "Avery", "Chloe", "Evelyn", "Jack", "Grace", "Aiden", "Daniel", "Sophia", "Jacob", "Ella", "Daniel", "Lucy", "Jackson", "Aria"};

        String[] lastNames = {"Smith", "Johnson", "Brown", "Williams", "Miller", "Taylor", "Wilson", "Moore", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson", "Clark", "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen", "Young", "Hernandez", "King", "Wright", "Lopez", "Hill", "Scott", "Green", "Adams", "Baker", "Nelson", "Carter", "Mitchell", "Perez", "Roberts", "Turner", "Phillips", "Campbell", "Parker"};

        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];

        return firstName + " " + lastName;
    }

    private String generateRandomStudentID() {
        StringBuilder studentID = new StringBuilder();
        studentID.append("ID");
        for (int i = 0; i < 6; i++) {
            studentID.append(random.nextInt(10));
        }
        return studentID.toString();
    }

    private String generateRandomEmail(String name) {
        String[] domains = {"example.com", "test.com", "school.edu", "mail.org", "company.net"};
        String[] nameParts = name.split(" ");
        String firstName = nameParts[0].toLowerCase();
        String lastName = nameParts[1].toLowerCase();
        String domain = domains[random.nextInt(domains.length)];
        int randomNumber = random.nextInt(100);
        return firstName + lastName + randomNumber + "@" + domain;
    }
}