package Telegram.Bot.AnimalShelter.repository;

import Telegram.Bot.AnimalShelter.entity.CatOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatOwnerRepository extends JpaRepository<CatOwner, Long> {
    Optional<CatOwner> findByTelegramId(Long telegramId);
}
