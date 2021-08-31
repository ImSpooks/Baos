package baosexample;

import me.imspooks.baos.Baos;

import java.io.IOException;

/**
 * Created by Nick on 31 aug. 2021.
 * Copyright © ImSpooks
 */
public class BaosExample {

    public static void main(String[] args) {
        try {

            ExampleObject object = new ExampleObject(1, "Hello, World!", System.currentTimeMillis());

            System.out.println("object = " + object);

            byte[] out = Baos.write(object);

            System.out.println("new object = " + Baos.read(out, new ExampleObject()));
            System.out.println("new object with reflection = " + Baos.read(out, ExampleObject.class));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}