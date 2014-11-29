/**
 *
 * @author marco
 */
public class Subset {

    public static void main(String[] args) {
        Integer k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (StdIn.hasNextLine() && !StdIn.isEmpty()) {
           queue.enqueue(StdIn.readString());
        }
        for (int i = 0; i < k; i++) {
          StdOut.println(queue.dequeue());  
        }

    }
}

