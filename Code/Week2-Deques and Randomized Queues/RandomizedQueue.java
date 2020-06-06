/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

//
// public class RandomizedQueue<Item> implements Iterable<Item> {
//     private class Node<Item> {
//         public Item data = null;
//         public Node<Item> next = null;
//
//         Node() {
//         }
//     }
//
//     private int length = 0;
//     private Node<Item> head;
//
//     // construct an empty randomized queue
//     public RandomizedQueue() {
//         head = new Node<Item>();
//     }
//
//     // is the randomized queue empty?
//     public boolean isEmpty() {
//         return (length == 0);
//     }
//
//     // return the number of items on the randomized queue
//     public int size() {
//         return length;
//     }
//
//     // add the item
//     public void enqueue(Item item) {
//         if (item == null) {
//             throw new IllegalArgumentException();
//         }
//         Node<Item> temp = new Node<Item>();
//         temp.data = item;
//         temp.next = this.head.next;
//         this.head.next = temp;
//         length++;
//     }
//
//     // remove and return a random item
//     public Item dequeue() {
//         if (isEmpty()) {
//             throw new java.util.NoSuchElementException();
//         }
//         int removeItem = StdRandom.uniform(length);
//         int k = 0;
//         Node<Item> tempBefore = head;
//         while (k != removeItem) {
//             tempBefore = tempBefore.next;
//             k++;
//         }
//         Node<Item> temp = tempBefore.next;
//         Item retItem = temp.data;
//         tempBefore.next = tempBefore.next.next;
//         length--;
//         return retItem;
//
//     }
//
//     // return a random item (but do not remove it)
//     public Item sample() {
//         if (isEmpty()) {
//             throw new java.util.NoSuchElementException();
//         }
//         int sampItem = StdRandom.uniform(length);
//         int k = 0;
//         Node<Item> temp = head.next;
//         while (k != sampItem) {
//             temp = temp.next;
//             k++;
//         }
//         return temp.data;
//     }
//
//     // return an independent iterator over items in random order
//     public Iterator<Item> iterator() {
//         return new RandomizedQueueIterator();
//     }
//
//     private class RandomizedQueueIterator implements Iterator<Item> {
//         private Node<Item> current;
//
//         public RandomizedQueueIterator() {
//             Node<Item> iterHead;
//             int iterSize = length;
//             iterHead = new Node<Item>();
//             if (iterSize != 0) {
//                 int[] randomOrder = new int[iterSize];
//                 for (int i = 0; i < iterSize; i++)
//                     randomOrder[i] = i;
//                 StdRandom.shuffle(randomOrder, 0, iterSize);
//                 for (int i = 0; i < iterSize; i++) {
//                     int j = 0;
//                     Node<Item> temp = head.next;
//                     while (j != randomOrder[i]) {
//                         temp = temp.next;
//                         j++;
//                     }
//                     Node<Item> tempIter = new Node<Item>();
//                     tempIter.data = temp.data;
//                     tempIter.next = iterHead.next;
//                     iterHead.next = tempIter;
//                 }
//             }
//             current = iterHead.next;
//         }
//
//         public boolean hasNext() {
//             return current != null;
//         }
//
//         public Item next() {
//
//             if (current == null) {
//                 throw new java.util.NoSuchElementException();
//             }
//             Item item = current.data;
//             if (item == null) {
//                 throw new java.util.NoSuchElementException();
//             }
//             current = current.next;
//             return item;
//         }
//
//         public void remove() {
//             throw new UnsupportedOperationException();
//         }
//     }
//
//     // unit testing (required)
//     public static void main(String[] args) {
//         RandomizedQueue<Integer> testObject = new RandomizedQueue<Integer>();
//         StdOut.println("Empty & Size testing.");
//         if (testObject.isEmpty())
//             StdOut.println("Object is empty.");
//         StdOut.printf("Obeject size is %d.\n", testObject.size());
//
//
//         StdOut.println("Enqueue & Sampling testing.");
//         testObject.enqueue(1);
//         testObject.enqueue(3);
//         testObject.enqueue(5);
//         if (testObject.isEmpty())
//             StdOut.println("Object is empty.");
//         else
//             StdOut.println("Object is not empty.");
//
//         StdOut.printf("Obeject size is %d.\n", testObject.size());
//
//         StdOut.printf("Obeject sample result: %d,%d,%d,%d,%d.", testObject.sample(),
//                       testObject.sample(), testObject.sample(), testObject.sample(),
//                       testObject.sample());
//
//
//         StdOut.println("Dequeue & Sampling testing.");
//         testObject.dequeue();
//         testObject.dequeue();
//         if (testObject.isEmpty())
//             StdOut.println("Object is empty.");
//         else
//             StdOut.println("Object is not empty.");
//
//         StdOut.printf("Obeject size is %d.\n", testObject.size());
//
//         StdOut.printf("Obeject sample result: %d,%d,%d,%d,%d.\n", testObject.sample(),
//                       testObject.sample(), testObject.sample(), testObject.sample(),
//                       testObject.sample());
//
//
//         StdOut.println("iterator testing.");
//         testObject.enqueue(2);
//         testObject.enqueue(4);
//         Iterator<Integer> testIter = testObject.iterator();
//         while (testIter.hasNext()) {
//             StdOut.println(testIter.next());
//         }
//     }
//
// }


