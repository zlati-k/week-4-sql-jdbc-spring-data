package pu.fmi.game.hangman.model.entity;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class HangmanDTO {

  private Long id;
  private LocalDateTime startedOnDate;
  private String currentWord;
  private Status status;
  private String wrongLetters;
  private int wrongGuesses;
  private int currentWrongGuess;
  private String playerUsername;
}
