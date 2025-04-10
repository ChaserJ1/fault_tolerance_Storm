package common;

public class Task {
    private TaskType type;
    private Node node;

    public Task(TaskType type) {
        this.type = type;
    }

    public TaskType getType() {
        return type;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
