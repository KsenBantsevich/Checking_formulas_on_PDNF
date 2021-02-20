package sample.controller;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class Controller {
    
    sample.controller.CheckClass check;

    @FXML
    private TextField formula;

    @FXML
    private RadioButton yesAnswer;

    @FXML
    private RadioButton noAnswer;

    @FXML
    private Button choice;


    @FXML
    void initialize() throws  ClassNotFoundException {

        choice.setOnAction(event -> {
            try {
              boolean answer = check.checkFormula(formula.getText());
                System.out.println(answer);




            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }
}

