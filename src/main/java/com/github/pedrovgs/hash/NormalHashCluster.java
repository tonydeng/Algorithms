package com.github.pedrovgs.hash;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tonydeng on 2017/5/22.
 */
public class NormalHashCluster extends Cluster {

    private static final Logger log = LoggerFactory.getLogger(NormalHashCluster.class);

    public NormalHashCluster() {
        super();
    }

    @Override
    public void addNode(Node node) {
        this.nodes.add(node);
    }

    @Override
    public void removeNode(Node node) {
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
