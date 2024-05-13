package jebi.hendardi.student.repository;

import jebi.hendardi.student.model.Student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    void deleteStudentById(Long id);

    List<Student> findStudentsByDepartment(String department);

    Page<Student> findStudentsByDepartment(String department, Pageable pageable);

    Optional<Student> findStudentById(Long id);
}
