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

    public void writeTypePrefixed(Object o) throws IOException {
        if (o == null) {
            this.write(-1);
        } else if (o instanceof String) {
            this.write(0);
            this.writeString((String) o);
        } else if (o instanceof Integer) {
            this.write(1);
            this.writeInt((Integer) o);
        } else if (o instanceof Long) {
            this.write(2);
            this.writeLong((Long) o);
        } else if (o instanceof Double) {
            this.write(3);
            this.writeDouble((Double) o);
        } else if (o instanceof List) {
            List<?> list = (List<?>) o;
            this.write(4);
            this.writeInt(list.size());
            for (Object inList : list) {
                this.writeTypePrefixed(inList);
            }
        } else if (o instanceof Boolean) {
            this.write(5);
            this.writeBoolean((Boolean) o);
        } else if (o instanceof UUID) {
            this.write(6);
            this.writeUUID((UUID) o);
        } else if (o instanceof byte[]) {
            this.write(7);
            this.writeInt(((byte[]) o).length);
            this.out.write((byte[]) o);
        } else if (o instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) o;

            this.write(8);
            this.writeInt(map.size());
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                this.writeTypePrefixed(entry.getKey());
                this.writeTypePrefixed(entry.getValue());
            }
        }
        else {
            throw new UnsupportedOperationException("Unknown prefix type " + o.getClass().getSimpleName());
        }
    }

    public DataOutputStream getOut() {
        return out;
    }
}