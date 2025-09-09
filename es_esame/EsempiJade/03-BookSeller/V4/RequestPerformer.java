import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class RequestPerformer extends OneShotBehaviour {
    public void action() {
        myAgent.doWait(20000);
        System.out.println("Preparing message...");
        ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
        for (int i=0; i < ((BookBuyerAgent)myAgent).sellerAgents.length; ++i) {
            cfp.addReceiver(((BookBuyerAgent)myAgent).sellerAgents[i]);
        }
        cfp.setContent(((BookBuyerAgent)myAgent).targetBookTitle);
        System.out.println("Sending message..." + cfp);
        myAgent.send(cfp);
    }
}
