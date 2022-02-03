package com.example.etlap;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MenuController extends Controller{
    @FXML private Spinner<Integer> percentSpinner;
    @FXML private Spinner<Integer> fixSpinner;
    @FXML private TableView<Etel> listTableView;
    @FXML private TableColumn<Etel, String> nameTC;
    @FXML private TableColumn<Etel, String> categoryTC;
    @FXML private TableColumn<Etel, Integer> priceTC;
    @FXML private TextArea detailTA;
    private DB db;

    public void initialize() {
        nameTC.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryTC.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceTC.setCellValueFactory(new PropertyValueFactory<>("price"));
        try{
            db = new DB();
            etlapFresh();
        }catch (Exception e){
            errorWrite(e);
        }
    }
    @FXML private void onAddNew() {
        try {
            AddController addController = (AddController) newWindow("add-view", "Add new dish", 540,540);
            addController.stage.setOnCloseRequest(r->etlapFresh());
            addController.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML private void onDelete() {
        int index = listTableView.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            alert("No selected item to delete");
            return;
        }
        Etel etel = listTableView.getSelectionModel().getSelectedItem();
        if (!confirm(String.format("Are you sure you want to delete this dish: %s?", etel.getName()))) return;
        try {
            db.deleteEtel(etel.getId());
            alert("Successful deletion");
            etlapFresh();
        } catch (Exception e) {
            errorWrite(e);
        }
    }
    @FXML private void onRaisePercent() {
        int index = listTableView.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            alert("No selected item for raising");
            return;
        }
        int percent = percentSpinner.getValue();
        if(alertWait(String.format("Are you sure you want to raise by %d percent?",percent))){
            Etel etel = listTableView.getSelectionModel().getSelectedItem();
            int percentValue = etel.getPrice()*percent/100;
            int newPrice = percentValue+etel.getPrice();
            etel.setPrice(newPrice);
            alert(String.format("Successfully raised, new price: %d Ft", newPrice));
            try {
                db.changeEtel(etel);
                etlapFresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML private void onRaiseFix() {
        int index = listTableView.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            alert("No selected item for raising");
            return;
        }
        int fix = fixSpinner.getValue();
        if(alertWait(String.format("Are you sure you want to raise by %d Ft?",fix))){
            Etel etel = listTableView.getSelectionModel().getSelectedItem();
            int newPrice = fix+etel.getPrice();
            etel.setPrice(newPrice);
            alert(String.format("Successfully raised, new price: %d Ft", newPrice));
            try {
                db.changeEtel(etel);
                etlapFresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void setListTV(){

    }
    private void etlapFresh(){
        try {
            List<Etel> etlap = db.getEtelek();
            listTableView.getItems().clear();
            for (Etel e:etlap) {
                listTableView.getItems().add(e);
            }
        } catch (SQLException exception) {
            errorWrite(exception);
        }
    }
    public void onTableClick() {
        Etel etel = listTableView.getSelectionModel().getSelectedItem();
        detailTA.setText(etel.getDetail());
    }
}