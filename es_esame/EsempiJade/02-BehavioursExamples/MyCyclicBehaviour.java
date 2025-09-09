import jade.core.behaviours.CyclicBehaviour;

public class MyCyclicBehaviour extends CyclicBehaviour {
    private int counter = 0;
    public void action() {
        // perform operation Y
        System.out.println("Operation Y");
        counter++;
        if (counter == 10)
        	myAgent.doDelete();
    }
}
