import java.util.ArrayList;
import java.util.Scanner;

/** @author: Linux_Mumu */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int num, op, index, insertIndex;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(i + 1);
        }
        for (int i = 0; i < m; i++) {
            num = sc.nextInt();
            op = sc.nextInt();
            index = list.indexOf(num);
            insertIndex = Math.min(index + op, list.size());
            insertIndex = Math.max(insertIndex, 0);
            list.remove(index);
            list.add(insertIndex, num);
        }
        for (int item : list) {
            System.out.print(item);
            System.out.print(" ");
        }
        System.out.println();
        sc.close();
    }
}