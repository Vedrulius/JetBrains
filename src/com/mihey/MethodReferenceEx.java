package com.mihey;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.BiFunction;

/**
 * Use isAscending variable to determine if array should be sorted in ascending or descending order.
 * If the variable is true, then you need to sort the array in the ascending order, otherwise,
 * in the descending order.
 * <p>
 * Sample Input 1:
 * <p>
 * ascending
 * 3 5 2 1 4
 * Sample Output 1:
 * <p>
 * 1 2 3 4 5
 */

public class MethodReferenceEx {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isAscending = scanner.nextLine().equals("ascending");
        int[] array = Arrays.stream(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        BiFunction<Integer, Integer, Integer> comparator = null;
        comparator = isAscending ? Math::min : Math::max;

        sort(array, comparator);
        Arrays.stream(array).forEach(e -> System.out.print(e + " "));
    }

    public static void sort(int[] array, BiFunction<Integer, Integer, Integer> comparator) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (comparator.apply(array[j], array[j + 1]) == array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
}
