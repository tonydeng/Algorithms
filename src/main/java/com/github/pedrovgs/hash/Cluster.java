package com.github.pedrovgs.hash;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonydeng on 2017/5/22.
 */
public abstract class Cluster {
    protected List<Node> nodes;

    public Cluster() {
        this.nodes = new ArrayList<>();
    }

    public abstract void addNode(Node node);

    public abstract void removeNode(Node node);

    public abstract Node get(String key);

    public long hash(String value){
        return value.hashCode();
    }
}
