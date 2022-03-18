package de.michl.sterbehilfeserver.person.match;

import de.michl.sterbehilfeserver.SterbehilfeServer;
import de.michl.sterbehilfeserver.person.client.Client;

public class MatchThread extends Thread {

    @Override
    public void run() {
        while (true) {
            if (SterbehilfeServer.instance.getPersonHandler().getFreeWorkers().size() == 0) {
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            try {
                for (Client client : SterbehilfeServer.instance.getPersonHandler().getWaitingList()) {
                    if (!SterbehilfeServer.instance.getPersonHandler().getFreeWorkers().isEmpty())
                        SterbehilfeServer.instance.getMatchHandler().match(SterbehilfeServer.instance.getPersonHandler().getFreeWorkers().get(0), client);
                    else
                        break;
                }
            }catch (Exception e){

            }

            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
