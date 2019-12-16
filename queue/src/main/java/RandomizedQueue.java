import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int index = 0;

    private static final int MAX_CAPACCITY = 10;

    private Item[] queue;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[MAX_CAPACCITY];
    }

    // is the randomized queue empty?

    public boolean isEmpty() {
        return index == 0;
    }
    // return the number of items on the randomized queue

    public int size() {
        return index;
    }
    // add the item

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (index == queue.length) {
            resize(calculateNewSize());
        }
        queue[index++] = item;
    }
    // remove and return a random item

    public Item dequeue() {
        if (index == 0) {
            throw new NoSuchElementException();
        }
        if (index == 1) {
            Item value = queue[0];
            queue[0] = null;
            index--;
            return value;
        }
        int randomIndex = randomIndex();
        Item value = queue[randomIndex];
        if (randomIndex == index - 1) {
            queue[randomIndex] = null;
        } else {
            queue[randomIndex] = queue[index - 1];
            queue[index - 1] = null;
        }
        index--;
        return value;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (index == 0) {
            throw new NoSuchElementException();
        }
        if (index == 1) {
            return queue[0];
        }
        return queue[randomIndex()];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private int calculateNewSize() {
        return 2 * queue.length + 1;
    }

    private void resize(int newSize) {
        queue = Arrays.copyOfRange(queue, 0, newSize);
    }

    private int randomIndex() {
        return StdRandom.uniform(0, index);
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int i = 0;
        private Item[] array = Arrays.copyOfRange(queue, 0, index);

        public RandomizedQueueIterator() {
            StdRandom.shuffle(array);
        }

        @Override
        public boolean hasNext() {
            return i < array.length;
        }

        @Override
        public Item next() {
            return array[i++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 1; i <= 12; i++) {
            queue.enqueue(i);
            System.out.println(queue.sample());
        }
        for (Integer value : queue) {
            System.out.println(value);
        }
        for (int i = 1; i <= 12; i++) {
            System.out.println(queue.dequeue());
        }
    }

}