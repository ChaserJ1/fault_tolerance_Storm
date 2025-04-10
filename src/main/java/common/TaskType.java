package common;

/**
 * 任务类型枚举
 */
public enum TaskType {
    CPU_INTENSIVE(1,"CPU密集型"),
    MEMORY_INTENSIVE(2,"内存密集型"),
    IO_INTENSIVE(3,"IO密集型");

    public int code;
    public String description;

    TaskType(int code,String description){
        this.code = code;
        this.description = description;
    }


}