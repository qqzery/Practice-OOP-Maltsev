package task2;

import java.util.Scanner;

/**
 * Клас Main4 підраховує кількість чергувань 0 та 1
 * у двійковому поданні заданого десяткового числа.
 */
public class main4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть десяткове число: ");
        int number = scanner.nextInt();

        // Перетворення у двійковий рядок
        String binaryString = Integer.toBinaryString(number);

        System.out.println("Двійкове подання числа: " + binaryString);

        int count = 0;

        // Підрахунок чергувань
        for (int i = 0; i < binaryString.length() - 1; i++) {
            if (binaryString.charAt(i) != binaryString.charAt(i + 1)) {
                count++;
            }
        }

        System.out.println("Кількість чергувань 0 та 1: " + count);
    }
}
