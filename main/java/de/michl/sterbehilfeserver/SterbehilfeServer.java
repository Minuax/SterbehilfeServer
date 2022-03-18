package de.michl.sterbehilfeserver;

import de.michl.sterbehilfeserver.communication.CommunicationHandler;
import de.michl.sterbehilfeserver.communication.ServerSocket;
import de.michl.sterbehilfeserver.communication.Socket;
import de.michl.sterbehilfeserver.person.PersonHandler;
import de.michl.sterbehilfeserver.person.match.MatchHandler;

import java.io.IOException;

public class SterbehilfeServer {

    public static SterbehilfeServer instance;

    private ServerSocket serverSocket;

    private PersonHandler personHandler;
    private MatchHandler matchHandler;

    public static void main(String[] args) {
        new SterbehilfeServer();
    }

    public SterbehilfeServer() {
        instance = this;

        try {
            startServer(11831);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);

        this.personHandler = new PersonHandler();
        this.matchHandler = new MatchHandler();

        while (true) {
            Socket socket = serverSocket.accept();

            CommunicationHandler communicationHandler = new CommunicationHandler(socket);
        }
    }

    public PersonHandler getPersonHandler() {
        return personHandler;
    }

    public MatchHandler getMatchHandler() {
        return matchHandler;
    }
}
