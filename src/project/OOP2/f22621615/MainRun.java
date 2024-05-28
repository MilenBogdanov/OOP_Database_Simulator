package project.OOP2.f22621615;

import project.OOP2.f22621615.basic_filefunctions.CommandCenter;
import project.OOP2.f22621615.database.Database;
import project.OOP2.f22621615.enums.CommandEnum;

import java.util.Scanner;

public class MainRun {
    private final Database database;
    private final CommandCenter commandCenter;
    private final Scanner scanner;

    /**
     * Constructor to initialize the Application.
     */
    public MainRun() {
        System.setProperty("user.dir", "D:\\oop1proekt\\proekt122621615\\");
        this.database = new Database();
        this.commandCenter = new CommandCenter(database);
        this.scanner = new Scanner(System.in);
    }

    /**
     * Method to start and run the application.
     */
    public void run() {
        System.out.println("Welcome to OOP-1 project!");
        System.out.println("Author - Milen Bogdanov, fn22621615");
        System.out.println();
        System.out.println("Current working directory: " + System.getProperty("user.dir"));

        while (true) {
            System.out.print("Enter a command: ");
            String input = scanner.nextLine();

            String[] tokens = input.split(" ", 2);
            CommandEnum commandName;
            try {
                commandName = CommandEnum.valueOf(tokens[0].toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid command. Type 'help' to see the list of commands.");
                continue;
            }
            String parameter = tokens.length > 1 ? tokens[1] : "";

            commandCenter.executeCommand(commandName, parameter);

            if (commandName == CommandEnum.EXIT) {
                break;
            }
        }
        scanner.close();
    }
}