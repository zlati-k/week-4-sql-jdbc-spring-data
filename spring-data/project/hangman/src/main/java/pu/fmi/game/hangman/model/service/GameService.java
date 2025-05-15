package pu.fmi.game.hangman.model.service;

import java.util.List;
import pu.fmi.game.hangman.model.entity.HangmanDTO;
import pu.fmi.game.hangman.model.entity.HangmanGame;
import pu.fmi.game.hangman.model.entity.Status;

public interface GameService {

  HangmanGame startNewGame(Long playerId);

  HangmanGame makeGuess(Long id, char letter);

  List<HangmanGame> findAllByStatus(Status status);

  HangmanGame getGameById(Long id);

  List<HangmanGame> getAllGames();

  List<HangmanGame> getRecent10Games();
}
