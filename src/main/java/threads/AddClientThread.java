package threads;

import model.Client;
import service.ClientService;

public class AddClientThread extends Thread {
    Client client;

    public AddClientThread() {

    }

    public AddClientThread(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        ClientService.getInstance().addClient(client);
    }
}