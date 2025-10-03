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
    public void drawTree(Canvas canvas) {

        System.out.println("Desenho da árvore ainda não implementado.");
    }
    public Node getRoot() {
        return root;
    }

}
