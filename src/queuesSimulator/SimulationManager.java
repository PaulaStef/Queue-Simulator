package queuesSimulator;

import View.SimulationFrame;
import strategy.SelectionPolicy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SimulationManager implements Runnable {
    public int timeLimit ;
    public int maxProcessingTime ;
    public int minProcessingTime ;
    public int numberOfServers ;
    public int numberOfClients ;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;
    public int minArrivalTime;
    public int maxArrivalTime;
    private Scheduler scheduler;
    private SimulationFrame frame;
    private ArrayList<Task> generatedTasks;
    private int start = 0;
    FileWriter writer;

   public SimulationManager(String[] args) throws IOException {
       //readFile(args);
       //scheduler = new Scheduler(numberOfServers,numberOfClients);
       //scheduler.changeStrategy(selectionPolicy);
       //frame = new SimulationFrame();
       generatedTasks = new ArrayList<Task>();
       //generateNRandomTasks(numberOfClients);
       writer = new FileWriter(args[1]);
   }
    public void readFile(String[] args){
        try {
            File fileIn = new File(args[0]);
            Scanner reader= new Scanner(fileIn);

            for(int i = 0; reader.hasNextLine(); i++) {
                String data = reader.nextLine();
                int comma = data.indexOf(",");

                if(i == 0) {
                    this.numberOfClients = Integer.parseInt(data);
                    System.out.println(this.numberOfClients);
                }
                if(i == 1) {
                    this.numberOfServers = Integer.parseInt(data);
                    System.out.println(this.numberOfServers);
                }
                if(i == 2) {
                    this.timeLimit = Integer.parseInt(data);
                }
                if(i == 3) {
                    this.minArrivalTime = Integer.parseInt(data.substring(0, comma));
                    this.maxArrivalTime = Integer.parseInt(data.substring(comma + 1));
                }
                if( i == 4) {
                    this.minProcessingTime = Integer.parseInt(data.substring(0, comma));
                    this.maxProcessingTime = Integer.parseInt(data.substring(comma + 1));
                }
            }

            reader.close();

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

   private void generateRandomTasks(int clients){
       for(int i = 1; i <= clients; i++) {
           Random rand = new Random();
           int arrivalTime = rand.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime;
           int processingTime = rand.nextInt(maxProcessingTime - minProcessingTime + 1) + minProcessingTime;
           Task task = new Task(i,arrivalTime,processingTime);
           generatedTasks.add(task);
       }
       Collections.sort(generatedTasks);
   }

    @Override
    public void run() {
       while(start == 0){
           try {
               Thread.sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
        //System.out.println(timeLimit);
      int currentTime = 0;
      while(currentTime < timeLimit){
          try {
              //System.out.println("\n" + "Time: " + currentTime + "\n");
              writer.write("\n" + "Time: " + currentTime + "\n");
          writer.write("Waiting clients: ");
          for(Task task : generatedTasks) {
              writer.write(task.toString() + ";");
          }
          writer.write("\n");
          } catch (IOException e) {
              e.printStackTrace();
          }
          int i = 0;
          for(Server server : scheduler.getServers()){
              try {
                  if (server.getTasks() == null ) {
                      writer.write("Queue " + i + ": Closed " + "\n");
                  } else {
                      writer.write("Queue " + i + ": ");
                      for (Task task : server.getTasks()) {
                          writer.write(task.toString() + ";");

                      }
                      writer.write("\n");
                  }
              }catch (Exception e){
                  System.out.println(e.getMessage());
              }
              i++;
          }
          Iterator<Task> iterator = generatedTasks.iterator();
          while(iterator.hasNext()){
              Task task = iterator.next();
              if(task.getArrivalTime() == currentTime){
                  scheduler.dispatchTask(task);
                  iterator.remove();
              }

          }
          frame.updateFrame(currentTime,generatedTasks,scheduler);
          currentTime++;
          try {
              Thread.sleep(1000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
      /*
      for(Server server : scheduler.getServers()){
          server.stopThread();
      } */
        try {
            writer.write("\nAverage waiting time: " + Server.getTotalWaitingTime() / numberOfClients);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFrame(SimulationFrame frame) {
        this.frame = frame;
    }

    public void setValues(){
       frame.getConfirm().addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               timeLimit = Integer.parseInt(frame.getSimulationTime().getText());
               numberOfClients = Integer.parseInt(frame.getClientsNo().getText());
               numberOfServers = Integer.parseInt(frame.getQueuesNo().getText());
               minArrivalTime = Integer.parseInt(frame.getMinArrival().getText());
               maxArrivalTime = Integer.parseInt(frame.getMaxArrival().getText());
               minProcessingTime = Integer.parseInt(frame.getMinProcess().getText());
               maxProcessingTime = Integer.parseInt(frame.getMaxArrival().getText());
               scheduler = new Scheduler(numberOfServers,numberOfClients);
               scheduler.changeStrategy(selectionPolicy);
               generateRandomTasks(numberOfClients);
               /*
               for(Task task : generatedTasks){
                   System.out.println(task.toString());
               }*/
               start = 1;
           }
       });
    }
}
