package de.michl.sterbehilfeserver.person.worker;

import de.michl.sterbehilfeserver.communication.CommunicationHandler;
import de.michl.sterbehilfeserver.communication.Socket;
import de.michl.sterbehilfeserver.person.Person;

public class Worker extends Person {

    private String name, sirname;
    private boolean occupied;

    public Worker(CommunicationHandler communicationHandler, Socket socket, int id, String username, String name, String sirname) {
        super(communicationHandler, socket, id, username);

        this.name = name;
        this.sirname = sirname;

        this.occupied = false;
    }

    public String getName() {return name;}

    public String getSirname() {return sirname;}

    public boolean isOccupied() {return occupied;}

    public void setOccupied(boolean occupied) {this.occupied = occupied;}

    @Override
    public String toString() {
        return "Worker{" +
                "name='" + name + '\'' +
                ", sirname='" + sirname + '\'' +
                ", occupied=" + occupied +
                '}';
    }
}
