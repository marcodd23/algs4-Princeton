import algs4.source.algs4lib.Queue;
import algs4.source.algs4lib.Shell;
import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author marco
 */
public class TestDeque {

    public TestDeque() {
    }

    public void testAscendingOrderInput(int N) {

        Integer[] array = new Integer[N];

        Deque<Integer> deque = new Deque<Integer>();
        for (int i = 1; i <= N; i++) {
            deque.addFirst(i);
        }

        String dequeString = deque.toString();
        System.out.println(dequeString);
        System.out.println("\nSize: " + deque.size() + "\n");

        for (int i = 0; !deque.isEmpty(); i++) {
            Integer last = deque.removeLast();
            array[i] = last;
            System.out.print(last + " ");
        }
        System.out.println("\n");
        assert isSorted(array);
    }

    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    private static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }

    public static void main(String[] args) {

        TestDeque test = new TestDeque();
        test.testAscendingOrderInput(10);
//        Queue<Integer> queue = new Queue<>();
//        for (int i = 1; i <= 10; i++) {
//            queue.enqueue(i);
//        }
//        System.out.println(queue.toString());

    }
}

