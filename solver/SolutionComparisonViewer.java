package solver;

import model.Client;
import model.Route;
import model.Solution;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class SolutionComparisonViewer {

    public static void showComparison(List<Solution> solutions, List<String> titles, Client depot) {
        if (solutions == null || solutions.isEmpty()) {
            throw new IllegalArgumentException("La liste des solutions est vide.");
        }
        if (titles == null || titles.size() != solutions.size()) {
            throw new IllegalArgumentException("La liste des titres doit avoir la même taille que solutions.");
        }
        if (depot == null) {
            throw new IllegalArgumentException("Le depot ne peut pas être null.");
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Comparaison des solutions VRPTW");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(1300, 800);
            frame.setLocationRelativeTo(null);

            JPanel grid = new JPanel();
            int n = solutions.size();
            int cols = (int) Math.ceil(Math.sqrt(n));
            int rows = (int) Math.ceil((double) n / cols);
            grid.setLayout(new GridLayout(rows, cols, 8, 8));
            grid.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

            Bounds globalBounds = computeGlobalBounds(solutions, depot);

            for (int i = 0; i < n; i++) {
                grid.add(new SolutionPanel(solutions.get(i), titles.get(i), depot, globalBounds));
            }

            frame.setContentPane(new JScrollPane(grid));
            frame.setVisible(true);
        });
    }

    private static Bounds computeGlobalBounds(List<Solution> solutions, Client depot) {
        double minX = depot.x;
        double maxX = depot.x;
        double minY = depot.y;
        double maxY = depot.y;

        for (Solution solution : solutions) {
            for (Route route : solution.routes) {
                for (Client c : route.clients) {
                    minX = Math.min(minX, c.x);
                    maxX = Math.max(maxX, c.x);
                    minY = Math.min(minY, c.y);
                    maxY = Math.max(maxY, c.y);
                }
            }
        }

        double dx = Math.max(1.0, maxX - minX);
        double dy = Math.max(1.0, maxY - minY);

        return new Bounds(minX - dx * 0.05, maxX + dx * 0.05, minY - dy * 0.05, maxY + dy * 0.05);
    }

    private static class Bounds {
        final double minX;
        final double maxX;
        final double minY;
        final double maxY;

        Bounds(double minX, double maxX, double minY, double maxY) {
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
        }
    }

    private static class SolutionPanel extends JPanel {

        private final Solution solution;
        private final String title;
        private final Client depot;
        private final Bounds bounds;

        private static final Color[] ROUTE_COLORS = {
                new Color(231, 76, 60),
                new Color(52, 152, 219),
                new Color(46, 204, 113),
                new Color(155, 89, 182),
                new Color(241, 196, 15),
                new Color(230, 126, 34),
                new Color(26, 188, 156),
                new Color(149, 165, 166)
        };

        SolutionPanel(Solution solution, String title, Client depot, Bounds bounds) {
            this.solution = solution;
            this.title = title;
            this.depot = depot;
            this.bounds = bounds;
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(new Color(225, 225, 225)));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int topMargin = 30;
            int margin = 20;

            g2.setColor(new Color(40, 40, 40));
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 14f));
            g2.drawString(title + " | routes=" + solution.routes.size(), 10, 20);

            g2.setColor(new Color(240, 240, 240));
            g2.fillRect(margin, topMargin, w - 2 * margin, h - topMargin - margin);

            List<Point2D.Double> depotPoint = new ArrayList<>();
            depotPoint.add(new Point2D.Double(depot.x, depot.y));
            Point depotScreen = map(depotPoint.get(0), w, h, margin, topMargin, bounds);

            int colorIndex = 0;
            for (Route route : solution.routes) {
                Color c = ROUTE_COLORS[colorIndex % ROUTE_COLORS.length];
                colorIndex++;
                g2.setColor(c);
                g2.setStroke(new BasicStroke(2f));

                Point prev = depotScreen;
                for (Client client : route.clients) {
                    Point current = map(new Point2D.Double(client.x, client.y), w, h, margin, topMargin, bounds);
                    g2.draw(new Line2D.Double(prev.x, prev.y, current.x, current.y));
                    g2.fillOval(current.x - 3, current.y - 3, 6, 6);
                    prev = current;
                }
                g2.draw(new Line2D.Double(prev.x, prev.y, depotScreen.x, depotScreen.y));
            }

            g2.setColor(Color.BLACK);
            g2.fillOval(depotScreen.x - 6, depotScreen.y - 6, 12, 12);
            g2.drawString("Depot", depotScreen.x + 8, depotScreen.y - 8);

            g2.dispose();
        }

        private Point map(Point2D.Double p, int w, int h, int margin, int topMargin, Bounds b) {
            double drawW = Math.max(1, w - 2.0 * margin);
            double drawH = Math.max(1, h - topMargin - margin);

            double nx = (p.x - b.minX) / Math.max(1e-9, (b.maxX - b.minX));
            double ny = (p.y - b.minY) / Math.max(1e-9, (b.maxY - b.minY));

            int px = (int) (margin + nx * drawW);
            int py = (int) (topMargin + (1.0 - ny) * drawH);
            return new Point(px, py);
        }
    }
}