package task1;

import java.util.Random;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        String[] quotes = {
                "Не бійся робити помилки — бійся нічого не робити.",
                "Успіх приходить до тих, хто не здається.",
                "Сьогодні ти можеш більше, ніж учора.",
                "Кожен день — це новий шанс.",
                "Мрій масштабно, дій впевнено!"
        };

        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Натисни Enter, щоб отримати мотиваційну цитату...");
        scanner.nextLine();

        int index = random.nextInt(quotes.length);
        System.out.println("Твоя цитата дня:");
        System.out.println("\"" + quotes[index] + "\"");

        System.out.println("\nГарного дня!");
    }
}

