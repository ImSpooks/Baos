package me.imspooks.baos.io;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by Nick on 05 aug. 2021.
 * Copyright © ImSpooks
 */
public class BaosInputStream {

    private final DataInputStream in;

    public BaosInputStream(DataInputStream in) {
        this.in = in;
    }

    public int read(byte[] b) throws IOException {
        return this.in.read(b);
    }

    public int read() throws IOException {
        return this.in.readByte();
    }

    public boolean readBoolean() throws IOException {
        return this.in.readBoolean();
    }

    public double readDouble() throws IOException {
        return this.in.readDouble();
    }

    public float readFloat() throws IOException {
        return this.in.readFloat();
    }

    public int readInt() throws IOException {
        return this.in.readInt();
    }

    public long readLong() throws IOException {
        return this.in.readLong();
    }

    public short readShort() throws IOException {
        return this.in.readShort();
    }

    public String readString() throws IOException {
        return this.readString(StandardCharsets.UTF_8);
    }

    public String readString(Charset charset) throws IOException {
        int length = this.readInt();
        byte[] b = new byte[length];
        this.read(b);
        return new String(b, charset);
    }

    public UUID readUUID() throws IOException {
        long most = this.readLong();
        long least = this.readLong();
        return new UUID(most, least);
    }

    public Object readTypePrefixed() throws IOException {
        int id = this.read();
        if (id == -1) {
            return null;
        } else if (id == 0) {
            return this.readString();
        } else if (id == 1) {
            return this.readInt();
        } else if (id == 2) {
            return this.readLong();
        } else if (id == 3) {
            return this.readDouble();
        } else if (id == 4) {
            // it's Packet list
            int size = this.readInt();
            List<Object> list = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                list.add(this.readTypePrefixed());
            }
            return list;
        } else if (id == 5) {
            return this.readBoolean();
        } else if (id == 6) {
            return this.readUUID();
        } else if (id == 7) {
            byte[] arr = new byte[in.readInt()];
            this.in.read(arr);
            return arr;
        } else if (id == 8) {
            int size = this.readInt();
            Map<Object, Object> map = new HashMap<>(size);

            for (int i = 0; i < size; i++) {
                map.put(this.readTypePrefixed(), this.readTypePrefixed());
            }
            return map;
        } else {
            throw new UnsupportedOperationException("Unknown ID " + id);
        }
    }


    public DataInputStream getIn() {
        return in;
    }
}