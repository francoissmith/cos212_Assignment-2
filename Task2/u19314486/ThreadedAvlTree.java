@SuppressWarnings({ "unchecked", "rawtypes" })
public class ThreadedAvlTree<T extends Comparable<T>> {
    public Node<T> root;

    public ThreadedAvlTree() {
        this.root = null;
    }

    int getHeight(Node<T> N) {
        if (N == null)
            return 0;

        return N.height;
    }

    static Node getLeftMost(Node node) {
        while (node != null && node.left != null)
            node = node.left;
        return node;
    }

    // Inorder traversal of a threaded avl tree
    void print(Node<T> node) {
        if (node == null)
            return;

        Node<T> cur = getLeftMost(node);

        while (cur != null) {
            System.out.print(" " + cur.data + " ");

            if (cur.rightThread == true)
                cur = cur.right;
            else
                cur = getLeftMost(cur.right);
        }
    }

    /* Do not edit the code above */

    void convertAVLtoThreaded(Node<T> node) {
        this.root = node;
        thread(node);
    }

    Node<T> thread(Node<T> node) {

        if (node == null)
            return null;
        if (node.left == null && node.right == null)
            return node;

        if (node.left != null) {
            Node<T> left = thread(node.left);

            left.right = node;
            left.rightThread = true;
        }

        if (node.right == null) {
            return node;
        }

        return thread(node.right);
    }

    /**
     * Insert the given data into the tree.
     * Duplicate data is not allowed. just return the node.
     */

    Node<T> insert(Node<T> node, T data) {

        if (root == null) {
            Node<T> newNode = new Node<T>(data);
            newNode.left = null;
            newNode.right = null;

            root = newNode;
        } else if (node.data.equals(data))
            return root;
        else if (data.compareTo(node.data) < 0) {
            if (node.left != null)
                insert(node.left, data);
            else
                addNode(node, data, true);
        } else if (data.compareTo(node.data) > 0) {
            if (node.rightThread == false) {
                if (node.left == null || node.right == null) {
                    addNode(node, data, false);
                } else
                    insert(node.right, data);
            } else
                addNode(node, data, false);
        }

        balanceTree(node);

        return root;
    }

    void addNode(Node<T> ptr, T data, boolean isleftChild) {
        Node<T> newNode = new Node<T>(data);

        if (isleftChild) {
            newNode.left = null;
            newNode.right = ptr;
            newNode.rightThread = true;

            ptr.left = newNode;
        } else {
            if (ptr.rightThread) {
                newNode.left = null;
                newNode.right = ptr.right;
                newNode.rightThread = true;

                ptr.right = newNode;
                ptr.rightThread = false;
            } else {
                newNode.left = null;
                newNode.right = null;

                ptr.right = newNode;
            }
        }
    }

    /**
     * Delete the given element \texttt{data} from the tree. Re-balance the tree
     * after deletion.
     * If the data is not in the tree, return the given node / root.
     */
    Node<T> removeNode(Node<T> root, T data) {
        if (root != null) {
            if (root.data.equals(data)) {
                this.root = deleteNode(data);
            } else if (data.compareTo(root.data) < 0) {
                if (root.left != null)
                    removeNode(root.left, data);
            } else {
                if (!root.rightThread)
                    removeNode(root.right, data);
            }
            balanceTree(root);
        }
        balanceTree(this.root);
        return this.root;
    }

    public Node<T> deleteNode(T data) {
        Node<T> node, ptr = root, prev = null;

        if (data.equals(this.root.data) && root.left == null && root.right == null) {
            // root = null;
            return null;
        }

        while (ptr != null && !data.equals(ptr.data)) {
            prev = ptr;
            if (data.compareTo(ptr.data) < 0)
                ptr = ptr.left;
            else if (ptr.rightThread)
                ptr = null;
            else
                ptr = ptr.right;
        }

        node = ptr;

        if (node.left != null && !node.rightThread) { // two children
            root = copyDelete(prev, node, 2);
        } else if (node.rightThread && node.left == null)
            root = copyDelete(prev, node, 0);
        else if (node.right == null && node.left == null)
            root = copyDelete(prev, node, 0);
        else if (node.left != null && (node.right == null || node.rightThread))
            root = copyDelete(prev, node, 1);
        else if (!node.rightThread && node.right != null && node.left == null)
            root = copyDelete(prev, node, 1);

        return root;
    }

    Node<T> copyDelete(Node<T> prev, Node<T> node, int children) {

        switch (children) {
            case 0: {
                if (prev.left == node) {
                    prev.left = null;
                } else if (prev.right == node) {
                    if (node.rightThread) {
                        prev.right = node.right;
                        prev.rightThread = true;
                    } else
                        prev.right = null;
                }
            }
                break;
            case 1: {
                if (node.right == null || node.rightThread) { // one left-child
                    if (prev == null) {
                        root = node.left;
                    }
                    else {
                        node.left.right = node.right;
                        if (node.rightThread)
                            node.left.rightThread = true;

                        if (prev.left == node)
                            prev.left = node.left;
                        else
                            prev.right = node.left;
                    }
            
                } else { // one right-child
                    if (prev == null)
                        root = node.right;
                    else if (prev.left == node)
                        prev.left = node.right;
                    else
                        prev.right = node.right;
                }
            }
                break;
            case 2: {
                Node<T> tmp = node.left;
                Node<T> previous = node;
                while (tmp.right != null) {
                    if (tmp.rightThread) {
                        break;
                    }
                    previous = tmp;
                    tmp = tmp.right;
                }

                // node.data = tmp.data;
                // System.out.println();
                // System.out.println(node.right.data);
                // System.out.println();

                if (previous == node) {
                    tmp.right = node.right;
                    tmp.rightThread = false;
                } else {
                    tmp.right = node.right;
                    tmp.left = node.left;
                    tmp.rightThread = false;
                    previous.rightThread = true;
                }

                node.left = null;
                node.right = null;

                if (node == root)
                    root = tmp;
                else if (prev.left == node)
                    prev.left = tmp;
                else
                    prev.right = tmp;
            }
                break;
        }
        return root;
    }

    void updateTreeHeight(Node<T> node) {
        if (node == null)
            return;

        Node<T> cur = getLeftMost(node);

        while (cur != null) {
            node.height = setHeight(node) - 1;

            if (cur.rightThread == true)
                cur = cur.right;
            else
                cur = getLeftMost(cur.right);
        }
    }

    int setHeight(Node<T> node) {
        if (node == null)
            return 0;

        int left;
        int right;

        if (node.left == null)
            left = setHeight(null);
        else
            left = setHeight(node.left);

        if (node.rightThread)
            right = setHeight(null);
        else
            right = setHeight(node.right);

        if (left > right)
            return left + 1;
        else
            return right + 1;
    }

    int balanceFactor(Node<T> node) {
        if (node == null)
            return 0;

        if (node.left == null && node.rightThread)
            return 0;
        else {
            if (node.rightThread && node.left != null)
                return 0 - (getHeight(node.left) + 1);
            else if (node.left == null && !node.rightThread)
                return (getHeight(node.right) + 1) - 0;
            else
                return (getHeight(node.right) + 1) - (getHeight(node.left) + 1);
        }
    }

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

    public void rotate(Node<T> c, Node<T> p, Node<T> g, boolean right) {
        if (right) { // right rotation
            p.left = c.right;
            c.right = p;
            if (c.rightThread)
                c.rightThread = false;
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
            if (p.right == null) {
                p.right = c;
                p.rightThread = true;
            }
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
}
