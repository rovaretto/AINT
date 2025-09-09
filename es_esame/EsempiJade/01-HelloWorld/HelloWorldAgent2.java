import jade.core.Agent;
import java.util.Iterator;

public class HelloWorldAgent2 extends Agent {
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
    }
}
