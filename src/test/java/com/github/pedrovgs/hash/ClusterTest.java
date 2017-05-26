package com.github.pedrovgs.hash;

import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * Created by tonydeng on 2017/5/22.
 */
public class ClusterTest {
    private static final Logger log = LoggerFactory.getLogger(ClusterTest.class);
    private static int DATA_COUNT = 100000;
    private static String PRE_KEY = "key";
    //    private static NumberFormat nf =
//        private Cluster cluster = new NormalHashCluster();
    private Cluster cluster = new ConsistencyHashCluster();

    public ClusterTest() throws NoSuchAlgorithmException {
    }


    @Before
    public void init() {
        cluster.add(new Node("c1", "192.168.0.1"));
        cluster.add(new Node("c2", "192.168.0.2"));
        cluster.add(new Node("c3", "192.168.0.3"));
        cluster.add(new Node("c4", "192.168.0.4"));
        cluster.add(new Node("c5", "192.168.0.5"));
    }

    @Test
    public void testHashCount() {

        IntStream.range(0, DATA_COUNT)
                .forEach(index -> {
                    Node node = cluster.get(PRE_KEY + index);
//                    log.info("node:'{}' index:'{}'", node, index);
                    node.put(PRE_KEY + index, "Test Data");
                });

        log.info("数据分布情况：");
        cluster.nodes.forEach(node ->
                log.info("IP : {} , 数据量： {}", node.getIp(), node.getData().size()));

        long hitCount = IntStream.range(0, DATA_COUNT)
                .filter(index -> cluster.get(PRE_KEY + index).get(PRE_KEY + index) != null)
                .count();
        log.info("缓存命中率：'{}'", hitCount * 1f / DATA_COUNT);
    }

    @Test
    public void testHashing() {
        Map<Integer, Integer> counter = Maps.newConcurrentMap();
        IntStream.range(0, DATA_COUNT)
                .forEach(index -> {
                    int h = Hashing.consistentHash(Hashing.sha1().hashInt(index), cluster.nodes.size());
                    if (counter.containsKey(h)) {
                        int oldValue = counter.get(h);
                        counter.put(h, oldValue + 1);
                    } else {
                        counter.put(h, 1);
                    }
                });
        long hitCount = 0L;
        for (Map.Entry<Integer, Integer> keys : counter.entrySet()) {
            log.info("IP: {}, 数据量： {}", cluster.nodes.get(keys.getKey()).getIp(), keys.getValue());
            hitCount = hitCount + keys.getValue();
        }

        log.info("缓存命中率：'{}'", hitCount * 1f / DATA_COUNT);
    }
}
