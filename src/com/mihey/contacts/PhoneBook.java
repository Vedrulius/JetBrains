package com.mihey.contacts;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneBook {
    public static void main(String[] args) {
        int count = 1;
        List<Contact> account = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        String action = getAction();
        while (!action.equals("exit")) {
            switch (action) {
                case "add":
                    account.add(addingContact());
                    action = getAction();
                    break;
                case "remove":
                    if (account.size() != 0) {
                        listingContact(account);
                        removingContact(account);
                        action = getAction();
                    } else {
                        System.out.println("No records to remove!");
                        action = getAction();
                    }
                    break;
                case "edit":
                    if (account.size() == 0) {
                        System.out.println("No records to edit!");
                        action = getAction();
                        break;
                    }
                    listingContact(account);
                    editingContact(account);
                    action = getAction();
                    break;
                case "count":
                    System.out.println("The Phone Book has " + account.size() + " records.");
                    action = getAction();
                    break;
                case "info":
                    if (account.size() == 0) {
                        System.out.println("No contacts!");
                        action = getAction();
                        break;
                    }
                    contactInfo(account);
                    action = getAction();
                    break;
            }
        }
    }

    static void search(List<Contact> list) {
        ArrayList<Contact> searchContact = new ArrayList<>();
        int count = 0;
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        Pattern pattern = Pattern.compile(s.trim(), Pattern.CASE_INSENSITIVE);
        for (Contact contact : list) {
            if (contact instanceof PersonContact) {
                String c = ((PersonContact) contact).getName() + ((PersonContact) contact).getLastName();
                Matcher matcher = pattern.matcher(c);
                if (matcher.find()) {
                    searchContact.add(contact);
                    count++;
                }
            } else {
                String c = ((Organization) contact).getName() + ((Organization) contact).getAddress();
                Matcher matcher = pattern.matcher(c);
                if (matcher.find()) {
                    searchContact.add(contact);
                    count++;
                }
            }
        }
        System.out.printf("Found %d results:\n",count);
        listingContact(searchContact);

    }

    static void listingContact(List<Contact> list) {
        int count = 1;
        for (Contact contact : list) {
            if (contact instanceof PersonContact) {
                System.out.printf("%d. %s %s\n", count, ((PersonContact) contact).getName(), ((PersonContact) contact).getLastName());
                count++;
            } else {
                System.out.printf("%d. %s\n", count, ((Organization) contact).getName());
                count++;
            }
        }
    }


    static void contactInfo(List<Contact> list) {
        int count = 1;
        for (Contact contact : list) {
            if (contact instanceof PersonContact) {
                System.out.printf("%d. %s %s\n", count, ((PersonContact) contact).getName(), ((PersonContact) contact).getLastName());
                count++;
            } else {
                System.out.printf("%d. %s\n", count, ((Organization) contact).getName());
                count++;
            }
        }
        System.out.println("Enter index to show info: ");
        Scanner sc = new Scanner(System.in);
        int rec = sc.nextInt() - 1;
        System.out.println(list.get(rec).toString());
    }

    static void removingContact(List<Contact> list) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Select a record: ");
        int rec = sc.nextInt() - 1;
        list.remove(rec);
        System.out.println("The record removed!");
    }

    static boolean isValid(String number) {
        String str1 = "[\\s]*[+]?[(][\\w]{1,}[)]([- ][\\w]{2,})*";
        String str2 = "[\\s]*[+]?[\\w]{1,}[\\s-][(][\\w]{2,}[)]([- ]?[\\w]{2,})*";
        String str3 = "[\\s]*[+]?[\\w]{1,}([- ][\\w]{2,})*";
        Pattern pattern = Pattern.compile(str1);
        Matcher matcher = pattern.matcher(number);
        Pattern pattern1 = Pattern.compile(str2);
        Matcher matcher1 = pattern1.matcher(number);
        Pattern pattern2 = Pattern.compile(str3);
        Matcher matcher2 = pattern2.matcher(number);
        return matcher.matches() || matcher1.matches() || matcher2.matches();
    }

    static void editingContact(List<Contact> list) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Select a record: ");
        int rec = sc.nextInt() - 1;
        sc.nextLine();
        Contact contact = list.get(rec);
        if (contact instanceof PersonContact) {
            System.out.println("Select a field (name, surname, birth, gender, number): ");
            String field = sc.nextLine();
            System.out.printf("Enter %s: \n", field);

            switch (field) {
                case "name":
                    ((PersonContact) contact).setName(sc.nextLine());
                    contact.setTimeEdited(LocalDateTime.now());
                    list.set(rec, contact);
                    break;
                case "surname":
                    ((PersonContact) contact).setLastName(sc.nextLine());
                    contact.setTimeEdited(LocalDateTime.now());
                    list.set(rec, contact);
                    break;
                case "birth":
                    ((PersonContact) contact).setBirthDate(sc.nextLine());
                    contact.setTimeEdited(LocalDateTime.now());
                    list.set(rec, contact);
                    break;
                case "gender":
                    ((PersonContact) contact).setGender(sc.nextLine());
                    contact.setTimeEdited(LocalDateTime.now());
                    list.set(rec, contact);
                    break;
                case "number":
                    String phoneNum = sc.nextLine();
                    if (!isValid(phoneNum)) {
                        System.out.println("Wrong number format!");
                        phoneNum = "[no number]";
                    }
                    contact.setPhoneNumber(phoneNum);
                    contact.setTimeEdited(LocalDateTime.now());
                    list.set(rec, contact);
                    break;
            }
        } else {
            System.out.println("Select a field (name, address, number): ");
            String field = sc.nextLine();
            System.out.printf("Enter %s: \n", field);

            switch (field) {
                case "name":
                    ((Organization) contact).setName(sc.nextLine());
                    contact.setTimeEdited(LocalDateTime.now());
                    list.set(rec, contact);
                    break;
                case "address":
                    ((Organization) contact).setAddress(sc.nextLine());
                    contact.setTimeEdited(LocalDateTime.now());
                    list.set(rec, contact);
                    break;
                case "number":
                    String phoneNum = sc.nextLine();
                    if (!isValid(phoneNum)) {
                        System.out.println("Wrong number format!");
                        phoneNum = "[no number]";
                    }
                    contact.setPhoneNumber(phoneNum);
                    contact.setTimeEdited(LocalDateTime.now());
                    list.set(rec, contact);
                    break;
            }
            System.out.println("The record updated!");
        }
    }

    static Contact addingContact() {
        Scanner sc = new Scanner(System.in);
        String name;
        String surName;
        String phoneNumber;
        String birth;
        String gender;
        String type;

        System.out.println("Enter the type (person, organization): ");
        type = sc.nextLine();
        if (type.equals("person")) {
            System.out.println("Enter the name: ");
            name = sc.nextLine();
            System.out.println("Enter the surname: ");
            surName = sc.nextLine();
            System.out.println("Enter the birth date: ");
            birth = sc.nextLine();
            if (!birth.matches("\\d{4}-\\d{2}-\\d{2}")) {
                System.out.println("Bad birth date!");
                birth = "[no data]";
            }
            System.out.println("Enter the gender (M, F): ");
            gender = sc.nextLine();
            if (!gender.matches("(M|F)")) {
                System.out.println("Bad gender!");
                gender = "[no data]";
            }
            System.out.println("Enter the number: ");
            phoneNumber = sc.nextLine();
            if (!isValid(phoneNumber)) {
                System.out.println("Wrong number format!");
                phoneNumber = "[no number]";
            }
            System.out.println("The record added.");

            return new PersonContact(phoneNumber, LocalDateTime.now(), LocalDateTime.now()
                    , name, surName, birth, gender);
        } else {
            System.out.println("Enter the organization name: ");
            name = sc.nextLine();
            System.out.println("Enter the address: ");
            surName = sc.nextLine();
            System.out.println("Enter the number: ");
            phoneNumber = sc.nextLine();
            if (!isValid(phoneNumber)) {
                System.out.println("Wrong number format!");
                phoneNumber = "[no number]";
            }
            System.out.println("The record added.");
        }
        return new Organization(phoneNumber, LocalDateTime.now(), LocalDateTime.now(), name, surName);
    }

    static String getAction() {
        System.out.println("[menu] Enter action (add, list, search, count, exit): ");
        Scanner scanner = new Scanner(System.in);
        String action = scanner.nextLine();
        while (!action.matches("(add|list|search|count|exit)")) {
            System.out.println("[menu] Enter action (add, list, search, count, exit): ");
            action = scanner.nextLine();
        }
        return action;
    }

}

