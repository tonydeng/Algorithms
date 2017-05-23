package com.github.pedrovgs.hash;

import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.IntStream;

/**
 * Created by tonydeng on 2017/5/22.
 */
@Slf4j
public class ConsistencyHashCluster extends Cluster {
    private SortedMap<Long, Node> virNodes = new TreeMap<>();
    private static final int VIR_NODE_COUNT = 512;

    private static final String SPLIT = "#";

    public ConsistencyHashCluster() throws NoSuchAlgorithmException {
        super();
    }

    @Override
    public void addNode(Node node) {
        this.nodes.add(node);
        IntStream.range(0, VIR_NODE_COUNT)
                .forEach(index -> {
                    long hash = hash(node.getIp() + SPLIT + index);
                    virNodes.put(hash, node);
                });
    }

    @Override
    public void removeNode(Node node) {
        nodes.removeIf(o -> node.getIp().equals(o.getIp()));

        IntStream.range(0, VIR_NODE_COUNT)
                .forEach(index -> {
                    long hash = hash(node.getIp() + SPLIT + index);
                    virNodes.remove(hash);
                });
    }

    @Override
    public Node get(String key) {
        long hash = hash(key);
        SortedMap<Long, Node> subMap = virNodes.tailMap(hash);
        if (subMap.isEmpty()) {
            return get(String.valueOf(hash));
        }
        return subMap.get(subMap.firstKey());
    }
}
