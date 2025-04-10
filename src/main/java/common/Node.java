package common;

import lombok.Data;

/**
 * 节点类，根据name来标识不同的节点
 */
@Data
public class Node {
    private String name;

    public Node(String name) {
        this.name = name;
    }

}
