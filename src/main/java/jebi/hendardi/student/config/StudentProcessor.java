package jebi.hendardi.student.config;

import org.springframework.batch.item.ItemProcessor;

import jebi.hendardi.student.model.Student;

public class StudentProcessor implements ItemProcessor<Student, Student>{

    @Override
    public Student process(Student student) throws Exception {
        return student;
    }
    
}
