import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    private static BankAccount mainAccount = null;
    private static BankAccount secondaryAccount = null;
    private static BankAccount currentAccount = null;
    private static Scanner scanner;
    private static String ownerName = "";
    
    public static void main(String[] args) {
        scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        
        System.out.println("БАНКОВСКАЯ СИСТЕМА");
        System.out.print("\nВведите имя владельца: ");
        ownerName = scanner.nextLine().trim();
        
        if (ownerName.isEmpty()) {
            ownerName = "Пользователь";
        }
        
        mainAccount = new BankAccount(ownerName);
        currentAccount = mainAccount;
        
        System.out.println("\nОсновной аккаунт создан");
        System.out.println("   ID счета: " + mainAccount.getNumber());
        System.out.println("   Баланс: " + mainAccount.getBalance() + " руб.");
        
        secondaryAccount = new BankAccount("Второй счет");
        System.out.println("\nЗапасной аккаунт создан");
        System.out.println("   ID счета: " + secondaryAccount.getNumber());
        System.out.println("   Баланс: " + secondaryAccount.getBalance() + " руб.");
        System.out.println(" \n");
        
        while (true) {
            showMainMenu();
        }
    }
    
    private static void showMainMenu() {
        System.out.println("ВЫБЕРИТЕ ДЕЙСТВИЕ");
        System.out.println("  Текущий счет: " + currentAccount.getNumber());
        System.out.println("  Баланс: " + currentAccount.getBalance() + " руб.");
        System.out.println("  Статус: " + (currentAccount.isBlocked() ? "ЗАБЛОКИРОВАН" : "АКТИВЕН"));
        System.out.println("  1  - Пополнить счет");
        System.out.println("  2  - Снять деньги");
        System.out.println("  3  - Перевести деньги");
        System.out.println("  4  - Переключиться на другой счет");
        System.out.println("  5  - Показать ID всех счетов");
        System.out.println("  6  - Заблокировать/Разблокировать счет");
        System.out.println("  7  - Информация о счете");
        System.out.println("  0  - Выйти");
        System.out.print("\n  Ваш выбор: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1: depositMenu(); break;
            case 2: withdrawMenu(); break;
            case 3: transferMenu(); break;
            case 4: switchAccount(); break;
            case 5: showAllAccountIds(); break;
            case 6: toggleBlock(); break;
            case 7: showAccountInfo(); break;
            case 0:
                System.out.println("\nСпасибо за использование банка!");
                System.exit(0);
                break;
            default:
                System.out.println("Неверный выбор!");
        }
    }
    
    private static void depositMenu() {
        System.out.print("\nВведите сумму пополнения: ");
        int amount = getIntInput();
        currentAccount.deposit(amount);
    }
    
    private static void withdrawMenu() {
        System.out.print("\nВведите сумму снятия: ");
        int amount = getIntInput();
        currentAccount.withdraw(amount);
    }
    
    private static void transferMenu() {
        BankAccount targetAccount;
        if (currentAccount == mainAccount) {
            targetAccount = secondaryAccount;
            System.out.println("\nПеревод с основного счета на второй счет");
        } else {
            targetAccount = mainAccount;
            System.out.println("\nПеревод со второго счета на основной счет");
        }
        
        System.out.println("  ID счета получателя: " + targetAccount.getNumber());
        System.out.print("Введите сумму перевода: ");
        int amount = getIntInput();
        
        currentAccount.transfer(targetAccount, amount);
    }
    
    private static void switchAccount() {
        if (currentAccount == mainAccount) {
            currentAccount = secondaryAccount;
            System.out.println("\nПереключено на второй счет");
        } else {
            currentAccount = mainAccount;
            System.out.println("\nПереключено на основной счет");
        }
        System.out.println("  ID счета: " + currentAccount.getNumber());
        System.out.println("  Баланс: " + currentAccount.getBalance() + " руб.");
    }
    
    private static void showAllAccountIds() {
        System.out.println("\nСПИСОК ВСЕХ СЧЕТОВ:");
        System.out.println("  Основной счет  ID " + mainAccount.getNumber() + " (Баланс: " + mainAccount.getBalance() + " руб.)");
        System.out.println("  Второй счет    ID " + secondaryAccount.getNumber() + " (Баланс: " + secondaryAccount.getBalance() + " руб.)");
        System.out.println("  Текущий счет: ID " + currentAccount.getNumber());
    }
    
    private static void toggleBlock() {
        boolean currentStatus = currentAccount.isBlocked();
        currentAccount.setBlocked(!currentStatus);
        System.out.println("\nСчет " + (currentAccount.isBlocked() ? "ЗАБЛОКИРОВАН" : "РАЗБЛОКИРОВАН"));
    }
    
    private static void showAccountInfo() {
        System.out.println("\nПОЛНАЯ ИНФОРМАЦИЯ О СЧЕТЕ:");
        System.out.println("  ID счета: " + currentAccount.getNumber());
        System.out.println("  Баланс: " + currentAccount.getBalance() + " руб.");
        System.out.println("  Дата открытия: " + currentAccount.getOpeningDate());
        System.out.println("  Статус: " + (currentAccount.isBlocked() ? "ЗАБЛОКИРОВАН" : "АКТИВЕН"));
        if (currentAccount == mainAccount) {
            System.out.println("  Владелец: " + ownerName);
        } else {
            System.out.println("Второй счет пользователя " + ownerName);
        }
    }
    
    private static int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.print("Введите число: ");
                    continue;
                }
                int value = Integer.parseInt(input);
                if (value < 0) {
                    System.out.print("Число не может быть отрицательным: ");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Ошибка! Введите целое число: ");
            }
        }
    }
}
