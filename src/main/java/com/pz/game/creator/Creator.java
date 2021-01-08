package com.pz.game.creator;

import com.pz.game.character.Character;
import com.pz.game.character.CharacterFactory;

import java.util.Scanner;

public class Creator {
    enum HeroType{
        THIEF(CharacterFactory.of(12,70,80,40,40)),
        WARRIOR(CharacterFactory.of(15,60,40,70,60)),
        ASSASSIN(CharacterFactory.of(12,80,50,70,30));

        private final Character character;


        HeroType(Character character) {
            this.character = character;
        }

        Character getCharacter() {
            return character;
        }
    }

    private final Scanner scanner;

    public Creator(Scanner scanner) {
        this.scanner = scanner;
    }

    public Character createCharacter(){
        System.out.println("Choose Class from:");
        for(int i = 1; i <= HeroType.values().length; i++){
            System.out.println(String.format("%d) %s", i, HeroType.values()[i-1].name()));
            }
        while (true){
            System.out.println("Type corresponding number:");
            String input = scanner.next();
            if(input.matches("[123]")){
                var classId = Integer.parseInt(input);
                return HeroType.values()[classId-1].getCharacter();
            }

        }
    }
}
