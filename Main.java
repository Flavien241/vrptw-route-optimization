import java.util.*;
import solver.SolutionBuilder;
import io.DataLoader;
import model.Client;
import model.Solution;

public class Main {

    public static void main(String[] args) throws Exception {

        String folder = "data/";

        String[] instances = {
            "data101.vrp",
            "data102.vrp",
            "data111.vrp",
            "data112.vrp",
            "data201.vrp",
            "data202.vrp",
            "data1101.vrp",
            "data1102.vrp",
            "data1201.vrp",
            "data1202.vrp"
        };

        for (String file : instances) {

            System.out.println("-------------");
            System.out.println("Instance : " + file);

            List<Client> clients = DataLoader.loadClients(folder + file);

            Client depot = clients.get(0);
            clients.remove(0);

            int capacity = 200;

            Solution solution = SolutionBuilder.buildInitialSolution(clients, depot, capacity);

            System.out.println("Vehicles used : " + solution.routes.size());
        }
    }
}