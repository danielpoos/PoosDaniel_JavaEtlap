package com.example.etlap;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class CatController extends Controller{
    @FXML private TextField textName;
    public DB db;

    public void initialize() {
        try{
            db = new DB();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void addOnClick() {
        String name = textName.getText().trim();
        if (textName.getText().isEmpty()) alert("You can't leave this field empty!");
        try {
            int successful = db.addKategoria(name);
            if (successful == 1) alert("Successfully added");
            else alert("Unsuccessful attempt to add");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
