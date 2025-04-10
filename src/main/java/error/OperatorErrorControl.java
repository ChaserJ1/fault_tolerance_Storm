package error;

import operators.Operator;

/**
 * 算子误差控制
 */
class OperatorErrorControl implements ErrorControl {
    private Operator operator;
    private double errorThreshold; // 误差阈值

    public OperatorErrorControl(Operator op) {
        this.operator = op;
        // 初始化阈值：权重越高允许误差越小（示例公式）
        this.errorThreshold = 1.0 / (1 + op.calculateWeight());
    }

    @Override
    public double getErrorThreshold() {
        return errorThreshold;
    }

    @Override
    public boolean isErrorExceeded(double currentError) {
        return currentError > errorThreshold;
    }

    @Override
    public void adjustBackupStrategy() {
        // 误差超标时增加备份比例（示例逻辑）
        double currentBackup = getCurrentBackupRatio();
        double newBackup = Math.min(currentBackup + 0.1, 1.0);
        setBackupRatio(newBackup);
        System.out.println("调整算子 " + operator.getOperatorId() + " 备份比例为: " + newBackup);
    }

    // 模拟获取/设置备份比例
    private double getCurrentBackupRatio() {
        return 0.5;
    }

    private void setBackupRatio(double ratio) {
    }
}