package tests;

import io.DataLoader;
import model.Client;
import model.Solution;
import solver.RandomSolutionGenerator;
import solver.SolutionBuilder;
import solver.SolutionComparisonViewer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainCompareSolutions {

    public static void main(String[] args) throws Exception {

        String file = "data/data101.vrp";
        int capacity = 200;

        List<Client> loaded = DataLoader.loadClients(file);
        if (loaded.isEmpty()) {
            System.out.println("Erreur chargement !");
            return;
        }

        Client depot = loaded.get(0);
        List<Client> clients = new ArrayList<>(loaded.subList(1, loaded.size()));

        Solution greedy = SolutionBuilder.buildInitialSolution(new ArrayList<>(clients), depot, capacity);
        Solution random1 = RandomSolutionGenerator.generateRandomSolution(new ArrayList<>(clients), depot, capacity);
        Solution random2 = RandomSolutionGenerator.generateRandomSolution(new ArrayList<>(clients), depot, capacity);

        List<Solution> solutions = Arrays.asList(greedy, random1, random2);
        List<String> titles = Arrays.asList(
                "Greedy (nearest)",
                "Random #1",
                "Random #2"
        );

        SolutionComparisonViewer.showComparison(solutions, titles, depot);
    }
}