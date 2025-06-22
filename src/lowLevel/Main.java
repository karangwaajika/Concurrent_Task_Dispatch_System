package lowLevel;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final int  BUFFER_CAPACITY   = 5;
        final int NUMBER_OF_TASK = 10;
        List<Task> buffer = new ArrayList<>();

        Producer runnable = new Producer(buffer, BUFFER_CAPACITY, NUMBER_OF_TASK);
        Thread producerLowLevel1 = new Thread(runnable);
        Thread producerLowLevel2 = new Thread(runnable);

        try{
            producerLowLevel1.start();
            producerLowLevel1.join();

            producerLowLevel2.start();
            producerLowLevel2.join();
            System.out.println(buffer.size());
            System.out.println(buffer.stream().map(i->i.getName()+" "+i.getPriority()).toList());
        }catch (InterruptedException e){
            System.out.println( e.getMessage());
        }


    }
}
