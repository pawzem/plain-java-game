package com.pz.game;

import com.pz.game.character.Character;
import com.pz.game.character.CharacterRepository;
import com.pz.game.character.InvalidAdvancementException;
import com.pz.game.creator.Creator;
import com.pz.game.dice.Dice;
import com.pz.game.generic.repository.RepositoryException;
import com.pz.game.level.Level;
import com.pz.game.level.LevelFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Game {

    public static void main(String[] args) throws InvalidAdvancementException {
        if(args.length != 1){
            System.out.println("Game needs one catalog to save progress. Please provide it as program parameter.");
            return;
        }
        var gameCatalog = args[0];
        var scanner = new Scanner(System.in);

        var characterRepository = new CharacterRepository(gameCatalog);
        System.out.println("Welcome to the Game!");

        Optional<Character> oldCharacter = null;
        try {
            oldCharacter = characterRepository.findAny();
        } catch (RepositoryException e) {
            System.out.println("Could not read character data from game catalog");
            return;
        }

        Character character = null;
        if(oldCharacter.isPresent()){
            var response = "";
            while (!response.matches("[YN]")){
                System.out.println("Old save found, do you wish to continue? (Y/N)");
                response = scanner.next();

            }
            character = "Y".equals(response) ? oldCharacter.get().heal(12) : createCharacter(scanner);

        } else {
            character = createCharacter(scanner);
        }


        var dice = new Dice();

        play(scanner, character, dice);
        advanceHero(scanner, character);

        try {
            characterRepository.save(character);
        } catch (RepositoryException e) {
            System.out.println("Could not save current progress");
            return;
        }


        System.out.println("Good bye");
    }

    private static Character createCharacter(Scanner scanner) {
        Character character;
        var creator = new Creator(scanner);
        character = creator.createCharacter();
        return character;
    }

    private static void advanceHero(Scanner scanner, Character character) throws InvalidAdvancementException {
        var characteristics = Arrays.stream(Character.Characteristics.values()).map(Enum::name).collect(Collectors.toUnmodifiableSet());
        Character.Characteristics advancement = null;
        while (Objects.isNull(advancement)){
            System.out.println("Choose attribute to advance: " + Arrays.toString(Character.Characteristics.values()));
            var input = scanner.next();
            if(characteristics.contains(input)){
                advancement = Character.Characteristics.valueOf(input);
            }
        }

        character.advance(advancement, character.getAvailableAdvancementPoints());
    }

    private static void play(Scanner scanner, Character character, Dice dice) {
        var level = LevelFactory.defaultLevel(dice);

        List<Integer> options = level.getOptions();

        while (!options.isEmpty()){
            System.out.println(String.format("Choose door number: %s", options.toString()));
            String input = scanner.next();
            if(input.matches("\\d+") && options.contains(Integer.parseInt(input))){
                var option = Integer.parseInt(input);
                Level.LevelResult levelResult = level.travel(character, option);

                options = level.getOptions();
                if(levelResult == Level.LevelResult.SUCCESS){
                    System.out.println("Congratulations you won!");
                } else if (levelResult == Level.LevelResult.FAILURE) {
                    System.out.println("You have lost ");
                    return;
                }
            }

        }
    }
}
