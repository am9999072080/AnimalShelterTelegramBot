package Telegram.Bot.AnimalShelter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "owner_report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long adaptationPeriodId;
    @Column
    private LocalDate receiveDate;
    @Column
    private String photoId;
    @Column
    private String foodRation;
    @Column
    private String animalHealth;
    @Column
    private String animalBehavior;

    public Report(Long adaptationPeriodId, LocalDate receiveDate, String photoId, String foodRation, String animalHealth, String animalBehavior) {
        this.adaptationPeriodId = adaptationPeriodId;
        this.receiveDate = receiveDate;
        this.photoId = photoId;
        this.foodRation = foodRation;
        this.animalHealth = animalHealth;
        this.animalBehavior = animalBehavior;
    }
}
