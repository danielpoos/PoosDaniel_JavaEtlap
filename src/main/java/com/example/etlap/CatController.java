package com.example.etlap;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

public class CatController extends Controller{
    @FXML private TextField textName;
    private List<Kategoria> categories;
    public DB db;

    public void initialize() {
        try{
            db = new DB();
            categories = db.getKategoria();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void addOnClick() {
        String name = textName.getText().trim();
        if (textName.getText().isEmpty()) alert("You can't leave this field empty!");
        for (Kategoria k:categories) {
            if (textName.getText().equals(k.getName())){
                alert("This category already exists!");
                return;
            }
        }
        try {
            int successful = db.addKategoria(name);
            if (successful == 1) alert("Successfully added");
            else alert("Unsuccessful attempt to add");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
