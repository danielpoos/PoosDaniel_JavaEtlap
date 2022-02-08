package com.example.etlap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB {
    private final Connection conn;

    public DB() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/etlapdb", "root", "");
    }

    public List<Etel> getEtelek() throws SQLException {
        List<Etel> etlap = new ArrayList<>();
        Statement statement = conn.createStatement();
        String sql = "SELECT etlap.id, etlap.nev, etlap.leiras, etlap.ar, kategoria.nev FROM etlap INNER JOIN kategoria ON etlap.kategoria_id = kategoria.id";
        ResultSet result = statement.executeQuery(sql);
        while(result.next()){
            etlap.add(new Etel(
                    result.getInt("id"),
                    result.getString("nev"),
                    result.getString("leiras"),
                    result.getInt("ar"),
                    result.getString("kategoria.nev")
            ));
        }
        return etlap;
    }
    public int addEtel(String name, String detail, int price, int category) throws SQLException {
        String sql = "INSERT INTO etlap(nev, leiras, ar, kategoria_id) VALUES (?,?,?,?);";
        PreparedStatement prepare = conn.prepareStatement(sql);
        prepare.setString(1, name);
        prepare.setString(2, detail);
        prepare.setInt(3, price);
        prepare.setInt(4, category);
        return prepare.executeUpdate();
    }
    public boolean deleteEtel(int id) throws SQLException {
        String sql = "DELETE FROM etlap WHERE id = ?;";
        PreparedStatement prepare = conn.prepareStatement(sql);
        prepare.setInt(1, id);
        int sorok = prepare.executeUpdate();
        return sorok==1;
    }
    public boolean changeEtel(Etel e) throws SQLException {
        String sql = "UPDATE etlap SET ar= ? WHERE id = ?;";
        PreparedStatement prepare = conn.prepareStatement(sql);
        prepare.setInt(1, e.getPrice());
        prepare.setInt(2, e.getId());
        int rows = prepare.executeUpdate();
        return rows ==1;
    }
    public List<Kategoria> getKategoria() throws SQLException {
        List<Kategoria> cat = new ArrayList<>();
        Statement statement = conn.createStatement();
        String sql = "SELECT * FROM kategoria;";
        ResultSet result = statement.executeQuery(sql);
        while(result.next()){
            cat.add(new Kategoria(
                    result.getInt("id"),
                    result.getString("nev")
            ));
        }
        return cat;
    }
    public int addKategoria(String name) throws SQLException {
        String sql = "INSERT INTO kategoria(nev) VALUES (?);";
        PreparedStatement prepare = conn.prepareStatement(sql);
        prepare.setString(1, name);
        return prepare.executeUpdate();
    }
    public boolean deleteKategoria(int id) throws SQLException {
        String sql = "DELETE FROM kategoria WHERE id = ?;";
        PreparedStatement prepare = conn.prepareStatement(sql);
        prepare.setInt(1, id);
        int sorok = prepare.executeUpdate();
        return sorok==1;
    }
    public boolean changeKategoria(Etel e) throws SQLException {
        String sql = "UPDATE etlap SET nev = ? WHERE id = ?;";
        PreparedStatement prepare = conn.prepareStatement(sql);
        prepare.setString(1, e.getName());
        int rows = prepare.executeUpdate();
        return rows ==1;
    }
}
