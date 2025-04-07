package task2;

import java.io.*;

/**
 * Клас MathProcessor виконує прості математичні обчислення.
 */
class MathProcessor implements Serializable {
    private double number1;
    private double number2;

    /**
     * Конструктор класу MathProcessor.
     *
     * @param number1 перше число
     * @param number2 друге число
     */
    public MathProcessor(double number1, double number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    /**
     * Повертає суму двох чисел.
     *
     * @return сума
     */
    public double add() {
        return number1 + number2;
    }

    /**
     * Повертає добуток двох чисел.
     *
     * @return добуток
     */
    public double multiply() {
        return number1 * number2;
    }
}

/**
 * Клас Main3 тестує роботу класу MathProcessor
 * та процес серіалізації/десеріалізації.
 */
public class main3 {
    public static void main(String[] args) {
        MathProcessor calc = new MathProcessor(10, 5);

        System.out.println("Перевірка обчислень:");
        System.out.println("Сума: " + calc.add());           // Очікуємо 15
        System.out.println("Множення: " + calc.multiply()); // Очікуємо 50

        // Серіалізація
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("math.ser"))) {
            out.writeObject(calc);
            System.out.println("Об'єкт серіалізовано успішно.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Десеріалізація
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("math.ser"))) {
            MathProcessor restoredCalc = (MathProcessor) in.readObject();
            System.out.println("Об'єкт десеріалізовано успішно.");

            System.out.println("Перевірка обчислень після десеріалізації:");
            System.out.println("Сума: " + restoredCalc.add());           // Очікуємо 15
            System.out.println("Множення: " + restoredCalc.multiply()); // Очікуємо 50

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
