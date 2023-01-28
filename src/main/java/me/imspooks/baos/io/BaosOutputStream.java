package me.imspooks.baos.io;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Nick on 05 aug. 2021.
 * Copyright © ImSpooks
 */
public class BaosOutputStream {

    private final DataOutputStream out;

    public BaosOutputStream(DataOutputStream out) {
        this.out = out;
    }

    public void write(byte[] data) throws IOException {
        this.out.write(data);
    }

    public void write(int byt) throws IOException {
        this.out.writeByte(byt);
    }

    public void writeNumber(Number number) throws IOException {
        if (number instanceof Integer) {
            this.write((byte) 0);
            this.writeInt((int) number);
        }
        if (number instanceof Double) {
            this.write((byte) 1);
            this.writeDouble((double) number);
        }
        if (number instanceof Float) {
            this.write((byte) 2);
            this.writeFloat((float) number);
        }
    }

    public void writeBoolean(boolean b) throws IOException {
        this.out.writeBoolean(b);
    }

    public void writeDouble(double d) throws IOException {
        this.out.writeDouble(d);
    }

    public void writeFloat(float f) throws IOException {
        this.out.writeFloat(f);
    }

    public void writeInt(int i) throws IOException {
        this.out.writeInt(i);
    }

    public void writeLong(long l) throws IOException {
        this.out.writeLong(l);
    }

    public void writeShort(short s) throws IOException {
        this.out.writeShort(s);
    }

    public void writeString(String s) throws IOException {
        this.writeString(s, StandardCharsets.UTF_8);
    }

    public void writeString(String s, Charset charset) throws IOException {
        byte[] bytes = s.getBytes(charset);
        this.writeInt(bytes.length);
        this.write(bytes);
    }

    public void writeUUID(UUID uuid) throws IOException {
        this.writeLong(uuid.getMostSignificantBits());
        this.writeLong(uuid.getLeastSignificantBits());
    }

    public void writeStringList(List<String> list, Charset charset) throws IOException {
        this.writeInt(list.size());
        for (String s : list) {
            this.writeString(s, charset);
        }
    }

    public void writeStringList(List<String> list) throws IOException {
        this.writeStringList(list, StandardCharsets.UTF_8);
    }

    public void writeByteList(List<Byte> list) throws IOException {
        this.writeInt(list.size());
        for (byte value : list) {
            this.write(value);
        }
    }

    public void writeShortList(List<Short> list) throws IOException {
        this.writeInt(list.size());
        for (short value : list) {
            this.writeShort(value);
        }
    }

    public void writeIntegerList(List<Integer> list) throws IOException {
        this.writeInt(list.size());
        for (int value : list) {
            this.writeInt(value);
        }
    }

    public void writeLongList(List<Long> list) throws IOException {
        this.writeInt(list.size());
        for (long value : list) {
            this.writeLong(value);
        }
    }

    public void writeFloatList(List<Float> list) throws IOException {
        this.writeInt(list.size());
        for (float value : list) {
            this.writeFloat(value);
        }
    }

    public void writeDoubleList(List<Double> list) throws IOException {
        this.writeInt(list.size());
        for (double value : list) {
            this.writeDouble(value);
        }
    }

    public void writeUUIDList(List<UUID> list) throws IOException {
        this.writeInt(list.size());
        for (UUID value : list) {
            this.writeUUID(value);
        }
    }

    public void writeStringToStringMap(Map<String, String> map) throws IOException {
        this.writeInt(map.size());

        for (String key : map.keySet()) {
            this.writeString(key);
            this.writeString(map.get(key));
        }
    }

    public <T> void writeStringToGenericMap(Map<String, T> map) throws IOException {
        this.writeInt(map.size());

        for (String key : map.keySet()) {
            this.writeString(key);
            this.writeTypePrefixed(map.get(key));
        }
    }

    public void writeIntToIntMap(Map<Integer, Integer> map) throws IOException {
        this.writeInt(map.size());

        for (Integer key : map.keySet()) {
            this.writeInt(key);
            this.writeInt(map.get(key));
        }
    }

    public <T> void writeIntToIntMap(Map<T, Integer> map, ValueTransformer<T, Integer> keyTransformer) throws IOException {
        this.writeInt(map.size());

        for (T t : map.keySet()) {
            this.writeInt(keyTransformer.transform(t));
            this.writeInt(map.get(t));
        }
    }

    /**
     * Uses {@link BaosTypedObject} to serialize the list's contents
     * Objects in the list must have a registered Type adapter
     */
    public <T> void writeList(List<T> list) throws IOException {
        this.writeInt(list.size());
        for (T t : list) {
            writeTypePrefixed(t);
        }
    }

    /**
     * Uses {@link BaosTypedObject} registered by {@link BaosTypeAdapters#addTypeAdapter(Class, BaosTypedObject)}
     */
    public void writeTypePrefixed(Object o) throws IOException {
        if (o == null) {
            this.write(0);
        } else {
            this.write(1);
            BaosTypedObject<?> object = BaosTypeAdapters.getFromClass(o.getClass());
            if (object == null) {
                throw new IllegalArgumentException("No type adapter found for class " + o.getClass());
            }
            object.write0(o, this);
        }
    }

    public DataOutputStream getOut() {
        return out;
    }


    /**
     * Used to convert a value.
     * for example a complex object to a primitive object, e.g Enum -> Integer
     *
     * @param <O> the old value
     * @param <N> the new value
     */
    public interface ValueTransformer<O, N> {

        N transform(O value);
    }
}