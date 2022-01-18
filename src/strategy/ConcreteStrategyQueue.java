package strategy;

import queuesSimulator.Server;
import queuesSimulator.Task;

import java.util.ArrayList;

public class ConcreteStrategyQueue implements Strategy{
    @Override
    public void addTask(ArrayList<Server> servers, Task t) {
        if(!servers.isEmpty()){
            int minPersons = -1;
            Server minServer = null;
            for(Server server : servers){
                if(server.getTasks().length < minPersons){
                    minPersons = server.getTasks().length;
                    minServer = server;
                }
            }
           if(minPersons > -1){
               minServer.addTask(t);
           }
        }
    }
}
