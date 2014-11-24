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

        if (first == null || last == null) {
            return true;
        } else {
            return false;
        }
    }

    public int size() {
        return N;
    }

    public void addFirst(Item item) {
        Node<Item> oldFirst = first;
        if (first == null) {
            last = new Node<Item>();
            first = last;
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
        Node<Item> newLast = new Node<Item>();
        newLast.item = item;
        newLast.next = null;
        newLast.prev = last;
        last.next = newLast;
        last = newLast;
        N++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        Item firstItem = first.item;
        first = first.next;
        first.prev = null;
        N--;
        if (isEmpty()) {
            last = null;
        }
        return firstItem;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        Item lastItem = last.item;
        last = last.prev;
        if (last != null) {
            last.next = null;
        }
        N--;
        if (isEmpty()) {
            first = null;
        }
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

    public String toString() {
        StringBuilder s = new StringBuilder();
        Iterator<Item> iterator = this.iterator();
        while (iterator.hasNext()) {
            s.append(iterator.next() + " ");
        }
        return s.toString();
    }

}

