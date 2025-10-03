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

    public void insertPublic(char letter, String morseCode) {
        root = insert(root, letter, morseCode, 0);
    }

    private Node insert(Node node, char letter, String code, int index) {
        if (index == code.length()) {
            return new Node(letter);
        }

        if (node == null) {
            node = new Node(' ');
        }

        char symbol = code.charAt(index);
        if (symbol == '.') {
            node.left = insert(node.left, letter, code, index + 1);
        } else if (symbol == '-') {
            node.right = insert(node.right, letter, code, index + 1);
        }
        return node;
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

    public void checkString(String word){
        word = word.toUpperCase().trim();
        if (word.isEmpty()) return;

        char c = word.charAt(0);
        if (!Character.isLetter(c) && c != ' '){
            throw new IllegalArgumentException("Caractere inválido '" + c +
                    "'. Somente letras e espaços são permitidos, tente novamente");
        }
        checkString(word.substring(1));
    }

    private String decodeWord(String morse, Node root) {

        morse = morse.trim();
        if (morse.isEmpty()) return "";
        int espaço = morse.indexOf(' ');
        String letraCode;
        String resto;
        if (espaço == -1){
            letraCode = morse;
            resto = "";
        } else {
            letraCode = morse.substring(0, espaço);
            resto = morse.substring(espaço + 1);
        }
        char letra = decodeLetter(letraCode, root, 0);
        return letra + decodeWord(resto, root);
    }

    private char decodeLetter(String code, Node atual, int index){
        if (atual == null) throw new IllegalArgumentException("Código Morse Inválido " +
                "ou ainda não inserido, insira e tente novamente");
        if (index == code.length()) return code.equals("/") ? ' ': atual.letter;

        char c = code.charAt(index);
        if (c == '.') return decodeLetter(code, atual.left, index + 1);
        if (c == '-') return decodeLetter(code, atual.right, index +1);
        if (c == '/') return ' ';
        throw new IllegalArgumentException("O caracter '" + c + "' é inválido para código morse.");
    }
    public String decodeWord(String morse){
        return decodeWord(morse, this.root);
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
