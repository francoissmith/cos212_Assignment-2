public class AvlTree<T extends Comparable<T>> {
    public Node<T> root;

    public AvlTree() {
        this.root = null;
    }


    int getHeight(Node<T> N) {
        if (N == null)
            return 0;

        return N.height;
    }

    /*Printing AvlTree in inorder*/
    void print(Node<T> node) {
        if (node == null)
            return;

        print(node.left);

        System.out.print(node.data + " ");

        print(node.right);
    }

    /* Do not edit the code above */

    /**
     * Insert the given data into the tree.
     * Duplicate data is not allowed. just return the node.
     */

    Node<T> insert(Node<T> node, T data) {
            if (root == null) {
                root =  node;
            }

        return root;
    }


    /**
     * Remove / Delete the node based on the given data
     * Return the node / root if the data do not exist
     */

    Node<T> removeNode(Node<T> root, T data) {

        return null;
    }

    /* Helper Function */
// ************************************************************** search()
    Node<T> search(T data) {
        Node<T> p = root;
        while (p != null) {
            if (data.equals(p.data)) 
                return p;
            else if (data.compareTo(p.data) < 0)
                p = p.left;
            else
                p = p.right;
        }

        return null;
    }
// **************************************************************
}
