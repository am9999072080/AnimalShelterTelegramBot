package Telegram.Bot.AnimalShelter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column
    private Long telegramId;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String phoneNumber;
    @Column
    private String shelterType;
    @Column
    private String shelterName;

    public User(Long telegramId, String firstName, String lastName, String phone) {
        this.telegramId = telegramId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phone;
    }
}
