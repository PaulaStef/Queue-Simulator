package queuesSimulator;

public class Task implements Comparable<Task> {
    private int ID;
    private int arrivalTime;
    private int finishTime;
    private int processingPeriod;

    public Task(int ID,int arrivalTime, int processingPeriod){
        this.ID = ID;
        this.processingPeriod = processingPeriod;
        this.arrivalTime = arrivalTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void decrementProcessingTime(){
        this.processingPeriod--;
    }
    public int getProcessingPeriod() {
        return processingPeriod;
    }

    public int getID() {
        return ID;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public String toString() {
        return "(" + ID + ", " + arrivalTime + ", " + processingPeriod + ")";
    }


    public int compareTo(Task t) {
        if(this.arrivalTime < t.arrivalTime)
                return -1;
        else
            if(this.arrivalTime > t.arrivalTime)
                return 1;
       return 0;
    }
}
