package me.imspooks.baos.io;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public List<String> readStringList(Charset charset) throws IOException {
        int size = this.readInt();
        List<String> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(this.readString(charset));
        }
        return list;
    }

    public List<String> readStringList() throws IOException {
        return this.readStringList(StandardCharsets.UTF_8);
    }

    public List<Byte> readByteList() throws IOException {
        int size = this.readInt();
        List<Byte> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add((byte) this.read());
        }
        return list;
    }

    public List<Short> readShortList() throws IOException {
        int size = this.readInt();
        List<Short> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(this.readShort());
        }
        return list;
    }

    public List<Integer> readIntegerList() throws IOException {
        int size = this.readInt();
        List<Integer> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(this.readInt());
        }
        return list;
    }

    public List<Long> readLongList() throws IOException {
        int size = this.readInt();
        List<Long> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(this.readLong());
        }
        return list;
    }

    public List<Float> readFloatList() throws IOException {
        int size = this.readInt();
        List<Float> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(this.readFloat());
        }
        return list;
    }

    public List<Double> readDoubleList() throws IOException {
        int size = this.readInt();
        List<Double> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(this.readDouble());
        }
        return list;
    }

    public List<UUID> readUUIDList() throws IOException {
        int size = this.readInt();
        List<UUID> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(this.readUUID());
        }
        return list;
    }

    /**
     * Attempts to read a list of the passed in object.
     * Passed in object must have a Type adapter registered in {@link BaosTypeAdapters}
     */
    public <T> List<T> readList(Class<T> obj) throws IOException {
        int size = this.readInt();
        List<T> list = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            list.add(readTypePrefixed(obj));
        }
        return list;
    }

    /**
     * Uses {@link BaosTypedObject} registered by {@link BaosTypeAdapters#addTypeAdapter(Class, BaosTypedObject)}
     */
    public <T> T readTypePrefixed(Class<T> clazz) throws IOException {
        int id = this.read();
        if (id == -1) {
            return null;
        } else {
            BaosTypedObject<T> object = BaosTypeAdapters.getFromClass(clazz);
            if (object == null) {
                throw new IllegalArgumentException("No type adapter found with index " + id);
            }
            return object.read(this);
        }
        /*else if (id == 0) {
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
        }*/
    }


    public DataInputStream getIn() {
        return in;
    }
}