package pu.fmi.game.hangman.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "HANGMAN_GAME")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HangmanGame {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "GAME_ID", nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "WORD_ID", referencedColumnName = "ID")
  private Word word;

  @Column(name = "STARTED_ON_DATE")
  private LocalDateTime startedOnDate;

  @Column(name="CURRENT_WORD", nullable = false, length = 100)
  private String currentWord;

  @Enumerated(EnumType.STRING)
  private Status status;

  @Column(name = "WRONG_LETTERS")
  private String wrongLetters;

  @Column(name = "WORD_GUESSES", nullable = false)
  private int wrongGuesses;

  @Column(name = "CURRENT_WRONG_GUESS", nullable = false)
  private int currentWrongGuess;

  @ManyToOne
  @JoinColumn(name = "PLAYER_ID", referencedColumnName = "ID")
  private Player player;

  public List<String> getWrongLetters() {
    return wrongLetters == null || wrongLetters.isBlank()
        ? new ArrayList<>()
        : new ArrayList<>(Arrays.asList(wrongLetters.split(",")));
  }

  public void setWrongLetters(List<String> letters) {
    this.wrongLetters = String.join(",", letters);
  }
}
