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
        if (last == null){
            last = new Node<Item>();
            first = last;
        }else {
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
        }else {
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
        }else {
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

    public String toString() {
        StringBuilder s = new StringBuilder();
        Iterator<Item> iterator = this.iterator();
        while (iterator.hasNext()) {
            s.append(iterator.next() + " ");
        }
        return s.toString();
    }

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
        
    }
}

