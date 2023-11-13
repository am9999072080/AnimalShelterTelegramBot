package Telegram.Bot.AnimalShelter.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "dog_shelter")
public class DogShelter {


    @OneToMany(mappedBy = "shelterId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Dog> list;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String aboutMe;

    @Column
    private String location;

    @Column
    private String name;

    @Column
    private String safety;

    @Column
    private String security;

    @Column
    private String workingHours;

    public DogShelter(String aboutMe, String location, String name, String safety, String security, String workingHours) {
        this.aboutMe = aboutMe;
        this.location = location;
        this.name = name;
        this.safety = safety;
        this.security = security;
        this.workingHours = workingHours;
    }

    public DogShelter(Long id, String aboutMe, String location, String name, String safety, String security, String workingHours) {
        this.id = id;
        this.aboutMe = aboutMe;
        this.location = location;
        this.name = name;
        this.safety = safety;
        this.security = security;
        this.workingHours = workingHours;
    }

    public DogShelter(List<Dog> list, Long id, String aboutMe, String location, String name, String safety, String security, String workingHours) {
        this.list = list;
        this.id = id;
        this.aboutMe = aboutMe;
        this.location = location;
        this.name = name;
        this.safety = safety;
        this.security = security;
        this.workingHours = workingHours;
    }
}
