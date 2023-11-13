package Telegram.Bot.AnimalShelter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("dog")
public class DogOwner extends Owner {
    @OneToMany(mappedBy = "ownerId")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Dog> dogList;

    public DogOwner(List<Adaptation> adaptationPeriodList, Long id, Long telegramId, String firstName, String lastName, String phoneNumber, List<Dog> dogList) {
        super(adaptationPeriodList, id, telegramId, firstName, lastName, phoneNumber);
        this.dogList = dogList;
    }

    public DogOwner(Long telegramId, String firstName, String lastName, String phoneNumber, List<Adaptation> adaptationPeriodList, List<Dog> dogList) {
        super(telegramId, firstName, lastName, phoneNumber, adaptationPeriodList);
        this.dogList = dogList;
    }

    public DogOwner(User user) {
        super(user);
    }
}
