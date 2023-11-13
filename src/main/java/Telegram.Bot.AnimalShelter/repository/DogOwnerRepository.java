package Telegram.Bot.AnimalShelter.repository;

import Telegram.Bot.AnimalShelter.entity.DogOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DogOwnerRepository extends JpaRepository<DogOwner, Long> {
    Optional<DogOwner> findByTelegramId(Long telegramId);
}
