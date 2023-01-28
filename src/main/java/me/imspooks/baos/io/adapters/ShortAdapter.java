package me.imspooks.baos.io.adapters;

import me.imspooks.baos.io.BaosInputStream;
import me.imspooks.baos.io.BaosOutputStream;
import me.imspooks.baos.io.BaosTypedObject;

import java.io.IOException;

public class ShortAdapter extends BaosTypedObject<Short> {
    @Override
    public void write(Short src, BaosOutputStream out) throws IOException {
        out.writeShort(src);
    }

    @Override
    public Short read(BaosInputStream in) throws IOException {
        return in.readShort();
    }
}
