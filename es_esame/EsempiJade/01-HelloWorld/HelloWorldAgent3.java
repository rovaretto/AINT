import jade.core.Agent;
import java.util.Iterator;

public class HelloWorldAgent3 extends Agent {
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
        System.out.println("My arguments are:");
        Object[] args = getArguments();
        if (args != null) {
            for (int i = 0; i < args.length; ++i) {
                System.out.println("- " + args[i]);
            }
        }
        doWait(15000);
        doDelete();
        System.out.println("Dopo doDelete().");
    }
    protected void takeDown() {
        System.out.println("Ho finito il mio lavoro.");
    }
}
