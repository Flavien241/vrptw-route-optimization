package solver;

import model.Client;
import model.Solution;

import java.util.List;

public class SolutionComparisonViewer {

    public static void showComparison(List<Solution> solutions, List<String> titles, Client depot) {
        SolutionGraphStreamViewer.compareSolutions(solutions, titles, depot);
    }
}