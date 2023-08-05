import java.util.Scanner;
import java.util.regex.Pattern;

public class SafeInput {
    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        String retString = ""; // Set this to zero length. Loop runs until it isn’t
        do {
            System.out.print("\n" + prompt + ": "); // show prompt add space
            retString = pipe.nextLine();
        } while (retString.length() == 0);
        return retString;
    }

    public static int getInt(Scanner pipe, String prompt) {
        int value = 0;
        do {
            System.out.print("\n" + prompt + ": ");
            while (!pipe.hasNextInt()) {
                System.out.println("That's not a number! Try again.");
                pipe.next();
            }
            value = pipe.nextInt();
            pipe.nextLine(); // Clear the newline character
        } while (value == 0);
        return value;
    }

    public static double getDouble(Scanner pipe, String prompt) {
        double value = 0;
        do {
            System.out.print("\n" + prompt + ": ");
            while (!pipe.hasNextDouble()) {
                System.out.println("That's not a valid number! Try again.");
                pipe.next();
            }
            value = pipe.nextDouble();
            pipe.nextLine(); // Clear the newline character
        } while (value == 0);
        return value;
    }

    public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
        int value;
        do {
            System.out.printf("\n%s [%d-%d]: ", prompt, low, high);
            while (!pipe.hasNextInt()) {
                System.out.println("That's not a number! Try again.");
                pipe.next();
            }
            value = pipe.nextInt();
            pipe.nextLine(); // Clear the newline character
        } while (value < low || value > high);
        return value;
    }

    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high) {
        double value;
        do {
            System.out.printf("\n%s [%.2f-%.2f]: ", prompt, low, high);
            while (!pipe.hasNextDouble()) {
                System.out.println("That's not a valid number! Try again.");
                pipe.next();
            }
            value = pipe.nextDouble();
            pipe.nextLine(); // Clear the newline character
        } while (value < low || value > high);
        return value;
    }

    public static boolean getYNConfirm(Scanner pipe, String prompt) {
        String response;
        do {
            System.out.print("\n" + prompt + " [Y/N]: ");
            response = pipe.nextLine().trim().toUpperCase();
        } while (!response.equals("Y") && !response.equals("N"));
        return response.equals("Y");
    }

    public static String getRegExString(Scanner pipe, String prompt, String regEx) {
        String input = "";
        while (true) {
            System.out.println(prompt);
            if (pipe.hasNextLine()) {
                input = pipe.nextLine();
                if (Pattern.matches(regEx, input)) {
                    break;
                } else {
                    System.out.println("The entered string does not match the expected pattern. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please try again.");
                pipe.nextLine(); // discard invalid input
            }
        }
        return input;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String firstName = "";
        String lastName = "";
        firstName = SafeInput.getNonZeroLenString(in, "Enter your first name");
        lastName = SafeInput.getNonZeroLenString(in, "Enter your last name");
        System.out.println("\nYour full name is: " + firstName + " " + lastName);

        prettyHeader("Message Centered Here");
    }

    public static void prettyHeader(String msg) {
        final int LINE_WIDTH = 60;
        int msgLength = msg.length();
        int totalStars = 6; // 3 stars on each side of the message
        int totalSpacing = LINE_WIDTH - (msgLength + totalStars);
        int leftSpacing = totalSpacing / 2;
        int rightSpacing = totalSpacing - leftSpacing;

        printLine(LINE_WIDTH);
        printMessageLine(msg, leftSpacing, rightSpacing);
        printLine(LINE_WIDTH);
    }

    public static void printLine(int length) {
        for (int i = 0; i < length; i++) {
            System.out.print("*");
        }
        System.out.println();
    }

    public static void printMessageLine(String msg, int leftSpacing, int rightSpacing) {
        printSpaces(leftSpacing);
        System.out.print("*** " + msg + " ***");
        printSpaces(rightSpacing);
        System.out.println();
    }

    public static void printSpaces(int length) {
        for (int i = 0; i < length; i++) {
            System.out.print(" ");
        }
    }
}
