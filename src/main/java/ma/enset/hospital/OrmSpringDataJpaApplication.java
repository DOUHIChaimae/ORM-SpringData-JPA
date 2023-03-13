package ma.enset.hospital;

import ma.enset.hospital.entities.Patient;
import ma.enset.hospital.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class OrmSpringDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrmSpringDataJpaApplication.class, args);
    }

    @Bean
    CommandLineRunner start(PatientRepository patientRepository) {
        return args -> {
            Stream.of("douhi1", "douhi2", "douhi3")
                    .forEach(name -> {
                                Patient patient = new Patient();
                                patient.setName(name);
                                patient.setBirthday(new Date());
                                patient.setScore((int) Math.random() * 100);
                                patient.setIsSick(false);
                                patientRepository.save(patient);
                            }
                    );
        };
    }


}

