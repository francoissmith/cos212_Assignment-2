public class Node<T extends Comparable<T>> {
   public T data;
   public int height;
   public Node<T> left;
   public Node<T> right;

    /** Used to indicate whether the right / left pointer is a normal
    pointer or a pointer to inorder successor.
     **/
    boolean rightThread;
    boolean leftThread;

    public Node(T item) {
        data = item;
        left = right = null;
    }

    public void print() {
        if (this.left == null && this.right == null) {
            System.out.print("[L: " + "null" + " | V: " + this.data + " | R: " + "null]    ");
        }
        else if (this.left == null || this.right == null) {
            if (this.left == null)
                System.out.print("[L: " + "null" + " | V: " + this.data + " | R: " + this.right.data + "]    ");
                
            if (this.right == null)
                System.out.print("[L: " + this.left.data + " | V: " + this.data + " | R: " + "null]    ");
        }
        else {
            System.out.print("[L: " + this.left.data + " | V: " + this.data + " | R: " + this.right.data + "]    ");
        }
    }
}
