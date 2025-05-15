package pu.fmi.game.hangman.model.wordprovider;

import pu.fmi.game.hangman.model.entity.Word;

public interface GenericWordProvider {

  String generateRandomWord();

  Word generateRandomWordFromDB();

}
