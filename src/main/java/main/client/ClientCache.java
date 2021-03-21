package main.client;

import main.common.entities.User;

public class ClientCache {

    private static final ClientCache INSTANCE = new ClientCache();

    private boolean isServerConnected = false;

    private User loggedUser;

    private ClientCache() {

    }

    public boolean isServerConnected() { return isServerConnected; }

    public void setServerConnected(boolean serverConnected) {
        isServerConnected = serverConnected;
    }

    public static ClientCache getInstance() {
        return INSTANCE;
    }

    public void storeUser(User user) {
        this.loggedUser = user;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void clearCache() {
        loggedUser = null;
    }

}
