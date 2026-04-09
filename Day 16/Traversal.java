import java.util.*;

public class Traversal {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        /*
         * for (int i : list) {
         * if (i == 4) {
         * list.remove(i);
         * }
         * }
         * System.out.println(list);
         */

        ListIterator<Integer> it = list.listIterator();
        while (it.hasNext()) {
            int i = it.next();
            if (i == 4) {
                it.remove();
            }
        }
        System.out.println(list);

        while (it.hasPrevious()) {
            int prev = it.previous();
            System.out.print(prev + " ");
        }
    }
}
