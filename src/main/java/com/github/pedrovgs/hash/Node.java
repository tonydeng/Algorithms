package com.github.pedrovgs.hash;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tonydeng on 2017/5/22.
 */
@Data
public class Node {
    private String domain;
    private String ip;
    private Map<String, Object> data;

    public Node(String domain, String ip) {
        this.domain = domain;
        this.ip = ip;
        this.data = new HashMap<>();
    }

    public <T> void put(String key, T value) {
        data.put(key, value);
    }

    public void remove(String key) {
        data.remove(key);
    }

    public <T> T get(String key) {
        return (T) data.get(key);
    }
}
