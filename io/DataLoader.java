package io;

import model.Client;
import java.io.*;
import java.util.*;

public class DataLoader {

    public static List<Client> loadClients(String filePath) throws Exception {

        List<Client> clients = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(filePath));

        String line;
        boolean readingClients = false;

        while ((line = br.readLine()) != null) {

            line = line.trim();

            if (line.startsWith("DATA_CLIENTS")) {
                readingClients = true;
                continue;
            }

            if (line.startsWith("DATA_DEPOTS")) {
                continue;
            }

            if (line.startsWith("d")) {

                String[] parts = line.split("\\s+");

                int id = 0;
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                int ready = Integer.parseInt(parts[3]);
                int due = Integer.parseInt(parts[4]);

                clients.add(new Client(id, x, y, 0, ready, due));
            }

            if (readingClients && line.startsWith("c")) {

                String[] parts = line.split("\\s+");

                int id = Integer.parseInt(parts[0].substring(1));
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                int ready = Integer.parseInt(parts[3]);
                int due = Integer.parseInt(parts[4]);
                int demand = Integer.parseInt(parts[5]);

                clients.add(new Client(id, x, y, demand, ready, due));
            }
        }

        br.close();

        return clients;
    }
}