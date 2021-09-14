package me.imspooks.baos;

import lombok.Getter;
import lombok.Setter;
import me.imspooks.baos.io.BaosInputStream;
import me.imspooks.baos.io.BaosOutputStream;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by Nick on 31 aug. 2021.
 * Copyright © ImSpooks
 */
@Getter @Setter
public class ExampleObject implements BaosSerializer, BaosDeserializer {

    private int id;
    private String name;
    private long timestamp;

    public ExampleObject(int id, String name, long timestamp) {
        this.id = id;
        this.name = name;
        this.timestamp = timestamp;
    }

    // Empty constructor, mostly useful for deserializing a new object
    public ExampleObject() {
    }

    @Override
    public void deserialize(BaosInputStream in) throws IOException {
        this.id = in.readInt();
        this.name = in.readString();
        this.timestamp = in.readLong();
    }

    @Override
    public void serialize(BaosOutputStream out) throws IOException {
        out.writeInt(this.id);
        out.writeString(this.name);
        out.writeLong(this.timestamp);
    }

    @Override
    public String toString() {
        return "ExampleObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExampleObject)) return false;
        ExampleObject that = (ExampleObject) o;
        return id == that.id &&
                timestamp == that.timestamp &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, timestamp);
    }
}