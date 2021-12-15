import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class HeapTester {
    static Heap heap;
    static FibonacciHeap fibonacciHeap;
    static boolean ERROR;


    public static void main(String[] args) {
        System.out.println("Welcome! lets check your Heap!");
        System.out.println();
        emptyTestN(); // check if your tree knows how to be empty

        insertDeleteMinTest(10, 5);
        // insert keys by increasing / decreasing / increasing & decreasing / random order for some reptitions
        //deleting nodes only by delete min

        insertDeleteByNodeTest(10, 5);
        // insert keys by increasing / decreasing / increasing & decreasing / random order for some reptitions
        //deleting nodes only by delete node

        DecreaseNodeTest(10, 5);
        // decreasing all the nodes by two ways: make each node the minimum or by random integer

        counterTest(1000 , 5); // test countersRep without deleteNode or decreaseKey

        //maybe in the future
//        potentialTest(1000);
//        linkTest(1000);
//        cutTest(1000);
    }
    private static void counterTest(int size , int repetitions) {
        if (!ERROR) {
            int cnt = 0;
            System.out.println("checking if your tree handle countersRep()");
            while (cnt < repetitions){
                if (!ERROR) {

            System.out.println("lets just insert!");
            fibonacciHeap = new FibonacciHeap();
            heap = new Heap();
            addKeysRandom(0 , size);
            int[] counterHeap = fibonacciHeap.countersRep();
            int[] answer = {size};

            if ( !Arrays.equals(counterHeap, answer)){
                ERROR = true;
                System.out.println("the array should be "+ Arrays.toString(answer));
                System.out.println("the actual array is " + Arrays.toString(counterHeap));
                break;}

            System.out.println("delete the minimum and check again ");
                fibonacciHeap.deleteMin(); //lets see successive linking
            counterHeap = fibonacciHeap.countersRep();
            answer = getArrayBinary(size-1);

            if ( !Arrays.equals(counterHeap, answer)){
                ERROR = true;
                System.out.println("the array should be "+ Arrays.toString(answer));
                System.out.println("the actual array is " + Arrays.toString(counterHeap));
                break;}

            Random rand = new Random();
            int numIterations = rand.nextInt(size);
            int countIter = 0;
            int currSize = size -1;
            while (countIter < numIterations){

                if (rand.nextFloat() < 0.5){
                    fibonacciHeap.insert(size+countIter+1);
                    answer[0] = answer[0] +1;
                    currSize++;
                    counterHeap = fibonacciHeap.countersRep();
                    if ( !Arrays.equals(counterHeap, answer)){
                        ERROR = true;
                        System.out.println("the array should be "+ Arrays.toString(answer));
                        System.out.println("the actual array is " + Arrays.toString(counterHeap));
                        break;}}
                else{
                    fibonacciHeap.deleteMin();
                    currSize--;
                    counterHeap = fibonacciHeap.countersRep();
                    answer = getArrayBinary(currSize);
                    if ( !Arrays.equals(counterHeap, answer)){
                        ERROR = true;
                        System.out.println("the array should be "+ Arrays.toString(answer));
                        System.out.println("the actual array is " + Arrays.toString(counterHeap));
                        break;}}
                countIter++;
                }
            cnt++; }}
            if (!ERROR){ System.out.println("passed counterTest!");}
    }}

    private static int[] getArrayBinary(int num) {
        String binary = Integer.toString(num , 2);
        int[] arr = new int[binary.length()];

        for (int j=0 ; j < binary.length() ; j++){
            arr[j] = Integer.parseInt(String.valueOf(binary.charAt(j)));
        }
        return arr; }

    private static void DecreaseNodeTest(int size, int numRandomReptitions) {
        String func = "in the function DecreaseNodeTest ";
        int cnt = 0;
        while (cnt < numRandomReptitions){
        if (!ERROR) {
            System.out.println("checking if your tree handle DecreaseNode - making each node the minimum ");
            System.out.println();
            fibonacciHeap = new FibonacciHeap();
            heap = new Heap();
            ArrayList<FibonacciHeap.HeapNode> nodes = addKeysRandom(0, size);
            Collections.shuffle(nodes);
            decreaseAndCheck(nodes ,false , size );}

        if (!ERROR) {
            System.out.println("checking if your tree handle DecreaseNode - Random decrease ");
            System.out.println();
            fibonacciHeap = new FibonacciHeap();
            heap = new Heap();
            ArrayList<FibonacciHeap.HeapNode> nodes = addKeysRandom(0, size);
            Collections.shuffle(nodes);
            decreaseAndCheck(nodes ,true , size );}
        cnt++;}
        if (!ERROR){ System.out.println("passed counterTest!");}
    }


    private static void decreaseAndCheck(ArrayList<FibonacciHeap.HeapNode> nodes, boolean random, int size) {
        Random rand = new Random();
        if (!random){
            for (FibonacciHeap.HeapNode node: nodes) {
                decrease(node , size +1);
                checkMin();
            }
            cleanAndCheckMin("in the function DecreaseNodeTest in making each node the minimum  ");
        }
        else {
            for (FibonacciHeap.HeapNode node: nodes) {
                int delta = rand.nextInt();
                decrease(node , delta);
                checkMin();
            }
            cleanAndCheckMin("in the function DecreaseNodeTest in Random delta case");
        }
        }






    private static void checkMin() {
        if (heap.findMin() != fibonacciHeap.findMin().getKey()){
            ERROR = true;
            System.out.println(" the minimum should be " + heap.findMin() +"and the actual minimum is " + fibonacciHeap.findMin());}

        if (heap.size() != fibonacciHeap.size()) {
            System.out.println(" the size should be " + heap.size() +"and the actual size is " + fibonacciHeap.size());}
    }

    private static void decrease(FibonacciHeap.HeapNode node, int delta) {
        int key = node.getKey();
        heap.delete(key);
        heap.insert(key-delta);
        fibonacciHeap.decreaseKey(node , delta);}


    private static void insertDeleteByNodeTest(int size ,int numRandomReptitions) {
        String func = "in the function insertDeleteByNodeTest ";
        if (!ERROR) {
            System.out.println("checking if your tree handle insertion & deleteNode ");
            System.out.println();

            System.out.println("checking increasing order ");

            fibonacciHeap = new FibonacciHeap();
            heap = new Heap();
            ArrayList<FibonacciHeap.HeapNode> nodes = addKeys(0, size);
            Collections.shuffle(nodes);
            cleanAndCheckNodes(nodes ,func + "Error in checking increasing order "); // take your heap and delete minimum till the heap is empty
        }
        if (!ERROR) {
            System.out.println("checking decreasing order ");
            fibonacciHeap = new FibonacciHeap();
            heap = new Heap();
            ArrayList<FibonacciHeap.HeapNode> nodes = addKeysReverse(0, size);
            Collections.shuffle(nodes);
            cleanAndCheckNodes(nodes ,func + "Error in checking decreasing order "); // take your heap and delete minimum till the heap is empty
        }
        if (!ERROR) {
            System.out.println("checking increasing & decreasing  order ");
            fibonacciHeap = new FibonacciHeap();
            heap = new Heap();
            ArrayList<FibonacciHeap.HeapNode> nodes = addKeys(0, size);
            ArrayList<FibonacciHeap.HeapNode> nodes2 = addKeysReverse(2*size, size);
            nodes.addAll(nodes2);
            Collections.shuffle(nodes);
            cleanAndCheckNodes(nodes ,func + "Error in checking increasing & decreasing order "); // take your heap and delete minimum till the heap is empty
        }
        if (!ERROR) {
            System.out.println("checking Random order ");
            int cnt = 0;
            while (cnt < numRandomReptitions){
                if (!ERROR) {
                    fibonacciHeap = new FibonacciHeap();
                    heap = new Heap();
                    ArrayList<FibonacciHeap.HeapNode> nodes = addKeysRandom(0, size);
                    Collections.shuffle(nodes);
                    cleanAndCheckNodes(nodes, func + "Error in checking Random order "); // take your heap and delete minimum till the heap is empty
                }
                cnt++;
            }}
        if (!ERROR){ System.out.println("passed insertDeleteByNodeTest!");}
    }

    private static void cleanAndCheckNodes(ArrayList<FibonacciHeap.HeapNode> nodes, String message) {
        while (!heap.isEmpty()) {
            if(!ERROR){
            if (heap.findMin() != fibonacciHeap.findMin().getKey()){
                ERROR = true;
                System.out.println(message);
                System.out.println(" the minimum should be " + heap.findMin() +" and the actual minimum is " + fibonacciHeap.findMin().getKey());}

            if (heap.size() != fibonacciHeap.size()) {
                System.out.println(message);
                System.out.println(" the size should be " + heap.size() +" and the actual size is " + fibonacciHeap.size());}
            FibonacciHeap.HeapNode tmp = nodes.get(0);
            nodes.remove(0);
            heap.delete(tmp.getKey());
            fibonacciHeap.delete(tmp);}}

        emptyTest(fibonacciHeap , message);
    }





    private static void insertDeleteMinTest(int size ,int numRandomReptitions) {
        String func = "in the function insertDeleteMinTest ";
        if (!ERROR) {
            System.out.println("checking if your tree handle insertion & deleteMin() ");
            System.out.println();
            System.out.println("checking increasing order ");

            fibonacciHeap = new FibonacciHeap();
            heap = new Heap();
            addKeys(0, size);
            cleanAndCheckMin(func + "Error in checking increasing order "); // take your heap and delete minimum till the heap is empty
        }
        if (!ERROR) {
            System.out.println("checking decreasing order ");
            fibonacciHeap = new FibonacciHeap();
            heap = new Heap();
            addKeysReverse(0, size);
            cleanAndCheckMin(func + "Error in checking decreasing order "); // take your heap and delete minimum till the heap is empty
        }
        if (!ERROR) {
            System.out.println("checking increasing & decreasing  order ");
            fibonacciHeap = new FibonacciHeap();
            heap = new Heap();
            addKeys(0, size);
            addKeysReverse(2*size, size);
            cleanAndCheckMin(func + "Error in checking increasing & decreasing order "); // take your heap and delete minimum till the heap is empty
        }
        if (!ERROR) {
            System.out.println("checking Random order ");
            int cnt = 0;
            while (cnt < numRandomReptitions){
                if (!ERROR) {
                    fibonacciHeap = new FibonacciHeap();
                    heap = new Heap();
                    addKeysRandom(0, size);
                    cleanAndCheckMin(func + "Error in checking Random order "); // take your heap and delete minimum till the heap is empty
                }
                cnt++;
            }}
        if (!ERROR){ System.out.println("passed insertDeleteMinTest!");}
    }






    private static void cleanAndCheckMin(String message) {
        while (!heap.isEmpty()) {
            if(!ERROR){
            if (heap.findMin() != fibonacciHeap.findMin().getKey()){
                ERROR = true;
                System.out.println(message);
                System.out.println(" the minimum should be " + heap.findMin() +" and the actual minimum is " + fibonacciHeap.findMin().getKey());}

             if (heap.size() != fibonacciHeap.size()) {
                 System.out.println(message);
                 System.out.println(" the size should be " + heap.size() +" and the actual size is " + fibonacciHeap.size());}

            heap.deleteMin();
            fibonacciHeap.deleteMin();}}

        emptyTest(fibonacciHeap , message);
    }




    private static void emptyTestN() {
        if (!ERROR){
            System.out.println("lets check if your heap knows how to be empty");
            }
            fibonacciHeap = new FibonacciHeap();
            emptyTest(fibonacciHeap , "");
            if (!ERROR){
                System.out.println("passed emptyTestN!");
            }


    }

    private static void emptyTest(FibonacciHeap fibo , String message) {
        if (!ERROR){
        if (!fibo.isEmpty()){
            System.out.println(message);
            ERROR = true;
            System.out.println("your heap should be empty");}
        if (fibo.findMin() != null){
            System.out.println(message);
            ERROR = true;
            System.out.println("the minimum should be null - your heap should be empty");}
        if (fibo.size() != 0){
            System.out.println(message);
            ERROR = true;
            System.out.println("the size should be 0 - your heap should be empty");}
        if (!Arrays.equals(fibo.countersRep(), new int[0])){
            System.out.println(message);
            ERROR = true;
            System.out.println("in counterRep the array should be empty array ");}
        if (fibo.potential() != 0){
            System.out.println(message);
            ERROR = true;
            System.out.println("the potential should be 0 - your heap should be empty");}
        }}




    static ArrayList<FibonacciHeap.HeapNode> addKeys(int start , int size) {
        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList<>();
        for (int i = 0; i < size; i++) {//@@@@@@@ i<1000 @@@@@
            heap.insert(start + i);
            nodes.add(fibonacciHeap.insert(start + i));}
        return nodes;
    }

    static ArrayList<FibonacciHeap.HeapNode> addKeysReverse(int start , int size) {
        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList<>();
        for (int i = size; i >= 0; i--) {
            heap.insert(start + i);
            nodes.add(fibonacciHeap.insert(start + i));}
        return nodes;
    }

    private static ArrayList<FibonacciHeap.HeapNode> addKeysRandom(int start, int size) {
        ArrayList<Integer> random = makeRandomKeys(start , size);
        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList<>();
        for (int i: random) {
            heap.insert(i);
            nodes.add(fibonacciHeap.insert(i));}
        return nodes;
    }




    private static ArrayList<Integer> makeRandomKeys(int start, int size) { // making a random list of keys
        ArrayList<Integer> numToInsert = new ArrayList<>();
        for (int i = start; i < start+size ; i++) {                 // insert all the numbers in to array-list
            numToInsert.add(i + 1);
        }
        Collections.shuffle(numToInsert); //make random order
        return numToInsert;}

}
