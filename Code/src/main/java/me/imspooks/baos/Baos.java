package me.imspooks.baos;

import me.imspooks.baos.io.BaosInputStream;
import me.imspooks.baos.io.BaosOutputStream;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Nick on 05 aug. 2021.
 * Copyright © ImSpooks
 */
public class Baos {

    public static <T extends BaosDeserializer> T read(byte[] bytes, Class<T> baosClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        Constructor<T> constructor = baosClass.getConstructor();
        T instance = constructor.newInstance();

        instance.deserialize(new BaosInputStream(new DataInputStream(new ByteArrayInputStream(bytes))));
        return instance;
    }

    public static <T extends BaosDeserializer> T read(byte[] bytes, T baosObject) throws IOException {
        baosObject.deserialize(new BaosInputStream(new DataInputStream(new ByteArrayInputStream(bytes))));
        return baosObject;
    }

    public static byte[] write(BaosSerializer baosObject) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        baosObject.serialize(new BaosOutputStream(new DataOutputStream(outputStream)));
        return outputStream.toByteArray();
    }
}