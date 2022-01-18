package queuesSimulator;

import View.SimulationFrame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        SimulationFrame frame = new SimulationFrame();
       SimulationManager simulation1 = new SimulationManager(args);
       simulation1.setFrame(frame);
       simulation1.setValues();
       Thread t = new Thread(simulation1);
       t.start();
    }

}
