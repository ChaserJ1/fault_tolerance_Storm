package storm;


import common.Task;
import common.TaskType;
import error.OperatorErrorControl;

import operators.Operator;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

// 集成功能的 Bolt
public class IntegratedBolt extends BaseRichBolt {
    private OutputCollector collector;
    private Operator operator;
    private OperatorErrorControl errorControl;


    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        // 初始化算子
        this.operator = new Operator("op1", 0.8, 0.6, 0.7, 0.3, 0.3, 0.4);
        this.errorControl = new OperatorErrorControl(operator);

    }

    @Override
    public void execute(Tuple input) {
        String data = input.getStringByField("data");
        // 计算算子权重
        double weight = operator.calculateWeight();

        // 模拟误差
        double currentError = 0.1;
        if (errorControl.isErrorExceeded(currentError)) {
            errorControl.adjustBackupStrategy();
        }

        // 模拟任务
        Task task = new Task(TaskType.CPU_INTENSIVE);

        // 发射处理结果
        collector.emit(new Values(data, weight));
        collector.ack(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("data", "weight"));
    }


}