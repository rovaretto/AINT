import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;


public class OfferRequestsServer extends CyclicBehaviour {
    public void action() {
        ACLMessage msg = myAgent.receive();
        System.out.println("Checking message...");
        if (msg != null) {
            System.out.println("Received message.");
            String title = msg.getContent();
            ACLMessage reply = msg.createReply();
            Integer price = (Integer) ((BookSellerAgent)myAgent).catalogue.get(title);
            if (price != null) {
                reply.setPerformative(ACLMessage.PROPOSE);
                reply.setContent(String.valueOf(price.intValue()));
                System.out.println("Available");
            }
            else {
                reply.setPerformative(ACLMessage.REFUSE);
                reply.setContent("non-available");
                System.out.println("Non available");
            }
            myAgent.send(reply);
        }
        else {
            System.out.println("No messages. I block myself.");
            block();
        }
    }
}
