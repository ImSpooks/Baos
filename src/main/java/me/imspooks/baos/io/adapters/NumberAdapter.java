package me.imspooks.baos.io.adapters;

import me.imspooks.baos.io.BaosInputStream;
import me.imspooks.baos.io.BaosOutputStream;
import me.imspooks.baos.io.BaosTypedObject;

import java.io.IOException;

public class NumberAdapter extends BaosTypedObject<Number>
{
    @Override
    public void write(Number number, BaosOutputStream out) throws IOException {
        out.writeNumber(number);
    }

    @Override
    public Number read(BaosInputStream in) throws IOException {
        return in.readNumber();
    }
}
