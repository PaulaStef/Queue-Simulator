package strategy;

import queuesSimulator.Server;
import queuesSimulator.Task;
import java.util.ArrayList;

public interface Strategy {
    public void addTask(ArrayList<Server> servers, Task t);
}
