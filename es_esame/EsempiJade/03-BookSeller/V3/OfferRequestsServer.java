import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;


public class OfferRequestsServer extends CyclicBehaviour {
    public void action() {
        ACLMessage msg = myAgent.receive();
        System.out.println("Checking message...");
        if (msg != null) {
            String title = msg.getContent();
            ACLMessage reply = msg.createReply();
            Integer price = (Integer) ((BookSellerAgent)myAgent).catalogue.get(title);
            if (price != null) {
                reply.setPerformative(ACLMessage.PROPOSE);
                reply.setContent(String.valueOf(price.intValue()));
            }
            else {
                reply.setPerformative(ACLMessage.REFUSE);
                reply.setContent("non-available");
            }
            myAgent.send(reply);
        }
        else {
            System.out.println("Nessun messaggio trovato in coda.");
            block();
            System.out.println("Nessun messaggio trovato in coda 2.");
        }
        System.out.println("Nessun messaggio trovato in coda 3.");
    }
}
