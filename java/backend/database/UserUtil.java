package database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class UserUtil {
    private static final String FILE_PATH = "backend/database/user.txt";
    private static final String SESSION_FILE_PATH = "backend/database/session.txt";

    public static void saveUserCredentials(String username, String password, String email) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write("Username: " + username + "\n");
            writer.write("Email: " + email + "\n");
            writer.write("Password: " + password + "\n");
            writer.write("Role: User" + "\n");
            writer.write("-----\n");
        } catch (IOException e) {
            System.out.println("An error occurred while saving user credentials.");
            e.printStackTrace();
        }
    }

    public static void saveSession(String username, String email) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SESSION_FILE_PATH, true))) {
            writer.write("Username: " + username + "\n");
            writer.write("Role: " + email + "\n");
            writer.write("-----\n");
        } catch (IOException e) {
            System.out.println("An error occurred while saving user credentials.");
            e.printStackTrace();
        }
    }

    public static Map<String, Map<String, String>> readUserCredentials() {
        Map<String, Map<String, String>> users = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            String username = null;
            Map<String, String> userDetails = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Username: ")) {
                    if (username != null && userDetails != null) {
                        users.put(username, userDetails);
                    }
                    username = line.substring(10).trim();
                    userDetails = new HashMap<>();
                } else if (line.startsWith("Password: ")) {
                    if (userDetails != null) {
                        userDetails.put("password", line.substring(10).trim());
                    }
                } else if (line.startsWith("Role: ")) {
                    if (userDetails != null) {
                        userDetails.put("role", line.substring(6).trim());
                    }
                }
            }

            if (username != null && userDetails != null) {
                users.put(username, userDetails);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading user credentials.");
            e.printStackTrace();
        }
        return users;
    }

    // public static boolean verifyCredentials(String username, String password) {
    // Map<String, String> users = readUserCredentials();
    // return users.containsKey(username) && users.get(username).equals(password);
    // }

    public static Map<String, String> readSessiondata() {
        Map<String, String> sessions = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SESSION_FILE_PATH))) {
            String line;
            String username = null;
            String role = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Username: ")) {
                    username = line.substring(10).trim();
                } else if (line.startsWith("Role: ")) {
                    role = line.substring(6).trim();
                    if (username != null && role != null) {
                        sessions.put(username, role);
                        username = null;
                        role = null;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading session data.");
            e.printStackTrace();
        }

        // // Debug: Print the parsed session data
        // System.out.println("Parsed session data:");
        // for (Map.Entry<String, String> entry : sessions.entrySet()) {
        // System.out.println("Username: " + entry.getKey() + ", Role: " +
        // entry.getValue());
        // }

        return sessions;
    }

    public static String getCurrentUsername() {
        Map<String, String> sessions = readSessiondata();
        System.out.println("Fetching current username from session data...");
        for (String username : sessions.keySet()) {
            // System.out.println("Found Username: " + username);
            if (username != null) {
                return username;
            }
        }
        System.out.println("No username found in session data.");
        return null;
    }

    public static void deleteSessionData() {
        try {
            // Create a file object representing the session data file
            File sessionFile = new File(SESSION_FILE_PATH);

            // Truncate the file to remove all its contents
            if (sessionFile.exists()) {
                sessionFile.delete();
                sessionFile.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while deleting session data.");
            e.printStackTrace();
        }
    }
}
