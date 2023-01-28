package me.imspooks.baos.io.adapters;

import me.imspooks.baos.io.BaosInputStream;
import me.imspooks.baos.io.BaosOutputStream;
import me.imspooks.baos.io.BaosTypedObject;

import java.io.IOException;

public class DoubleAdapter extends BaosTypedObject<Double>
{

    @Override
    public void write(Double src, BaosOutputStream out) throws IOException {
        out.writeDouble(src);
    }

    @Override
    public Double read(BaosInputStream in) throws IOException {
        return in.readDouble();
    }
}
