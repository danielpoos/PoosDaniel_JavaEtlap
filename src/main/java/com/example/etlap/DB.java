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
        String sql = "SELECT * FROM etlap;";
        ResultSet result = statement.executeQuery(sql);
        while(result.next()){
            etlap.add(new Etel(
                    result.getInt("id"),
                    result.getString("nev"),
                    result.getString("leiras"),
                    result.getInt("ar"),
                    result.getString("kategoria_id")
            ));
        }
        return etlap;
    }

    public int addEtel(String name, String detail, int price, String category) throws SQLException {
        String sql = "INSERT INTO etlap(nev, leiras, ar, kategoria_id) VALUES (?,?,?,?);";
        PreparedStatement prepare = conn.prepareStatement(sql);
        prepare.setString(1, name);
        prepare.setString(2, detail);
        prepare.setInt(3, price);
        prepare.setString(4, category);
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
        String sql = "UPDATE etlap SET nev = ?, leiras = ?, ar= ?, kategoria_id = ? WHERE id = ?;";
        PreparedStatement prepare = conn.prepareStatement(sql);
        prepare.setString(1, e.getName());
        prepare.setString(4, e.getCategory());
        prepare.setInt(3, e.getPrice());
        prepare.setString(2, e.getDetail());
        prepare.setInt(5, e.getId());
        int rows = prepare.executeUpdate();
        return rows ==1;
    }

}
