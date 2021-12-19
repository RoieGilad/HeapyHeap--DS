public class theoritic {

    public static void main(String[] args) {
        questionOne(false , 0);
//        questionTwo();
    }

    private static void questionTwo() {
        int[] arr = {6,8,10,12,14};
        for (int i : arr){
            System.out.println();
            int m = (int) Math.pow(3,i)-1;
            System.out.println("i is now " + i);
            System.out.println("m is now "+ m);
            int cuts = FibonacciHeap.totalCuts();
            int Links = FibonacciHeap.totalLinks();
            long startTime = System.currentTimeMillis();

            FibonacciHeap heap = new FibonacciHeap();
            for (int k = m ; k >= 0 ; k--){
                heap.insert(k);}

            int numDelete = (3*m)/4;
            System.out.println("making deleteMin " + numDelete + " times");

            for (int j = 0 ; j < numDelete ; j++){
                heap.deleteMin();}

            long endTime = System.currentTimeMillis();
            System.out.println("the total time in mS is "+ (endTime-startTime));
            cuts = FibonacciHeap.totalCuts() - cuts;
            Links = FibonacciHeap.totalLinks() - Links;
            System.out.println("the total links is " + Links);
            System.out.println("the total cut is " + cuts);
            System.out.println("the potential is " + heap.potential());
            }
        }


    private static void questionOne( boolean print , int questionNUm) {
        System.out.println("question 1:");
        System.out.println();

        for (int power = 10 ; power <= 25 ; power+=5){
            int cuts = FibonacciHeap.totalCuts();
            int Links = FibonacciHeap.totalLinks();

            int m = (int) Math.pow(2,power);
            FibonacciHeap heap = new FibonacciHeap();
            System.out.println("the power is "+ power +" ,and m is "+m);

        FibonacciHeap.HeapNode[] nodes = new FibonacciHeap.HeapNode[m+1];
        long startTime = System.currentTimeMillis();
            for(int k = m-1 ; k >= -1 ; k--){
            nodes[k+1] = heap.insert(k);}
        if (questionNUm != 5){
            heap.deleteMin();

            if (print){
            System.out.println("insert" +(m-1) +" , "+ (m-2)+" ... "+" 0 ,"+" -1");
            System.out.println("delete min = -1");}}

        int i = (int)( Math.log(m)/Math.log(2));
        while (i >=1){
            int tmp = m-((int)Math.pow(2,i))+1;

            if(questionNUm != 4){
                heap.decreaseKey(nodes[tmp] , m+1);}

            if (questionNUm == 4){
                 tmp = m-((int)Math.pow(2,i));
                heap.decreaseKey(nodes[tmp] , m+1);}

            if (print){
            System.out.println("decreased the node with the key "+(tmp) + "by delta of "+(m-1));
            System.out.println("the new key is "+((tmp)-(m-1)));}

            i = (int) i/2;
        }
        if (questionNUm == 6){
            heap.decreaseKey(nodes[m-2+1],m+1 );
            System.out.println("in question 1.6 made decreaseKey of "+ (m-2) + " by delta of "+ (m+1));}

        long endTime = System.currentTimeMillis();
        System.out.println("the total time in mS is "+ (endTime-startTime));
        cuts = FibonacciHeap.totalCuts() - cuts;
        Links = FibonacciHeap.totalLinks() - Links;
        System.out.println("the total links is " + Links);
        System.out.println("the total cut is " + cuts);
        System.out.println("the potential is " + heap.potential());
    }
    }






}
