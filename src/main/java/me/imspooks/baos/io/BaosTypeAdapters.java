package me.imspooks.baos.io;

import me.imspooks.baos.io.adapters.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Nick on 10 jun. 2022.
 * Copyright © ImSpooks
 */
public class BaosTypeAdapters {

    private static final List<BaosTypedObject<?>> TYPES = new ArrayList<>();


    // register default adapters
    static {
        TYPES.add(new BooleanAdapter());
        TYPES.add(new DoubleAdapter());
        TYPES.add(new FloatAdapter());
        TYPES.add(new LongAdapter());
        TYPES.add(new NumberAdapter());
        TYPES.add(new ShortAdapter());
        TYPES.add(new StringAdapter());
    }

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