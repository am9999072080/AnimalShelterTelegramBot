package Telegram.Bot.AnimalShelter.repository;

import Telegram.Bot.AnimalShelter.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    Optional<Volunteer> findByTelegramId(Long telegramId);
}
