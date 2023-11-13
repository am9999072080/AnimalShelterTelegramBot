package Telegram.Bot.AnimalShelter.repository;

import Telegram.Bot.AnimalShelter.entity.CatShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatShelterRepository extends JpaRepository<CatShelter, Long> {

    Optional<CatShelter> findByName(String name);
}
