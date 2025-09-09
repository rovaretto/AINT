import jade.core.behaviours.TickerBehaviour;
import jade.core.Agent;

public class MyTickerBehaviour extends TickerBehaviour {
    public MyTickerBehaviour(Agent agent, int time) {
        super(agent, time);
    }
    public void onTick() {
        // perform operation X
        System.out.println("Operation Z");
        myAgent.addBehaviour(new MyOneShotBehaviour());
        myAgent.addBehaviour(new MyOneShotBehaviour());
        //myAgent.doDelete();
    }
}
