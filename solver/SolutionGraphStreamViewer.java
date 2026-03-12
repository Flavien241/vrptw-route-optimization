package solver;

import model.Client;
import model.Route;
import model.Solution;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.view.Viewer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolutionGraphStreamViewer {

    public static void compareSolutions(List<Solution> solutions, List<String> titles, Client depot) {
        compareSolutions(solutions, titles, depot, true);
    }

    public static void compareSolutions(List<Solution> solutions, List<String> titles, Client depot, boolean fixedPositions) {
        if (solutions == null || solutions.isEmpty()) {
            throw new IllegalArgumentException("La liste des solutions est vide.");
        }
        if (titles == null || titles.size() != solutions.size()) {
            throw new IllegalArgumentException("La liste des titres doit avoir la même taille que solutions.");
        }
        if (depot == null) {
            throw new IllegalArgumentException("Le depot ne peut pas être null.");
        }

        System.setProperty("org.graphstream.ui", "swing");

        for (int i = 0; i < solutions.size(); i++) {
            Graph graph = buildGraph(solutions.get(i), titles.get(i), depot);
            Viewer viewer = graph.display(false);
            if (fixedPositions) {
                viewer.disableAutoLayout();
            } else {
                viewer.enableAutoLayout();
            }
        }
    }

    private static Graph buildGraph(Solution solution, String title, Client depot) {
        Graph graph = new MultiGraph(title);
        graph.setAttribute("ui.stylesheet", style());
        graph.setAttribute("ui.title", title + " | routes=" + solution.routes.size());

        String depotId = "depot";
        Node depotNode = graph.addNode(depotId);
        depotNode.setAttribute("xyz", depot.x, depot.y, 0);
        depotNode.setAttribute("ui.label", "Depot");
        depotNode.setAttribute("ui.class", "depot");

        Map<Integer, String> clientNodeIds = new HashMap<>();
        int nodeIdx = 1;

        for (Route route : solution.routes) {
            for (Client c : route.clients) {
                if (!clientNodeIds.containsKey(c.id)) {
                    String nodeId = "c" + nodeIdx++;
                    clientNodeIds.put(c.id, nodeId);

                    Node n = graph.addNode(nodeId);
                    n.setAttribute("xyz", c.x, c.y, 0);
                    n.setAttribute("ui.label", "C" + c.id);
                }
            }
        }

        int edgeIdx = 1;
        for (int ri = 0; ri < solution.routes.size(); ri++) {
            Route route = solution.routes.get(ri);
            String prev = depotId;

            for (Client c : route.clients) {
                String current = clientNodeIds.get(c.id);
                Edge e = graph.addEdge("e" + edgeIdx++, prev, current, true);
                e.setAttribute("ui.class", "r" + (ri % 6));
                prev = current;
            }

            Edge back = graph.addEdge("e" + edgeIdx++, prev, depotId, true);
            back.setAttribute("ui.class", "r" + (ri % 6));
        }

        return graph;
    }

    private static String style() {
        return ""
                + "graph { padding: 40px; }"
                + "node { size: 10px; fill-color: #2c3e50; text-size: 12; text-offset: 0px, -12px; }"
                + "node.depot { size: 14px; fill-color: #000000; text-size: 14; }"
                + "edge { size: 2px; arrow-size: 6px, 4px; }"
                + "edge.r0 { fill-color: #e74c3c; }"
                + "edge.r1 { fill-color: #3498db; }"
                + "edge.r2 { fill-color: #2ecc71; }"
                + "edge.r3 { fill-color: #f1c40f; }"
                + "edge.r4 { fill-color: #9b59b6; }"
                + "edge.r5 { fill-color: #e67e22; }";
    }
}