package de.michl.sterbehilfeserver.communication;

import de.michl.sterbehilfeserver.SterbehilfeServer;
import de.michl.sterbehilfeserver.person.client.Client;
import de.michl.sterbehilfeserver.person.worker.Worker;

import java.io.IOException;

public class CommunicationThread extends Thread {

    private CommunicationHandler communicationHandler;

    private boolean running;

    public CommunicationThread(CommunicationHandler communicationHandler) {
        this.communicationHandler = communicationHandler;
        this.running = true;
    }

    @Override
    public void run() {
        String message;
        try {
            while (running && (message = this.communicationHandler.getSocket().readLine()) != null) {
                this.communicationHandler.processMessage(message);
            }

            System.out.println("killed");

            if (this.communicationHandler.getPerson() instanceof Client) {
                SterbehilfeServer.instance.getPersonHandler().removeClient((Client) this.communicationHandler.getPerson());
            } else {
                SterbehilfeServer.instance.getPersonHandler().registerWorker((Worker) this.communicationHandler.getPerson());
            }
        } catch (IOException e) {
            running = false;
        }
        super.run();
    }
}
