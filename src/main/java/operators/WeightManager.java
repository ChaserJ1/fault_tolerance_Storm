package operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据贪心算法分配算子权重
 */
public class WeightManager {
    private List<Operator> operators;
    private double totalResources; // 系统总资源

    public WeightManager(double resources) {
        this.totalResources = resources;
        this.operators = new ArrayList<>();
    }


    /**
     * 添加算子
     * @param op
     */
    public void addOperator(Operator op) {
        operators.add(op);
    }

    /**
     * 按权重降序排序
     */
    private void sortByWeight() {
        operators.sort((o1, o2) -> Double.compare(o2.calculateWeight(), o1.calculateWeight()));
    }

    /**
     * 贪心算法分配备份资源
     * @return
     */
    public Map<Operator, Double> allocateResources() {
        // 根据权重降序排序
        sortByWeight();
        Map<Operator, Double> allocation = new HashMap<>();
        double usedResources = 0;

        for (Operator op : operators) {
            // 计算当前算子可分配的最大比例（基于权重）
            double proposed = op.calculateWeight() / totalResources;
            double actual = Math.min(proposed, totalResources - usedResources);
            allocation.put(op, actual);
            usedResources += actual;
            if (usedResources >= totalResources) break;
        }
        return allocation;
    }

    /**
     * 动态调整算子权重
     * @param newWeights
     */
    public void adjustOperatorWeights(Map<String, Double[]> newWeights) {
        for (Operator op : operators) {
            if (newWeights.containsKey(op.getOperatorId())) {
                Double[] weights = newWeights.get(op.getOperatorId());
                op.adjustWeights(weights[0], weights[1], weights[2]);
            }
        }
    }
}