import javax.xml.bind.ValidationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<T> implements Iterable<T> {

    private Node<T> root;

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
    public void addFirst(T item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        size++;
        if (root == null) {
            root = new Node<T>(item);
        } else {
            Node<T> nextNode = new Node<T>(item);
            root.prev = nextNode;
            nextNode.next = root;
            root = nextNode;
        }

    }

    // add the item to the back
    public void addLast(T item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        size++;
        if (root == null) {
            root = new Node<T>(item);
        } else {
            Node<T> last = findLast(root);
            Node<T> newNode = new Node<T>(item);
            newNode.prev = last;
            last.next = newNode;

        }
    }

    // remove and return the item from the front
    public T removeFirst() {
        size--;
        if (root == null) {
            throw new NoSuchElementException();
        } else {
            T value = root.value;
            root = root.next;
            root.prev = null;
            return value;
        }
    }

    // remove and return the item from the back
    public T removeLast() {
        size--;
        if (root == null) {
            throw new NoSuchElementException();
        } else {
            Node<T> last = findLast(root);
            T value = last.getValue();
            last.prev.next = null;
            return value;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<T> iterator() {
        return null;
    }


    private Node<T> findLast(Node<T> root) {
        Node<T> walk = root;
        while (walk.next != null) {
            walk = walk.next;
        }
        return walk;
    }

    private void printAll() {
        Node<T> walk = root;
        while (walk.next != null) {
            System.out.println(walk.value);
            walk = walk.next;
        }
        System.out.println(walk.value);

    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        deque.addLast("1");
        deque.addLast("2");
        deque.addFirst("3");
        deque.addFirst("4");
        deque.printAll();
        System.out.println("--------------------");
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        System.out.println("--------------------");
        deque.printAll();
        System.out.println("--------------------");
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
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
}