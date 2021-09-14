package me.imspooks.baos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Nick on 14 Sep 2021.
 * Copyright © ImSpooks
 */
public class BaosTest {

    @Test
    public void testSerializationDeserialization() {
        try {
            ExampleObject object = new ExampleObject(1, "Hello, World!", System.currentTimeMillis());

            byte[] out = Baos.write(object);

            assertEquals(object, Baos.read(out, new ExampleObject()), "Written object doesn't match read object.");
            assertEquals(object, Baos.read(out, ExampleObject.class), "Written object doesn't match read object using reflection.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}