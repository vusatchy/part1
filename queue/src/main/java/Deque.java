import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> root;

    private int size = 0;

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return root == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        size++;
        if (root == null) {
            root = new Node<Item>(item);
        } else {
            Node<Item> nextNode = new Node<Item>(item);
            root.prev = nextNode;
            nextNode.next = root;
            root = nextNode;
        }

    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        size++;
        if (root == null) {
            root = new Node<Item>(item);
        } else {
            Node<Item> last = findLast(root);
            Node<Item> newNode = new Node<Item>(item);
            newNode.prev = last;
            last.next = newNode;

        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (root == null) {
            throw new NoSuchElementException();
        } else {
            size--;
            Item value = root.value;
            if (root.next != null) {
                root = root.next;
                root.prev = null;
            } else {
                root = null;
            }
            return value;
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (root == null) {
            throw new NoSuchElementException();
        } else {
            size--;
            Node<Item> last = findLast(root);
            Item value = last.getValue();
            if (last != root) {
                last.prev.next = null;
            } else {
                root = null;
            }
            return value;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }


    private Node<Item> findLast(Node<Item> root) {
        Node<Item> walk = root;
        while (walk.next != null) {
            walk = walk.next;
        }
        return walk;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        /*deque.addLast("1");
        deque.addLast("2");
        deque.addFirst("3");
        deque.addFirst("4");
        Iterator<String> iterator = deque.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println(deque.removeLast());
        System.out.println(deque.removeFirst());*/

    }


    private class Node<T> {

        private T value;

        private Node<T> next;

        private Node<T> prev;

        public Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        public Node(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }
    }

    private class DequeIterator implements Iterator<Item> {

        private Node<Item> current = root;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            Item value = current.getValue();
            current = current.next;
            return value;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}