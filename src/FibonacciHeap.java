/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap{
    private int size;
    private int numTree;
    private int numMarked;
    private static int Links;
    private static int cuts;
    private HeapNode start;
    private HeapNode tail;
    private HeapNode min;


    public HeapNode getStart(){ return this.start;} //return the first root in the heap

    public HeapNode getTail() { return this.tail; } //return the last root in the heap

    private void insertBefore(HeapNode newNode , HeapNode position){ // put newNode before position;

        if (position == newNode){ // make a circle
            newNode.setNext(newNode);
            newNode.setPrev(newNode);}

        else{
        HeapNode tmp = position.getPrev();
        //connect tmp with newNode
        tmp.setNext(newNode);
        newNode.setPrev(tmp);
        //connect position with newNode
        position.setPrev(newNode);
        newNode.setNext(position);
    }}

   private void addAt(HeapNode node){ // adding a node between tail and start
       if (this.isEmpty()){ // if empty make a circle of size 1
           insertBefore(node , node);
           this.start = node;
           this.tail = node;}

       else {
           insertBefore(node, this.start);}

   }

    private void addAtStart(HeapNode node){ // adding a node before start and make him first
        addAt(node);
        this.start = node;}



    private void addAtTail(HeapNode node){  // adding a node after tail and make him tail
       addAt(node);
       this.tail = node;}



    private HeapNode  link(HeapNode node1 , HeapNode node2){
        Links++;
        this.numTree--;

        if (node1.getKey() > node2.getKey()) { // always node1 < node2
            HeapNode tmp = node1;
            node1 = node2;
            node2 = tmp;
        }
        if (node1.getRank() > 0) { //attaching the children
            insertBefore(node2, node1.getChild());}

        else {
            insertBefore(node2, node2); //like this? to check
        }
        node1.setChild(node2);
        node2.setParent(node1);
        node1.setRank(node1.getRank()+1); //node1.rank ++
        return node1; //return the new root
    }

    private void putFamily(HeapNode prev , HeapNode next ,HeapNode start ){ // push a bunch of linked nodes between prev and next

        HeapNode lastChild = start.getPrev(); // find the last node in linked list of start

        //connect start & prev
        prev.setNext(start);
        start.setPrev(prev);

        // connect lastChild & next
        lastChild.setNext(next);
        next.setPrev(lastChild);

        if (this.start == next){ // if there is a new tail -> update
            this.tail = lastChild;}

        if (this.tail == prev){ // if there is a new start -> update
            this.start = start; }
    }






   /**
    * public boolean isEmpty()
    *
    * Returns true if and only if the heap is empty.
    *   
    */
    public boolean isEmpty(){	return this.start == null;    }    // the heap is empty iff start = null

   /**
    * public HeapNode insert(int key)
    *
    * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
    * The added key is assumed not to already belong to the heap.  
    * 
    * Returns the newly created node.
    */
   public HeapNode insert(int key) {
       HeapNode tmp = new HeapNode(key);
       return this.insert(tmp);
   }

    private void insert(int key, HeapNode origin) {
        HeapNode tmp = new HeapNode(key, origin);
        this.insert(tmp);
    }

    private HeapNode insert(HeapNode node) {
        if (this.isEmpty() || node.getKey() < this.min.getKey()) {
            this.min = node;
        }
        this.addAtStart(node);
        this.size++;
        this.numTree++;


        return node;
    }
   /**
    * public void deleteMin()
    *
    * Deletes the node containing the minimum key.
    *
    */
    public void deleteMin() {

        if (!this.isEmpty()) {

            deleteMinFunc(); //disconnect min node from the heap by replacing him by his children.
            this.size -= 1; // down size
            this.consolidation(); // successive linking and searching for new min while linking
            }}

    private void deleteMinFunc() { //disconnect min node from the heap by replacing him by his children.
        if (numTree == 1) { //only one tree in  the heap;

            if (this.min.getChild() == null) { // no children :(
                this.start = null;
                this.tail = null;
            }
            else { // just make the childrens roots
                HeapNode tmp = this.min.getChild();
                this.start = tmp;
                this.tail = tmp.getPrev();}}


        else{ // at least two trees in the heap
            // updating the start tail if needed:
            if (this.start == this.min){
                this.start = this.min.getNext();
            }
            if (this.tail == this.min){
                this.tail = this.min.getPrev();
            }


            if (this.min.getChild() == null) { // no children :(
                this.min.getPrev().setNext(this.min.getNext());
                this.min.getNext().setPrev(this.min.getPrev());
            }
            else {  // replace min with his children in the trees list
                this.putFamily(this.min.getPrev(), this.min.getNext(), this.min.getChild());
            }
        }
    }



    private void consolidation () { // // successive linking and searching for new min while linking
        HeapNode curr = this.start;
        HeapNode tmp = null;
        HeapNode[] basket = new HeapNode[2 * ((int) Math.log(this.size()) + 1)]; // make array of size O(logn) ---> size = 2*logn low value
        while (tmp != this.start) { //interate over childs and make links
            tmp = curr.getNext();
            curr.setParent(null); //to check
            makeUnmarked(curr); // to check
            linkAndPut(curr, basket);
            curr = tmp;
        }
        fixListOfRoots( basket ); //make the array a linked list of all the trees in the heap
    }



    private void  fixListOfRoots(HeapNode[] basket){ //make the array a linked list of all the trees in the heap & update all the necessary fields
            boolean first = false;
            int min = 0;
            numTree = 0; // count again how many trees in the heap of successive linking
            HeapNode currMin = null;
            this.start = null;
            this.tail = null;

            for (HeapNode node : basket) { // iterate over array and fix prev next and start & tail

                if (node != null) {
                    numTree++;
//                    node.setParent(null);    // make sure all parent fields of root is null
//                    this.makeUnmarked(node); //make sure all roots are unmarked

                    if (!first) {
                        currMin = node;
                        min = currMin.getKey();
                        this.start = node;
                       this.addAtStart(node);
                        first = true;}

                     else {
                        if (node.getKey() < min) {
                            min = node.getKey();
                            currMin = node;}

                        this.addAtTail(node);
                        }

                }}
            this.min = currMin;
    }


        private void linkAndPut(HeapNode node, HeapNode[]basket ){
            int i = node.getRank();

            if (basket[i] == null) { //just put in the basket
                basket[i] = node;
            } else { // there is two heaps in the same rank ---> link together
                HeapNode newRoot = link(node, basket[i]);
                basket[i] = null;
                linkAndPut(newRoot, basket);
            }

        }


        /**
         * public HeapNode findMin()
         *
         * Returns the node of the heap whose key is minimal, or null if the heap is empty.
         *
         */
        public HeapNode findMin () { return this.isEmpty() ? null : this.min;}

        /**
         * public void meld (FibonacciHeap heap2)
         *
         * Melds heap2 with the current heap.
         *
         */
        public void meld (FibonacciHeap heap2){

            if (this.isEmpty()){        //just push the roots list of heap2
                this.start = heap2.getStart();
                this.tail = heap2.getTail();}

            if (! ( this.isEmpty() ) ){     // push root list of heap2 after this.tail
                this.putFamily(this.tail , this.start , heap2.getStart());}

            // updating all fields
            size += heap2.size();
            numTree += heap2.getNumTrees();
            numMarked += heap2.getNumMarked();
        }


        public int getNumTrees(){ return this.numTree;} //return the number of tree in the heap

        public int getNumMarked(){ return this.numMarked;} // return the number of marked nodes in the heap

        private void makeUnmarked(HeapNode node){ //make a node unmarked and update num of nodes marked is necessary
            boolean marked = node.getMark();
            node.setMark(false);
            if(marked){
                this.numMarked--;}
        }



        /**
         * public int size()
         *
         * Returns the number of elements in the heap.
         *
         */
        public int size (){ return this.size; }

        /**
         * public int[] countersRep()
         *
         * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
         * Note: The size of of the array depends on the maximum order of a tree, and an empty heap returns an empty array.
         *
         */
        public int[] countersRep () {
            if (this.isEmpty()){      //special case
                return new int[0];}
            //else
            HeapNode[] nodes = new HeapNode[numTree];

            int i = 0;
            int maxRank = 0;

            HeapNode curr = this.start;
            HeapNode tmp = null;

            //interating over trees and insert them into an array while finding the maximum rank in the heap
            while (tmp != this.start) {
                tmp = curr.getNext();
                maxRank = Math.max(maxRank , curr.getRank()); //keep the maximum rank
                nodes[i++] = curr;
                curr = tmp;} //moving forward

            int[] counter = new int[maxRank+1];
            
            //lets count
            for (HeapNode node: nodes ) {
                counter[node.getRank()] = counter[node.getRank()] + 1;
                }
            return counter;}


        /**
         * public void delete(HeapNode x)
         *
         * Deletes the node x from the heap.
         * It is assumed that x indeed belongs to the heap.
         *
         */
        public void delete (HeapNode x){ //TODO BIG DILEMMA!!!!
            int diff = ( x.getKey() - this.min.getKey() ); // x is greater that min by diff
            decreaseKey(x , (1+diff) ); //make x minimum
            deleteMin(); // delete x
        }

        /**
         * public void decreaseKey(HeapNode x, int delta)
         *
         * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
         * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
         */
        public void decreaseKey(HeapNode x, int delta) {
            int new_key = x.getKey() - delta;
            x.setKey(new_key);
            if (new_key < this.findMin().getKey()) {
                this.min = x;
            }

            if (x.getParent() != null && new_key < x.getParent().getKey()) {
                this.cascadingCut(x, x.getParent());
            }

        }

        private void cut(HeapNode node, HeapNode parent){ //real cut, update rank, unmark
            node.setParent(null);
            makeUnmarked(node); //the marked num will decrease in the func
            parent.setRank(parent.getRank()-1);
            if (node.getNext() == node){
                parent.setChild(null);
            } else { //connect children
                if (parent.getChild() == node) {
                    parent.setChild(node.getNext()); //TODO NOT SURE!!!
                }
                node.getPrev().setNext(node.getNext());
                node.getNext().setPrev(node.getPrev());
            }
            addAtStart(node);
            this.numTree++;
            cuts++;
        }

        private void cascadingCut(HeapNode node, HeapNode parent){ //update numTree + marks
            cut(node, parent);
            if (parent.getParent()!= null){
                if (!parent.getMark()){
                    parent.setMark(true);
                    numMarked++;
                } else{
                    cascadingCut(parent, parent.getParent());
                }
            }
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
        public int potential (){ return (numTree + (2 * numMarked) );}


        /**
         * public static int totalLinks()
         *
         * This static function returns the total number of link operations made during the
         * run-time of the program. A link operation is the operation which gets as input two
         * trees of the same rank, and generates a tree of rank bigger by one, by hanging the
         * tree which has larger value in its root under the other tree.
         */
        public static int totalLinks (){ return Links;}

        /**
         * public static int totalCuts()
         *
         * This static function returns the total number of cut operations made during the
         * run-time of the program. A cut operation is the operation which disconnects a subtree
         * from its parent (during decreaseKey/delete methods).
         */
        public static int totalCuts (){ return cuts;}

        /**
         * public static int[] kMin(FibonacciHeap H, int k)
         *
         * This static function returns the k smallest elements in a Fibonacci heap that contains a single tree.
         * The function should run in O(k*deg(H)). (deg(H) is the degree of the only tree in H.)
         *
         * ###CRITICAL### : you are NOT allowed to change H.
         */
        public static int[] kMin(FibonacciHeap H, int k) {
            int[] answer = new int[k];
            FibonacciHeap help = new FibonacciHeap();
            help.insert(H.findMin().getKey(), H.findMin());

            for(int i = 0; i < k; ++i) {
                int tmp = help.removeAndPut();
                answer[i] = tmp;
            }

            return answer;
        }

    private int removeAndPut() {
        HeapNode origin = this.findMin().getOrigin();
        HeapNode firstChild = origin.getChild();
        HeapNode child = firstChild;

        for(HeapNode tmp = null; tmp != firstChild; child = tmp) {
            tmp = child.getNext();
            this.insert(child.getKey(), child);
        }

        this.deleteMin();
        return origin.getKey();
    }

    public HeapNode getFirst() { return this.start; }

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
       private HeapNode origin;
       public int key;
       public int rank;

       public HeapNode(int key) {
           this.key = key;
       }
       public HeapNode(int key, HeapNode origin) {
           this.key = key;
           this.origin = origin;}

       public HeapNode getOrigin(){ return this.origin;}

       public void setChild(HeapNode node){
           this.child = node;}

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

       public void setMark(boolean mark){
           this.marked = mark;
       }

       public boolean getMark(){return this.marked ;}

       public void setRank(int k){
           this.rank = k;
       }
       public int getRank(){
           return this.rank;
       }
       public void setKey(int num){ //added for the decrease key
           this.key = num;
       }



    	public int getKey() {
    		return this.key;
    	}
    }
}
