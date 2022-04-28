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
    // **************************************************************_insert()
    /**
     * Insert the given data into the tree.
     * Duplicate data is not allowed. just return the node.
     */

    Node<T> insert(Node<T> node, T data) {
        Node<T> newNode = new Node<T>(data);
        newNode.left = null;
        newNode.right = null;

        if (root == null)
            root = newNode;
        else if (node.data.equals(data))
            return root;
        else if (data.compareTo(node.data) < 0) {
            if (node.left != null)
                insert(node.left, data);
            else
                node.left = newNode;
        } else if (data.compareTo(node.data) > 0) {
            if (node.right != null)
                insert(node.right, data);
            else
                node.right = newNode;
        }

        balanceTree(node);
        return root;
    }

    // **************************************************************_removeNode()
    /**
     * Remove / Delete the node based on the given data.
     * Return the node / root if the data do not exist.
     */

    Node<T> removeNode(Node<T> root, T data) {
        if (root != null) {
            if (root.data.equals(data)) {
                deleteNode(data);
            } else if (data.compareTo(root.data) < 0){
                if (root.left != null)
                    removeNode(root.left, data);
            }
            else {
                if (root.right != null) 
                    removeNode(root.right, data);
            }
            balanceTree(root);
        }
        return this.root;
    }

    /* Helper Function */

    // **************************************************************_updateTreeHeight()
    /**
     * Update height values of tree.
     * Starting at passed in node.
     */
    void updateTreeHeight(Node<T> node) {
        if (node != null) {
            updateTreeHeight(node.left);
            node.height = setHeight(node) - 1;
            updateTreeHeight(node.right);
        }
    }

    // **************************************************************_setHeight()
    /**
     * Set height of passed in node recursively.
     * Return int of largest sub-tree.
     */
    int setHeight(Node<T> node) {
        if (node == null)
            return 0;
        else {
            int left = setHeight(node.left);
            int right = setHeight(node.right);

            if (left > right)
                return left + 1;
            else
                return right + 1;
        }
    }

    // **************************************************************_balanceFactor()
    /**
     * Calculate balance factor of passed in node.
     */
    int balanceFactor(Node<T> node) {

        if (node == null)
            return 0;
        else if (node.left == null && node.right == null)
            return 0;
        else {
            if (node.right == null && node.left != null)
                return 0 - (node.left.height + 1);
            else if (node.left == null && node.right != null)
                return (node.right.height + 1) - 0;
            else
                return (node.right.height + 1) - (node.left.height + 1);
        }

    }

    // **************************************************************_balanceTree()
    /**
     * Balance the tree after insertion or deletion.
     * Return new root after balancing
     */
    Node<T> balanceTree(Node<T> node) {

        updateTreeHeight(node);

        int factor = balanceFactor(node);

        if (factor > 1) { // right-sub tree
            if (balanceFactor(node.right) < 0) { // left-sub of right-sub tree
                rotate(node.right.left, node.right, node, true);
                rotate(node.right, node, predecessor(node), false);

            } else {
                rotate(node.right, node, predecessor(node), false);
            }
        } else if (factor < -1) { // left-sub tree
            if (balanceFactor(node.left) > 0) {// right-sub of left-sub tree
                rotate(node.left.right, node.left, node, false);
                rotate(node.left, node, predecessor(node), true);
            } else {
                rotate(node.left, node, predecessor(node), true);
            }
        }

        return node;
    }

    // **************************************************************_rotate()
    /**
     * Do a left or a right rotation
     */
    public void rotate(Node<T> c, Node<T> p, Node<T> g, boolean right) {
        if (right) { // right rotation
            p.left = c.right;
            c.right = p;
            if (g != null)
                if (p == g.left)
                    g.left = c;
                else
                    g.right = c;
            else
                root = c;
        } else { // left rotation
            p.right = c.left;
            c.left = p;
            if (g != null)
                if (p == g.left)
                    g.left = c;
                else
                    g.right = c;
            else
                root = c;
        }

        updateTreeHeight(root);
    }

    // **************************************************************_predecessor()
    /**
     * find predecessor of passed in node
     */
    public Node<T> predecessor(Node<T> node) {
        Node<T> ptr = root;

        if (node.equals(root))
            return null;

        while (ptr != null) {
            if (node.equals(ptr.left) || node.equals(ptr.right))
                return ptr;
            else if (node.data.compareTo(ptr.data) < 0)
                ptr = ptr.left;
            else
                ptr = ptr.right;
        }

        return null;
    }

    // **************************************************************_deleteNode()
    /**
     * Remove / Delete the node based on the given data.
     * Return the node / root if the data do not exist.
     */

    public void deleteNode(T data) {
        Node<T> node, ptr = root, prev = null;

        if (data.equals(this.root.data)) {
            root = null;
            return;
        }

        while (ptr != null && !data.equals(ptr.data)) {
            prev = ptr;
            if (data.compareTo(ptr.data) < 0)
                ptr = ptr.left;
            else
                ptr = ptr.right;
        }

        node = ptr;

        if (ptr != null && data.equals(ptr.data)) {
            if (node.right == null)
                node = node.left;
            else if (node.left == null)
                node = node.right;
            else {
                Node<T> tmp = node.left;
                Node<T> previous = node;
                while (tmp.right != null) {
                    previous = tmp;
                    tmp = tmp.right;
                }
                node.data = tmp.data;

                if (previous == node) {
                    previous.left = tmp.left;
                } else
                    previous.right = tmp.left;
            }

            if (ptr == root)
                root = node;
            else if (prev.left == ptr)
                prev.left = node;
            else
                prev.right = node;
        }

    }
}
