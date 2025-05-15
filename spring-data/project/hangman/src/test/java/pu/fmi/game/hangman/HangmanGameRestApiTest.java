package pu.fmi.game.hangman;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.web.reactive.server.WebTestClient;
import pu.fmi.game.hangman.model.entity.HangmanGame;
import pu.fmi.game.hangman.model.service.GameService;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class HangmanGameRestApiTest {

  @Autowired
  WebTestClient client;

  @Autowired
  GameService gameService;

  @Test
  void testGetGameById() {

    HangmanGame createdGame = client
        .post()
        .uri("/hangman-games?playerId=1")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(HangmanGame.class)
        .returnResult()
        .getResponseBody();

    client
        .get()
        .uri("/hangman-games/{id}", createdGame.getId())
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
          .jsonPath("$.id").isEqualTo(createdGame.getId())
          .jsonPath("$.status").isEqualTo(createdGame.getStatus())
          .jsonPath("$.currentWrongGuess").isEqualTo(createdGame.getCurrentWrongGuess())
          .jsonPath("$.wrongGuesses").isEqualTo(createdGame.getWrongGuesses());
  }

  @Test
  void testListTop10HangmanGames(@Autowired WebTestClient client) {

    HangmanGame oldGame = gameService.startNewGame(1L);

    for (int i = 0; i < 10; i++) {
      gameService.startNewGame(1L);
    }

    client
        .get()
        .uri("/hangman-games")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(HangmanGame.class)
        .hasSize(10)
        .value(games -> {
          assertThat(games).noneMatch(game ->
              oldGame.getId().equals(game.getId())
          );
        });
  }

}
