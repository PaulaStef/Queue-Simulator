package queuesSimulator;

import strategy.ConcreteStrategyQueue;
import strategy.ConcreteStrategyTime;
import strategy.SelectionPolicy;
import strategy.Strategy;

import java.util.ArrayList;

public class Scheduler {
    private ArrayList<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        this.servers = new ArrayList<>();
        for(int i = 0; i < maxNoServers; i++){
            Server server = new Server(maxTasksPerServer);
            servers.add(server);
            Thread thread = new Thread(server);
            try{
                thread.start();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void changeStrategy(SelectionPolicy policy){
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            this.strategy = new ConcreteStrategyQueue();
            //System.out.println("Strategy : Queue");
        }
        if (policy == SelectionPolicy.SHORTEST_TIME) {
            this.strategy = new ConcreteStrategyTime();
            //System.out.println("Strategy : Time");
        }
    }

    public void dispatchTask(Task t){
        this.strategy.addTask(servers,t);
    }

    public ArrayList<Server> getServers() {
        return servers;
    }
}
