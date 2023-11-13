package Telegram.Bot.AnimalShelter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "owners")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "animal",
        discriminatorType = DiscriminatorType.STRING)
public class Owner {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ownerId")
    private List<Adaptation> adaptationPeriodList;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long telegramId;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String phoneNumber;


    public Owner(Long telegramId, String firstName, String lastName, String phoneNumber, List<Adaptation> adaptationPeriodList) {
        this.telegramId = telegramId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.adaptationPeriodList = adaptationPeriodList;
    }

    public Owner(User user) {
        this.setTelegramId(user.getTelegramId());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setPhoneNumber(user.getPhoneNumber());
    }
}