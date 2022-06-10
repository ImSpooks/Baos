package me.imspooks.baos;

import lombok.Data;

/**
 * Created by Bert on 10 Jun 2022
 * Copyright © EpicGodMC
 */
@Data
public final class Pair<K,V>
{

    private final K key;

    private final V value;

}
