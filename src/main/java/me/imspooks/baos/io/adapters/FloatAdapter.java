package me.imspooks.baos.io.adapters;

import me.imspooks.baos.io.BaosInputStream;
import me.imspooks.baos.io.BaosOutputStream;
import me.imspooks.baos.io.BaosTypedObject;

import java.io.IOException;

public class FloatAdapter extends BaosTypedObject<Float>
{

    @Override
    public void write(Float src, BaosOutputStream out) throws IOException {
        out.writeFloat(src);
    }

    @Override
    public Float read(BaosInputStream in) throws IOException {
        return in.readFloat();
    }
}
