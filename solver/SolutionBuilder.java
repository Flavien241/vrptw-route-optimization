package solver;

import model.*;
import utils.Distance;

import java.util.*;

public class SolutionBuilder {

    public static Solution buildInitialSolution(List<Client> clients, Client depot, int vehicleCapacity) {

        List<Client> remainingClients = new ArrayList<>(clients);

        Solution solution = new Solution();

        while (!remainingClients.isEmpty()) {

            Route route = new Route();
            int currentLoad = 0;

            Client current = depot;

            while (true) {

                Client nearest = null;
                double bestDistance = Double.MAX_VALUE;

                for (Client c : remainingClients) {

                    double dist = Distance.euclidean(current, c);

                    if (dist < bestDistance && currentLoad + c.demand <= vehicleCapacity) {
                        bestDistance = dist;
                        nearest = c;
                    }
                }

                if (nearest == null)
                    break;

                route.clients.add(nearest);

                currentLoad += nearest.demand;

                current = nearest;

                remainingClients.remove(nearest);
            }

            solution.routes.add(route);
        }

        return solution;
    }
}