package me.imspooks.baos.io;

import lombok.Data;

import java.io.IOException;

@Data
public abstract class BaosTypedObject<T> {

    Class<T> type;

    public abstract void write(T t, BaosOutputStream out) throws IOException;

    public abstract T read(BaosInputStream in) throws IOException;

    @SuppressWarnings("unchecked")
    void write0(Object o, BaosOutputStream out) throws IOException {
        this.write((T) o, out);
    }

//        @SuppressWarnings("unchecked")
//        void write(Object o, BaosOutputStream out) {
//            this.out.accept((T) o, out);
//        }
}