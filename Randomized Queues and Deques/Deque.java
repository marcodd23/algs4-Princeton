/*----------------------------------------------------------------
 *  Author:        Marco Di Dionisio
 *
 *  Compilation:   javac -cp ../stdlib.jar:../algs4.jar:. Deque.java
 *  Execution:     java -cp ../stdlib.jar:../algs4.jar:. Deque
 *
 *  Tests the percolation as per the specification available at:
 *    http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 *
 *----------------------------------------------------------------*/

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author marco
 */
public class Deque<Item> implements Iterable<Item> {

    private int N;
    private Node<Item> first;
    private Node<Item> last;

    private static class Node<Item> {

        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    public Deque() {
        first = null;
        last = null;
        N = 0;
    }

    public boolean isEmpty() {
        if (first == null) {
            return true;
        } else {
            return false;
        }
    }

    public int size() {
        return N;
    }

    public void addFirst(Item item) {
        checkNullItem(item);
        Node<Item> oldFirst = first;
        if (first == null) {
            first = new Node<Item>();
            last = first;
        } else {
            first = new Node<Item>();
            oldFirst.prev = first;
        }
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        N++;
    }

    public void addLast(Item item) {
        checkNullItem(item);
        Node<Item> oldLast = last;
        if (last == null) {
            last = new Node<Item>();
            first = last;
        } else {
            last = new Node<Item>();
            oldLast.next = last;
        }
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        N++;
    }

    /*public void addLast(Item item) {
     checkNullItem(item);
     Node<Item> newLast = new Node<Item>();
     newLast.item = item;
     newLast.next = null;
     newLast.prev = last;
     last.next = newLast;
     last = newLast;
     N++;
     }*/
    public Item removeFirst() {
        checkRemoveFromEmpty();
        Item firstItem = first.item;
        first = first.next;
        if (first != null) {
            first.prev = null;
        } else {
            first = null;
            last = first;
        }
        N--;
        return firstItem;
    }

    public Item removeLast() {
        checkRemoveFromEmpty();
        Item lastItem = last.item;
        last = last.prev;
        if (last != null) {
            last.next = null;
        } else {
            last = null;
            first = last;
        }
        N--;
        return lastItem;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    private class ListIterator<Item> implements Iterator<Item> {

        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        @Override
        public boolean hasNext() {
            if (current != null) {
                return true;
            }
            return false;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

    /*private String toString() {
        StringBuilder s = new StringBuilder();
        Iterator<Item> iterator = this.iterator();
        while (iterator.hasNext()) {
            s.append(iterator.next() + " ");
        }
        return s.toString();
    }*/

    private void checkNullItem(Item item) {
        if (item == null) {
            throw new NullPointerException("You attempt to insert a NULL item !!! ");
        }
    }

    private void checkRemoveFromEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
    }

    public static void main(String[] args) {
        int N = 100;
        Integer[] array = new Integer[N];
        Deque<Integer> deque = new Deque<Integer>();
        for (int i = 1; i <= N; i++) {
            deque.addFirst(i);
        }
        int choise;
        int inputItem;
        int removed;

        while (true) {

            StdOut.println("Select :\n");
            StdOut.println("Add a number on head: 1\n");
            StdOut.println("Add a number on tail: 2\n");
            StdOut.println("Remove a number on head: 3\n");
            StdOut.println("Remove a number on tail: 4\n");
            StdOut.println("EXIT                    : 5\n");

            int selection = StdIn.readInt();

            switch (selection) {
                case 1: {
                    do {
                        StdOut.println("Enter the number to add on head: \n");
                        inputItem = StdIn.readInt();
                        deque.addFirst(inputItem);
                        StdOut.println("There are \"" + deque.size() + "\" items in the Deque\n");         
                        StdOut.println("press 1 to insert another or 0 to continue: \n");
                        choise = StdIn.readInt();              
                    } while (choise != 0);
                    break;
                }
                case 2: {
                    do {
                        StdOut.println("Enter the number to add on tail: \n");
                        inputItem = StdIn.readInt();
                        deque.addLast(inputItem);
                        StdOut.println("There are \"" + deque.size() + "\" items in the Deque\n");
                        StdOut.println("press 1 to insert another or 0 to continue: \n");
                        choise = StdIn.readInt();  
                    } while (choise != 0);
                    break;
                }
                case 3: {
                    do {
                        removed = deque.removeFirst();
                        StdOut.println("removed " + removed + " from head\n");
                        StdOut.println("There are \"" + deque.size() + "\" items in the Deque\n");
                        StdOut.println("Press 1 to remove another item from head or press 0 to continue \n");
                        choise = StdIn.readInt();  
                    } while (choise != 0);
                    break;
                }
                case 4: {
                    do {
                        removed = deque.removeLast();
                        StdOut.println("removed " + removed + " from head\n");
                        StdOut.println("There are \"" + deque.size() + "\" items in the Deque\n");
                        StdOut.println("Press 1 to remove another item from head or press 0 to continue \n");
                        choise = StdIn.readInt();  
                    } while (choise != 0);
                    break;
                }
                case 5: {
                    return;
                }
            }
        }
        /*for (int i = 0; !deque.isEmpty(); i++) {
         Integer last = deque.removeLast();
         array[i] = last;
         System.out.print(last + " ");
         }*/
        //System.out.println("\n");
        //assertTrue(deque.isEmpty());
        //assertTrue(isSorted(array));

    }
}
