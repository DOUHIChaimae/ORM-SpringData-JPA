package ma.enset.hospital;

import ma.enset.hospital.entities.*;
import ma.enset.hospital.repositories.ConsultationRepository;
import ma.enset.hospital.repositories.MedecinRepository;
import ma.enset.hospital.repositories.PatientRepository;
import ma.enset.hospital.repositories.RendezVousRepository;
import ma.enset.hospital.services.IHospitalService;
import ma.enset.hospital.services.UserService;
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
    CommandLineRunner start(IHospitalService hospitalService, PatientRepository patientRepository, RendezVousRepository rendezVousRepository, MedecinRepository medecinRepository, UserService userService) {
        return args -> {
            Stream.of("patient1", "patient2", "patient3")
                    .forEach(name -> {
                        Patient patient = new Patient();
                        patient.setName(name);
                        patient.setBirthday(new Date());
                        patient.setIsSick(false);
                        hospitalService.savePatient(patient);
                    });
            Stream.of("medecin1", "medecin2", "medecin3", "medecin4")
                    .forEach(name -> {
                                Medecin medecin = new Medecin();
                                medecin.setName(name);
                                medecin.setSpeciality(Math.random() > 0.5 ? "Cardio" : "dentist");
                                medecin.setEmail(name + "@gmail.com");
                                hospitalService.saveMedecin(medecin);
                            }
                    );
            Patient patient = patientRepository.findAll().get(0);
            Patient patient1 = patientRepository.findByName("patient1");
            Medecin medecin = medecinRepository.findByName("medecin2");
            RendezVous rendezVous = new RendezVous();
            rendezVous.setDate(new Date());
            rendezVous.setStatus(RDVStatus.PENDING);
            rendezVous.setMedecin(medecin);
            rendezVous.setPatient(patient);
            hospitalService.saveRDV(rendezVous);
            Consultation consultation = new Consultation();
            consultation.setConsultationDate(new Date());
            consultation.setRendezVous(rendezVous);
            consultation.setRapport("Rapport de la consultation..");
            hospitalService.saveConsultation(consultation);

            User user1 = new User();
            user1.setUsername("user1");
            user1.setPassword("user123");
            userService.addNewUser(user1);

            User user2 = new User();
            user2.setUsername("user2");
            user2.setPassword("admin123");
            userService.addNewUser(user2);

            Stream.of("STUDENT", "USER", "ADMIN")
                    .forEach(role -> {
                        Role role1 = new Role();
                        role1.setRoleName(role);
                        userService.addNewRole(role1);

                    });
            userService.addRoleToUser("user1", "STUDENT");
            userService.addRoleToUser("user1", "USER");
            userService.addRoleToUser("admin", "USER");
            userService.addRoleToUser("admin", "ADMIN");


        };
    }

}

