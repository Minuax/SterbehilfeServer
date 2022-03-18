package de.michl.sterbehilfeserver.person.client;

import de.michl.sterbehilfeserver.communication.CommunicationHandler;
import de.michl.sterbehilfeserver.communication.Socket;
import de.michl.sterbehilfeserver.person.Person;

public class Client extends Person {

    public Client(CommunicationHandler communicationHandler, Socket socket, int id, String username) {
        super(communicationHandler, socket, id, username);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
