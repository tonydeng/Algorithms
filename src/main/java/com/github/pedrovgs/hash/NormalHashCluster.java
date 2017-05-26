package com.github.pedrovgs.hash;


import lombok.extern.slf4j.Slf4j;

/**
 * Created by tonydeng on 2017/5/22.
 */
@Slf4j
public class NormalHashCluster extends Cluster {


    public NormalHashCluster() {
        super();
    }

    @Override
    public void add(Node node) {
        this.nodes.add(node);
    }

    @Override
    public void remove(Node node) {
        this.nodes.removeIf(o ->
                o.getIp().equals(node.getIp()) ||
                        o.getDomain().equals(node.getDomain())
        );
    }

    @Override
    public Node get(String key) {
        long hash = hash(key);
        long index = hash % nodes.size();
        return nodes.get((int) index);
    }
}
