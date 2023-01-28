package me.imspooks.baos.io.adapters;

import me.imspooks.baos.io.BaosInputStream;
import me.imspooks.baos.io.BaosOutputStream;
import me.imspooks.baos.io.BaosTypedObject;

import java.io.IOException;

public class LongAdapter extends BaosTypedObject<Long>
{

    @Override
    public void write(Long src, BaosOutputStream out) throws IOException {
        out.writeLong(src);
    }

    @Override
    public Long read(BaosInputStream in) throws IOException {
        return in.readLong();
    }
}
