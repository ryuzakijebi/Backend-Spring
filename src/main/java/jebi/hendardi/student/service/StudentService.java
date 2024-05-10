package jebi.hendardi.student.service;

import jakarta.transaction.Transactional;
import jebi.hendardi.student.exception.UserNotFoundException;
import jebi.hendardi.student.model.Student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import jebi.hendardi.student.repository.StudentRepository;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findPartialStudentsSortedById(int startIndex, int limit) {
        Page<Student> page = studentRepository.findAll(PageRequest.of(0, startIndex + limit, Sort.by("id")));
        List<Student> students = page.getContent();
        if (students.size() > startIndex) {
            return students.subList(startIndex, Math.min(startIndex + limit, students.size()));
        } else {
            return Collections.emptyList();
        }
    }
    

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudentById(Long id) {
        return studentRepository.findStudentById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteStudentById(id);
    }
}
