package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class TaskManager {
    static String[][] tasks = new String[0][];
    private static final String FILE_NAME_TASKS = "src/main/java/pl/coderslab/tasks.csv";
    private static final String EXIT_TASKS = "exit";
    private static final String LIST_TASKS = "list";
    private static final String ADD_TASK = "add";
    private static final String REMOVE_TASK = "remove";

    public static void main(String[] args) {
        taskShowOptions();
        loadTasks();
        taskCheckOptions();
    }

    static void taskCheckOptions() {
        try(Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String line = scanner.nextLine();
                switch (line.toLowerCase()) {
                    case EXIT_TASKS:
                        exitTask();
                        return;
                    case LIST_TASKS:
                        taskReadTasks();
                        break;
                    case ADD_TASK:
                        addTask();
                        break;
                    case REMOVE_TASK:
                        removeTask();
                        break;
                    default:
                        System.out.println("(Error) Please select a correct option!");
                        taskShowOptions();
                        break;
                }
            }
        } catch (NoSuchElementException exception) {
            System.out.println("Blad");
        }
    }

    static void exitTask() {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileWriter fileWriter = new FileWriter(FILE_NAME_TASKS, false)) {
            for (int i = 0; i < tasks.length; i++) {
                for (int j = 0; j < tasks[i].length; j++) {
                    stringBuilder.append(tasks[i][j]);
                    if (j < tasks[i].length - 1) {
                        stringBuilder.append(", ");
                    }
                }
                stringBuilder.append("\n");
            }
            fileWriter.append(stringBuilder);
        } catch (IOException exception) {
            System.out.println("(Error) Unable to save file!");
        }
        System.out.println(ConsoleColors.RED + "Bye, bye.");
    }

    static void removeTask() {
        System.out.println("Please select number to remove.");
        Scanner scanner = new Scanner(System.in);
        try {
            int idTask;
            do {
                idTask = scanner.nextInt();
                if (idTask < 0 || idTask >= tasks.length) {
                    System.out.println("(Error) Wrong tasks number! Please select number to remove.");
                }
            } while (idTask < 0 || idTask >= tasks.length);

            System.out.printf("Task %s [ %d ] has been removed.", tasks[idTask][0], idTask);
            tasks = ArrayUtils.remove(tasks, idTask);
            taskShowOptions();
        } catch (InputMismatchException | ArrayIndexOutOfBoundsException exception) {
            System.out.println("(Error) Wrong tasks number!s");

        }
    }

    static void addTask() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please add task description");
        while(!scanner.hasNext("(?=.*[a-zA-Z\\s])[a-zA-Z ]{4,25}")) {
            scanner.nextLine();
            System.out.println("Invalid input! Task name should consist only of letters from [A-Z] and should contain between 4 and 25 characters.");
        }
        String name = scanner.nextLine();

        System.out.println("Please add task due date [dd-mm-yyyy]");
        while (!scanner.hasNext("\\b(?<day>0?[1-9]|[12]\\d|3[01])-(?<month>0?[1-9]|1[0-2])-(?<year>\\d{4})\\b")) {
            scanner.nextLine();
            System.out.println("Wrong date format! Use [dd-mm-yyyy]");
        }
        String data = scanner.nextLine();


        System.out.println("Is your task is important: true / false");
        while (!scanner.hasNext("\\b(true|false)\\b")) {
            scanner.nextLine();
            System.out.println("Invalid data! Use true or false!");
        }
        String important = scanner.next("\\b(true|false)\\b");


        System.out.printf("Task: %s is added!", name);

        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = name;
        tasks[tasks.length - 1][1] = data;
        tasks[tasks.length - 1][2] = important;

        taskShowOptions();

    }

    static void taskShowOptions() {
        String[] taskOptions = {"add", "remove", "list", "exit"};

        System.out.println();
        System.out.println(ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);
        for (String s : taskOptions) {
            System.out.println(s);
        }
    }

    static void loadTasks() {
        try (Scanner scan = new Scanner(new File(FILE_NAME_TASKS))) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                tasks = Arrays.copyOf(tasks, tasks.length + 1);
                tasks[tasks.length - 1] = line.split(",");
            }
        } catch (FileNotFoundException exc) {
            System.out.println("(Error) Unable to read file!");
        }
    }

    static void taskReadTasks() {
        if (tasks.length == 0) {
            System.out.println("(Error) No tasks in the list!");
        }
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + " : ");
            for (int x = 0; x < tasks[i].length; x++) {
                System.out.print(tasks[i][x] + " ");
            }
            System.out.println();
        }
    }
}

