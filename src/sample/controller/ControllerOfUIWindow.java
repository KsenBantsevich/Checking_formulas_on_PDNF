//////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Выполнена студенткой группы 821703 БГУИР Банцевич Ксенией Андреевной
// Controller of user interface
// 19.01.2021 v1.0
//
package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ControllerOfUIWindow {
    
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

                int answer = CheckClass.checkFormula(formula.getText());

                RadioButton selection = (RadioButton) group.getSelectedToggle();
                if (answer==0){
                    alert.setHeaderText("Формула является СДНФ");
                }

                if(answer==1) {
                    alert.setHeaderText("Формула не является СДНФ");
                }
                if(answer==2){
                    alert.setHeaderText("Будьте внимательнее");
                    alert.setContentText("Вы ввели некорректную строку!");
                }
                else {
                    try {
                        if ((yes.equals(selection.getText()) && answer == 0) || (no.equals(selection.getText()) && answer == 1)) {
                            alert.setContentText("Ваш ответ правильный!");
                        } else {
                            alert.setContentText("Ваш ответ неправильный!");
                        }
                    } catch (Exception e) {
                        alert.setContentText("Вы не выбрали ответ!");
                    }
                }
                alert.showAndWait();


            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}