abstract class Contact {
    String phoneNumber;
    LocalDateTime timeCreated;
    LocalDateTime timeEdited;

    public Contact(String phoneNumber, LocalDateTime timeCreated, LocalDateTime timeEdited) {
        this.phoneNumber = phoneNumber;
        this.timeCreated = timeCreated;
        this.timeEdited = timeEdited;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public LocalDateTime getTimeEdited() {
        return timeEdited;
    }

    public void setTimeEdited(LocalDateTime timeEdited) {
        this.timeEdited = timeEdited;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

class Organization extends Contact {

    private String name;
    private String address;

    public Organization(String phoneNumber, LocalDateTime timeCreated, LocalDateTime timeEdited, String name, String address) {
        super(phoneNumber, timeCreated, timeEdited);
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return
                "Organization name: " + name + '\n' +
                        "Address: " + address + '\n' +
                        "Number: " + phoneNumber + '\n' +
                        "Time created: " + timeCreated.withSecond(0).withNano(0) + '\n' +
                        "Time last edit: " + timeEdited.withSecond(0).withNano(0);
    }
}

class PersonContact extends Contact {

    private String name;
    private String lastName;
    private String gender;
    private String birthDate;


    public PersonContact(String phoneNumber, LocalDateTime timeCreated, LocalDateTime timeEdited
            , String name, String lastName, String gender, String birthDate) {
        super(phoneNumber, timeCreated, timeEdited);
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return
                "Name: " + name + '\n' +
                        "Surname: " + lastName + '\n' +
                        "Birth date: " + birthDate + '\n' +
                        "Gender: " + gender + '\n' +
                        "Number: " + phoneNumber + '\n' +
                        "Time created: " + timeCreated.withSecond(0).withNano(0) + '\n' +
                        "Time last edit: " + timeEdited.withSecond(0).withNano(0);
    }
}

//class PhoneBook {
//
//    private final PersonContact contact;
//
//    public PhoneBook(PersonContact contact) {
//        this.contact = contact;
//    }
//}
