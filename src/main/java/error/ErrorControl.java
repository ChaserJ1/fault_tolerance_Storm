package error;

/**
 * 误差控制接口
 */
interface ErrorControl {
    // 根据权重获取允许的最大误差阈值
    double getErrorThreshold();

    // 检测误差是否超标
    boolean isErrorExceeded(double currentError);

    // 动态调整备份策略（误差超标时触发）
    void adjustBackupStrategy();
}