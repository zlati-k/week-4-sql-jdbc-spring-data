package pu.fmi.game.hangman.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pu.fmi.game.hangman.model.config.GameProperties;
import pu.fmi.game.hangman.model.entity.HangmanGame;
import pu.fmi.game.hangman.model.entity.Player;
import pu.fmi.game.hangman.model.entity.Status;
import pu.fmi.game.hangman.model.entity.Word;
import pu.fmi.game.hangman.model.repository.GameRepository;
import pu.fmi.game.hangman.model.repository.PlayerRepository;
import pu.fmi.game.hangman.model.wordprovider.GenericWordProvider;

@Service
@RequiredArgsConstructor
public class HangmanGameService implements GameService {

  private static final int INITIAL_WRONG_GUESSES = 0;
  private static final String PLACEHOLDER_SYMBOL = "_";

  private final GenericWordProvider wordProvider;
  private final GameProperties gameProperties;
  private final GameRepository gameRepository;
  private final PlayerRepository playerRepository;

  @Override
  public HangmanGame startNewGame(Long playerId){

    Optional<Player> optionalPlayer = playerRepository.findById(playerId);
    if(optionalPlayer.isEmpty()){
      return null;
    }

    HangmanGame hangmanGame = new HangmanGame();
    Word randomSelectedWord = wordProvider.generateRandomWordFromDB();
    hangmanGame.setWord(randomSelectedWord);

    hangmanGame.setStartedOnDate(LocalDateTime.now());
    hangmanGame.setWrongGuesses(gameProperties.getMaxNumberOfGuesses());
    hangmanGame.setCurrentWrongGuess(INITIAL_WRONG_GUESSES);
    hangmanGame.setStatus(Status.IN_PROGRESS);
    hangmanGame.setCurrentWord(generateInitialWord(randomSelectedWord.getName().length()));
    hangmanGame.setPlayer(optionalPlayer.get());

    gameRepository.save(hangmanGame);
    return hangmanGame;
  }

  @Override
  public HangmanGame makeGuess(Long id, char letter){

    HangmanGame hangmanGame = getGameById(id);
    if(hangmanGame == null){
      return null;
    }

    String wordToGuess = hangmanGame.getWord().getName();
    String currentWord = hangmanGame.getCurrentWord();

    if(currentWord.indexOf(letter) >= 0 || hangmanGame.getWrongLetters().contains(String.valueOf(letter))){
      return hangmanGame;
    }

    if(checkIsLetterWrong(wordToGuess, letter)){
      hangmanGame.setCurrentWrongGuess(hangmanGame.getCurrentWrongGuess() + 1);
      List<String> wrongLettersList = hangmanGame.getWrongLetters();
      wrongLettersList.add(String.valueOf(letter));
      hangmanGame.setWrongLetters(wrongLettersList);
    } else {
      String currentWordAfterFilled =
          getCurrentWordAfterLetterFill(wordToGuess, currentWord, letter);
      hangmanGame.setCurrentWord(currentWordAfterFilled);
    }

    updateGameStatusIfFinished(hangmanGame);

    gameRepository.save(hangmanGame);
    return hangmanGame;
  }

  @Override
  public HangmanGame getGameById(Long id){
    Optional<HangmanGame> optional = gameRepository.findById(id);
    if(optional.isEmpty()){
      return null;
    }
    return optional.get();
  }

  @Override
  public List<HangmanGame> getAllGames(){
    return this.gameRepository.findAll();
  }

  @Override
  public List<HangmanGame> getRecent10Games() {
    return this.gameRepository.findRecent10();
  }

  @Override
  public List<HangmanGame> findAllByStatus(Status status){
    return gameRepository.findByStatus(status);
  }

  private boolean checkIsLetterWrong(String wordToGuess, char letter){
    return wordToGuess.indexOf(letter) == -1;
  }

  private String getCurrentWordAfterLetterFill(String wordToGuess, String currentWord, char letter){

    StringBuilder newCurrentWord = new StringBuilder();

    for (int index = 0; index < wordToGuess.length(); index++) {

      char actualLetter = wordToGuess.charAt(index);
      char currentLetter = currentWord.charAt(index);

      if(actualLetter == letter){
        newCurrentWord.append(letter);
        continue;
      }

      newCurrentWord.append(currentLetter);
    }

    return newCurrentWord.toString();
  }

  private void updateGameStatusIfFinished(HangmanGame hangmanGame){
    if(hangmanGame.getWord().getName().equals(hangmanGame.getCurrentWord())){
      hangmanGame.setStatus(Status.WON);
      return;
    }
    if(hangmanGame.getCurrentWrongGuess() >= hangmanGame.getWrongGuesses()){
      hangmanGame.setStatus(Status.LOST);
    }

  }

  private String generateInitialWord(int wordToGuessLength){
    StringBuilder stringBuilder = new StringBuilder();
    for(int i = 0; i < wordToGuessLength; i++){
      stringBuilder.append(PLACEHOLDER_SYMBOL);
    }
    return stringBuilder.toString();
  }

}
