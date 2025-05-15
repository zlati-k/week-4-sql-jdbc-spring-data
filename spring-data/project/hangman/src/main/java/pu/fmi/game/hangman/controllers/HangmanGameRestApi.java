package pu.fmi.game.hangman.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pu.fmi.game.hangman.model.entity.HangmanGame;
import pu.fmi.game.hangman.model.entity.Status;
import pu.fmi.game.hangman.model.service.HangmanGameService;

@RestController
@RequestMapping("/hangman-games")
public class HangmanGameRestApi {

  public final HangmanGameService hangmanGameService;

  public HangmanGameRestApi(HangmanGameService hangmanGameService){
    this.hangmanGameService = hangmanGameService;
  }

  // POST /hangman-games
  @PostMapping
  public HangmanGame storeHangmanGame(@RequestParam Long playerId){
    return hangmanGameService.startNewGame(playerId);
  }

  // PUT /hangman-games/{id}/letter/{guessLetter}
  @PutMapping("/{id}/letter/{guessLetter}")
  public HangmanGame makeGuess(
      @PathVariable Long id,
      @PathVariable Character guessLetter
  ){
    return hangmanGameService.makeGuess(id, guessLetter);
  }

  // GET /hangman-games/{id}
  @GetMapping("/{id}")
  public HangmanGame getGameById(@PathVariable Long id) throws IllegalAccessException {
    return hangmanGameService.getGameById(id);
  }

  // GET /hangman-games
  @GetMapping
  public List<HangmanGame> fetchAllGames() {
    return hangmanGameService.getRecent10Games();
  }

  // GET /hangman-games/status
  @GetMapping("/status")
  public List<HangmanGame> fetchAllByStatus(@RequestParam Status status){
    return hangmanGameService.findAllByStatus(status);
  }

}
