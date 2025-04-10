package storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;

// 构建并运行 Storm 拓扑
public class StormTopology {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("data-spout", new DataSpout());
        builder.setBolt("integrated-bolt", new IntegratedBolt()).shuffleGrouping("data-spout");

        Config conf = new Config();
        conf.setDebug(false);

        if (args != null && args.length > 0) {
            // 提交到集群运行
            try {
                backtype.storm.StormSubmitter.submitTopologyWithProgressBar(args[0], conf, builder.createTopology());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // 本地模式运行
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("test", conf, builder.createTopology());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cluster.shutdown();
        }
    }
}