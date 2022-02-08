package com.example.etlap;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MenuController extends Controller{
    @FXML private TableColumn<Kategoria, String> catName;
    @FXML private Button addNew;
    @FXML private TabPane tab;
    @FXML private Spinner<Integer> percentSpinner;
    @FXML private Spinner<Integer> fixSpinner;
    @FXML private TableView<Etel> listTableView;
    @FXML private TableView<Kategoria> catTableView;
    @FXML private TableColumn<Etel, String> nameTC;
    @FXML private TableColumn<Etel, String> categoryTC;
    @FXML private TableColumn<Etel, Integer> priceTC;
    @FXML private TextArea detailTA;
    private DB db;
    private List<Etel> etlap;
    private List<Kategoria> cat;
    public void initialize() {
        nameTC.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryTC.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceTC.setCellValueFactory(new PropertyValueFactory<>("price"));
        catName.setCellValueFactory(new PropertyValueFactory<>("name"));
        try{
            db = new DB();

            etlapFresh();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML private void onAddNew() {
        if (tab.getSelectionModel().getSelectedIndex() == 0){
            try {
                AddController addController = (AddController) newWindow("add-view.fxml", "Add new dish", 300,300);
                addController.stage.setOnCloseRequest(r->etlapFresh());
                addController.getStage().show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (tab.getSelectionModel().getSelectedIndex() == 1){

        }
    }
    @FXML private void onDelete() {
        if (tab.getSelectionModel().getSelectedIndex() == 0){
            int index = listTableView.getSelectionModel().getSelectedIndex();
            if (index == -1) {
                alert("No selected item to delete");
                return;
            }
            Etel etel = listTableView.getSelectionModel().getSelectedItem();
            if (!confirm(String.format("Are you sure you want to delete this dish: %s?", etel.getName()))) return;
            try {
                if(db.deleteEtel(etel.getId()))alert("Successful deletion");
                else alert("Unsuccessful deletion");
                etlapFresh();
            } catch (Exception e) {
                e.printStackTrace();
        }
        if (tab.getSelectionModel().getSelectedIndex() == 1) {
            int ind = catTableView.getSelectionModel().getSelectedIndex();
            if (ind == -1) {
                alert("No selected item to delete");
                return;
            }
            Kategoria kategoria = catTableView.getSelectionModel().getSelectedItem();
            if (!confirm(String.format("Are you sure you want to delete this category: %s?", etel.getName()))) return;
            try {
                if(db.deleteKategoria(kategoria.getId()))alert("Successful deletion");
                else alert("Unsuccessful deletion");
                etlapFresh();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    }
    @FXML private void onRaisePercent() {
        int index = listTableView.getSelectionModel().getSelectedIndex();
        int percent = percentSpinner.getValue();
        if (index == -1) {
            if (!alertWait(String.format("Are you sure you want to raise all dishes by %d percent?",percent))) return;
            for (Etel e: etlap) {
                e = listTableView.getSelectionModel().getSelectedItem();
                raisePercent(percent, e);
            }
        }
        if(alertWait(String.format("Are you sure you want to raise by %d percent?",percent))){
            Etel etel = listTableView.getSelectionModel().getSelectedItem();
            raisePercent(percent, etel);
        }
    }
    private void raisePercent(int percent, Etel etel) {
        int newPrice = etel.getPrice()*percent/100+etel.getPrice();
        etel.setPrice(newPrice);
        alert(String.format("Successfully raised, new price: %d Ft", newPrice));
        try {
            db.changeEtel(etel);
            etlapFresh();
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }
    @FXML private void onRaiseFix() {
        int index = listTableView.getSelectionModel().getSelectedIndex();
        int fix = fixSpinner.getValue();
        if (index == -1) {
            if (!alertWait(String.format("Are you sure you want to raise all dishes by %d Ft?",fix))) return;
            for (Etel e: etlap) {
                e = listTableView.getSelectionModel().getSelectedItem();
                raiseFix(fix, e);
            }
        }
        if(alertWait(String.format("Are you sure you want to raise by %d Ft?",fix))){
            Etel etel = listTableView.getSelectionModel().getSelectedItem();
            raiseFix(fix, etel);
        }
    }
    private void raiseFix(int fix, Etel etel) {
        int newPrice = fix+etel.getPrice();
        etel.setPrice(newPrice);
        alert(String.format("Successfully raised, new price: %d Ft", newPrice));
        try {
            db.changeEtel(etel);
            etlapFresh();
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }
    private void etlapFresh(){
        try {
            etlap = db.getEtelek();
            cat = db.getKategoria();
            listTableView.getItems().clear();
            catTableView.getItems().clear();
            for (Etel e:etlap) {
                listTableView.getItems().add(e);
            }
            for (Kategoria k:cat) {
                catTableView.getItems().add(k);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    public void onTableClick() {
        Etel etel = listTableView.getSelectionModel().getSelectedItem();
        detailTA.setText(etel.getDetail());
    }
    public void onSelect() {
        if (tab.getSelectionModel().getSelectedIndex() == 0) addNew.setText("Add new dish");
        if (tab.getSelectionModel().getSelectedIndex() == 1) addNew.setText("Add new category");
    }
}