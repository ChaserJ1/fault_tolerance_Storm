package common;


import lombok.Data;

/**
 * 任务类，包含了任务类型和该任务关联的节点
 */
@Data
public class Task {

    private TaskType type;
    private Node node;

    public Task(TaskType type) {
        this.type = type;
    }

}
