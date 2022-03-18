package de.michl.sterbehilfeserver.person.match;

import de.michl.sterbehilfeserver.SterbehilfeServer;
import de.michl.sterbehilfeserver.communication.Socket;
import de.michl.sterbehilfeserver.person.Person;
import de.michl.sterbehilfeserver.person.client.Client;
import de.michl.sterbehilfeserver.person.worker.Worker;

import java.util.ArrayList;

public class MatchHandler {

    private ArrayList<Match> matchArrayList;

    public MatchHandler() {
        this.matchArrayList = new ArrayList<>();

        new MatchThread().start();
    }

    public void match(Worker worker, Client client) {
        if (worker != null && client != null) {
            Match match = new Match(worker, client);
            this.matchArrayList.add(match);

            SterbehilfeServer.instance.getPersonHandler().getFreeWorkers().remove(worker);
            SterbehilfeServer.instance.getPersonHandler().getWaitingList().remove(client);

            worker.getCommunicationHandler().sendMessage("matchFound;" + client.getUsername());
            client.getCommunicationHandler().sendMessage("matchFound;" + worker.getUsername());
        }
    }

    public void unmatch(Person person) {
        if (person instanceof Client) {
            Worker worker = getMatchByPerson(person).getWorker();

            SterbehilfeServer.instance.getPersonHandler().getFreeWorkers().add(worker);
            SterbehilfeServer.instance.getPersonHandler().removeClient((Client) person);

            worker.getCommunicationHandler().sendMessage("unmatch");
        } else {
            Client client = getMatchByPerson(person).getClient();

            SterbehilfeServer.instance.getPersonHandler().getWaitingList().add(client);
            SterbehilfeServer.instance.getPersonHandler().removeWorker((Worker) person);

            client.getCommunicationHandler().sendMessage("startQueue");
        }

        this.matchArrayList.remove(getMatchByPerson(person));
    }

    public Match getMatchByPerson(Person person) {
        Match match = null;
        for (Match m : this.matchArrayList) {
            if (m.getClient() == person || m.getWorker() == person) {
                match = m;
            }
        }

        return match;
    }

    public Socket getSocketByPerson(Person person) {
        Socket socket = null;
        if (person instanceof Client) {
            if (getMatchByPerson(person) != null) {
                socket = getMatchByPerson(person).getWorker().getSocket();
            }
        } else {
            if (getMatchByPerson(person) != null) {
                socket = getMatchByPerson(person).getClient().getSocket();
            }
        }
        return socket;
    }

    public ArrayList<Match> getMatchArrayList() {
        return matchArrayList;
    }
}
