package com.traveldiary.models;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static final String USERS_FILE = "users.txt";
    private static UserManager instance;
    private final Map<String, String> users = new HashMap<>();

    private UserManager() {
        loadUsers();
    }

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void writeUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (Map.Entry<String, String> entry : users.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, password);
        writeUsers();
        return true;
    }

    public boolean authenticateUser(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}
