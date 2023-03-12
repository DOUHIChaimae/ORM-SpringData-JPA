package ma.enset.ormspringdatajpa.repositories;

import ma.enset.ormspringdatajpa.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByIsSick(Boolean isSick);

    Page<Patient> findByIsSick(Boolean isSick, Pageable pageable);

    List<Patient> findByIsSickAndScoreLessThan(Boolean isSick, int score);

    List<Patient> findByIsSickIsTrueAndScoreLessThan(int score);

    List<Patient> findByBirthdayBetween(Date date1, Date date2);

    @Query("select p from Patient p where p.name like :x and p.score = :y")
    List<Patient> searchPatients(@Param("x") String nom, @Param("y") int sc);
}
