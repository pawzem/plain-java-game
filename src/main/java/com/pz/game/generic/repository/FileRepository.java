package com.pz.game.generic.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public abstract class FileRepository<E extends Entity<I>, I> implements Repository<E, I>{

    private final Path repositoryFolder;

    public FileRepository(Path repositoryFolder) {
        this.repositoryFolder = repositoryFolder;
    }

    public E save(E entity) throws RepositoryException {
        String serializedCharacter = serialize(entity);

        try {
            saveSerializedStringToFile(entity.getId(), serializedCharacter);
        } catch (IOException e) {
            throw new RepositoryException(e.getMessage());
        }

        return entity;
    }

    public Optional<E> findAny() throws RepositoryException {
        try {
            var repository = repositoryFolder.toFile();
            if(repository.exists() && repository.isDirectory()){
                Optional<File> anyFile = Arrays.stream(Objects.requireNonNull(repository.listFiles())).findAny();
                if(anyFile.isEmpty()){
                    return Optional.empty();
                } else {
                    Optional<String> serializedValue = getSerializedValue(anyFile.get());
                    return serializedValue.map(this::deserialize);
                }

            }

            return Optional.empty();


        } catch (IOException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    public Optional<E> find(I id) throws RepositoryException {
        try {
            return getSerializedValue(id)
                    .map(this::deserialize);
        } catch (IOException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    protected abstract String serialize(E entity);

    protected abstract E deserialize(String serialized);


    private Optional<String> getSerializedValue(I id) throws IOException {
        File filePath = getFilePath(id);
        return getSerializedValue(filePath);
    }

    private Optional<String> getSerializedValue(File filePath) throws IOException {
        if(!filePath.exists()){
            return Optional.empty();
        }
        return Optional.of(Files.readString(filePath.toPath()));
    }

    private void saveSerializedStringToFile(I id, String serializedCharacter) throws IOException {
        if(!repositoryFolder.toFile().exists()){
            Files.createDirectory(repositoryFolder);
        }

        File characterFile = getFilePath(id);
        Path characterPath = characterFile.toPath();
        Files.write(characterPath, serializedCharacter.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private File getFilePath(I id) {
        String fileNameWithExtension = id.toString() + ".txt";
        return new File(repositoryFolder.toAbsolutePath().toString() + File.separator + fileNameWithExtension);
    }
}
