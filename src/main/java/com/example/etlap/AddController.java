package com.example.etlap;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class AddController extends Controller{
    @FXML private ChoiceBox<Kategoria> choiceCategory;
    @FXML private Spinner<Integer> scrollPrice;
    @FXML private TextArea textDetail;
    @FXML private TextField textName;
    private DB db;

    public void initialize() {
        try{
            db = new DB();
        }catch (Exception e){
            errorWrite(e);
        }
    }

    @FXML public void addOnClick() {
        String name = textName.getText().trim();
        String detail = textDetail.getText().trim();
        int price = scrollPrice.getValue();
        int category = choiceCategory.getSelectionModel().getSelectedIndex();
        if (textName.getText().isEmpty()) alert("You can't leave this field empty!");
        else if (textDetail.getText().isEmpty()) alert("You can't leave this field empty!");
        else if (choiceCategory == null) alert("You can't leave this field empty!");
        try{
            price = scrollPrice.getValue();
        }catch (Exception e){
            alert("Something's wrong");
            return;
        }
        if(price <50 || price > 15000){
            alert("Wrong number");
            return;
        }
        if (category == -1){
            alert("No selected category");
            return;
        }
        try {
            int successful = db.addEtel(name, detail, price, category);
            if (successful == 1) alert("Successfully added");
            else alert("Unsuccessful attempt to add");
        }catch (SQLException e) {
            errorWrite(e);
        }
    }
}
