package ma.enset.hospital;

import ma.enset.hospital.entities.Medecin;
import ma.enset.hospital.entities.Patient;
import ma.enset.hospital.repositories.MedecinRepository;
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
    CommandLineRunner start(PatientRepository patientRepository, MedecinRepository medecinRepository) {
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
            Stream.of("medecin1", "medecin2", "medecin3","medecin4")
                    .forEach(name -> {
                                Medecin medecin = new Medecin();
                                medecin.setName(name);
                                medecin.setSpeciality(Math.random() > 0.5 ? "Cardio" : "dentist");
                                medecin.setEmail(name + "@gmail.com");
                                medecinRepository.save(medecin);
                            }
                    );
        };
    }


}

