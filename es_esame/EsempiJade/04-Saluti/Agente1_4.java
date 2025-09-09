//package saluti;

import jade.core.Agent;

public class Agente1_4 extends Agent {

    public String targetSaluti;

    protected void setup() {

        System.out.println("Buongiorno, mi presento sono " + getName() + 
            " e sono pronto ad operare!");

        Object[] args = getArguments();
        if (args != null && args.length > 0) {
        
            targetSaluti = (String) args[0];
            System.out.println("Voglio salutare: " + targetSaluti);

            addBehaviour(new Agente1_4_Behaviour(this, targetSaluti));
        }
    
    }

}
                                

