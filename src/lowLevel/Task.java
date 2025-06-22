package lowLevel;

import java.time.Instant;
import java.util.UUID;

public class Task implements Comparable<Task>{
    private UUID id;
    private String name;
    private int priority;
    private Instant createdTimestamp;
    private String payload;

    public Task(String name, int priority, String payload){
        this.id = UUID.randomUUID();
        this.name = name;
        this.priority = priority;
        this.payload = payload;
        this.createdTimestamp = Instant.now();
    }

    @Override
    public int compareTo(Task o) {
        //insert highest priority first.
        return Integer.compare(o.priority, this.priority);
    }

    public String getName(){
        return this.name;
    }

    public int getPriority(){
        return this.priority;
    }
}
