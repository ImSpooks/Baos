package me.imspooks.baos;


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