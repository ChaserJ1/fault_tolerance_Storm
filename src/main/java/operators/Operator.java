package operators;


import lombok.Data;

/**
 * 算子类，包含算子权重计算逻辑
 */
@Data
public class Operator {
    private final String operatorId;
    private double outputImpact;       // 输出影响度 I_i
    private double computationCost;    // 计算复杂度 C_i
    private double dependencyDegree;   // 依赖程度 D_i
    private double alpha, beta, gamma; // 权重系数


    public Operator(String id, double I, double C, double D, double a, double b, double g) {
        this.operatorId = id;
        this.outputImpact = I;
        this.computationCost = C;
        this.dependencyDegree = D;
        this.alpha = a;
        this.beta = b;
        this.gamma = g;
    }


    /**
     * 计算算子权重
     * @return
     */
    public double calculateWeight() {
        return alpha * outputImpact + beta * computationCost + gamma * dependencyDegree;
    }

    /**
     * 根据负载情况动态调整权重系数
     */
    public void adjustWeights(double newAlpha, double newBeta, double newGamma) {
        this.alpha = newAlpha;
        this.beta = newBeta;
        this.gamma = newGamma;
    }

}
