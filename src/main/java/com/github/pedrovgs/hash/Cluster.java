package com.github.pedrovgs.hash;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonydeng on 2017/5/22.
 */
public abstract class Cluster {
    protected final List<Node> nodes;
    private final MessageDigest md;

    public Cluster() throws NoSuchAlgorithmException {
        this.nodes = new ArrayList<>();
        this.md = MessageDigest.getInstance("SHA1");
    }

    public abstract void addNode(Node node);

    public abstract void removeNode(Node node);

    public abstract Node get(String key);

    public long hash(String value) {
        md.reset();
        final byte[] digest = md.digest(value.getBytes());
        return (getLong(digest, 0) ^ getLong(digest, 8));
    }

    private static final long getLong(final byte[] array, final int offset) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value = ((value << 8) | (array[offset + i] & 0xFF));
        }
        return Math.abs(value);
    }
}
