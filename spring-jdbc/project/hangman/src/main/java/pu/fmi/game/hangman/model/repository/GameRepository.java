package pu.fmi.game.hangman.model.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pu.fmi.game.hangman.model.entity.HangmanGame;

@Repository
public class GameRepository {

  private final JdbcTemplate jdbcTemplate;

  public GameRepository(JdbcTemplate jdbcTemplate){
    this.jdbcTemplate = jdbcTemplate;
  }

  public void saveGame(HangmanGame hangmanGame){
    String query = "INSERT INTO HANGMAN_GAME "
        + "(WORD_ID, STARTED_ON_DATE, CURRENT_WORD, "
        + "STATUS, WRONG_GUESSES, CURRENT_WRONG_GUESS, PLAYER_ID) "
        + "VALUES(?, ?, ?, ?, ?, ?, ?);";

    jdbcTemplate.update(query,
        1,
        hangmanGame.getStartedOnDate(),
        hangmanGame.getCurrentWord(),
        hangmanGame.getStatus().toString(),
        hangmanGame.getWrongGuesses(),
        hangmanGame.getCurrentWrongGuess(),
        1
    );
  }

  public List<HangmanGame> getAllGames(){

    return jdbcTemplate.query("SELECT * FROM HANGMAN_GAME", (rs, rowNum) -> {
      HangmanGame hangmanGame = new HangmanGame();
      hangmanGame.setId(rs.getLong("ID"));
      hangmanGame.setCurrentWord(rs.getString("CURRENT_WORD"));
      hangmanGame.setWrongGuesses(rs.getInt("WRONG_GUESSES"));
      return hangmanGame;
    });
  }

  public HangmanGame getGameById(Long id) {

    String query = "SELECT * FROM HANGMAN_GAME WHERE ID = " + id;

    return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
          HangmanGame hangmanGame = new HangmanGame();
          hangmanGame.setId( rs.getLong("ID"));
          hangmanGame.setCurrentWord(rs.getString("CURRENT_WORD"));
          hangmanGame.setWrongGuesses(rs.getInt("WRONG_GUESSES"));
          return hangmanGame;
        });
  }

}
