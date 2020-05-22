package com.razi.ubbtt.Utils;

public class Tuple3<K, V, U> {
    private final K first;
    private final V second;
    private final U third;

    public Tuple3(K first, V second, U third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public K first() {
        return this.first;
    }

    public V second() {
        return this.second;
    }

    public U third() {
        return this.third;
    }
}
