/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private class Node<Item> {
        public Item data = null;
        public Node<Item> last = null;
        public Node<Item> next = null;

        Node() {
        }
    }

    private final Node<Item> head;
    private final Node<Item> tail;
    private int length = 0;

    // construct an empty deque
    public Deque() {
        this.head = new Node<Item>();
        this.tail = new Node<Item>();

        this.head.next = this.tail;
        this.tail.last = this.head;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.length == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.length;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node<Item> tempFirst = new Node<Item>();
        tempFirst.data = item;
        tempFirst.next = this.head.next;
        tempFirst.last = this.head;

        this.head.next.last = tempFirst;
        this.head.next = tempFirst;

        this.length++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node<Item> tempLast = new Node<Item>();
        tempLast.data = item;
        tempLast.next = this.tail;

        tempLast.last = this.tail.last;
        this.tail.last.next = tempLast;
        this.tail.last = tempLast;

        this.length++;

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Node<Item> tempRemove = this.head.next;
        Item temp;
        temp = tempRemove.data;
        this.head.next = tempRemove.next;
        this.head.next.last = this.head;
        this.length--;
        return temp;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Node<Item> tempRemove = this.tail.last;
        Item temp;
        temp = tempRemove.data;
        this.tail.last = tempRemove.last;
        this.tail.last.next = this.tail;
        this.length--;
        return temp;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current = head.next;

        public boolean hasNext() {
            return current.data != null;
        }

        public Item next() {
            Item item = current.data;
            if (item == null) {
                throw new java.util.NoSuchElementException();
            }
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }


    }


    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addFirst(5);

        int[] randomOrder;
        int iterSize = 3;
        randomOrder = new int[iterSize];
        for (int i = 0; i < iterSize; i++)
            randomOrder[i] = i;
        StdRandom.shuffle(randomOrder, 0, iterSize);
        for (int i = 0; i < iterSize; i++)
            StdOut.println(randomOrder[i]);
    }

}