import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {


    private int length = 0;
    private Item[] data;
    private int capacity = 1;

    // construct an empty randomized queue
    public RandomizedQueue() {
        data = (Item[]) new Object[capacity];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (length == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return length;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        this.data[length] = item;
        length++;
        if (length == capacity) {
            capacity *= 2;
            Item[] temp = (Item[]) new Object[capacity];
            for (int i = 0; i < length; i++) {
                temp[i] = data[i];
            }
            data = temp;
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        int k = StdRandom.uniform(length);
        Item res = data[k];
        data[k] = data[length - 1];
        data[length - 1] = null;
        length--;

        if (length == capacity / 4) {
            capacity /= 2;
            Item[] temp = (Item[]) new Object[capacity];
            for (int i = 0; i < length; i++) {
                temp[i] = data[i];
            }
            data = temp;
        }

        return res;

    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int sampItem = StdRandom.uniform(length);
        return data[sampItem];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final int lengthIter = length;
        private int lengthCurrent = 0;
        private final Item[] dataIter;

        public RandomizedQueueIterator() {
            dataIter = (Item[]) new Object[lengthIter];
            if (lengthIter != 0) {
                int[] randomOrder = new int[lengthIter];
                for (int i = 0; i < lengthIter; i++)
                    randomOrder[i] = i;
                StdRandom.shuffle(randomOrder, 0, length);
                for (int i = 0; i < lengthIter; i++)
                    dataIter[i] = data[randomOrder[i]];
            }

        }

        public boolean hasNext() {
            return lengthCurrent != lengthIter;
        }

        public Item next() {
            if (lengthCurrent == lengthIter) {
                throw new java.util.NoSuchElementException();
            }
            Item item = dataIter[lengthCurrent];
            if (item == null) {
                throw new java.util.NoSuchElementException();
            }
            lengthCurrent++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }


    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> testObject = new RandomizedQueue<Integer>();
        StdOut.println("Empty & Size testing.");
        if (testObject.isEmpty())
            StdOut.println("Object is empty.");
        StdOut.printf("Obeject size is %d.\n", testObject.size());


        StdOut.println("Enqueue & Sampling testing.");
        testObject.enqueue(1);
        testObject.enqueue(3);
        testObject.enqueue(5);
        if (testObject.isEmpty())
            StdOut.println("Object is empty.");
        else
            StdOut.println("Object is not empty.");

        StdOut.printf("Obeject size is %d.\n", testObject.size());

        StdOut.printf("Obeject sample result: %d,%d,%d,%d,%d.\n", testObject.sample(),
                      testObject.sample(), testObject.sample(), testObject.sample(),
                      testObject.sample());


        StdOut.println("Dequeue & Sampling testing.");
        testObject.dequeue();
        testObject.dequeue();
        if (testObject.isEmpty())
            StdOut.println("Object is empty.");
        else
            StdOut.println("Object is not empty.");

        StdOut.printf("Obeject size is %d.\n", testObject.size());

        StdOut.printf("Obeject sample result: %d,%d,%d,%d,%d.\n", testObject.sample(),
                      testObject.sample(), testObject.sample(), testObject.sample(),
                      testObject.sample());


        StdOut.println("iterator testing.");
        testObject.enqueue(2);
        testObject.enqueue(4);
        Iterator<Integer> testIter = testObject.iterator();
        while (testIter.hasNext()) {
            StdOut.println(testIter.next());
        }
    }

}
