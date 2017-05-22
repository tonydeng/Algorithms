package com.github.pedrovgs.hash;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by tonydeng on 2017/5/22.
 */
public class ClusterTest {
    private static final Logger log = LoggerFactory.getLogger(ClusterTest.class);
    private static int DATA_COUNT = 100;
    private static String PRE_KEY = "key";
    private Cluster cluster = new NormalHashCluster();


    @Test
    public void testHash() {
        List<String> values = Arrays.asList("1", "1", "2");

        values.forEach(v -> log.info("value :'{}'  hash:'{}'", v, cluster.hash(v)));
    }

    @Test
    public void testNormalHash() {
        cluster.addNode(new Node("c1", "192.168.0.1"));
        cluster.addNode(new Node("c2", "192.168.0.2"));
        cluster.addNode(new Node("c3", "192.168.0.3"));
        cluster.addNode(new Node("c4", "192.168.0.4"));

        IntStream.range(0, DATA_COUNT)
                .forEach(index -> {
                    Node node = cluster.get(PRE_KEY + index);
                    node.put(PRE_KEY + index, "Test Data");
                });

        log.info("数据分布情况：");
        cluster.nodes.forEach(node ->
                log.info("IP : {} , 数据量： {}", node.getIp(), node.getData().size()));

        long hitCOunt = IntStream.range(0, DATA_COUNT)
                .filter(index -> cluster.get(PRE_KEY + index).get(PRE_KEY + index) != null)
                .count();
        log.info("缓存命中率：'{}'", hitCOunt * 1f / DATA_COUNT);
    }
}
