package codigoMorse;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TreeVisualizer extends Application {

    private MorseBST bst = new MorseBST();
    private Canvas canvas = new Canvas(1000, 600);

    private static final String estiloBotao = """
        -fx-background-color: #ff69b4;
        -fx-text-fill: white;
        -fx-font-weight: bold;
    """;

    private static final String estiloBotaoHover = """
        -fx-background-color: #ff1493;
        -fx-text-fill: white;
        -fx-font-weight: bold;
    """;

    private void aplicarHover(Button btn) {
        btn.setStyle(estiloBotao);
        btn.setOnMouseEntered(e -> btn.setStyle(estiloBotaoHover));
        btn.setOnMouseExited(e -> btn.setStyle(estiloBotao));
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Menu - Árvore Binária Código Morse");

        Button inserirBtn = new Button("Inserir");
        Button codificarBtn = new Button("Codificar");
        Button decodificarBtn = new Button("Decodificar");
        Button exibirBtn = new Button("Exibir Árvore");

        aplicarHover(inserirBtn);
        aplicarHover(codificarBtn);
        aplicarHover(decodificarBtn);
        aplicarHover(exibirBtn);

        inserirBtn.setOnAction(e -> mostrarJanelaInserir());
        codificarBtn.setOnAction(e -> mostrarJanelaCodificar());
        decodificarBtn.setOnAction(e -> mostrarJanelaDecodificar());
        exibirBtn.setOnAction(e -> mostrarJanelaArvore());


        VBox menu = new VBox(20, inserirBtn, codificarBtn, decodificarBtn, exibirBtn);
        menu.setPadding(new Insets(20));
        menu.setAlignment(Pos.CENTER);
        menu.setPrefWidth(300);

        Scene scene = new Scene(menu, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // JANELA INSERIR
    private void mostrarJanelaInserir() {
        Stage janela = new Stage();
        janela.setTitle("Inserir Letra");

        TextField letraField = new TextField();
        letraField.setPromptText("Letra (A-Z)");

        TextField morseField = new TextField();
        morseField.setPromptText("Código Morse (. -)");

        Button confirmar = new Button("Inserir");
        aplicarHover(confirmar);
        confirmar.setOnAction(e -> {
            if (letraField.getText().length() == 1 && !morseField.getText().isEmpty()) {
                char letra = Character.toUpperCase(letraField.getText().charAt(0));
                bst.insert(letra, morseField.getText());
                mostrarInfo("Letra inserida", "Letra " + letra + " adicionada com código " + morseField.getText());
                janela.close();
            } else {
                mostrarErro("Entrada inválida.");
            }
        });

        VBox layout = new VBox(10, letraField, morseField, confirmar);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);
        janela.setScene(new Scene(layout, 300, 150));
        janela.show();
    }

    // JANELA CODIFICAR
    private void mostrarJanelaCodificar() {
        Stage janela = new Stage();
        janela.setTitle("Codificar Palavra");

        TextField palavraField = new TextField();


        Button confirmar = new Button("Codificar");
        aplicarHover(confirmar);
        confirmar.setOnAction(e -> {
            String palavra = palavraField.getText().toUpperCase().trim();
            if (palavra.isEmpty()){
                mostrarErro("Digite ao menos uma palavra para codificar.");
                return;
            }
            if (bst.isEmpty()) {
                mostrarErro("Árvore vazia. Insira letras primeiro.");
                return;
            }
            try {
                String codificado = bst.encodeWord(palavra);
                mostrarInfo("Resultado", "Morse: " + codificado);
                janela.close();
            }catch (IllegalArgumentException er){
                mostrarErro(er.getMessage());
            }catch (Exception ex) {
                mostrarErro(ex.getMessage());
            }
        });

        VBox layout = new VBox(10, palavraField, confirmar);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);
        janela.setScene(new Scene(layout, 300, 150));
        janela.show();
    }

    // JANELA DECODIFICAR
    private void mostrarJanelaDecodificar() {
        Stage janela = new Stage();
        janela.setTitle("Decodificar Código Morse");

        TextField codigoField = new TextField();


        Button confirmar = new Button("Decodificar");
        aplicarHover(confirmar);
        confirmar.setOnAction(e -> {
            if (bst.isEmpty()) {
                mostrarErro("Árvore vazia. Insira letras primeiro.");
                return;
            }
            try {
                String decodificado = bst.decodeWord(codigoField.getText());
                mostrarInfo("Resultado", "Palavra: " + decodificado);
                janela.close();
            } catch (Exception ex) {
                mostrarErro(ex.getMessage());
            }
        });

        VBox layout = new VBox(10, codigoField, confirmar);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);
        janela.setScene(new Scene(layout, 350, 150));
        janela.show();
    }

    // MOSTRAR ARVORE
    private void mostrarJanelaArvore() {
        Stage janela = new Stage();
        janela.setTitle("Árvore Morse");

        bst.drawTree(canvas);

        VBox layout = new VBox(canvas);
        layout.setAlignment(Pos.CENTER);
        janela.setScene(new Scene(layout, 1000, 600));
        janela.show();
    }


    private void mostrarErro(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.showAndWait();
    }

    private void mostrarInfo(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
