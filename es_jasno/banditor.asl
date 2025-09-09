currentOffer(20000).
all_proposals_received
  :- nb_participants(NP) &                 // number of participants
     .count(accept[source(_)], NO) &   // number of proposes received
     .count(refuse[source(_)], NR) &      // number of refusals received
     NP = NO + NR.

/*registrazione al Directory facilitator*/
!register.
+!register <- .df_register(banditor).

!start_auction.

/*Plans*/
+!start_auction
    <-  .print("Waiting participants ");
        .wait(500);
        !send_offer.


+!send_offer
    <-  .df_search("participant",LP);
        +nb_participants(.length(LP));
        ?currentOffer(X);
        .print("Sending offer of ",X," to ",LP);
        .send(LP,tell,currentOffer(X));
        .wait(all_proposals_received);
        !eval_offer.


+!retract_refuse
    :   refuse[source(A)]
    <-  -refuse[source(A)];
        !retract_refuse.

+!retract_refuse
    :   not refuse[source(A)]
    <- true.

+!eval_offer
    :   not accept[source(A)]
    <-  .print("decremento");
        -currentOffer(X);
        +currentOffer(X-10);
        !retract_refuse;
        !send_offer.

+!eval_offer
    :   accept[source(A)]
    <-  .df_search("participant",LP);
        .send(A, tell,winner);
        .send(LP, tell,loser);
        .print("Winner is agent ",A).