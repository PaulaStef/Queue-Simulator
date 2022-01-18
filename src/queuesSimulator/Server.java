package queuesSimulator;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private boolean runThreadTime = true ;
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private static double totalWaitingTime = 0;
    private static double totalProcessedTasks = 0;

    public Server(int limit) {
        this.tasks = new LinkedBlockingQueue<Task>(limit);
        this.waitingPeriod = new AtomicInteger(0);

    }
    public void addTask(Task task){
        waitingPeriod.addAndGet(task.getProcessingPeriod());
        tasks.add(task);
    }

    @Override
    public void run() {
      while(runThreadTime) {
          if(!tasks.isEmpty()) {
              Task task = tasks.peek();

              if(!runThreadTime) {
                  break;
              }

              while(task.getProcessingPeriod() > 0) {
                  try {
                      Thread.currentThread();
                      Thread.sleep(1000); // one second
                  } catch (InterruptedException e) {
                      System.out.println("Someone interrupted");
                  }

                  task.decrementProcessingTime();
                  this.waitingPeriod.getAndDecrement();
              }
              totalProcessedTasks++;
              tasks.remove();
          }
      }
    }

    public Task[] getTasks() {
        if (this.tasks.isEmpty())
            return null;

        Task tasks[] = new Task[this.tasks.size()];
        int i = 0;

        for (Task task : this.tasks) {
            tasks[i] = task;
            i++;
        }

        return tasks;
    }

    public Integer getWaitingPeriod() {
        return waitingPeriod.intValue();
    }

    public void setRunThreadTime(boolean runThreadTime) {
        this.runThreadTime = runThreadTime;
    }

    public static double getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public static void setTotalWaitingTime(double totalWaitingTime) {
        Server.totalWaitingTime = totalWaitingTime;
    }

    public void stopThread() {
        runThreadTime = false;
        tasks.add(new Task(0, 0, 0));
    }

    public static double getTotalProcessedTasks() {
        return totalProcessedTasks;
    }
}
