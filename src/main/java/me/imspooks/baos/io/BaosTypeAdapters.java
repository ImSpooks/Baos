package me.imspooks.baos.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Nick on 10 jun. 2022.
 * Copyright © ImSpooks
 */
public class BaosTypeAdapters {

    private static final List<BaosTypedObject<?>> TYPES = new ArrayList<>();

    @SuppressWarnings("unchecked")
    static <T> BaosTypedObject<T> getFromClass(Class<T> clazz) {
        for (BaosTypedObject<?> type : TYPES) {
            if (Objects.equals(type.getType(), clazz)) {
                return (BaosTypedObject<T>) type;
            }
        }
        return null;
    }

    public static <T> void addTypeAdapter(Class<T> clazz, BaosTypedObject<T> object) {
        object.type = clazz;
        TYPES.add(object);
    }


}