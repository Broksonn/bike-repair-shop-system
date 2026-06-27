package Serwis;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;


public class Login {

    Connection conn;
    String role = "";
    int serwis_id = -1;

    public Login(Connection conn) throws SQLException, ClassNotFoundException {
        this.conn = conn;
    }

    //logowanie, sprawdzenie czy konto o podanym loginie i haśle istnieje
    public boolean login(String user, String pass) throws SQLException {
        String sql = "select * from uzytkownik where login = ? and haslo = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, user);
        ps.setString(2, pass);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            role = rs.getString(2);
            int id = rs.getInt(1);
            rs.close();
            ps.close();
            if (role.equals("Serwisant")){
                String sId = "select serwisant_id from serwisant where user_id = ?";
                PreparedStatement ps2 = conn.prepareStatement(sId);
                ps2.setInt(1, id);
                ResultSet rs2 = ps2.executeQuery();
                if (rs2.next()) {
                    serwis_id = rs2.getInt(1);
                }
            }
            return true;
        }
        else {
            rs.close();
            ps.close();
            return false;
        }
    }

    public int getSerwis_id() {
        return serwis_id;
    }

    //dodanie nowego konta użytkownika
    public void addUser(String stan, String login, String pass, String phone, String info) throws SQLException {
        String sql = "select dodaj_uzytkownika(?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, stan);
        ps.setString(2, login);
        ps.setString(3, pass);
        ps.setString(4, phone);
        ps.setString(5, info);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int nowyId = rs.getInt(1);
            System.out.println("Nowy user_id: " + nowyId);
            if (stan.equals("Serwisant")) {
                addSerwisant(nowyId);
            }
        }
        rs.close();
        ps.close();
    }

    //dodanie nowego serwisanta
    public void addSerwisant(int id) throws SQLException {
        String sql = "select dodaj_serwisanta(?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int nowyId = rs.getInt(1);
            System.out.println("Nowy serwisant_id: " + nowyId);
            serwis_id = nowyId;
        }
    }

    //zmiana hasła na koncie użytkownika
    public void zmianaHasla(String oldPass, String newPass, String login) throws SQLException {
        String sql = "select * from uzytkownik where login = ? and haslo = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, login);
        ps.setString(2, oldPass);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int id = rs.getInt(1);
            rs.close();
            ps.close();
            sql = "call zmien_haslo(?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, newPass);
            ps.execute();
            ps.close();
        }
    }

    //usunięcie konta użytkownika, usunięcie konta serwistant jeśli użytkownik miał stanowisko serwisant
    public void delUser(int id) throws SQLException {
        String sql0 = "select stanowisko from uzytkownik where user_id = ?";
        PreparedStatement ps2 = conn.prepareStatement(sql0);
        ps2.setInt(1, id);
        ResultSet rs2 = ps2.executeQuery();
        if (rs2.next()) {
            if (rs2.getString("stanowisko").equals("Serwisant")) {
                String sqlSerw = "delete from serwisant where user_id = ?";
                PreparedStatement ps3 = conn.prepareStatement(sqlSerw);
                ps3.setInt(1, id);
                ps3.execute();
                ps3.close();
            }
        }
        ps2.close();
        String sql = "call usun_uzytkownika(?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
        ps.close();
    }

    //pobranie listy użytkowników
    public ArrayList<String[]> seeUsers() throws SQLException {
        String sql = "select * from uzytkownik";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<String[]> userList = new ArrayList<>();
        while (rs.next()) {
            String[] user = new String[6];
            for (int i = 0; i < 6; i++) {
                user[i] = rs.getString(i + 1);
            }
            userList.add(user);
        }
        rs.close();
        ps.close();
        return userList;
    }

    //zmiana danych użytkownika
    public void changeUser(int id, String stanowisko, String numer, String opis) throws SQLException {
        String sql = "call aktualizuj_uzytkownika(?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setString(2, stanowisko);
        ps.setString(3, numer);
        ps.setString(4, opis);
        ps.execute();
        ps.close();
        if (stanowisko.equals("Serwisant")) {
            System.out.println("t1");
            if(!serwisantId(id)){
                System.out.println("t2");
                addSerwisant(id);
            }
        }
    }

    public boolean serwisantId(int id) throws SQLException {
        String sql = "select * from serwisant where user_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            System.out.println(rs.getInt(1) + " " + rs.getString(2));
            return true;
        }
        return false;
    }

    public String getRole() {
        System.out.println(role);
        return role;
    }
}
