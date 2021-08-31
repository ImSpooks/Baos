package me.imspooks.baos;

import me.imspooks.baos.io.BaosOutputStream;

import java.io.IOException;

/**
 * Created by Nick on 05 aug. 2021.
 * Copyright © ImSpooks
 */
public interface BaosSerializer {

    void serialize(BaosOutputStream out) throws IOException;
}