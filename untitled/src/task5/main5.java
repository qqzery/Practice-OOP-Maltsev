package task5;

import java.io.*;
import java.util.*;

/**
 * Інтерфейс для команд.
 */
interface Command {
    void execute();
    void undo();
}

/**
 * Клас для підрахунку чергувань та роботи з колекцією.
 */
class BinaryAlternationCalculator implements Serializable {
    private List<String> results = new ArrayList<>();

    /**
     * Обчислює кількість чергувань бітів у двійковому представленні числа.
     * @param number Число для обчислення.
     * @return Кількість чергувань.
     */
    public int countAlternations(int number) {
        String binaryString = Integer.toBinaryString(number);
        int count = 0;
        for (int i = 0; i < binaryString.length() - 1; i++) {
            if (binaryString.charAt(i) != binaryString.charAt(i + 1)) {
                count++;
            }
        }
        String result = "Число: " + number + " -> " + binaryString + " | Чергувань: " + count;
        results.add(result);
        return count;
    }

    public List<String> getResults() {
        return results;
    }

    public void removeLastResult() {
        if (!results.isEmpty()) {
            results.remove(results.size() - 1);
        }
    }
}

/**
 * Клас для виконання команд підрахунку чергувань.
 */
class CountAlternationCommand implements Command {
    private BinaryAlternationCalculator calculator;
    private int number;
    private String result;

    public CountAlternationCommand(BinaryAlternationCalculator calculator, int number) {
        this.calculator = calculator;
        this.number = number;
    }

    @Override
    public void execute() {
        int count = calculator.countAlternations(number);
        result = "Число: " + number + " -> " + Integer.toBinaryString(number) + " | Чергувань: " + count;
    }

    @Override
    public void undo() {
        calculator.removeLastResult();
    }

    public String getResult() {
        return result;
    }
}

/**
 * Клас для скасування операцій.
 */
class UndoManager {
    private Stack<Command> history = new Stack<>();

    public void executeCommand(Command command) {
        command.execute();
        history.push(command);
    }

    public void undo() {
        if (!history.isEmpty()) {
            Command command = history.pop();
            command.undo();
            System.out.println("Операцію скасовано.");
        } else {
            System.out.println("Немає операцій для скасування.");
        }
    }
}

/**
 * Клас макрокоманди для об'єднання кількох команд.
 */
class MacroCommand implements Command {
    private List<Command> commands = new ArrayList<>();

    public void addCommand(Command command) {
        commands.add(command);
    }

    @Override
    public void execute() {
        for (Command command : commands) {
            command.execute();
        }
    }

    @Override
    public void undo() {
        for (Command command : commands) {
            command.undo();
        }
    }
}

/**
 * Шаблон Singleton для управління обчисленнями.
 */
class CalculatorSingleton {
    private static CalculatorSingleton instance;
    private BinaryAlternationCalculator calculator;

    private CalculatorSingleton() {
        calculator = new BinaryAlternationCalculator();
    }

    public static CalculatorSingleton getInstance() {
        if (instance == null) {
            instance = new CalculatorSingleton();
        }
        return instance;
    }

    public BinaryAlternationCalculator getCalculator() {
        return calculator;
    }
}

/**
 * Головний клас для демонстрації роботи програми.
 */
public class main5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UndoManager undoManager = new UndoManager();
        CalculatorSingleton calculatorSingleton = CalculatorSingleton.getInstance();
        BinaryAlternationCalculator calculator = calculatorSingleton.getCalculator();

        // Діалог з користувачем
        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Ввести число та підрахувати чергування");
            System.out.println("2. Скасувати останню операцію (undo)");
            System.out.println("3. Вивести результати");
            System.out.println("4. Вийти");
            System.out.print("Виберіть опцію: ");
            int option = scanner.nextInt();

            if (option == 1) {
                System.out.print("Введіть число: ");
                int number = scanner.nextInt();
                Command command = new CountAlternationCommand(calculator, number);
                undoManager.executeCommand(command);
                System.out.println(((CountAlternationCommand) command).getResult());
            } else if (option == 2) {
                undoManager.undo();
            } else if (option == 3) {
                System.out.println("Результати:");
                for (String result : calculator.getResults()) {
                    System.out.println(result);
                }
            } else if (option == 4) {
                break;
            } else {
                System.out.println("Невірний вибір.");
            }
        }

        scanner.close();
    }
}
