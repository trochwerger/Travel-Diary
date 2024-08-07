package com.traveldiary.models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JournalEntryManager {

    private static final String ENTRIES_FILE = "entries.txt";
    private static JournalEntryManager instance;
    private final List<JournalEntry> entries = new ArrayList<>();

    private JournalEntryManager() {
        loadEntries();
    }

    public static JournalEntryManager getInstance() {
        if (instance == null) {
            instance = new JournalEntryManager();
        }
        return instance;
    }

    public boolean saveEntry(JournalEntry entry) {
        try {
            entries.removeIf(e -> e.getTitle().equals(entry.getTitle()));
            entries.add(entry);
            writeEntries();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEntry(String title) {
        try {
            System.out.println("Current Entries:");
            entries.forEach(e -> System.out.println(e.getTitle()));

            boolean removed = entries.removeIf(e -> e.getTitle().equals(title));

            if (removed) {
                System.out.println("Entry with title \"" + title + "\" removed.");
                writeEntries();
            } else {
                System.out.println("No entry with title \"" + title + "\" found.");
            }

            return removed;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<JournalEntry> getJournalEntries(String user) {
        return entries.stream()
                .filter(entry -> entry.getUser() != null && entry.getUser().equals(user))
                .collect(Collectors.toList());
    }

    private void loadEntries() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ENTRIES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                JournalEntry entry = JournalEntry.fromString(line);
                if (entry != null) {
                    entries.add(entry);
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeEntries() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ENTRIES_FILE))) {
            for (JournalEntry entry : entries) {
                writer.write(entry.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
