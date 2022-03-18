package de.michl.sterbehilfeserver.person.match;

import de.michl.sterbehilfeserver.person.client.Client;
import de.michl.sterbehilfeserver.person.worker.Worker;

public class Match {

    private Worker worker;
    private Client client;

    public Match(Worker worker, Client client){
        this.worker = worker;
        this.client = client;
    }

    public Worker getWorker() {return worker;}

    public Client getClient() {return client;}
}
