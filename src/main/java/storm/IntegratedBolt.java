package storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import error.OperatorErrorControl;
import loadbalance.LoadPredictor;
import loadbalance.TaskScheduler;
import operators.Operator;

import java.util.Map;

// 集成功能的 Bolt
public class IntegratedBolt extends BaseRichBolt {
    private OutputCollector collector;
    private Operator operator;
    private OperatorErrorControl errorControl;
    private LoadPredictor loadPredictor;
    private TaskScheduler taskScheduler;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        // 初始化算子
        this.operator = new Operator("op1", 0.8, 0.6, 0.7, 0.3, 0.3, 0.4);
        this.errorControl = new OperatorErrorControl(operator);
        this.loadPredictor = new LoadPredictor(3, 1); // 假设输入特征数为 3，输出维度为 1
        this.taskScheduler = new TaskScheduler(loadPredictor);
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
        taskScheduler.scheduleTask(task);

        // 发射处理结果
        collector.emit(new Values(data, weight));
        collector.ack(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("data", "weight"));
    }
}