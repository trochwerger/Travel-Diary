package com.traveldiary.models;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class JournalEntryManager {

    private static JournalEntryManager instance;
    private Map<String, List<JournalEntry>> userJournalEntries;

    private JournalEntryManager() {
        userJournalEntries = new HashMap<>();
    }

    public static JournalEntryManager getInstance() {
        if (instance == null) {
            instance = new JournalEntryManager();
        }
        return instance;
    }

    public List<JournalEntry> getJournalEntries(String username) {
        return userJournalEntries.getOrDefault(username, new ArrayList<>());
    }

    public void addJournalEntry(String username, JournalEntry entry) {
        userJournalEntries.computeIfAbsent(username, k -> new ArrayList<>()).add(entry);
    }

    public void updateJournalEntry(String username, JournalEntry oldEntry, JournalEntry newEntry) {
        List<JournalEntry> entries = userJournalEntries.get(username);
        if (entries != null) {
            int index = entries.indexOf(oldEntry);
            if (index != -1) {
                entries.set(index, newEntry);
            }
        }
    }

    public void deleteJournalEntry(String username, JournalEntry entry) {
        List<JournalEntry> entries = userJournalEntries.get(username);
        if (entries != null) {
            entries.remove(entry);
        }
    }
}
