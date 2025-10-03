package codigoMorse;

import javafx.scene.canvas.Canvas;

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

    public void drawTree(Canvas canvas) {

        System.out.println("Desenho da árvore ainda não implementado.");
    }
    public Node getRoot() {
        return root;
    }

}
