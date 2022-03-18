package de.michl.sterbehilfeserver.communication;

import de.michl.sterbehilfeserver.SterbehilfeServer;
import de.michl.sterbehilfeserver.person.Person;
import de.michl.sterbehilfeserver.person.client.Client;
import de.michl.sterbehilfeserver.person.worker.Worker;

import java.io.IOException;
import java.util.Base64;

public class CommunicationHandler {

    private Socket socket;
    private CommunicationThread communicationThread;
    private Person person;

    public CommunicationHandler(Socket socket) {
        this.socket = socket;

        this.communicationThread = new CommunicationThread(this);
        this.communicationThread.start();
    }

    public void processMessage(String msg) throws IOException {
        String message = new String(Base64.getDecoder().decode(msg.getBytes()));

        if (message.split(";").length > 0) {
            String[] messageParts = message.split(";");

            String command = messageParts[0];
            switch (command) {
                case "connect":
                    this.person = messageParts[1].equalsIgnoreCase("client") ? new Client(this, this.socket, Integer.parseInt(messageParts[2]), messageParts[3]) : new Worker(this, this.socket, Integer.parseInt(messageParts[2]), messageParts[3], messageParts[4], messageParts[5]);

                    if (this.person instanceof Client) {
                        SterbehilfeServer.instance.getPersonHandler().registerClient((Client) this.person);
                        sendMessage("startQueue");
                    } else {
                        SterbehilfeServer.instance.getPersonHandler().registerWorker((Worker) this.person);
                    }
                    break;

                case "disconnect":
                    if (SterbehilfeServer.instance.getMatchHandler().getMatchByPerson(this.person) == null) {
                        if (this.person instanceof Client) {
                            SterbehilfeServer.instance.getPersonHandler().getWaitingList().remove((Client) this.person);
                            SterbehilfeServer.instance.getPersonHandler().removeClient((Client) this.person);
                        } else {
                            SterbehilfeServer.instance.getPersonHandler().getFreeWorkers().remove((Worker) this.person);
                            SterbehilfeServer.instance.getPersonHandler().removeWorker((Worker) this.person);
                        }
                    } else {
                        SterbehilfeServer.instance.getMatchHandler().unmatch(this.person);
                    }
                    break;
                case "searchWorker":
                    sendMessage("endQueue");
                    break;
                case "ping":
                    sendMessage("chatAppend;pong");
                    break;
                case "chatMessage":
                    SterbehilfeServer.instance.getMatchHandler().getSocketByPerson(this.person).write(Base64.getEncoder().encodeToString(("chatAppend;" + messageParts[1]).getBytes()));
                    break;
                default:
                    System.out.println("Something went wrong when processing message: " + message);
            }
        }
    }

    public void sendMessage(String message) {
        String encodedString = Base64.getEncoder().encodeToString(message.getBytes());
        try {
            this.socket.write(encodedString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public Person getPerson() {
        return person;
    }
}
