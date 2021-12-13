/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap
{
    private int size;
    private int numTree;
    private int numMarked;
    private static int Links;
    private static int cuts;
    private HeapNode start;
    private HeapNode tail;
    private HeapNode min;

    //added for the meld
    public HeapNode getStart(){
        return this.start;
    }

    private void insertBefore(HeapNode newNode , HeapNode position){ // put newNode before position;

    }

    private void addAtStart(HeapNode node){
        HeapNode tmp = this.start; //save the first tree
        this.start = node; //set the node to be the first
        node.next = tmp;
        tmp.prev = node;
        //TODO CIRCLE CONNECTION
        if (tmp == null){ //if this node is the only one (do we need it?) //TODO CONDITION SHOULD BE EMPTY
            this.tail = node;
        }
    }

    //
    private void addAtTail(HeapNode node){
        this.tail.next = node; //attach the node to the last one
        node.prev = this.tail;
        this.tail = node;
        //TODO CIRCLE CONNECTION
        if (node.prev == null){ //need?? //TODO CONDITION SHOULD BE EMPTY
            this.start = node;
        }
    }

    private HeapNode  link(HeapNode node1 , HeapNode node2){
        if (node1.getKey() > node2.getKey()) { // always node1 < node2
            HeapNode tmp = node1;
            node1 = node2;
            node2 = tmp;
        }
        if (node1.getRank() > 0) { //attaching the children
            insertBefore(node2, node1.getChild());
        } else {
            insertBefore(node2, node2); //like this? to check
        }
        node1.setChild(node2);
        node2.setParent(node1);
        node1.setRank(node1.getRank()+1); //node1.rank ++; // do we need a func setrank?
        return node1; //return the new root
    }

    private void putFamily(HeapNode firstNode , HeapNode position ){

        
    }



   /**
    * public boolean isEmpty()
    *
    * Returns true if and only if the heap is empty.
    *   
    */
    public boolean isEmpty()
    {
    	return this.size() ==0; // should be replaced by student code
    }
		
   /**
    * public HeapNode insert(int key)
    *
    * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
    * The added key is assumed not to already belong to the heap.  
    * 
    * Returns the newly created node.
    */
    public HeapNode insert(int key){
    	HeapNode node = new HeapNode(key);
        this.addAtStart(node);
        this.size ++;
        if (isEmpty() || key<this.min.getKey()){
            this.min = node;
        }
        //addAtStart();
        // size+1
        // check change min

        return node; //why return?

    }
   /**
    * public void deleteMin()
    *
    * Deletes the node containing the minimum key.
    *
    */
    public void deleteMin(){
        this.putFamily(this.min); // delete min
        this.size -= 1; // down size
        this.consolidation(); // successive linking
    }

    private void consolidation(){
        HeapNode curr = this.start;
        HeapNode tmp = null;
        HeapNode[] basket = new HeapNode[2*((int)Math.log(this.size())+1)]; // make array of size O(logn) ---> size = 2*logn low value
        while (tmp != this.start){ //interate over childs and make links
             tmp = curr.getNext();
             linkAndPut(curr ,basket);
             curr = tmp;
        }
        boolean first = false;
        int min = 0;
        HeapNode currMin = null;
        for (HeapNode node : basket ) { // iterate over array and fix prev next and start & tail

            if (node != null) {
                node.setParent(null);    // make sure all parent fields of root is null

                if (!first) {
                    currMin = node;
                    min = currMin.getKey();
                    this.addAtStart(node);
                    first = true;
                }
                else {
                    if (node.getKey() < min){
                        min = node.getKey();
                        currMin = node;}
                    }
                    this.addAtTail(node);
                }}
        this.min = currMin;
        }

    private void linkAndPut(HeapNode node  , HeapNode[] basket ){
        int i = node.getRank();

        if (basket[i] == null){ //just put in the basket
            basket[i] = node;}

        else{ // there is two heaps in the same rank ---> link together
            HeapNode newRoot = link(node , basket[i]);
            basket[i] = null;
            linkAndPut(newRoot , basket);
               }

    }






   /**
    * public HeapNode findMin()
    *
    * Returns the node of the heap whose key is minimal, or null if the heap is empty.
    *
    */
    public HeapNode findMin()
    {
    	return this.isEmpty() ? null: this.min;// should be replaced by student code
    } 
    
   /**
    * public void meld (FibonacciHeap heap2)
    *
    * Melds heap2 with the current heap.
    *
    */
    public void meld (FibonacciHeap heap2)
    {
        link(this.tail, heap2.getStart());
        this.size += heap2.size();
    	  return; // should be replaced by student code   		
    }

   /**
    * public int size()
    *
    * Returns the number of elements in the heap.
    *   
    */
    public int size()
    {
    	return this.size; // should be replaced by student code
    }
    	
    /**
    * public int[] countersRep()
    *
    * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
    * Note: The size of of the array depends on the maximum order of a tree, and an empty heap returns an empty array.
    * 
    */
    public int[] countersRep()
    {
    	int[] arr = new int[100];
        return arr; //	 to be replaced by student code
    }
	
   /**
    * public void delete(HeapNode x)
    *
    * Deletes the node x from the heap.
	* It is assumed that x indeed belongs to the heap.
    *
    */
    public void delete(HeapNode x) 
    {    
    	return; // should be replaced by student code
    }

   /**
    * public void decreaseKey(HeapNode x, int delta)
    *
    * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
    * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
    */
    public void decreaseKey(HeapNode x, int delta)
    {    
    	return; // should be replaced by student code
    }

   /**
    * public int potential() 
    *
    * This function returns the current potential of the heap, which is:
    * Potential = #trees + 2*#marked
    * 
    * In words: The potential equals to the number of trees in the heap
    * plus twice the number of marked nodes in the heap. 
    */
    public int potential() 
    {    
    	return -234; // should be replaced by student code
    }

   /**
    * public static int totalLinks() 
    *
    * This static function returns the total number of link operations made during the
    * run-time of the program. A link operation is the operation which gets as input two
    * trees of the same rank, and generates a tree of rank bigger by one, by hanging the
    * tree which has larger value in its root under the other tree.
    */
    public static int totalLinks()
    {    
    	return -345; // should be replaced by student code
    }

   /**
    * public static int totalCuts() 
    *
    * This static function returns the total number of cut operations made during the
    * run-time of the program. A cut operation is the operation which disconnects a subtree
    * from its parent (during decreaseKey/delete methods). 
    */
    public static int totalCuts()
    {    
    	return -456; // should be replaced by student code
    }

     /**
    * public static int[] kMin(FibonacciHeap H, int k) 
    *
    * This static function returns the k smallest elements in a Fibonacci heap that contains a single tree.
    * The function should run in O(k*deg(H)). (deg(H) is the degree of the only tree in H.)
    *  
    * ###CRITICAL### : you are NOT allowed to change H. 
    */
    public static int[] kMin(FibonacciHeap H, int k)
    {    
        int[] arr = new int[100];
        return arr; // should be replaced by student code
    }
    
   /**
    * public class HeapNode
    * 
    * If you wish to implement classes other than FibonacciHeap
    * (for example HeapNode), do it in this file, not in another file. 
    *  
    */
    public static class HeapNode{
        //TODO WHY PUBLIC
       public HeapNode child;
       public HeapNode parent;
       public HeapNode prev;
       public HeapNode next;
       public boolean marked;
       public int key;
       public int rank;

       public HeapNode(int key) {
           this.key = key;
       }

       public void setChild(HeapNode node){
           this.child = node;
       }
       public HeapNode getChild(){
           return this.child;
       }

       public void setParent(HeapNode node){
           this.parent = node;
       }
       public HeapNode getParent(){
           return this.parent;
       }

       public void setNext(HeapNode node){
           this.next = node;
       }
       public HeapNode getNext(){
           return this.next;
       }

       public void setPrev(HeapNode node){
           this.prev = node;
       }
       public HeapNode getPrev(){
           return this.prev;
       }

       public void mark(){
           this.marked = true;
       }

       public void setRank(int k){
           this.rank = k;
       }
       public int getRank(){
           return this.rank;
       }



    	public int getKey() {
    		return this.key;
    	}
    }
}
