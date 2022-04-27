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

    /* Printing AvlTree in inorder */
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
        Node<T> newNode = new Node<T>(data);
        newNode.left = null;
        newNode.right = null;

        if (root == null) {
            root = newNode;
        } else {
            if (data.equals(node.data)) {
                return node;
            } else if (data.compareTo(node.data) < 0) {
                if (node.left != null) {
                    return insert(node.left, data);
                } else {
                    node.left = newNode;
                }
            } else if (data.compareTo(node.data) > 0) {
                if (node.right != null) {
                    return insert(node.right, data);
                } else {
                    node.right = newNode;
                }
            }
        }

        setHeight(root);
        System.out.println();

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
    // ************************************************************** setHeight()
    void setHeight(Node<T>node) {
        if (node != null) {
            setHeight(node.left);
            node.height = setNodeHeight(node);
            setHeight(node.right);
            System.out.println("data: " + node.data + " , height: " + node.height );
        }

    }

    // ************************************************************** setNodeHeight()
    int setNodeHeight(Node<T> node) {
        if (node == null)
            return 0;
        else {
            int left = setNodeHeight(node.left);
            int right = setNodeHeight(node.right);

            if (left > right)
                return left + 1;
            else
                return right + 1;
        }
    }
    
    // **************************************************************
}
