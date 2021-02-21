package sample.controller;

import javafx.fxml.FXML;


import javafx.scene.control.*;

public class ControllerOfUIWindow {
    
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
    void initialize()  {
        String yes = "Является";
        String no = "Не является";

        ToggleGroup group = new ToggleGroup();
        yesAnswer.setToggleGroup(group);
        noAnswer.setToggleGroup(group);

        choice.setOnAction(event -> {
            try {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Results");

                boolean answer = check.checkFormula(formula.getText());

                RadioButton selection = (RadioButton) group.getSelectedToggle();

                if((yes.equals(selection.getText())&&answer)||(no.equals(selection.getText())&&!answer)){
                    alert.setContentText("Ваш ответ правильный!");
                }
                else {
                    alert.setContentText("Ваш ответ неправильный!");
                }

                if (answer){
                    alert.setHeaderText("Формула является СДНФ");
                }
                else {
                    alert.setHeaderText("Формула не является СДНФ");
                }
                alert.showAndWait();


            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }
}

