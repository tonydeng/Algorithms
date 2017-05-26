package com.github.pedrovgs.hash;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonydeng on 2017/5/22.
 */
public abstract class Cluster {
    protected List<Node> nodes;
    private HashFunction hashFunction;
    public Cluster() {
        this.nodes = new ArrayList<>();
        this.hashFunction  = new Sha1HashFunction();
    }

    abstract void add(Node node);

    abstract void remove(Node node);

    abstract Node get(String key);

    public long hash(String value){
        return hashFunction.hash(value);
    }
}
