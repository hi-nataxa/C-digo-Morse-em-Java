package codigoMorse;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class MorseBST {
    private Node root;

    public MorseBST() {
        root = new Node('*');
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void insert(char letter, String code) {
        Node current = root;

        for (int i = 0; i < code.length(); i++) {
            char character = code.charAt(i);

            if (character == '.') {
                if (current.left == null) {
                    current.left = new Node('*');
                }
                current = current.left;
            } else if (character == '-') {
                if (current.right == null) {
                    current.right = new Node('*');
                }
                current = current.right;
            }
        }

        current.letter = letter;
    }

    private String findCode(Node node, char letter, String code) {
        if (node == null) return null;
        if (node.letter == letter) return code;

        String left = findCode(node.left, letter, code + ".");
        if (left != null) return left;

        return findCode(node.right, letter, code + "-");
    }

    public String findCodePublic(char letter) {
        return findCode(root, letter, "");
    }

    public String encodeWord(String word) {
        word = word.toUpperCase().trim();
        if (word.isEmpty()){
            return "";
        }
        char c = word.charAt(0);
        String code;
        if (c == ' '){
            code = "/ ";
        }else{
            code = findCodePublic(c);
            if (code == null){
                throw new IllegalArgumentException( "A letra '" + c + "' não está na árvore, " +
                        "tente uma palavra diferente ou insira a letra " +
                        "e tente novamente.");
            }
            code += " ";
        }
        return code + encodeWord(word.substring(1));
    }

    public String decodeWord(String morse) {

        return "[Decodificação não implementada]";
    }

    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }


    public void drawTree(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        drawNode(gc, root, canvas.getWidth() / 2, 40, canvas.getWidth() / 4);
    }

    private void drawNode(GraphicsContext gc, Node node, double x, double y, double xOffset) {
        if (node == null) return;

        // círculo
        gc.strokeOval(x - 15, y - 15, 30, 30);
        // letra
        gc.strokeText(String.valueOf(node.letter == ' ' ? ' ' : node.letter), x - 5, y + 5);

        if (node.left != null) {
            double newX = x - xOffset;
            double newY = y + 80;
            gc.strokeLine(x, y + 15, newX, newY - 15);
            drawNode(gc, node.left, newX, newY, xOffset / 2);
        }
        if (node.right != null) {
            double newX = x + xOffset;
            double newY = y + 80;
            gc.strokeLine(x, y + 15, newX, newY - 15);
            drawNode(gc, node.right, newX, newY, xOffset / 2);
        }
    }
}
