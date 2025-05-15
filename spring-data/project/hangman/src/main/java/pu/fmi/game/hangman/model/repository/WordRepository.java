package pu.fmi.game.hangman.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pu.fmi.game.hangman.model.entity.Word;

public interface WordRepository extends JpaRepository<Word, Long> {

}
