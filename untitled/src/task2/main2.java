package task2;

import java.io.*;
import java.util.Scanner;

// Клас, що серіалізується
class UserSession implements Serializable {
    private String username;
    private transient String password; // transient - не буде збережено

    public UserSession(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void showInfo() {
        System.out.println("Ім'я користувача: " + username);
        System.out.println("Пароль: " + (password != null ? password : "(відсутній після серіалізації)"));
    }
}

public class main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввід даних користувача
        System.out.print("Введіть ім'я користувача: ");
        String username = scanner.nextLine();

        System.out.print("Введіть пароль: ");
        String password = scanner.nextLine();

        // Створення об'єкта
        UserSession session = new UserSession(username, password);
        System.out.println("\n[До серіалізації]");
        session.showInfo();

        // Серіалізація
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("session.ser"))) {
            out.writeObject(session);
            System.out.println("\nОб'єкт серіалізовано.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Десеріалізація
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("session.ser"))) {
            UserSession restored = (UserSession) in.readObject();
            System.out.println("\n[Після десеріалізації]");
            restored.showInfo();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

