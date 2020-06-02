package service;


import exceptions.ClientNotFoundException;
import exceptions.ExistingClientException;
import exceptions.WrongPasswordException;
import model.Client;
import repository.ClientRepository;
import threads.AddClientThread;

import java.util.Base64;

public class ClientService {
    private static final ClientService instance = new ClientService();
    ClientRepository clientRepository = ClientRepository.getInstance();
    AuditService auditService = new AuditService();

    private ClientService() {}

    public static ClientService getInstance() {
        return instance;
    }

    public void addClient(Client client) {
        clientRepository.addClient(client);
        auditService.timeStamp("A client was added to database.", Thread.currentThread().getName());
    }

    public Client getClientByEmail(String email) {
        auditService.timeStamp("A client was selected from database.", Thread.currentThread().getName());
        return clientRepository.getClientByEmail(email);
    }

    public void updatePassword(String email, String oldPassword, String newPassword) throws WrongPasswordException {
        byte[] decodedBytes = Base64.getDecoder().decode(getClientByEmail(email).getPassword());

        String decryptedPassword = new String(decodedBytes);
        if (!decryptedPassword.equals(oldPassword))
            throw new WrongPasswordException("The password for this user is wrong!");

        String encryptedPassword = Base64.getEncoder().withoutPadding().encodeToString(newPassword.getBytes());
        clientRepository.updatePassword(email, encryptedPassword);
        auditService.timeStamp("The password was updated", Thread.currentThread().getName());
    }

    public void removeClient (String email) throws ClientNotFoundException {
        Client client = clientRepository.getClientByEmail(email);
        if(client == null)
            throw new ClientNotFoundException("This client id does not exist!");
        else{
            clientRepository.removeClientByEmail(email);
            auditService.timeStamp("A client was deleted.", Thread.currentThread().getName());
        }
    }

    public void register(Client client) throws ExistingClientException {
        String password = client.getPassword();
        String encryptedPassword = Base64.getEncoder().withoutPadding().encodeToString(password.getBytes());

        client.setPassword(encryptedPassword);

        Client existingClient = getClientByEmail(client.getEmail());

        if(existingClient != null)
            throw new ExistingClientException("Client already registered!");

        AddClientThread addClientThread = new AddClientThread(client);
        addClientThread.start();
        try {
            addClientThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        auditService.timeStamp("A client was registered.", Thread.currentThread().getName());
    }

    public void login (String email, String password) throws ClientNotFoundException, WrongPasswordException {
        Client existingClient = getClientByEmail(email);

        if(existingClient == null)
            throw new ClientNotFoundException("This user is not registered!");

        byte[] decodedBytes = Base64.getDecoder().decode(existingClient.getPassword());

        String decryptedPassword = new String(decodedBytes);
        if (!decryptedPassword.equals(password))
            throw new WrongPasswordException("The password for this user is wrong!");

        auditService.timeStamp("A client was logged.", Thread.currentThread().getName());

    }

}
