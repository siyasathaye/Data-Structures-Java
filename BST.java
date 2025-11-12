
public class BST<I extends Comparable<I>, T>{

    class BSTNode {
        private I index;
        private T data;
        private BSTNode left;
        private BSTNode right;

        /**
         * Default constructor. Sets all instance variables to be null.
         */
        public BSTNode() {
            index = null;
            data = null;
            left = null;
            right = null;
        }

        /**
         * Constructor. Sets data and index to be _data and _index respectively.
         */
        public BSTNode(I _index, T _data) {
            this.index = _index;
            this.data = _data;
            right = null;
            left = null;
        }

        /**
         * Returns the index stored in this node.
         */
        public I getIndex() {
            return this.index;
        }

        /**
         * Returns the data stored in this node.
         */
        public T getData() {
            return this.data;
        }

        /**
         * Updates the data in this node to the specified value.
         */
        public void setData(T d) {
            this.data = d;
        }

        /**
         * Returns a string representation of the node, indicating its index and data.
         */
        public String toString() {
            return "index:\t" + this.index + ",\t" + "data:\t" + this.data + "\n";
        }
    }


    private BSTNode root;
    private int size;

    /**
     * Constructor. Initializes an empty BST with root set to null and size set to 0.
     */
    public BST() {
        this.root = null;
        this.size = 0;
    }


    /**
     * Performs an in-order traversal of the BST and records indices and data values.
     */
    private String inOrderTraversal(BSTNode node) {
        if (node == null)
            return "";
        return inOrderTraversal(node.left) + node.toString() + inOrderTraversal(node.right);
    }

    /**
     * Returns a string representation of the entire BST using in-order traversal.
     */
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("In-order Traversal of the BST ...\n").append("==================\n").append(inOrderTraversal(root));
        return output.toString();
    }

    /**
     * Returns the size of the BST, i.e., the number of valid nodes.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Adds a new node with the specified index and data to the BST.
     */
    private void addHelper(BSTNode currRoot, BSTNode newnode) {
        int compare_value = newnode.getIndex().compareTo(currRoot.getIndex());
        if (compare_value < 0){
            if (currRoot.left == null){
                currRoot.left = newnode;
            }
            else {
                addHelper(currRoot.left, newnode);
            }
        }
        else if (compare_value > 0) {
            if (currRoot.right == null){
                currRoot.right = newnode;
            }
            else {
                addHelper(currRoot.right, newnode);
            }
        }

    }
    public void addNode(I _index, T _data) {
        BSTNode newNode = new BSTNode(_index, _data);
        if (root == null) {
            root = newNode;
        } else {
            addHelper(root, newNode);
        }
        size++;
    }

    /**
     * Searches for a node with the specified index in the BST.
     */
    private BSTNode searchHelper(BSTNode node, I _index) {
        if (node == null) {
            return null;
        }
        int compare_value = _index.compareTo(node.getIndex());
        if ( compare_value == 0) {
            return node;
        }
        else if (compare_value < 0){
            return searchHelper(node.left, _index);
        }
        else {
            return searchHelper(node.right, _index);
        }
    }
    public BSTNode searchNode(I _index) {
        return searchHelper(root, _index);
    }

    /**
     * Removes a node with the specified index from the BST.
     */
    private BSTNode findMin(BSTNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private BSTNode removeHelper(BSTNode node, I _index) throws IllegalArgumentException {
        if (node == null) {
           throw new IllegalArgumentException("removeNode(I _index): No node with an index " + _index + " in the BST");
        }
        int compare_value = _index.compareTo(node.getIndex());
        if (compare_value == 0) {
            if (node.left == null && node.right == null) {
                return null;
            } else if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            BSTNode successor = findMin(node.right);
            node.index = successor.index;
            node.data = successor.data;
            node.right = removeHelper(node.right, successor.index);
        }
        else if (compare_value < 0){
            node.left = removeHelper(node.left, _index);
        }
        else {
            node.right = removeHelper(node.right, _index);
        }
        return node;
    }
    public void removeNode(I _index) {
        try {
            this.root = removeHelper(root, _index);
            size--;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    /**
     * Updates a node's data with a new value, given its index.
     */
    private void updateHelper(BSTNode node, I _index, T _newData) throws IllegalArgumentException{
        if (node == null) {
            throw new IllegalArgumentException("updateNode(I _index, T _newData): No node with an index " + _index + " in the BST");
        }
        int compare_value = _index.compareTo(node.getIndex());
        if ( compare_value == 0) {
            node.data = _newData;
        }
        else if (compare_value < 0){
            updateHelper(node.left, _index, _newData);
        }
        else {
            updateHelper(node.right, _index, _newData);

        }
    }
    public void updateNode(I _index, T _newData) {
        updateHelper(root, _index, _newData);
    }


    
/************************************ GRADING CODE (DO NOT MODIFY) ************************************ */
    /**
     * Performs a pre-order traversal of the BST.
     */
    private void preOrderTraversal(BSTNode node, int[] idx, String[] arr, boolean dataFlag) {
        // DO NOT CHANGE THIS. THIS FOR TESTING PURPOSES
        if(node == null)
            return;

        if(dataFlag)
            arr[idx[0]] = String.valueOf(node.getData());
        else
            arr[idx[0]] = String.valueOf(node.getIndex());
        idx[0]++;
        
        preOrderTraversal(node.left, idx, arr, dataFlag);
        preOrderTraversal(node.right, idx, arr, dataFlag);
    }

    /**
     * Returns an array of data values in pre-order traversal order.
     * @return A String array containing the data values of all nodes in pre-order order
     */
    public String[] getDataArray() {
        /// DO NOT CHANGE THIS. THIS FOR TESTING PURPOSES
        String[] dataArr = new String[size];
        preOrderTraversal(this.root, new int[1], dataArr, true);
        return dataArr;
    }

    /**
     * Returns an array of index values in pre-order traversal order.
     * @return A String array containing the index values of all nodes in pre-order order
     */
    public String[] getIndexArray() {
        // DO NOT CHANGE THIS. THIS FOR TESTING PURPOSES
        String[] indexArr = new String[size];
        preOrderTraversal(this.root, new int[1], indexArr, false);
        return indexArr;
    }

/****************************************************************************************************** */

}
