package tests;

import io.DataLoader;
import model.Client;
import model.Solution;
import solver.SolutionBuilder;

import java.util.List;

public class MainQuestion2 {

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

            System.out.println("-----------------------------");
            System.out.println("Instance : " + file);

            List<Client> clients = DataLoader.loadClients(folder + file);

            System.out.println("Clients chargés : " + clients.size());

            if (clients.isEmpty()) {
                System.out.println("Erreur de chargement !");
                continue;
            }

            Client depot = clients.get(0);
            clients.remove(0);

            int vehicleCapacity = 200;

            Solution solution = SolutionBuilder.buildInitialSolution(clients, depot, vehicleCapacity);

            System.out.println("Nombre de véhicules utilisés : " + solution.routes.size());
        }
    }
}