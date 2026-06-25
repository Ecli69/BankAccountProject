import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

public class BankAccount {
    private String ownerName;
    private int balance;
    private LocalDateTime openingDate;
    private boolean isBlocked;
    private String number;
    
    public BankAccount(String ownerName) {
        this.ownerName = ownerName;
        this.balance = 0;
        this.openingDate = LocalDateTime.now();
        this.isBlocked = false;
        this.number = generateAccountNumber();
    }
    
    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
    
    public boolean deposit(int amount) {
        if (amount <= 0) {
            System.out.println("Ошибка: сумма должна быть больше 0");
            return false;
        }
        if (isBlocked) {
            System.out.println("Ошибка: счет заблокирован");
            return false;
        }
        balance += amount;
        System.out.println("Пополнение на " + amount + " руб. Баланс: " + balance + " руб.");
        return true;
    }
    
    public boolean withdraw(int amount) {
        if (amount <= 0) {
            System.out.println("Ошибка: сумма должна быть больше 0");
            return false;
        }
        if (isBlocked) {
            System.out.println("Ошибка: счет заблокирован");
            return false;
        }
        if (amount > balance) {
            System.out.println("Ошибка: недостаточно средств. Доступно: " + balance + " руб.");
            return false;
        }
        balance -= amount;
        System.out.println("Снято " + amount + " руб. Баланс: " + balance + " руб.");
        return true;
    }
    
    public boolean transfer(BankAccount otherAccount, int amount) {
        if (otherAccount == null) {
            System.out.println("Ошибка: целевой счет не существует");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Ошибка: сумма перевода должна быть больше 0");
            return false;
        }
        if (isBlocked) {
            System.out.println("Ошибка: ваш счет заблокирован");
            return false;
        }
        if (otherAccount.isBlocked) {
            System.out.println("Ошибка: счет получателя заблокирован");
            return false;
        }
        if (amount > balance) {
            System.out.println("Ошибка: недостаточно средств. Доступно: " + balance + " руб.");
            return false;
        }
        
        this.balance -= amount;
        otherAccount.balance += amount;
        
        System.out.println("Перевод " + amount + " руб. на счет ID " + otherAccount.getNumber() + " выполнен!");
        System.out.println("   Ваш баланс: " + balance + " руб.");
        return true;
    }
    
    @Override
    public String toString() {
        return
               "ID счета: " + number + "\n" +
               "Владелец: " + ownerName + "\n" +
               "Баланс: " + balance + " руб.\n" +
               "Открыт: " + openingDate + "   \n" +
               "Статус: " + (isBlocked ? "ЗАБЛОКИРОВАН" : "АКТИВЕН") + "\n";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BankAccount that = (BankAccount) obj;
        return Objects.equals(number, that.number) &&
               Objects.equals(ownerName, that.ownerName) &&
               openingDate.equals(that.openingDate);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(number, ownerName, openingDate);
    }
    
    public String getOwnerName() { return ownerName; }
    public int getBalance() { return balance; }
    public LocalDateTime getOpeningDate() { return openingDate; }
    public boolean isBlocked() { return isBlocked; }
    public void setBlocked(boolean blocked) { isBlocked = blocked; }
    public String getNumber() { return number; }
}