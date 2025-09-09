import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Objects;
import java.util.Random;

public class Participant extends Agent {
    private int privateValue;
    private AID banditorAID;

    private void registration(){
        // Descrizione dell'agente
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID()); // imposta l'AID (nome) dell'agente

        // Descrizione del servizio offerto
        ServiceDescription sd = new ServiceDescription();
        sd.setType("partecipante");       // tipo del servizio
        sd.setName("partecipante-asta"); // nome (arbitrario) del servizio

        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
            System.out.println("Partecipante registrato al DF con successo.");
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }

    private AID findBanditor(){
        // Definizione del tipo di servizio da cercare
        ServiceDescription sd = new ServiceDescription();
        sd.setType("banditore"); // tipo del servizio cercato

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.addServices(sd);

        try {
            DFAgentDescription[] risultati = DFService.search(this, dfd);
            if (risultati.length > 0) {
                System.out.println("Partecipante "+ getLocalName() + ": Trovato agente banditore");
                return risultati[0].getName();
            } else {
                System.out.println("Nessun agente trovato con il servizio 'vendita-asta'");
            }
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void setup() {
        privateValue = new Random().nextInt(7000,10000);
        System.out.println("Ciao sono il partecipante " + getLocalName() + " con privateValue: " + privateValue);

        registration();

        banditorAID = findBanditor();

        addBehaviour(new BuyerBehaviour());
        addBehaviour(new InformReceiverBehaviour());
    }



    @Override
    protected void takeDown() {}

    private class BuyerBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            MessageTemplate proposeTemplate = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
            ACLMessage msgPropose = myAgent.receive(proposeTemplate);

            if(msgPropose != null) {
                int current_price = Integer.parseInt(msgPropose.getContent());

                if (current_price <= privateValue) {
                    ACLMessage acceptMsg = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                    acceptMsg.addReceiver(banditorAID);
                    acceptMsg.setContent(privateValue+"");
                    myAgent.send(acceptMsg);
                }else{
                    ACLMessage acceptMsg = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
                    acceptMsg.addReceiver(banditorAID);
                    myAgent.send(acceptMsg);
                }
            }else {
                block();
            }
        }
    }

    private class InformReceiverBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            MessageTemplate informTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
            ACLMessage msgInform = myAgent.receive(informTemplate);
            if (msgInform != null) {
                String result = msgInform.getContent();
                if (Objects.equals(result, "winner")){
                    System.out.printf("I win! :) Says agent %s\n", myAgent.getLocalName());
                }else{
                    System.out.printf("I loose! :( Says agent %s\n", myAgent.getLocalName());
                }
                doDelete();
            }else{
                block();
            }
        }
    }
}
