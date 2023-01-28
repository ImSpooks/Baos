package me.imspooks.baos.io.adapters;

import me.imspooks.baos.io.BaosInputStream;
import me.imspooks.baos.io.BaosOutputStream;
import me.imspooks.baos.io.BaosTypedObject;

import java.io.IOException;

public class BooleanAdapter extends BaosTypedObject<Boolean>
{
    @Override
    public void write(Boolean src, BaosOutputStream out) throws IOException {
        out.writeBoolean(src);
    }

    @Override
    public Boolean read(BaosInputStream in) throws IOException {
        return in.readBoolean();
    }
}
