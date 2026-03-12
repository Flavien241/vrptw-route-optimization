package model;
import java.util.ArrayList;

public class Route {

    public ArrayList<Client> clients;
    public int load;

    public Route() {
        clients = new ArrayList<>();
        load = 0;
    }

}