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
        //insert highest priority first or big one first
        return Integer.compare(o.priority, this.priority);
    }

    public String getName(){
        return this.name;
    }

    public int getPriority(){
        return this.priority;
    }

    public String getTime(){
        return  this.createdTimestamp.toString();
    }

    public UUID getId(){
        return this.id;
    }

    enum TaskStatus { SUBMITTED, PROCESSING, COMPLETED, FAILED }
}
