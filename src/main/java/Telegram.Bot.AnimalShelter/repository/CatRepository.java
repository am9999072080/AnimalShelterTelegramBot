package Telegram.Bot.AnimalShelter.repository;

import Telegram.Bot.AnimalShelter.entity.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    List<Cat> findAllByOwnerId(Long id);
}
