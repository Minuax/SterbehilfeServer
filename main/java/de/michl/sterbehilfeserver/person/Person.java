package de.michl.sterbehilfeserver.person;

import de.michl.sterbehilfeserver.communication.CommunicationHandler;
import de.michl.sterbehilfeserver.communication.Socket;

public abstract class Person {

    private CommunicationHandler communicationHandler;
    private Socket socket;

    private int id;
    private String username;


    public Person(CommunicationHandler communicationHandler, Socket socket, int id, String username) {
        this.communicationHandler = communicationHandler;
        this.socket = socket;
        this.id = id;
        this.username = username;
    }

    public CommunicationHandler getCommunicationHandler() {return communicationHandler;}

    public Socket getSocket() {return socket;}

    public int getId() {return id;}

    public String getUsername() {
        return username;
    }


    @Override
    public String toString() {
        return "Person{" +
                "socket=" + socket +
                ", id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
