import jade.core.behaviours.WakerBehaviour;
import jade.core.Agent;

public class MyWakerBehaviour extends WakerBehaviour {
    public MyWakerBehaviour(Agent agent, int time) {
        super(agent, time);
    }
    public void onWake() {
        // perform operation X
        System.out.println("Operation X");
        
        //myAgent.doDelete();
    }
}
