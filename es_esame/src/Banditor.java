import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Banditor extends Agent {
    private int offer = 50000;
    private List<AID>  participants;

    private void registration(){
        // Descrizione dell'agente
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID()); // imposta l'AID (nome) dell'agente

        // Descrizione del servizio offerto
        ServiceDescription sd = new ServiceDescription();
        sd.setType("banditore");       // tipo del servizio
        sd.setName("banditore-asta"); // nome (arbitrario) del servizio

        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
            System.out.println("Banditore registrato al DF con successo.");
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }

    private List<AID> collectParticipants(){
        List<AID> participants = new ArrayList<>();

        // Definizione del tipo di servizio da cercare
        ServiceDescription sd = new ServiceDescription();
        sd.setType("partecipante"); // tipo del servizio cercato

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.addServices(sd);

        try {
            DFAgentDescription[] risultati = DFService.search(this, dfd);
            if (risultati.length > 0) {
                System.out.println("Banditore: Trovati " + risultati.length + " agenti che partecipano.");

                 participants = Arrays.stream(risultati)
                                            .map(DFAgentDescription::getName)
                                            .toList();
            } else {
                System.out.println("Banditore: Nessun agente trovato che partecipa.");
            }
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        return participants;
    }

    protected void setup(){
            System.out.println("Ciao sono il banditore " + getLocalName());

            registration();

            participants = collectParticipants();

            addBehaviour(new ProposingOfferBehaviour());
            addBehaviour(new AcceptOfferBehaviour());
    }


    private class ProposingOfferBehaviour extends CyclicBehaviour {
        int state = 0;
        int repliesCnt = 0;

        @Override
        public void action() {
            switch (state) {
                case 0:
                    //send propose
                    ACLMessage proposeMsg = new ACLMessage(ACLMessage.PROPOSE);
                    proposeMsg.setContent(offer+"");
                    for (AID part : participants){
                        proposeMsg.addReceiver(part);
                    }
                    myAgent.send(proposeMsg);
                    state = 1;
                    break;
                case 1:
                    MessageTemplate mtReject = MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL);
                    ACLMessage reply = myAgent.receive(mtReject);
                    if (reply != null) {
                        // Reply received
                        repliesCnt++;
                        if (repliesCnt >= participants.size()) {
                            // We received all replies
                            state = 0;
                            repliesCnt = 0;
                            offer -= 100;
                        }
                    }
                    else {
                        block();
                    }

            }
        }
    }

    private class AcceptOfferBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            MessageTemplate mtAccept = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
            ACLMessage reply = myAgent.receive(mtAccept);
            if (reply != null) {
                // Reply received
                ACLMessage informWinnerMsg =  new ACLMessage(ACLMessage.INFORM);
                informWinnerMsg.addReceiver(reply.getSender());
                informWinnerMsg.setContent("winner");
                myAgent.send(informWinnerMsg);

                System.out.println("Banditore: " + reply.getSender().getLocalName() + " ha vinto l'asta offrendo " + offer);

                //send lose to other agent

                ACLMessage informLooserMsg =  new ACLMessage(ACLMessage.INFORM);
                for (AID part : participants){
                    if(!part.equals(reply.getSender())){
                        informLooserMsg.addReceiver(part);
                    }
                }
                informLooserMsg.setContent("looser");
                myAgent.send(informLooserMsg);

                doDelete();
            }
            else {
                block();
            }
        }
    }
}
