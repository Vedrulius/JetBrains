package com.mihey.simple_banking_system;

import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class SimpleBanking {
    //    Welcome screen
    static void greet() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
    }

    //    Balance screen
    static void balance() {
        System.out.println("1. Balance\n" +
                "2. Add income\n" +
                "3. Do transfer\n" +
                "4. Close account\n" +
                "5. Log out\n" +
                "0. Exit");
    }

    // validate card number and pin
    static boolean validate(String cn, String cardNumber, String p, String pin) {

        return (cn.equals(cardNumber) && p.equals(pin));
    }

    //    return menu number
    static String getAnswer() {
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        while (!answer.matches("[0-5]")) {
            greet();
            answer = scanner.nextLine();
        }
        return answer;
    }

    // generating PIN
    static String generatePin() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            sb.append(r.nextInt(10) + "");
        }
        return sb.toString();
    }

    //generating card number
    static String generateCardNumber() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        int luhn = 0;
        int[] cn = new int[15];
        cn[0] = 4;

        for (int i = 0; i < 9; i++) {
            cn[i + 6] = r.nextInt(10);
        }
        for (int i : cn) {
            sb.append(i);
        }
        for (int i = 0; i < cn.length; i++) {
            if (i % 2 == 0) {
                cn[i] *= 2;
            }
            if (cn[i] > 9) {
                cn[i] -= 9;
            }
            luhn += cn[i];
        }
        return sb.append(10 - luhn % 10).toString();
    }

    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:/home/mihey/Sqlite/" + fileName;
        String sql = "CREATE TABLE IF NOT EXISTS card ("
                + "     id INTEGER PRIMARY KEY,"
                + "     number TEXT,"
                + "     pin TEXT,"
                + "     balance INTEGER DEFAULT 0"
                + ");";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void connect(String fileName) {

        String url = "jdbc:sqlite:/home/mihey/Sqlite/" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static Account select(String fileName, String pin) {
        String cn = null;
        String p = null;
        int b = 0;
        String url = "jdbc:sqlite:/home/mihey/Sqlite/" + fileName;
        String query = "select number,pin,balance from card where pin= " + "'" + pin + "'";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            cn = rs.getString("number");
            p = rs.getString("pin");
            b = rs.getInt("balance");

        } catch (SQLException e) {
        }
        return new Account(cn, p, b);
    }

    static void insert(String fileName, String card, String pin) {
        String url = "jdbc:sqlite:/home/mihey/Sqlite/" + fileName;
        String query = "insert into card (number, pin) values ('" + card + "','" + pin + "')";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static void closeAccount(String fileName, String pin) {
        String url = "jdbc:sqlite:/home/mihey/Sqlite/" + fileName;
        String query = "delete from card where pin= " + "'" + pin + "'";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static final class Account {
        private final String cardNumber;
        private final String pin;
        private int balance;

        public Account(String cardNumber, String pin, int balance) {
            this.cardNumber = cardNumber;
            this.pin = pin;
            this.balance = balance;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public String getPin() {
            return pin;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

    }

    public static void main(String[] args) {
        String db = args[1];
        createNewDatabase(db);
        String pin = null;
        String cardNumber = null;
        Scanner sc = new Scanner(System.in);
        greet();
        String answer = getAnswer();
        while (!answer.equals("0")) {
            switch (answer) {
                case ("1"):
                    pin = generatePin();
                    cardNumber = generateCardNumber();
                    System.out.printf("Your card has been created\n" +
                            "Your card number:\n%s\n" +
                            "Your card PIN:\n%s\n", cardNumber, pin);
                    insert(db, cardNumber, pin);
                    System.out.println();
                    greet();
                    answer = getAnswer();
                    break;
                case ("2"):
                    System.out.println("Enter your card number:");
                    String cn = sc.nextLine();
                    System.out.println("Enter your PIN:");
                    String p = sc.nextLine();
                    Account account = select(db, p);
                    if (validate(cn, account.getCardNumber(), p, account.getPin())) {
                        System.out.println("You have successfully logged in!\n");
                        balance();
                        answer = getAnswer();
                        boolean b = true;
                        while (b) {
                            switch (answer) {
                                case("0"):
                                    b=false;
                                    break;
                                case ("1"):
                                    System.out.printf("\nBalance: %d\n\n", account.getBalance());
                                    balance();
                                    answer = getAnswer();
                                    break;
                                case ("2"):
                                    System.out.println("Enter income: ");
                                    int income = sc.nextInt();
                                    System.out.println("Income was added!\n");
                                    account.setBalance(account.getBalance() + income);
                                    balance();
                                    answer = getAnswer();
                                    break;
                                case ("3"):
                                    System.out.println("Transfer\n" +
                                            "Enter card number: ");
                                    int transferCard = sc.nextInt();
                                    System.out.println("Enter how much money you want to transfer: ");
                                    int transfer = sc.nextInt();
                                    if (transfer > account.getBalance()) {
                                        System.out.println("Not enough money!\n");
                                    } else {
                                        System.out.println("Success!\n");
                                        account.setBalance(account.getBalance() - transfer);
                                    }
                                    balance();
                                    answer = getAnswer();
                                    break;
                                case ("4"):
                                    closeAccount(db, account.getPin());
                                    System.out.println("\nThe account has been closed!\n");
                                    greet();
                                    answer = getAnswer();
                                    b = false;
                                    break;
                                case ("5"):
                                    System.out.println("\nYou have successfully logged out!\n");
                                    greet();
                                    answer = getAnswer();
                                    b = false;
                                    break;
                            }
                        }

                    } else {
                        System.out.println("\nWrong card number or PIN!\n");
                        greet();
                        answer = getAnswer();
                    }
                    break;
            }
        }
        System.out.println("\nBye!");
    }
}
