package ma.enset.hospital;

import ma.enset.hospital.entities.*;
import ma.enset.hospital.repositories.ConsultationRepository;
import ma.enset.hospital.repositories.MedecinRepository;
import ma.enset.hospital.repositories.PatientRepository;
import ma.enset.hospital.repositories.RendezVousRepository;
import ma.enset.hospital.services.IHospitalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class OrmSpringDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrmSpringDataJpaApplication.class, args);
    }

    @Bean
    CommandLineRunner start(IHospitalService hospitalService, PatientRepository patientRepository, RendezVousRepository rendezVousRepository, MedecinRepository medecinRepository) {
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
        };
    }

}

