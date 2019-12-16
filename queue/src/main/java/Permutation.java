import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        for (String value : StdIn.readStrings()) {
            queue.enqueue(value);
        }

        for (int i = 0; i < k; i++) {
            System.out.println(queue.dequeue());
        }
    }
}