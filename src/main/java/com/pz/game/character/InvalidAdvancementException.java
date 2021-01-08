package com.pz.game.character;

public class InvalidAdvancementException extends Exception{
    InvalidAdvancementException(int available, int proposed) {
        super(String.format("Tried to assign more points %d then available %d", proposed, available));
    }
}
