package converter;

import java.util.*;
import java.lang.*;
public class UserInterface {

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private String result;
    private String fromNum;
    public String inputCommand;
    Scanner scanner = new Scanner(System.in);

    // Constructor
    public UserInterface() {
    }
    public void runMenu() {
        // Level 1: Ask for bases and store them or exit program
        while (true) {
            System.out.println("Enter two numbers in format: {source base} {target base} (To quit type /exit)");
            // store the base values if user specifies numbers
            if (scanner.hasNextInt()) {
                int fromBase = scanner.nextInt();
                int toBase = scanner.nextInt();
                // Level 2: Ask for number to convert or go back to menu 1
                while (true) {
                    System.out.println("Enter number in base " + fromBase + " to convert to base " + toBase + " (To go back type /back)");
                    inputCommand = scanner.next();
                    if (!"/back".equals(inputCommand)) {
                        fromNum = inputCommand;
                        // Calculate and display the result
                        this.setResult(NumConverter.convertNum(fromNum, fromBase, toBase));
                        System.out.println("Conversion result: " + this.getResult());
                    } else if ("/back".equals(inputCommand)) {
                        break; // Restart the first loop
                    }
                }

            } else if ("/exit".equals(scanner.next())) {
                System.exit(0);
            }

        }
    }

    public static void main(String[] args) {
        UserInterface userInterface = new UserInterface();
        userInterface.runMenu();

    }
}
