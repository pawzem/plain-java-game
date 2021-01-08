package com.pz.game.generic.repository;

import java.util.Optional;

public interface Repository<E, I> {
    Optional<E> find(I id) throws RepositoryException;
    E save(E entity) throws RepositoryException;

}
