import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class ListMaker {
    private static ArrayList<String> list = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static String currentFileName = null;
    private static boolean needsToBeSaved = false;

    public static void main(String[] args) {
        final String menu = "A - Add  D - Delete  V - View  O - Open  S - Save  C - Clear  Q - Quit";
        boolean done = false;
        String cmd = "";

        do {
            cmd = SafeInput.getRegExString(scanner, menu, "[AaDdVvQqOoSsCc]");
            cmd = cmd.toUpperCase();

            switch (cmd) {
                case "A":
                    addToList();
                    break;
                case "D":
                    deleteFromList();
                    break;
                case "V":
                    displayList();
                    break;
                case "O":
                    loadList();
                    break;
                case "S":
                    saveList();
                    break;
                case "C":
                    clearList();
                    break;
                case "Q":
                    if (needsToBeSaved && SafeInput.getYNConfirm(scanner, "You have unsaved changes. Save before quitting?")) {
                        saveList();
                    }
                    done = true;
                    break;
                default:
                    System.out.println("Invalid command. Please enter a valid command.");
            }
        } while (!done);
    }

    private static void addToList() {
        System.out.println("Enter the item to be added:");
        String item = scanner.nextLine();
        list.add(item);
        needsToBeSaved = true;
    }

    private static void deleteFromList() {
        displayList();
        int index = SafeInput.getRangedInt(scanner, "Enter the number of the item to delete", 1, list.size());
        list.remove(index - 1);
        needsToBeSaved = true;
    }

    private static void displayList() {
        System.out.println("+++++++++++++++++++++++++++++++++++++");
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                System.out.printf("%3d%35s\n", i + 1, list.get(i));
            }
        } else {
            System.out.println("+++     List is empty     +++");
        }
        System.out.println("+++++++++++++++++++++++++++++++++++++");
    }

    private static void loadList() {
        if (needsToBeSaved && SafeInput.getYNConfirm(scanner, "You have unsaved changes. Save before loading?")) {
            saveList();
        }
        System.out.println("Enter filename to load from (without .txt extension):");
        String fileName = scanner.nextLine();
        Path path = Path.of(fileName + ".txt");

        if (Files.exists(path)) {
            try {
                list = new ArrayList<>(Files.readAllLines(path));
                needsToBeSaved = false;
                currentFileName = fileName;
            } catch (IOException ex) {
                System.out.println("Error reading the file.");
            }
        } else {
            System.out.println("File does not exist.");
        }
    }

    private static void saveList() {
        if (currentFileName == null) {
            System.out.println("Enter filename to save to (without .txt extension):");
            currentFileName = scanner.nextLine();
        }

        Path path = Path.of(currentFileName + ".txt");
        try {
            Files.write(path, list);
            needsToBeSaved = false;
        } catch (IOException ex) {
            System.out.println("Error writing to the file.");
        }
    }

    private static void clearList() {
        if (SafeInput.getYNConfirm(scanner, "Are you sure you want to clear the list?")) {
            list.clear();
            needsToBeSaved = true;
        }
    }
}