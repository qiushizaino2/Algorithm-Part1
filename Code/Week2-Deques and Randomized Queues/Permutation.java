/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> client = new RandomizedQueue<String>();

        int inputNums = 0;
        while (!StdIn.isEmpty()) {
            String n = StdIn.readString();
            if (inputNums < k) {
                client.enqueue(n);
            }
            else {
                int sampItem = StdRandom.uniform(inputNums + 1);
                if (sampItem < k) {
                    client.dequeue();
                    client.enqueue(n);
                }
            }
            inputNums++;
        }
        Iterator<String> testIter = client.iterator();
        while (testIter.hasNext()) {
            StdOut.println(testIter.next());
        }
    }
}
