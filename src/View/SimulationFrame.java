package View;

import queuesSimulator.Scheduler;
import queuesSimulator.Server;
import queuesSimulator.Task;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SimulationFrame {
    private JFrame framePP;
    private JPanel panelPP;
    private JTextField simulationTime;
    private JTextField clientsNo;
    private JTextField queuesNo;
    private JTextField minArrival;
    private JTextField maxArrival;
    private JTextField minProcess;
    private JTextField maxProcess;
    private JButton confirm;
    private JTextArea result;

    public SimulationFrame() {
        initialize();
    }

    private void setFields(){
        simulationTime = new JTextField();
        simulationTime.setBounds(60, 50,40,40);
        JLabel label_1 = new JLabel("Simulation time ");
        label_1.setBounds(40,20,100,30);
        panelPP.add(label_1);
        panelPP.add(simulationTime);

        clientsNo = new JTextField();
        clientsNo.setBounds(180, 50,40,40);
        JLabel label_2 = new JLabel("Number clients ");
        label_2.setBounds(160,20,100,30);
        panelPP.add(label_2);
        panelPP.add(clientsNo);

        queuesNo = new JTextField();
        queuesNo.setBounds(300, 50,40,40);
        JLabel label_3 = new JLabel("Number queues ");
        label_3.setBounds(280,20,100,30);
        panelPP.add(label_3);
        panelPP.add(queuesNo);

        minArrival = new JTextField();
        minArrival.setBounds(420, 50,40,40);
        JLabel label_4 = new JLabel("Min arrival time ");
        label_4.setBounds(400,20,130,30);
        panelPP.add(label_4);
        panelPP.add(minArrival);

        maxArrival = new JTextField();
        maxArrival.setBounds(540, 50,40,40);
        JLabel label_5 = new JLabel("Max arrival time ");
        label_5.setBounds(520,20,140,30);
        panelPP.add(label_5);
        panelPP.add(maxArrival);


        maxProcess = new JTextField();
        maxProcess.setBounds(140, 160,40,40);
        JLabel label_6 = new JLabel("Max processing time ");
        label_6.setBounds(120,130,140,30);
        panelPP.add(label_6);
        panelPP.add(maxProcess);

        minProcess = new JTextField();
        minProcess.setBounds(340, 160,40,40);
        JLabel label_8 = new JLabel("Min processing time ");
        label_8.setBounds(320,130,140,30);
        panelPP.add(label_8);
        panelPP.add(minProcess);

        result = new JTextArea();
        result.setBounds(100, 250,500,450);
        panelPP.add(result);

        confirm = new JButton("Confirm");
        confirm.setBounds(460,160,100,50);
        confirm.setFont(new Font("Ariel",Font.PLAIN,17));
        panelPP.add(confirm);

    }
    private void initialize(){
        framePP = new JFrame();
        framePP.setSize(900, 800);
        framePP.setBounds(550, 650, 800, 700);
        framePP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framePP.getContentPane().setLayout(null);
        panelPP = new JPanel();
        panelPP.setBounds(20, 20, 650, 600);
        framePP.getContentPane().add(panelPP);
        panelPP.setLayout(null);
        setFields();
        framePP.add(panelPP);
        framePP.setVisible(true);
    }

    public void updateFrame(int time, ArrayList<Task> tasks, Scheduler scheduler){
     result.setText("Time: " + time + "\n");
     result.setText(result.getText() + "Waiting Clients: ");
        for(Task task : tasks) {
            result.setText(result.getText() + task.toString() + ";");
        }
        result.setText(result.getText() + "\n");
        int i = 0;
        for(Server server : scheduler.getServers()){
                if (server.getTasks() == null ) {
                    result.setText(result.getText() + "Queue " + i + ": Closed " + "\n");
                } else {

                    result.setText(result.getText()+"\n" + "Queue " + i + ": ");
                    for (Task task : server.getTasks()) {
                        result.setText(result.getText() + task.toString() + ";");

                    }
                   result.setText(result.getText() + "\n");
                }
            i++;
        }
    }

    public JButton getConfirm() {
        return confirm;
    }

    public void setConfirm(JButton confirm) {
        this.confirm = confirm;
    }

    public JTextField getSimulationTime() {
        return simulationTime;
    }

    public JTextField getClientsNo() {
        return clientsNo;
    }

    public JTextField getQueuesNo() {
        return queuesNo;
    }

    public JTextField getMinArrival() {
        return minArrival;
    }

    public JTextField getMaxArrival() {
        return maxArrival;
    }

    public JTextField getMinProcess() {
        return minProcess;
    }

    public JTextField getMaxProcess() {
        return maxProcess;
    }
}

