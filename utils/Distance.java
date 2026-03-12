package utils;
import model.Client;

public class Distance {

    public static double euclidean(Client a, Client b) {

        double dx = a.x - b.x;
        double dy = a.y - b.y;

        return Math.sqrt(dx * dx + dy * dy);
    }

}