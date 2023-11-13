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
@DiscriminatorValue("cat")
public class CatOwner extends Owner {

    @OneToMany(mappedBy = "ownerId")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Cat> catList;

    public CatOwner(List<Adaptation> adaptationPeriodList, Long id, Long telegramId, String firstName, String lastName, String phoneNumber, List<Cat> catList) {
        super(adaptationPeriodList, id, telegramId, firstName, lastName, phoneNumber);
        this.catList = catList;
    }

    public CatOwner(Long telegramId, String firstName, String lastName, String phoneNumber, List<Adaptation> adaptationPeriodList, List<Cat> catList) {
        super(telegramId, firstName, lastName, phoneNumber, adaptationPeriodList);
        this.catList = catList;
    }

    public CatOwner(User user) {
        super(user);
    }
}