package pu.fmi.game.hangman.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pu.fmi.game.hangman.model.entity.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

}
