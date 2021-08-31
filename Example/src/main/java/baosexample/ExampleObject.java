package baosexample;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.imspooks.baos.BaosDeserializer;
import me.imspooks.baos.BaosSerializer;
import me.imspooks.baos.io.BaosInputStream;
import me.imspooks.baos.io.BaosOutputStream;

import java.io.IOException;

/**
 * Created by Nick on 31 aug. 2021.
 * Copyright © ImSpooks
 */
@Getter
@Setter
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
}