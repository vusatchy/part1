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
        if (root == null) {
            throw new NoSuchElementException();
        } else {
            size--;
            T value = root.value;
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
    public T removeLast() {
        if (root == null) {
            throw new NoSuchElementException();
        } else {
            size--;
            Node<T> last = findLast(root);
            T value = last.getValue();
            if (last != root) {
                last.prev.next = null;
            } else {
                root = null;
            }
            return value;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<T> iterator() {
        return new DequeIterator();
    }


    private Node<T> findLast(Node<T> root) {
        Node<T> walk = root;
        while (walk.next != null) {
            walk = walk.next;
        }
        return walk;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        deque.addLast("1");
        deque.addLast("2");
        deque.addFirst("3");
        deque.addFirst("4");
        Iterator<String> iterator = deque.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println(deque.removeLast());
        System.out.println(deque.removeFirst());
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

    private class DequeIterator implements Iterator<T> {

        private Node<T> current = root;

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            T value = current.getValue();
            current = current.next;
            return value;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}