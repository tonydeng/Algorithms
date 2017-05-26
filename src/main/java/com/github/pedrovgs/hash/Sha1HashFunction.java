package com.github.pedrovgs.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by tonydeng on 2017/5/23.
 */
public class Sha1HashFunction implements HashFunction {
    private MessageDigest md;

    public Sha1HashFunction() {
        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public long hash(String value) {
        md.reset();
        final byte[] digest = md.digest(value.getBytes());
        return (getLong(digest, 0) ^ getLong(digest, 8));
    }
}
