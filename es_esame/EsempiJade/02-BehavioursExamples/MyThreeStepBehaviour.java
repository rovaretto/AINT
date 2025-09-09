import jade.core.behaviours.Behaviour;

public class MyThreeStepBehaviour extends Behaviour {
    private int step = 0;
    public void action() {
        System.out.println("Executing action of mythreestepbehaviour, step: " + step + ".");
        switch (step) {
        case 0: 
            // perform operation X
            System.out.println("Operation X");
            step++;
            break;
        case 1: 
            // perform operation Y
            System.out.println("Operation Y");
            step++;
            break;
        case 2: 
            // perform operation Z
            System.out.println("Operation Z");
            step++;
            break;
        }        
        System.out.println("Completed execution of action of mythreestepbehaviour, step: " + step + ".");    
    }
    public boolean done() {
        System.out.println("Testing done condition: " + (step == 3));
        return step == 3;
    }
}
