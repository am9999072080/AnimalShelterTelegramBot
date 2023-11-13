package Telegram.Bot.AnimalShelter.repository;

import Telegram.Bot.AnimalShelter.entity.Adaptation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdaptationRepository extends JpaRepository<Adaptation, Long> {

    List<Adaptation> findAllByOwnerId(Long ownerId);
}
