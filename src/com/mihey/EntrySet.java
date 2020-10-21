package com.mihey;
/**
 * Input: the first line contains an integer nn, which is the number of insert queries.
 * Each of the next nn lines contains two records separated by a space:
 * the first represents the integer id, and the second corresponds to the string name.
 *
 * Output: for each unique id, print all names associated with the id.
 * All id's can be printed in arbitrary order, though names should be printed
 * in the same order they were inserted into the table.
 */

import java.util.Scanner;

public class EntrySet {

    private static class TableEntry<T> {
        private final int key;
        private final T value;

        public TableEntry(int key, T value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public T getValue() {
            return value;
        }
    }

    private static class HashTable<T> {
        private int size;
        private TableEntry[] table;

        public HashTable(int size) {
            this.size = size;
            table = new TableEntry[size];
        }

        public boolean put(int key, T value) {
            int idx = findKey(key);

            if (idx == -1) {
                return false;
            }

            table[idx] = new TableEntry(key, value);
            return true;
        }

        public T get(int key) {
            int idx = findKey(key);

            if (idx == -1 || table[idx] == null) {
                return null;
            }

            return (T) table[idx].getValue();
        }

        public String entrySet() {
            StringBuilder tableStringBuilder = new StringBuilder();

            for (TableEntry tableEntry : table) {
                if (tableEntry != null) {
                    tableStringBuilder.append(tableEntry.getKey()
                            + ": " + tableEntry.getValue() + "\n");
                }
            }

            return tableStringBuilder.toString();
        }

        private int findKey(int key) {
            int hash = key % size;

            while (!(table[hash] == null || table[hash].getKey() == key)) {
                hash = (hash + 1) % size;

                if (hash == key % size) {
                    return -1;
                }
            }

            return hash;
        }

        private void rehash() {
            // put your code here
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        HashTable<String> table = new HashTable(n);
        for (int i = 0; i < n; i++) {
            int key = sc.nextInt();
            String val = sc.next();
            if (table.get(key) == null) {
                table.put(key, val);
            } else {
                table.put(key, table.get(key) + " " + val);
            }
        }
        System.out.print(table.entrySet());
    }
}



