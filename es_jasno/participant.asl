/*registrazione al Directory facilitator*/
!register.
+!register <- .df_register(participant).

!inizializza.

+!inizializza
  <- .random(R);
     Value = (3000*R)+7000;
     +privateValue(Value).

+currentOffer(X)
    :   privateValue(Y) &
        X > Y
    <-  .print("Recived ", X, ", refuse because my private value is ", Y);
        .df_search("banditor",LB);
        .send(LB, tell, refuse).

+currentOffer(X)
    :   privateValue(Y) &
        X <= Y
    <-  .print("Recived ", X, ", accept");
        .df_search("banditor",LP);
        .send(LP, tell, accept).

+winner
    <-  .my_name(Me);
        .print(Me, ": ho vinto!").

+loser
    :   not winner
    <-  .my_name(Me);
        .print(Me, ": ho perso :(").