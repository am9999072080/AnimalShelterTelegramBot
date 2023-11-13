package Telegram.Bot.AnimalShelter.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "adaptation_period")
public class Adaptation {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adaptationPeriodId")
    private List<Report> reports;

    public enum AnimalType {
        DOG,
        CAT
    }

    public enum Result {
        SUCCESSFUL,
        UNSUCCESSFUL,
        IN_PROGRESS,
        EXTENDED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id")
    private Long ownerId;
    @Column(name = "animal_id")
    private Long animalId;
    @Enumerated(EnumType.STRING)
    @Column(name = "animal_type")
    private AnimalType animalType;
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;

    @Column
    private LocalDate dateOfLastReport;
    @Enumerated(EnumType.STRING)
    @Column(name = "result")
    private Result result;

    public Adaptation(Long ownerId, Long animalId, AnimalType animalType, LocalDate startDate, LocalDate endDate, LocalDate dateOfLastReport, List<Report> reports, Result result) {

        this.ownerId = ownerId;
        this.animalId = animalId;
        this.animalType = animalType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dateOfLastReport = dateOfLastReport;
        this.reports = reports;
        this.result = result;
    }

    public Adaptation(List<Report> reports, Long id, Long ownerId, Long animalId, AnimalType animalType, LocalDate startDate, LocalDate endDate, LocalDate dateOfLastReport, Result result) {
        this.reports = reports;
        this.id = id;
        this.ownerId = ownerId;
        this.animalId = animalId;
        this.animalType = animalType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dateOfLastReport = dateOfLastReport;
        this.result = result;
    }
}
