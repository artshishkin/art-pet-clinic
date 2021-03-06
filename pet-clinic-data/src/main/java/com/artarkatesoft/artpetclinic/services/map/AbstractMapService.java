package com.artarkatesoft.artpetclinic.services.map;

import com.artarkatesoft.artpetclinic.model.BaseEntity;

import java.util.*;

public abstract class AbstractMapService<T extends BaseEntity, ID extends Long> {
    protected Map<Long, T> map = new HashMap<>();

    public Set<T> findAll() {
        return new HashSet<>(map.values());
    }


    public T findById(ID id) {
        return map.get(id);
    }

    public T save(T object) {
        Objects.requireNonNull(object, "Object cannot be null");
        Long id = object.getId();
        Long newId = (id != null) ? id : getNextId();
        object.setId(newId);
        map.put(newId, object);
        return object;
    }

    private Long getNextId() {
        return (map.isEmpty()) ? 1 : Collections.max(map.keySet()) + 1L;
    }

    public void delete(T object) {
        map.entrySet().removeIf(entry -> Objects.equals(entry.getValue(), object));
    }

    public void deleteById(ID id) {
        map.remove(id);
    }
}
