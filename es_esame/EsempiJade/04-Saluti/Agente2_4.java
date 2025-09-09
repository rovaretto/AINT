//package saluti;

import jade.core.Agent;

public class Agente2_4 extends Agent {

    protected void setup() {

        System.out.println("Buongiorno, mi presento sono " + getName() + 
            " e sono pronto ad operare!");

        addBehaviour(new Agente2_4_Behaviour(this));

    }

}
                                

