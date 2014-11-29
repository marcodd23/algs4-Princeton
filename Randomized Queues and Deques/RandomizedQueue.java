import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author marco
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] rqItems;
    private int N;
    //private int dummyN;
    private int head;
    private int tail;

    public RandomizedQueue() {
        rqItems = (Item[]) new Object[2];
        N = 0;
        //dummyN = 0;
        head = 0;
        tail = 0;
    }

    private void Resize(int max) {
        if (max < N) {
            return;
        }
        //assert (max >= N);
        Item[] swap = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            swap[i] = rqItems[(head + i) % rqItems.length];
        }
        rqItems = swap;
        head = 0;
        tail = N;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        checkNullItem(item);
        if (N == rqItems.length) {
            Resize(2 * rqItems.length);
        }
        rqItems[tail++] = item;
        N++;
        if (N < rqItems.length && tail == rqItems.length) {
            tail = 0; // array ciclico , ricomincio dall' inizio se c'e' ancora spazio
        }
    }

    public Item dequeue() {
        checkExtractFromEmpty();
        int a;
        int b;
        int index;
        Item item;
        if (tail > head) {
            a = head;
            b = tail;
            index = StdRandom.uniform(a, b);
        } else {
            a = head;
            b = (rqItems.length) + tail;
            index = StdRandom.uniform(a, b);
            if (index >= rqItems.length) {
                index = index - rqItems.length;
            }
        }
        item = rqItems[index];

        //shiftArray(index);
        if (tail == 0) {
            tail = rqItems.length - 1;
            rqItems[index] = rqItems[tail];
            rqItems[tail] = null;
        } else {
            tail--;
            rqItems[index] = rqItems[tail];
            rqItems[tail] = null;
        }
        N--;
        if (N > 0 && N == rqItems.length / 4) {
            Resize(rqItems.length / 2);
        }
        return item;
    }

    private void shiftArray(int index) {
        if (tail > head) {
            if ((tail - index) < (index - head)) {
                shiftLeft(index);
            } else {
                shiftRight(index);
            }
        } else {
            //index si trova tra head e fine array
            if (index >= head) {
                if ((tail + (rqItems.length - index)) < (index - head)) {
                    shiftLeft(index);
                } else {
                    shiftRight(index);
                }
            } else { //index si trova tra l'inizio dell'array e tail
                if ((tail - index) < ((rqItems.length - head) + index)) {
                    shiftLeft(index);
                } else {
                    shiftRight(index);
                }
            }
        }
    }

    private void shiftLeft(int index) {
        if (tail < index) {
            for (int i = index; i < rqItems.length - 1; i++) {
                rqItems[i] = rqItems[i + 1];
            }
            rqItems[rqItems.length - 1] = rqItems[0];
            for (int i = 0; i < tail; i++) {
                if (i == tail - 1) {
                    rqItems[i] = null;
                    continue;
                }
                rqItems[i] = rqItems[i + 1];
            }
            if (tail == 0) {
                tail = rqItems.length - 1;
            } else {
                tail--;
            }
        } else {
            for (int i = index; i < tail; i++) {
                if (i == tail - 1) {
                    rqItems[i] = null;
                    continue;
                }
                rqItems[i] = rqItems[i + 1];
            }
            tail--;
        }
    }

    private void shiftRight(int index) {
        if (head > index) {
            for (int i = index; i > 0; i--) {
                rqItems[i] = rqItems[i - 1];
            }
            rqItems[0] = rqItems[rqItems.length - 1];
            for (int i = rqItems.length - 1; i >= head; i--) {
                if (i == head) {
                    rqItems[i] = null;
                    continue;
                }
                rqItems[i] = rqItems[i - 1];
            }
            if (head == rqItems.length - 1) {
                head = 0;
            } else {
                head++;
            }
        } else {
            for (int i = index; i >= head; i--) {
                if (i == head) {
                    rqItems[i] = null;
                    continue;
                }
                rqItems[i] = rqItems[i - 1];
            }
            head++;
        }
    }

    public Item sample() {
        checkExtractFromEmpty();
        int a;
        int b;
        int index;
        Item item;
        if (tail > head) {
            a = head;
            b = tail;
            index = StdRandom.uniform(a, b);
        } else {
            a = head;
            b = (rqItems.length) + tail;
            index = StdRandom.uniform(a, b);
            if (index >= rqItems.length) {
                index = index - rqItems.length;
            }
        }
        item = rqItems[index];
        return item;
    }

    private void checkNullItem(Item item) {
        if (item == null) {
            throw new NullPointerException("You attempt to insert a NULL item !!! ");
        }
    }

    private void checkExtractFromEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator<Item>(rqItems, head, N);
    }

    /*private class ArrayIterator<Item> implements Iterator<Item> {

     // private Integer[] arrayExtracted;
     private RandomizedQueue<Item> arrayItems;
     int index;

     public ArrayIterator(Item[] array, int head) {
     arrayItems = new RandomizedQueue<Item>();
     for (int i = 0; i < array.length; i++) {
     index = (head + i) % array.length;
     if (array[index] == null) {
     break;
     }
     arrayItems.enqueue(array[index]);
     }
     }

     @Override
     public boolean hasNext() {
     return (!arrayItems.isEmpty());
     }

     @Override
     public Item next() {
     if (!hasNext()) {
     throw new NoSuchElementException();
     }
     Item item = arrayItems.dequeue();
     return item;
     }

     @Override
     public void remove() {
     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     }

     }*/

    private class ArrayIterator<Item> implements Iterator<Item> {

        // private Integer[] arrayExtracted;
        private Item[] arrayItems;
        private int currentIndex;

        public ArrayIterator(Item[] array, int head, int N) {
            currentIndex = 0;
            int index;
            arrayItems = (Item[]) new Object[N];
            for (int i = 0; i < array.length; i++) {
                index = (head + i) % array.length;
                if (array[index] == null) {
                    break;
                }
                arrayItems[i] = array[index];              
            }
            StdRandom.shuffle(arrayItems);
        }

        @Override
        public boolean hasNext() {
            return (currentIndex < arrayItems.length);
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = arrayItems[currentIndex];
            currentIndex++;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

    public static void main(String[] args) {

        int N = 100;
        RandomizedQueue<Integer> deque = new RandomizedQueue<Integer>();
        for (int i = 1; i <= N; i++) {
            deque.enqueue(i);
        }
        for (Integer item1 : deque) {
            System.out.print(item1 + "--->");
            StringBuilder builder = new StringBuilder();
            for (Integer item2 : deque) {
                builder.append(item2.toString() + ", ");
            }
            System.out.println(builder);
        }
        System.out.println("\n");

    }

}

