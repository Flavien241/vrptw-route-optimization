package model;
public class Client {

    public int id;
    public double x;
    public double y;
    public int demand;

    public int readyTime;
    public int dueTime;

    public Client(int id, double x, double y, int demand, int readyTime, int dueTime) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.demand = demand;
        this.readyTime = readyTime;
        this.dueTime = dueTime;
    }

}