package jebi.hendardi.student.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jebi.hendardi.student.model.Student;
import jebi.hendardi.student.service.StudentService;
import jebi.hendardi.student.service.DataGenerator;
import java.io.IOException;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    private final JobLauncher jobLauncher;
    private final Job job;

    public StudentController(StudentService studentService, JobLauncher jobLauncher, Job job) {
        this.studentService = studentService;
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.findAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/partial/{pageIndex}/{pageSize}")
    public ResponseEntity<List<Student>> getPartialStudents(@PathVariable("pageIndex") int pageIndex,
            @PathVariable("pageSize") int pageSize) {
        int startIndex = (pageIndex * pageSize);
        List<Student> students = studentService.findPartialStudentsSortedById(startIndex, pageSize);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
   

    @GetMapping("/find/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {
        Student student = studentService.findStudentById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student newStudent = studentService.addStudent(student);
        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Long id, @RequestBody Student updatedStudent) {
        Student existingStudent = studentService.findStudentById(id);
        if (existingStudent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updatedStudent.setId(id);                                                                                                                                                       
        Student updated = studentService.updateStudent(updatedStudent);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/batch")
    public void importCsvToDBJob() {
        JobParameters jobParameter = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();

        try {
            jobLauncher.run(job, jobParameter);
        } catch (JobExecutionAlreadyRunningException
                | JobRestartException
                | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException e) {
            e.printStackTrace();
        }

    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateStudentsCSV() {
        try {
            List<Student> students = DataGenerator.generateStudents();
            DataGenerator.writeStudentsToCSV(students);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to generate students CSV", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Students CSV generated successfully", HttpStatus.OK);
    }

}
