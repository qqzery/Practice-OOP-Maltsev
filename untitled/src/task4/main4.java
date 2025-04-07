package task4;

import java.io.*;
import java.util.*;

/**
 * Інтерфейс для відображення результатів.
 */
interface ResultDisplay {
    void displayResults(List<String> results);
}

/**
 * Реалізація виведення результатів у текстовому вигляді.
 */
class TextResultDisplay implements ResultDisplay {
    @Override
    public void displayResults(List<String> results) {
        System.out.println("Результати обчислень:");
        for (String result : results) {
            System.out.println(result);
        }
    }
}

/**
 * Реалізація виведення результатів у вигляді текстової таблиці.
 */
class TableResultDisplay implements ResultDisplay {
    private String tableHeader;

    public TableResultDisplay(String tableHeader) {
        this.tableHeader = tableHeader;
    }

    @Override
    public void displayResults(List<String> results) {
        System.out.println(tableHeader);
        System.out.println("---------------------------------------------------");
        for (String result : results) {
            System.out.println(result);
        }
    }
}

/**
 * Фабрика для створення об'єктів ResultDisplay.
 */
interface DisplayFactory {
    ResultDisplay createDisplay();
}

/**
 * Конкретна фабрика для текстового виводу.
 */
class TextDisplayFactory implements DisplayFactory {
    @Override
    public ResultDisplay createDisplay() {
        return new TextResultDisplay();
    }
}

/**
 * Конкретна фабрика для таблиці.
 */
class TableDisplayFactory implements DisplayFactory {
    private String tableHeader;

    public TableDisplayFactory(String tableHeader) {
        this.tableHeader = tableHeader;
    }

    @Override
    public ResultDisplay createDisplay() {
        return new TableResultDisplay(tableHeader);
    }
}

/**
 * Основний клас для підрахунку чергувань та роботи з колекцією.
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
}

/**
 * Головний клас з демонстрацією роботи.
 */
public class main4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BinaryAlternationCalculator calc = new BinaryAlternationCalculator();

        // Діалог з користувачем
        System.out.print("Введіть кількість чисел: ");
        int n = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.print("Введіть число: ");
            int number = scanner.nextInt();
            calc.countAlternations(number);
        }

        // Вибір формату виводу
        System.out.print("Виберіть формат виведення (1 - текстовий, 2 - таблиця): ");
        int displayOption = scanner.nextInt();

        DisplayFactory factory;
        if (displayOption == 1) {
            factory = new TextDisplayFactory();
        } else {
            scanner.nextLine();  // consume the leftover newline
            System.out.print("Введіть заголовок таблиці: ");
            String tableHeader = scanner.nextLine();
            factory = new TableDisplayFactory(tableHeader);
        }

        ResultDisplay display = factory.createDisplay();
        display.displayResults(calc.getResults());

        // Серіалізація
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("results.ser"))) {
            out.writeObject(calc);
            System.out.println("Результати збережено у файл.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Десеріалізація та виведення результатів
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("results.ser"))) {
            BinaryAlternationCalculator loadedCalc = (BinaryAlternationCalculator) in.readObject();

            display.displayResults(loadedCalc.getResults());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
