import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class BankAccountGUI extends JFrame {
    private BankAccount mainAccount;
    private BankAccount secondaryAccount;
    private BankAccount currentAccount;
    private JLabel accountIdLabel;
    private JLabel balanceLabel;
    private JLabel statusLabel;
    private JTextField amountField;
    private JTextArea logArea;
    private JComboBox<String> accountSelector;
    
    public BankAccountGUI() {
        setTitle("Банковская система");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 720);
        setLocationRelativeTo(null);
        setResizable(false);
        
        String name = JOptionPane.showInputDialog(this, "Введите имя владельца:", "Создание счета", JOptionPane.QUESTION_MESSAGE);
        if (name == null || name.trim().isEmpty()) {
            name = "Пользователь";
        }
        
        mainAccount = new BankAccount(name.trim());
        secondaryAccount = new BankAccount("Второй счет");
        currentAccount = mainAccount;
        
        createUI();
        
        updateAccountInfo();
        addLog("Система запущена");
        addLog("Основной счет создан: ID " + mainAccount.getNumber());
        addLog("Второй счет создан: ID " + secondaryAccount.getNumber());
    }
    
    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(240, 248, 255));
        
        JPanel infoPanel = createInfoPanel();
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        
        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = createBottomPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(0, 0, 255));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 200), 2),
            new EmptyBorder(15, 15, 15, 15)
        ));
        panel.setPreferredSize(new Dimension(770, 120));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titleLabel = new JLabel("БАНКОВСКАЯ СИСТЕМА");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 6;
        panel.add(titleLabel, gbc);
        
        Font infoFont = new Font("Arial", Font.PLAIN, 15);
        Color labelColor = new Color(220, 240, 255);
        
        JLabel idLabel = new JLabel("ID счета:");
        idLabel.setFont(infoFont);
        idLabel.setForeground(labelColor);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(idLabel, gbc);
        
        accountIdLabel = new JLabel("-");
        accountIdLabel.setFont(new Font("Arial", Font.BOLD, 15));
        accountIdLabel.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(accountIdLabel, gbc);
        
        JLabel balLabel = new JLabel("Баланс:");
        balLabel.setFont(infoFont);
        balLabel.setForeground(labelColor);
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(balLabel, gbc);
        
        balanceLabel = new JLabel("-");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        balanceLabel.setForeground(new Color(50, 255, 50));
        gbc.gridx = 3;
        gbc.gridy = 1;
        panel.add(balanceLabel, gbc);
        
        JLabel statLabel = new JLabel("Статус:");
        statLabel.setFont(infoFont);
        statLabel.setForeground(labelColor);
        gbc.gridx = 4;
        gbc.gridy = 1;
        panel.add(statLabel, gbc);
        
        statusLabel = new JLabel("-");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 5;
        gbc.gridy = 1;
        panel.add(statusLabel, gbc);
        
        return panel;
    }
    
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(770, 330));
        
        JPanel selectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectPanel.setBackground(new Color(245, 245, 245));
        selectPanel.setPreferredSize(new Dimension(770, 45));
        
        JLabel selectLabel = new JLabel("Выберите счет:");
        selectLabel.setFont(new Font("Arial", Font.BOLD, 14));
        selectPanel.add(selectLabel);
        
        accountSelector = new JComboBox<>(new String[]{"Основной", "Второй"});
        accountSelector.setFont(new Font("Arial", Font.PLAIN, 14));
        accountSelector.setPreferredSize(new Dimension(130, 32));
        accountSelector.addActionListener(e -> switchAccount());
        selectPanel.add(accountSelector);
        
        JButton refreshButton = createColoredButton("Обновить", new Color(52, 152, 219));
        refreshButton.setPreferredSize(new Dimension(110, 32));
        refreshButton.addActionListener(e -> updateAccountInfo());
        selectPanel.add(refreshButton);
        
        panel.add(selectPanel, BorderLayout.NORTH);
        
        JPanel operPanel = new JPanel(new GridBagLayout());
        operPanel.setBackground(Color.WHITE);
        operPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 255), 2),
            "ОПЕРАЦИИ",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 16)
        ));
        operPanel.setPreferredSize(new Dimension(750, 250));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel amountLabel = new JLabel("Сумма (руб.):");
        amountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        operPanel.add(amountLabel, gbc);
        
        amountField = new JTextField(15);
        amountField.setFont(new Font("Arial", Font.PLAIN, 16));
        amountField.setPreferredSize(new Dimension(250, 38));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        operPanel.add(amountField, gbc);
        
        JButton depositBtn = createColoredButton("Пополнить", new Color(46, 204, 113));
        depositBtn.setPreferredSize(new Dimension(160, 50));
        depositBtn.addActionListener(e -> deposit());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        operPanel.add(depositBtn, gbc);
        
        JButton withdrawBtn = createColoredButton("Снять", new Color(241, 196, 15));
        withdrawBtn.setPreferredSize(new Dimension(160, 50));
        withdrawBtn.addActionListener(e -> withdraw());
        gbc.gridx = 1;
        gbc.gridy = 1;
        operPanel.add(withdrawBtn, gbc);
        
        JButton transferBtn = createColoredButton("Перевести", new Color(52, 152, 219));
        transferBtn.setPreferredSize(new Dimension(160, 50));
        transferBtn.addActionListener(e -> transfer());
        gbc.gridx = 2;
        gbc.gridy = 1;
        operPanel.add(transferBtn, gbc);
        
        JPanel bottomButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        bottomButtonsPanel.setBackground(Color.WHITE);
        
        JButton blockBtn = createColoredButton("Блокировка счета", new Color(192, 57, 43));
        blockBtn.setPreferredSize(new Dimension(180, 50));
        blockBtn.addActionListener(e -> toggleBlock());
        bottomButtonsPanel.add(blockBtn);
        
        JButton exitBtn = createColoredButton("Выход", new Color(149, 165, 166));
        exitBtn.setPreferredSize(new Dimension(120, 50));
        exitBtn.addActionListener(e -> exitApplication());
        bottomButtonsPanel.add(exitBtn);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        operPanel.add(bottomButtonsPanel, gbc);
        
        panel.add(operPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JButton createColoredButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setContentAreaFilled(false);
        return button;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 255), 2),
            "ЖУРНАЛ ОПЕРАЦИЙ",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14)
        ));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(770, 200));
        
        logArea = new JTextArea(10, 55);
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        logArea.setBackground(new Color(250, 250, 250));
        
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void deposit() {
        int amount = getAmount();
        if (amount == -1) return;
        
        boolean result = currentAccount.deposit(amount);
        if (result) {
            addLog("Пополнение на " + amount + " руб. - УСПЕШНО");
            updateAccountInfo();
        } else {
            addLog("Пополнение на " + amount + " руб. - ОШИБКА");
        }
    }
    
    private void withdraw() {
        int amount = getAmount();
        if (amount == -1) return;
        
        boolean result = currentAccount.withdraw(amount);
        if (result) {
            addLog("Снятие " + amount + " руб. - УСПЕШНО");
            updateAccountInfo();
        } else {
            addLog("Снятие " + amount + " руб. - ОШИБКА");
        }
    }
    
    private void transfer() {
        int amount = getAmount();
        if (amount == -1) return;
        
        BankAccount target;
        if (currentAccount == mainAccount) {
            target = secondaryAccount;
        } else {
            target = mainAccount;
        }
        
        boolean result = currentAccount.transfer(target, amount);
        if (result) {
            addLog("Перевод " + amount + " руб. на счет ID " + target.getNumber() + " - УСПЕШНО");
            updateAccountInfo();
        } else {
            addLog("Перевод " + amount + " руб. - ОШИБКА");
        }
    }
    
    private void toggleBlock() {
        boolean newStatus = !currentAccount.isBlocked();
        currentAccount.setBlocked(newStatus);
        String status = newStatus ? "ЗАБЛОКИРОВАН" : "АКТИВЕН";
        addLog("Счет " + status);
        updateAccountInfo();
    }
    
    private void exitApplication() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Вы уверены, что хотите выйти?",
            "Выход из программы",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    private void switchAccount() {
        int index = accountSelector.getSelectedIndex();
        if (index == 0) {
            currentAccount = mainAccount;
            addLog("Переключено на основной счет");
        } else {
            currentAccount = secondaryAccount;
            addLog("Переключено на второй счет");
        }
        updateAccountInfo();
    }
    
    private void updateAccountInfo() {
        accountIdLabel.setText(currentAccount.getNumber());
        balanceLabel.setText(currentAccount.getBalance() + " руб.");
        
        String status = currentAccount.isBlocked() ? "ЗАБЛОКИРОВАН" : "АКТИВЕН";
        statusLabel.setText(status);
        statusLabel.setForeground(currentAccount.isBlocked() ? Color.RED : new Color(50, 255, 50));
        
        if (currentAccount == mainAccount) {
            accountSelector.setSelectedIndex(0);
        } else {
            accountSelector.setSelectedIndex(1);
        }
    }
    
    private int getAmount() {
        try {
            String text = amountField.getText().trim();
            if (text.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Введите сумму!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return -1;
            }
            int amount = Integer.parseInt(text);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Сумма должна быть больше 0!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return -1;
            }
            amountField.setText("");
            return amount;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Введите целое число!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }
    
    private void addLog(String message) {
        logArea.append("[" + java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")) + "] " + message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new BankAccountGUI().setVisible(true);
        });
    }
}
