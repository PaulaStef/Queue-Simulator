package strategy;

import queuesSimulator.Server;
import queuesSimulator.Task;
import java.util.ArrayList;

public class ConcreteStrategyTime implements Strategy{
    @Override
    public void addTask(ArrayList<Server> servers, Task t) {
        if(!servers.isEmpty()){
            int minWaitingTime = Integer.MAX_VALUE;
            Server minServer = null;
            for(Server server : servers){
                if(server.getWaitingPeriod() < minWaitingTime){
                    minWaitingTime = server.getWaitingPeriod();
                    minServer = server;
                }
            }
            if(minWaitingTime > -1){
                Server.setTotalWaitingTime(Server.getTotalWaitingTime() + minServer.getWaitingPeriod() - 1);
                minServer.addTask(t);
            }
        }
    }
}
