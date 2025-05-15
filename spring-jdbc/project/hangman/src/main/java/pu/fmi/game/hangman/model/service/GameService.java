package pu.fmi.game.hangman.model.service;

import java.util.List;
import pu.fmi.game.hangman.model.entity.HangmanGame;

public interface GameService {

  List<HangmanGame> getAllGames();

  HangmanGame startNewGame();

  HangmanGame makeGuess(Long id, char letter);

  HangmanGame getGameById(Long id);
}
