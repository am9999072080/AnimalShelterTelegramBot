package Telegram.Bot.AnimalShelter.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "cat_shelter")
public class CatShelter {

    @OneToMany(mappedBy = "shelterId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Cat> list;
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

    public CatShelter(String aboutMe, String location, String name, String safety, String security, String workingHours) {
        this.aboutMe = aboutMe;
        this.location = location;
        this.name = name;
        this.safety = safety;
        this.security = security;
        this.workingHours = workingHours;
    }

    public CatShelter(List<Cat> list, Long id, String aboutMe, String location, String name, String safety, String security, String workingHours) {
        this.list = list;
        this.id = id;
        this.aboutMe = aboutMe;
        this.location = location;
        this.name = name;
        this.safety = safety;
        this.security = security;
        this.workingHours = workingHours;
    }

    public CatShelter(Long id, String aboutMe, String location, String name, String safety, String security, String workingHours) {
        this.id = id;
        this.aboutMe = aboutMe;
        this.location = location;
        this.name = name;
        this.safety = safety;
        this.security = security;
        this.workingHours = workingHours;


    }

}
