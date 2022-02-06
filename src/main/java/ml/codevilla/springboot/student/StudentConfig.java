package ml.codevilla.springboot.student;

/*Create Config class
    [30]-StudentConfig and annotate @Configuration

        @Bean - by making it a bean it will be pickup by spring Context
        CommandLineRunner commandLineRunner(StudentRepo studentRepo) {
            return args ->{
                Student one = new Student(pass arguments);
                Student two = new Student(pass arguments);
            };
            studentRepo.saveAll(
                List.of(one,two)
           );
        }
    [31]- @Transient..you dont want age to be stored in our database:
        It can be calculated from DOB.
        go to-> Student Class


 */
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args -> {
            Student mariam = new Student(
                    "Mariam",
                    "mariam.jamal@gmail.com",
                    LocalDate.of(2000, FEBRUARY, 5)
            );
            Student alex = new Student(
                    "Alex",
                    "alex@gmail.com",
                    LocalDate.of(2004, FEBRUARY, 5)
            );

            repository.saveAll(
                    List.of(mariam,alex)
            );
        };
    }
}
