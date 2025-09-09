import jade.core.Agent;
import jade.core.behaviours.*;

import java.util.*;

public class BookSellerAgent extends Agent {
    private Hashtable catalogue;
    private BookSellerGui myGui;
    protected void setup() {
        catalogue = new Hashtable();
        
        myGui = new BookSellerGui(this);
        myGui.showGui();
        
        addBehaviour(new OfferRequestsServer());
        
        addBehaviour(new PurchaseOrdersServer());
    }
    protected void takeDown() {
        myGui.dispose();
        System.out.println("Seller-agent " + getAID().getName() + " terminating.");
    }
    public void updateCatalogue(final String title, final int price) {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                catalogue.put(title, new Integer(price));
            }
        } );
    }
}
