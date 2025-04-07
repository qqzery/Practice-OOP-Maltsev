package task3;

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
 * Основний клас для підрахунку чергувань та роботи з колекцією.
 */
class BinaryAlternationCalculator implements Serializable {
    private List<String> results = new ArrayList<>();

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
public class main3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BinaryAlternationCalculator calc = new BinaryAlternationCalculator();

        System.out.print("Введіть кількість чисел: ");
        int n = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.print("Введіть число: ");
            int number = scanner.nextInt();
            calc.countAlternations(number);
        }

        // Серіалізація
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("results.ser"))) {
            out.writeObject(calc);
            System.out.println("Результати збережено у файл.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Десеріалізація та вивід результатів
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("results.ser"))) {
            BinaryAlternationCalculator loadedCalc = (BinaryAlternationCalculator) in.readObject();

            DisplayFactory factory = new TextDisplayFactory();
            ResultDisplay display = factory.createDisplay();

            display.displayResults(loadedCalc.getResults());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
