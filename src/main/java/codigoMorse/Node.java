package codigoMorse;

public class Node {
    char letter;
    Node left;
    Node right;

    public Node(char letter) {
        this.letter = letter;
        this.left = null;
        this.right = null;
    }
}
