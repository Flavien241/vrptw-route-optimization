package tests;

import io.DataLoader;
import model.Client;
import model.Route;
import model.Solution;
import solver.RandomSolutionGenerator;

import java.util.List;

public class MainQuestion3 {

    public static void main(String[] args) throws Exception {

        String folder = "data/";

        String[] instances = {
                "data101.vrp",
                
        };

        for (String file : instances) {

            System.out.println("------------------------------");
            System.out.println("Instance : " + file);

            List<Client> clients = DataLoader.loadClients(folder + file);

            System.out.println("Clients chargés : " + clients.size());

            if (clients.isEmpty()) {
                System.out.println("Erreur chargement !");
                continue;
            }

            Client depot = clients.get(0);
            clients.remove(0);

            int capacity = 200;

            Solution solution = RandomSolutionGenerator.generateRandomSolution(clients, depot, capacity);
            System.out.println("Nombre de routes : " + solution.routes.size());

int routeNumber = 1;

for (Route r : solution.routes) {

    System.out.print("Route " + routeNumber + " : depot -> ");

    for (Client c : r.clients) {
        System.out.print("C" + c.id + " -> ");
    }

    System.out.println("depot");

    routeNumber++;
}

            System.out.println("Nombre de routes : " + solution.routes.size());
        }
    }
}