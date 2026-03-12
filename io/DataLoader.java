package io;
import java.util.*;

import model.Client;

import java.io.*;

public class DataLoader {

    public static List<Client> loadClients(String filePath) throws Exception {

        List<Client> clients = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(filePath));

        String line;

        while ((line = br.readLine()) != null) {

            if (line.trim().isEmpty())
                continue;

            String[] parts = line.split("\\s+");

            int id = Integer.parseInt(parts[0]);
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);

            clients.add(new Client(id, x, y, 0, 0, 0));
        }

        br.close();

        return clients;
    }
}