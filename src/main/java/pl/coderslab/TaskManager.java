package pl.coderslab;

import java.util.Arrays;

public class TaskManager {
    public static void main(String[] args) {
        taskShowOptions();
    }

    static void taskShowOptions() {
        String[] taskOptions = {"add", "remove", "list", "exit"};

        System.out.println(ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);
        for(String s : taskOptions) {
            System.out.println(s);
        }

    }
}
