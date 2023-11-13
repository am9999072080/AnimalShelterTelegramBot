package Telegram.Bot.AnimalShelter.repository;

import Telegram.Bot.AnimalShelter.entity.DogShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DogShelterRepository extends JpaRepository<DogShelter, Long> {
    Optional<DogShelter> findByName(String name);
}
