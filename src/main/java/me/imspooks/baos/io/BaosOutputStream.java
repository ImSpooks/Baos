package me.imspooks.baos.io;

import org.bukkit.Location;
import org.bukkit.block.Block;

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

    public void writeBlockLocation(Block b) throws IOException {
        this.writeBlockLocation(b.getLocation());
    }

    public void writeBlockLocation(Location l) throws IOException {
        this.writeUUID(l.getWorld().getUID());
        this.writeInt(l.getBlockX());
        this.writeInt(l.getBlockX());
        this.writeInt(l.getBlockX());
    }

    public void writeBlockLocationPair(Block b1, Block b2) throws IOException {
        writeBlockLocationPair(b1.getLocation(), b2.getLocation());
    }

    public void writeBlockLocationPair(Location l1, Location l2) throws IOException {
        writeBlockLocation(l1);
        writeBlockLocation(l2);
    }

    public void writeLocation(Location l) throws IOException {
        this.writeUUID(l.getWorld().getUID());
        this.writeDouble(l.getX());
        this.writeDouble(l.getY());
        this.writeDouble(l.getZ());
    }

    public void writeLocationPair(Location l1, Location l2) throws IOException {
        writeLocation(l1);
        writeLocation(l2);
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
        } else {
            throw new UnsupportedOperationException("Unknown prefix type " + o.getClass().getSimpleName());
        }
    }

    public DataOutputStream getOut() {
        return out;
    }
}