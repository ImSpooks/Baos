package me.imspooks.baos.io.adapters;

import me.imspooks.baos.io.BaosInputStream;
import me.imspooks.baos.io.BaosOutputStream;
import me.imspooks.baos.io.BaosTypedObject;

import java.io.IOException;

public class StringAdapter extends BaosTypedObject<String>
{

    @Override
    public void write(String s, BaosOutputStream out) throws IOException {
        out.writeString(s);
    }

    @Override
    public String read(BaosInputStream in) throws IOException {
        return in.readString();
    }
}
