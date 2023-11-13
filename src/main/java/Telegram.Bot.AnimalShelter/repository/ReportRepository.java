package Telegram.Bot.AnimalShelter.repository;

import Telegram.Bot.AnimalShelter.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByReceiveDateAndAdaptationPeriodId(LocalDate date, Long id);

    List<Report> findByAdaptationPeriodId(Long adaptationPeriod);

}
