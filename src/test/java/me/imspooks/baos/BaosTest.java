package me.imspooks.baos;

import me.imspooks.baos.io.BaosInputStream;
import me.imspooks.baos.io.BaosOutputStream;
import me.imspooks.baos.io.BaosTypeAdapters;
import me.imspooks.baos.io.BaosTypedObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @Test
    public void testGenericLists() {
        try {
            BaosTypeAdapters.addTypeAdapter(ExampleObjectTwo.class, new BaosTypedObject<ExampleObjectTwo>() {
                @Override
                public void write(ExampleObjectTwo exampleObjectTwo, BaosOutputStream out) throws IOException {
                    out.writeString(exampleObjectTwo.getWorld());
                    out.writeDouble(exampleObjectTwo.getX());
                    out.writeDouble(exampleObjectTwo.getY());
                    out.writeDouble(exampleObjectTwo.getZ());
                }

                @Override
                public ExampleObjectTwo read(BaosInputStream in) throws IOException {
                    return new ExampleObjectTwo(in.readString(), in.readDouble(), in.readDouble(), in.readDouble());
                }
            });

            Random random = new Random();

            List<ExampleObjectTwo> original = new ArrayList<>();

            for (int i = 0; i < random.nextInt(100); i++) {
                original.add(new ExampleObjectTwo("world", random.nextDouble(), random.nextDouble(), random.nextDouble()));
            }

            byte[] written = Baos.write(out -> out.writeList(original));

            Baos.read(written, in -> {
                List<ExampleObjectTwo> read = in.readList(ExampleObjectTwo.class);

                assertEquals(original.size(), read.size(), "Written list does not match read list");
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testTypeAdapters() {
        try {
            BaosTypeAdapters.addTypeAdapter(ExampleObjectTwo.class, new BaosTypedObject<ExampleObjectTwo>() {
                @Override
                public void write(ExampleObjectTwo exampleObjectTwo, BaosOutputStream out) throws IOException {
                    out.writeString(exampleObjectTwo.getWorld());
                    out.writeDouble(exampleObjectTwo.getX());
                    out.writeDouble(exampleObjectTwo.getY());
                    out.writeDouble(exampleObjectTwo.getZ());
                }

                @Override
                public ExampleObjectTwo read(BaosInputStream in) throws IOException {
                    return new ExampleObjectTwo(in.readString(), in.readDouble(), in.readDouble(), in.readDouble());
                }
            });

            Random random = new Random();

            ExampleObjectTwo original = new ExampleObjectTwo("world", random.nextDouble(), random.nextDouble(), random.nextDouble());

            byte[] written = Baos.write(out -> out.writeTypePrefixed(original));

            Baos.read(written, in -> {
                ExampleObjectTwo read = in.readTypePrefixed(ExampleObjectTwo.class);

                assertEquals(original, read, "Written object doesn't match read object.");
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}