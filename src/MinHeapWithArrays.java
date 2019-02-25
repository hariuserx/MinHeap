import java.util.Scanner;

@SuppressWarnings("Duplicates")

/*
  author: hari kishore
  In this impl we dont have setTail func, we store an array index with references to the nodes.
 */
public class MinHeapWithArrays {

    private final int MAX_SIZE = 6000001;

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
        Node[] nodeAccess = new Node[MAX_SIZE];

        int getMinimum(){
            return root.data;
        }

        void insert(int data){
            size++;
            if(root == null){
                root = new Node(data);
                tail = root;
                nodeAccess[size] = root;
            }
            else if(tail.left == null){
                tail.left = new Node(data);
                tail.left.parent = tail;
                minHeapify(tail.left);
                nodeAccess[size] = tail.left;
            }else {
                tail.right = new Node(data);
                tail.right.parent = tail;
                minHeapify(tail.right);
                Node prevTail = tail;
                nodeAccess[size] = tail.right;
                tail = nodeAccess[size/2 + 1];
                tail.prevTail = prevTail;
            }
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
            nodeAccess[size] = null;
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

        MinHeapWithArrays minHeapWithArrays = new MinHeapWithArrays();
        MinHeap minHeap = minHeapWithArrays.new MinHeap();
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
