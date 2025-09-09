import jade.core.Agent;
import java.util.Iterator;

public class HelloWorldAgent4 extends Agent {
    protected void setup () {
        // Printout a welcome message
        System.out.println("Hello World. I'am an agent!");
        System.out.println("My local-name is " + getAID().getLocalName());
        System.out.println("My GUID is " + getAID().getName());
        System.out.println("My addresses are:");
        Iterator it = getAID().getAllAddresses();
        while (it.hasNext()) {
            System.out.println("- " + it.next());
        }
        //OneShotBehaviour
        //addBehaviour(new MyOneShotBehaviour());
        //addBehaviour(new MyOneShotBehaviour());
        //CyclicBehaviour
        //addBehaviour(new MyCyclicBehaviour());
        //Behavviour
        //addBehaviour(new MyBehaviour());
        //Behaviour
        //addBehaviour(new MySimpleThreeStepBehaviour());
        //addBehaviour(new MyOneShotBehaviour());
        //addBehaviour(new MyOneShotBehaviour());
        //Behaviour
        //addBehaviour(new MyThreeStepBehaviour());
        //addBehaviour(new MyOneShotBehaviour());
        //addBehaviour(new MyThreeStepBehaviour());
        //WakerBehaviour
        addBehaviour(new MyWakerBehaviour(this, 10000));        
        //TickerBehaviour
        addBehaviour(new MyTickerBehaviour(this, 10000)); 
        //doDelete();       

    }
    protected void takeDown() {
        System.out.println("Agent " + getAID().getName() + " termination.");
    }
}
