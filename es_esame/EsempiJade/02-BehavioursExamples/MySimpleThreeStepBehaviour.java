import jade.core.behaviours.Behaviour;

public class MySimpleThreeStepBehaviour extends Behaviour {
    private int step = 0;
    public void action() {
        System.out.println("Executing action of mysimplethreestepbehaviour, step: " + step + ".");
        // perform operation W
        System.out.println("Operation W");
        //myAgent.addBehaviour(new MyOneShotBehaviour());
        step++;
        System.out.println("Completed execution of action of mysimplethreestepbehaviour, step: " + step + ".");    
    }
    public boolean done() {
        System.out.println("Testing done condition: " + (step == 3));
        return step == 3;
    }
}
