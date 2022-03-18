package de.michl.sterbehilfeserver.person;

import de.michl.sterbehilfeserver.person.client.Client;
import de.michl.sterbehilfeserver.person.worker.Worker;

import java.util.ArrayList;

public class PersonHandler {

    private ArrayList<Worker> workerArrayList;
    private ArrayList<Client> clientArrayList;

    private volatile ArrayList<Worker> freeWorkers;
    private volatile ArrayList<Client> waitingList;


    public PersonHandler() {
        this.workerArrayList = new ArrayList<>();
        this.clientArrayList = new ArrayList<>();
        this.freeWorkers = new ArrayList<>();
        this.waitingList = new ArrayList<>();
    }

    public void registerWorker(Worker worker){
        this.workerArrayList.add(worker);
        this.freeWorkers.add(worker);

        System.out.println("Registered Worker: " + worker);
    }

    public void registerClient(Client client){
        this.clientArrayList.add(client);
        this.waitingList.add(client);

        System.out.println("Registered Client: " + client);
    }

    public void removeWorker(Worker worker){
        this.workerArrayList.remove(worker);
    }

    public void removeClient(Client client){
        this.clientArrayList.remove(client);
    }

    public ArrayList<Worker> getWorkerArrayList() {return workerArrayList;}
    public ArrayList<Client> getClientArrayList() {return clientArrayList;}
    public ArrayList<Worker> getFreeWorkers() {return freeWorkers;}
    public ArrayList<Client> getWaitingList() {return waitingList;}
}
