package Serwis;

import javax.swing.*;
import java.awt.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Connection;
import java.util.Scanner;

public class Main {

    static Connection  conn;

    public Main() throws SQLException, ClassNotFoundException {
    }

    static void main() throws SQLException, ClassNotFoundException {

        String url = "jdbc:postgresql://ep-orange-surf-agw99xvo-pooler.c-2.eu-central-1.aws.neon.tech/neondb?user=neondb_owner&password=npg_NsijS1yTK0Io&sslmode=require&channelBinding=require";     //zmienić na takie jakie macie ustawione/jak sie doda baze na jakiś hosting
        Properties prop = new Properties();
        String user = "majkelhendrysiak";
        String password = "hasloA!1";
        prop.setProperty("user", user);
        prop.setProperty("password", password);
        conn = DriverManager.getConnection(url, prop);  //łączenie z bazą

        GUI gui = new GUI();
        Login login = new Login(conn);
        Magazyn magazyn = new Magazyn(conn);

        gui.setLoginAction(login);
        gui.setMenuAction(magazyn, login);
        gui.setMagazynCzesciAction(magazyn);
        gui.setDodajUzytkownikaAction(login);
        gui.setZmianaHaslaAction(login);
        gui.setUsunUzytkownikaAction(login);
        gui.setAktualizujUzytkownikaAction(login);
        gui.setFormularzZlecenAction(magazyn);
    }
}
