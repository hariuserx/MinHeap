import java.util.Scanner;

/**
 * author: hari kishore
 */
public class MinHeapWithoutArrays {

    class Node{
        Node left;
        Node right;
        Node parent;
        Node prevTail;
        int data;

        Node(int data){
            this.data = data;
        }
    }

    class MinHeap{

        Node root;
        Node tail;
        int size;

        int getMinimum(){
            return root.data;
        }

        void setTail(Node node){

            /*
              If we reach this stage that means a level is completely filled
              and we need to proceed to the next level by going to the extreme left.
             */
            if(node.parent == null){
                tail = node;
                while(tail.left != null){
                    tail = tail.left;
                }
            }
            /*
              If current node is the left node, go to the right node and
              proceed left from there to reach the left most node.
             */
            else if(node.parent.left == node){
                tail = node.parent.right;
                while(tail.left != null){
                    tail = tail.left;
                }
            }
            else if(node.parent.right == node){
                setTail(node.parent);
            }
        }

        void insert(int data){
            if(root == null){
                root = new Node(data);
                tail = root;
            }
            else if(tail.left == null){
                tail.left = new Node(data);
                tail.left.parent = tail;
                minHeapify(tail.left);
            }else {
                tail.right = new Node(data);
                tail.right.parent = tail;
                minHeapify(tail.right);
                Node prevTail = tail;
                setTail(tail);
                tail.prevTail = prevTail;
            }
            size++;
        }

        void deleteRoot(){
            if(root == null) {
                System.out.println("MinHeap is empty");
                return;
            }
            if(tail == root){
                tail = null;
                root = null;
            }
            else {
                if (tail.right != null){
                    swapNodeData(tail.right, root);
                    tail.right = null;
                    revMinHeapify(root);
                }
                else if(tail.left != null) {
                    swapNodeData(tail.left, root);
                    tail.left = null;
                    revMinHeapify(root);
                }
                else {
                    tail = tail.prevTail;
                    deleteRoot();
                    size++;
                }
            }
            size--;
        }

        void swapNodeData(Node a, Node b){
            int temp = a.data;
            a.data = b.data;
            b.data = temp;
        }

        void minHeapify(Node node){
            if(node.parent != null){
                if(node.parent.data > node.data){
                    swapNodeData(node.parent, node);
                    minHeapify(node.parent);
                }
            }
        }

        void revMinHeapify(Node node){
            if(node == null || node.left == null)
                return;
            Node min = node.left;
            if(node.right != null && min.data > node.right.data){
                min = node.right;
            }
            if(min.data < node.data){
                swapNodeData(node, min);
                revMinHeapify(min);
            }
        }
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        MinHeapWithoutArrays minHeapWithoutArrays = new MinHeapWithoutArrays();
        MinHeap minHeap = minHeapWithoutArrays.new MinHeap();
        char type = scanner.next().charAt(0);
        while('s' != type) {
            switch (type) {
                case 'i':
                    minHeap.insert(scanner.nextInt());
                    break;
                case 'm':
                    if(minHeap.root == null)
                        System.out.println("Empty heap");
                    else
                        System.out.println("minimum Element :" + minHeap.getMinimum());
                    break;
                case 'd':
                    if(minHeap.root == null)
                        System.out.println("Delete: Empty heap");
                    else {
                        System.out.println("Delete: " + minHeap.getMinimum());
                        minHeap.deleteRoot();
                    }
                    break;
                case 'n':
                    System.out.println("size :" + minHeap.size);
                    break;
                    default:
                        System.out.println("Check the command");
                        break;
            }
            type = scanner.next().charAt(0);
        }
    }
}
