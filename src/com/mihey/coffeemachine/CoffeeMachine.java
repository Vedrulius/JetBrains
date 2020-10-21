package com.mihey.coffeemachine;

import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int w = 400;
        int m = 540;
        int cb = 120;
        int dc = 9;
        int money = 550;
        while (true) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String action = sc.next();
            if (action.equals("exit")) break;
            switch (action) {
                case ("buy"):
                    System.out.println("What do you want to buy? 1 - espresso, 2 - latte," +
                            " 3 - cappuccino, back - to main menu:");
                    String buy=sc.next();
                    switch (buy) {
                        case("back"):
                            System.out.println();
                            break;
                        case ("3"):
                            if (w > 199 && m > 99 && cb > 11) {
                                System.out.println("I have enough resources, making you a coffee!\n");
                                w -= 200;
                                m -= 100;
                                cb -= 12;
                                dc--;
                                money += 6;
                                break;
                            }
                            if (w < 200) System.out.println("Sorry, not enough water!\n");
                            if (m < 100) System.out.println("Sorry, not enough milk!\n");
                            if (cb < 12) System.out.println("Sorry, not enough coffee beans!\n");
                            break;
                        case ("2"):
                            if (w > 349 && m > 75 && cb > 19) {
                                System.out.println("I have enough resources, making you a coffee!\n");
                                w -= 350;
                                m -= 75;
                                cb -= 20;
                                dc--;
                                money += 7;
                                break;
                            }
                            if (w < 350) System.out.println("Sorry, not enough water!\n");
                            if (m < 75) System.out.println("Sorry, not enough milk!\n");
                            if (cb < 20) System.out.println("Sorry, not enough coffee beans!\n");
                            break;
                        case ("1"):
                            if (w > 249 && cb > 16) {
                                System.out.println("I have enough resources, making you a coffee!\n");
                                w -= 250;
                                cb -= 16;
                                dc--;
                                money += 4;
                                break;
                            }
                            if (w < 250) System.out.println("Sorry, not enough water!\n");
                            if (cb < 16) System.out.println("Sorry, not enough coffee beans!\n");
                            break;
                    }
                    break;
                case ("fill"):
                    System.out.println("Write how many ml of water do you want to add:");
                    int water = sc.nextInt();
                    w += water;
                    System.out.println("Write how many ml of milk do you want to add:");
                    int milk = sc.nextInt();
                    m += milk;
                    System.out.println("Write how many grams of coffee beans do you want to add:");
                    int cof = sc.nextInt();
                    cb += cof;
                    System.out.println("Write how many disposable cups of coffee do you want to add:");
                    int disCups = sc.nextInt();
                    dc += disCups;
                    System.out.println();
                    break;
                case ("take"):
                    System.out.println("I gave you $" + money + "\n");
                    money = 0;
                    break;
                case ("remaining"):
                    System.out.println("\nThe coffee machine has:\n" +
                            w + " of water\n" +
                            m + " of milk\n" +
                            cb + " of coffee beans\n" +
                            dc + " of disposable cups\n" +
                            "$" + money + " of money\n");
                    break;
            }
        }
    }
}
