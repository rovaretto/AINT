import jade.core.behaviours.Behaviour;

public class MyBehaviour extends Behaviour {
    private int counter = 0;
    public void action() {
        // perform operation Y
        System.out.println("Operation Y");
        counter++;
    }
    public boolean done() {
        return counter == 10;
    }
}
