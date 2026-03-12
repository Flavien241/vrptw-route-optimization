package solver;

import model.Client;
import model.Route;
import model.Solution;

import java.util.*;

public class RandomSolutionGenerator {

    public static Solution generateRandomSolution(List<Client> clients, Client depot, int capacity) {

        // copier la liste pour ne pas modifier l'original
        List<Client> shuffledClients = new ArrayList<>(clients);

        // mélange aléatoire
        Collections.shuffle(shuffledClients);

        Solution solution = new Solution();

        Route route = new Route();
        int load = 0;

        for (Client c : shuffledClients) {

            // si capacité dépassée → nouvelle route
            if (load + c.demand > capacity) {

                solution.routes.add(route);

                route = new Route();
                load = 0;
            }

            route.clients.add(c);
            load += c.demand;
        }

        // ajouter la dernière route
        solution.routes.add(route);

        return solution;
    }
}