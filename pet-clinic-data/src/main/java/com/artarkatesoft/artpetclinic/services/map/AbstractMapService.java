package com.artarkatesoft.artpetclinic.services.map;

import java.util.*;

public abstract class AbstractMapService<T, ID> {
    protected Map<ID, T> map = new HashMap<>();

    public Set<T> findAll() {
        return new HashSet<>(map.values());
    }


    public T findById(ID id) {
        return map.get(id);
    }

    protected T save(ID id, T object) {
        map.put(id, object);
        return object;
    }

    public void delete(T object) {
        map.entrySet().removeIf(entry -> Objects.equals(entry.getValue(), object));
    }

    public void deleteById(ID id) {
        map.remove(id);
    }
}
