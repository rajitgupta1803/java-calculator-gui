package Calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    String keyboardInp;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Calculator.fxml"));
        Parent root = loader.load();
        Controller c = loader.getController();
        Scene sceneIntro = new Scene(root, Color.LIGHTGRAY);
        primaryStage.setTitle("Calculator");

        sceneIntro.addEventFilter(KeyEvent.KEY_PRESSED, k -> {
            if ( k.getCode() == KeyCode.SPACE ||  k.getCode() == KeyCode.ENTER){
                k.consume();
            }
        });
        KeyCombination MultiplyCombi = new KeyCodeCombination(KeyCode.DIGIT8, KeyCodeCombination.SHIFT_DOWN);
        KeyCombination PlusCombi = new KeyCodeCombination(KeyCode.EQUALS, KeyCodeCombination.SHIFT_DOWN);
        sceneIntro.setOnKeyPressed(event -> {
            keyboardInp = event.getCode().toString();
            if(MultiplyCombi.match(event)){
                c.handleKey("MULTIPLY");
            }
            else if(PlusCombi.match(event)){
                c.handleKey("ADD");
            }
            else {
                c.handleKey(keyboardInp);
            }
        });

        primaryStage.setScene(sceneIntro);

        String css = this.getClass().getResource("stylesheet.css").toExternalForm();
        sceneIntro.getStylesheets().add(css);

        Image icon = new Image("icon.png");
        primaryStage.getIcons().add(icon);
        primaryStage.show();
        primaryStage.setResizable(false);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
