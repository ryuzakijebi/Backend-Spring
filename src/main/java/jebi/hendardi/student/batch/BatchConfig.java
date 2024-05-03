package jebi.hendardi.student.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;
import jebi.hendardi.student.model.Student;


@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public ItemProcessor<Student, Student> processor() {
        return new StudentItemProcessor();
    }
    
    public class StudentItemProcessor implements ItemProcessor<Student, Student> {

        @Override
        public Student process(Student student) throws Exception {
            return student;
        }
    }

    @Bean
    public JpaItemWriter<Student> entityWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Student> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public Step myStep(DataGenerator dataGenerator, JpaTransactionManager transactionManager,
            JobRepository jobRepository,
            ItemProcessor<Student, Student> processor,
            JpaItemWriter<Student> entityWriter) {
        return new StepBuilder("myStep", jobRepository)
                .<Student, Student>chunk(100, transactionManager)
                .reader(new ListItemReader<>(dataGenerator.generateRandomData())) 
                .processor(processor)
                .writer(entityWriter)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public Job myJob(JobRepository jobRepository, Step step) {
        return new JobBuilder("job", jobRepository)
                .start(step)
                .build();
    }
}
