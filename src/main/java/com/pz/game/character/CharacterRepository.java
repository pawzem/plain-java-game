package com.pz.game.character;

import com.pz.game.generic.repository.FileRepository;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharacterRepository extends FileRepository<Character, UUID> {
    private static final String CHARACTER_FOLDER_NAME = "character";


    public CharacterRepository(String gameFolder) {
        super(Path.of(gameFolder, CHARACTER_FOLDER_NAME));
    }

    @Override
    protected String serialize(Character entity) {
        return String.format("%s;%d;%s;%d;%d;%d;%d;%s",
                entity.getId().toString(),
                entity.getExperience(),
                entity.getState(),
                entity.getStats().getOrDefault(Character.Characteristics.STRENGTH, 0),
                entity.getStats().getOrDefault(Character.Characteristics.TOUGHNESS, 0),
                entity.getStats().getOrDefault(Character.Characteristics.DODGE, 0),
                entity.getStats().getOrDefault(Character.Characteristics.ATTACK, 0),
                entity.getHitPoints());
    }

    @Override
    protected Character deserialize(String serialized) {
        String[] splitSerializedValue = serialized.split(";");
        return  CharacterFactory.of(UUID.fromString(splitSerializedValue[0]),
                Integer.parseInt(splitSerializedValue[7]),
                Integer.parseInt(splitSerializedValue[1]),
                Integer.parseInt(splitSerializedValue[6]),
                Integer.parseInt(splitSerializedValue[5]),
                Integer.parseInt(splitSerializedValue[3]),
                Integer.parseInt(splitSerializedValue[4]),
                Condition.State.valueOf(splitSerializedValue[2]));
    }


}
