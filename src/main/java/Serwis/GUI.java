package Serwis;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;

public class GUI{

    JFrame frame;

    JButton returnButton = new JButton("Powrót");
    //login
    JTextField login = new JTextField("admin");
    JPasswordField password = new JPasswordField("admin");
    JLabel textLogin = new JLabel("Login:");
    JLabel textPassword = new JLabel("Haslo:");
    JButton loginButton = new JButton("Zaloguj się");
    JLabel textLoginWindow =  new JLabel("System Serwisu Rowerowego - Logowanie");

    //menu
    JLabel panelSerw = new JLabel("Witaj!");
    JButton noweZlecenie = new JButton("Nowe zlecenie");
    JButton panelSerwisanta = new JButton("Panel serwisanta");
    JButton magazyn = new JButton("Magazyn części");
    JButton wyloguj = new JButton("Wyloguj");
    JButton magazynRowerow = new JButton("Magazyn Rowerow");
    JButton dodajUzytkownika =  new JButton("Dodaj Użytkownika");
    JButton zmianaHasla = new JButton("Zmień hasło");
    JButton usunUzytkownika = new JButton("Usuń użytkownika");
    JButton aktualizujUzytkownika = new JButton("Aktualizuj użytkownika");
    JButton listaUslug = new JButton("Lista Uslug");
    JButton zakup = new  JButton("Zakup części");
    JButton historia = new JButton("Historia zleceń");

    //magazyn części
    JButton magazynCzesciReturn = new JButton("Powrót");
    JButton magazynCzesciReturn2 = new JButton("Powrót");
    JButton magazynCzesciAdd = new JButton("Dodaj części");

    //nowy uzytkownik
    String[] opcje = {"Administrator", "Serwisant", "Kierownik"};
    JComboBox stanowisko = new JComboBox(opcje);
    JTextField newLogin = new JTextField("");
    JPasswordField newPassword = new JPasswordField("");
    JTextField newPhoneNumber = new JTextField("");
    JTextField informacjeDodatkowe = new JTextField("");
    JButton addUzytkownik = new JButton("Dodaj Użytkownika");

    //zmiana hasla
    JPasswordField newPasswordChange = new JPasswordField("");
    JPasswordField oldPassword = new JPasswordField("");
    JButton zmianaHaslaButton = new JButton("Zmień hasło");

    //usun użytkownika
    JButton usunUzytkownikaButton = new JButton("Usuń użytkownika");
    JComboBox delUsersList = new JComboBox();

    //aktualizuj użytkownika
    JComboBox changeUsersList = new JComboBox();
    JButton aktualizujUzytkownikaButton = new JButton("Aktualizuj dane");
    String [] stanowiskaAkt = {"Administrator", "Serwisant", "Kierownik"};
    JComboBox stanA = new JComboBox(stanowiskaAkt);
    JTextField opisA = new JTextField("");
    JTextField phoneA = new JTextField("");
    JLabel idA = new  JLabel("");

    //formularz zleceń
    JTextField imieF = new JTextField("");
    JTextField nazwF = new JTextField("");
    JTextField numerF = new JTextField("");
    JTextField mailF = new JTextField("");
    JTextField markaF = new JTextField("");
    JTextField modelF = new JTextField("");
    JTextField infoF = new JTextField("");
    JTextField opisUstF = new JTextField("");
    JTextField lokF = new JTextField("");
    JButton przyjmijButton = new JButton("Przyjmij do serwisu");

    //magazyn rowerow
    JButton [] usunRowerButtons = new JButton[0];
    JButton [] przeniesRowerButtons;
    JTextField [] przeniesRowerLok;

    //zlecenia
    JButton [] wejdzZlecenieButtons = new JButton[0];

    String sLogin;
    String sPassword;
    String role;

    int serwisId = -1;

    public GUI() {
        frame = new JFrame("Serwis rowerowy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLayout(null);
        frame.setResizable(false);

        loginWindow();

        frame.setVisible(true);

        SwingUtilities.updateComponentTreeUI(frame);
    }

    //okno logowania
    public void loginWindow(){
        frame.getContentPane().removeAll();
        textLoginWindow.setBounds(300, 100, 800, 50);
        textLoginWindow.setFont(new Font("Arial", Font.PLAIN, 30));
        textLogin.setBounds(400, 200, 200, 30);
        textLogin.setFont(new Font("Arial", Font.PLAIN, 20));
        textPassword.setBounds(400, 250, 200, 30);
        textPassword.setFont(new Font("Arial", Font.PLAIN, 20));
        login.setBounds(600, 200, 200, 30);
        login.setFont(new Font("Arial", Font.PLAIN, 20));
        password.setBounds(600, 250, 200, 30);
        password.setFont(new Font("Arial", Font.PLAIN, 20));
        loginButton.setBounds(500, 300, 200, 30);
        loginButton.setFont(new Font("Arial", Font.PLAIN, 20));

        frame.add(textLoginWindow);
        frame.add(textLogin);
        frame.add(textPassword);
        frame.add(login);
        frame.add(password);
        frame.add(loginButton);

        frame.repaint();
        frame.revalidate();
    }

    //przypisanie przycisków okna logowania
    public void setLoginAction(Login loginC){
        loginButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                sLogin = login.getText();
                sPassword = password.getText();
                try {
                    if(loginC.login(sLogin, sPassword)){
                        role = loginC.getRole();
                        if (role.equals("Serwisant")){
                            serwisId = loginC.getSerwis_id();
                        }
                        else{
                            serwisId = -1;
                        }
                        menuWindow();
                    }
                    else{
                        JOptionPane.showMessageDialog(frame, "Niepoprawne dane logowania!");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        password.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                sLogin = login.getText();
                sPassword = password.getText();
                try {
                    if(loginC.login(sLogin, sPassword)){
                        menuWindow();
                    }
                    else{
                        JOptionPane.showMessageDialog(frame, "Niepoprawne dane logowania!");
                    }
                    role = loginC.getRole();
                    if (role.equals("Serwisant")){
                        serwisId = loginC.getSerwis_id();
                    }
                    else{
                        serwisId = -1;
                    }
                    menuWindow();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    //okno menu głównego
    public void menuWindow(){
        frame.getContentPane().removeAll();

        panelSerw = new JLabel("Witaj " + sLogin + "!");

        panelSerw.setFont(new Font("Arial", Font.PLAIN, 30));
        panelSerw.setBounds(300, 100, 800, 50);
        noweZlecenie.setFont(new Font("Arial", Font.PLAIN, 20));
        noweZlecenie.setBounds(350, 200, 200, 80);
        panelSerwisanta.setFont(new Font("Arial", Font.PLAIN, 20));
        panelSerwisanta.setBounds(350, 300, 200, 80);
        magazyn.setFont(new Font("Arial", Font.PLAIN, 20));
        magazyn.setBounds(650, 300, 200, 80);
        wyloguj.setFont(new Font("Arial", Font.PLAIN, 12));
        wyloguj.setBounds(950, 600, 100, 80);
        magazynRowerow.setFont(new Font("Arial", Font.PLAIN, 20));
        magazynRowerow.setBounds(650, 200, 200, 80);
        dodajUzytkownika.setFont(new Font("Arial", Font.PLAIN, 20));
        dodajUzytkownika.setBounds(350, 400, 200, 80);
        zmianaHasla.setFont(new Font("Arial", Font.PLAIN, 20));
        zmianaHasla.setBounds(650, 400, 200, 80);
        usunUzytkownika.setFont(new Font("Arial", Font.PLAIN, 20));
        usunUzytkownika.setBounds(350, 500, 200, 80);
        aktualizujUzytkownika.setFont(new Font("Arial", Font.PLAIN, 20));
        aktualizujUzytkownika.setBounds(650, 500, 200, 80);
        listaUslug.setFont(new Font("Arial", Font.PLAIN, 20));
        listaUslug.setBounds(350, 600, 200, 80);
        zakup.setFont(new Font("Arial", Font.PLAIN, 20));
        zakup.setBounds(350, 500, 200, 80);
        historia.setFont(new Font("Arial", Font.PLAIN, 20));
        historia.setBounds(650, 600, 200, 80);

        frame.add(panelSerw);
        frame.add(noweZlecenie);
        frame.add(magazyn);
        frame.add(wyloguj);
        frame.add(panelSerwisanta);
        frame.add(magazynRowerow);
        frame.add(zmianaHasla);
        frame.add(listaUslug);
        frame.add(historia);

        if (role.equals("Administrator") || role.equals("Kierownik")){
            frame.add(dodajUzytkownika);
            frame.add(aktualizujUzytkownika);
            if (role.equals("Administrator")){
                frame.add(usunUzytkownika);
            }
            else {
                frame.add(zakup);
            }
        }

        frame.repaint();
        frame.revalidate();
    }

    //przypisanie przycisków menu głównego
    public void setMenuAction(Magazyn magazyn0, Login loginC){
        noweZlecenie.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                formularzZleceniaWindow(magazyn0);
            }
        });
        panelSerwisanta.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    przegladZlecenWindow(magazyn0);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        magazyn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    magazynWindow(magazyn0.magazyn());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        magazynRowerow.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    magazynRowerowWindow(magazyn0.magazynRowerow(), magazyn0);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        wyloguj.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                login.setText("");
                password.setText("");
                sLogin = "";
                sPassword = "";
                loginWindow();
            }
        });
        dodajUzytkownika.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                dodajUzytkownikaWindow();
            }
        });
        zmianaHasla.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                zmianaHaslaWindow();
            }
        });
        usunUzytkownika.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                usunUzytkownikaWindow(loginC);
            }
        });
        aktualizujUzytkownika.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                aktualizujUzytkownikaWindow(loginC);
            }
        });
        listaUslug.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    listaUslugWindow(magazyn0);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        zakup.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    magazyn0.zakup_czesci();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        historia.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    historiaZlecenWindow(magazyn0);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    //okno magazynu części
    public void magazynWindow(ArrayList<String[]> magazyn){
        frame.getContentPane().removeAll();

        JLabel label = new JLabel("Magazyn części");
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        label.setBounds(490, 35, 800, 50);
        JTable table = new JTable(magazyn.size(), 5);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(850);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(0).setHeaderValue("ID");
        table.getColumnModel().getColumn(1).setHeaderValue("Producent");
        table.getColumnModel().getColumn(2).setHeaderValue("Cena");
        table.getColumnModel().getColumn(3).setHeaderValue("Ilość minimalna");
        table.getColumnModel().getColumn(4).setHeaderValue("Ilość");
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 20));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 100, 1200, 600);

        magazynCzesciAdd.setFont(new Font("Arial", Font.PLAIN, 20));
        magazynCzesciAdd.setBounds(950, 0, 200, 50);
        magazynCzesciReturn.setFont(new Font("Arial", Font.PLAIN, 20));
        magazynCzesciReturn.setBounds(950, 50, 200, 50);

        frame.add(scrollPane);
        frame.add(label);
        frame.add(magazynCzesciReturn);
        frame.add(magazynCzesciAdd);

        for(int i = 0; i < magazyn.size(); i++){
            for(int j = 0; j < 5; j++){
                table.setValueAt(magazyn.get(i)[j], i, j);
            }
        }

        frame.repaint();
        frame.revalidate();
    }

    //przypisanie przycisków magazynu części
    public void setMagazynCzesciAction(Magazyn magazynCzesci){
        magazynCzesciAdd.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    dodajCzesciWindow(magazynCzesci);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        magazynCzesciReturn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                menuWindow();
            }
        });
    }

    //formularz dodania nowego konta użytkownika
    public void dodajUzytkownikaWindow(){
        frame.getContentPane().removeAll();

        JLabel label = new JLabel("Dodaj Użytkownika");
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        label.setBounds(495, 35, 600, 50);

        JLabel newLoginLabel = new JLabel("Login:");
        JLabel newPasswordLabel = new JLabel("Hasło:");
        JLabel newPhoneNumberLabel = new JLabel("Numer Telefonu:");
        JLabel informacjeDodatkoweLabel = new JLabel("Informacje dodatkowe:");

        newLoginLabel.setBounds(290, 150, 600, 50);
        newPasswordLabel.setBounds(290, 200, 600, 50);
        newPhoneNumberLabel.setBounds(290, 250, 600, 50);
        informacjeDodatkoweLabel.setBounds(290, 300, 600, 50);

        stanowisko.setFont(new Font("Arial", Font.PLAIN, 20));
        newLoginLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        newPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        newPhoneNumberLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        informacjeDodatkoweLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        newLogin.setFont(new Font("Arial", Font.PLAIN, 20));
        newPassword.setFont(new Font("Arial", Font.PLAIN, 20));
        newPhoneNumber.setFont(new Font("Arial", Font.PLAIN, 20));
        informacjeDodatkowe.setFont(new Font("Arial", Font.PLAIN, 20));
        addUzytkownik.setFont(new Font("Arial", Font.PLAIN, 20));

        stanowisko.setBounds(500, 100, 400, 40);
        newLogin.setBounds(500, 150, 400, 40);
        newPassword.setBounds(500, 200, 400, 40);
        newPhoneNumber.setBounds(500, 250, 400, 40);
        informacjeDodatkowe.setBounds(500, 300, 400, 40);
        addUzytkownik.setBounds(600, 400, 200, 40);

        frame.add(label);
        frame.add(stanowisko);
        frame.add(newLogin);
        frame.add(newPassword);
        frame.add(newPhoneNumber);
        frame.add(informacjeDodatkowe);
        frame.add(addUzytkownik);

        returnButton.setBounds(100, 600, 200, 80);
        returnButton.setFont(new Font("Arial", Font.PLAIN, 20));
        frame.add(returnButton);

        frame.add(newLoginLabel);
        frame.add(newPasswordLabel);
        frame.add(newPhoneNumberLabel);
        frame.add(informacjeDodatkoweLabel);

        frame.repaint();
        frame.revalidate();
    }

    //przypisanie przycisków dodania konta użytkownika
    public void setDodajUzytkownikaAction(Login login1){
        addUzytkownik.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    login1.addUser((String) stanowisko.getSelectedItem(), newLogin.getText(), newPassword.getText(), newPhoneNumber.getText(), informacjeDodatkowe.getText());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Niepoprawne dane!");
                    throw new RuntimeException(ex);
                }
                menuWindow();
            }
        });
        returnButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                menuWindow();
            }
        });
    }

    //okno zmiany hasła obecne zalogowanego konta
    public void zmianaHaslaWindow(){
        frame.getContentPane().removeAll();

        JLabel label = new JLabel("Zmiana hasła");
        JLabel oldPasswordLabel = new JLabel("Stare hasło:");
        JLabel newPasswordLabel = new JLabel("Nowe hasło:");

        label.setFont(new Font("Arial", Font.PLAIN, 30));
        oldPassword.setFont(new Font("Arial", Font.PLAIN, 20));
        newPasswordChange.setFont(new Font("Arial", Font.PLAIN, 20));
        oldPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        newPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        zmianaHaslaButton.setFont(new Font("Arial", Font.PLAIN, 20));

        label.setBounds(495, 50, 600, 50);
        oldPassword.setBounds(500, 100, 200, 50);
        newPasswordChange.setBounds(500, 160, 200, 50);
        oldPasswordLabel.setBounds(300, 100, 200, 50);
        newPasswordLabel.setBounds(300, 160, 200, 50);
        zmianaHaslaButton.setBounds(400, 250, 200, 50);

        frame.add(label);
        frame.add(oldPassword);
        frame.add(newPasswordChange);
        frame.add(newPasswordLabel);
        frame.add(oldPasswordLabel);
        frame.add(zmianaHaslaButton);

        returnButton.setBounds(100, 600, 200, 80);
        returnButton.setFont(new Font("Arial", Font.PLAIN, 20));
        frame.add(returnButton);

        frame.repaint();
        frame.revalidate();
    }

    //przypisanie przycisków okna zmiany hasło
    public void setZmianaHaslaAction(Login login1){
        zmianaHaslaButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    login1.zmianaHasla(oldPassword.getText(), newPasswordChange.getText(), sLogin);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                oldPassword.setText("");
                newPasswordChange.setText("");
                menuWindow();
            }
        });
    }

    //okno usuwania użytkownika
    public void usunUzytkownikaWindow(Login login1){
        frame.getContentPane().removeAll();

        JLabel idUsunLabel = new JLabel("Usuń użytkownika");
        JLabel idZmianaLabel = new JLabel("Login: ");

        idUsunLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        idZmianaLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        idUsunLabel.setBounds(500, 50, 600, 50);
        idZmianaLabel.setBounds(20, 100, 200, 50);


        ArrayList<String[]> delUser =  new ArrayList<>();
        try {
            delUser = login1.seeUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String[] usernames = new String[delUser.size()];
        for (int i = 0; i < delUser.size(); i++) {
            usernames[i] = delUser.get(i)[2];
        }
        delUsersList = new JComboBox<>(usernames);

        delUsersList.setFont(new Font("Arial", Font.PLAIN, 20));
        delUsersList.setBounds(80, 100, 200, 50);
        usunUzytkownikaButton.setFont(new Font("Arial", Font.PLAIN, 20));
        usunUzytkownikaButton.setBounds(500, 600, 200, 80);

        JLabel idL = new JLabel("ID:");
        JLabel stanL =  new JLabel("Stanowisko:");
        JLabel loginL = new JLabel("Login:");
        JLabel opisL = new JLabel("Dodatkowe informacje:");

        JLabel id = new JLabel(delUser.get(0)[0]);
        JLabel stan = new JLabel(delUser.get(0)[1]);
        JLabel loginU = new JLabel(delUser.get(0)[2]);
        JLabel opis = new JLabel(delUser.get(0)[5]);
        usunUzytkownikaButton.setText("Usuń " + delUser.get(0)[0]);

        ArrayList<String[]> finalDelUser = delUser;
        delUsersList.addItemListener(new  ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e){
                if (e.getStateChange() == ItemEvent.SELECTED){
                    //System.out.println(e.getItem().toString());
                    for(int i = 0; i < finalDelUser.size(); i++){
                        if(e.getItem().toString().equals(finalDelUser.get(i)[2])){
                            id.setText(finalDelUser.get(i)[0]);
                            stan.setText(finalDelUser.get(i)[1]);
                            loginU.setText(finalDelUser.get(i)[2]);
                            opis.setText(finalDelUser.get(i)[5]);
                            usunUzytkownikaButton.setText("Usuń " + finalDelUser.get(i)[0]);
                            break;
                        }
                    }
                }
            }
        });

        idL.setFont(new Font("Arial", Font.PLAIN, 20));
        stanL.setFont(new Font("Arial", Font.PLAIN, 20));
        loginL.setFont(new Font("Arial", Font.PLAIN, 20));
        opisL.setFont(new Font("Arial", Font.PLAIN, 20));
        id.setFont(new Font("Arial", Font.PLAIN, 20));
        stan.setFont(new Font("Arial", Font.PLAIN, 20));
        loginU.setFont(new Font("Arial", Font.PLAIN, 20));
        opis.setFont(new Font("Arial", Font.PLAIN, 20));

        idL.setBounds(300, 100, 250, 50);
        stanL.setBounds(300, 150, 250, 50);
        loginL.setBounds(300, 200, 250, 50);
        opisL.setBounds(300, 250, 250, 50);
        id.setBounds(550, 100, 400, 50);
        stan.setBounds(550, 150, 400, 50);
        loginU.setBounds(550, 200, 400, 50);
        opis.setBounds(550, 250, 400, 50);

        frame.add(idUsunLabel);
        frame.add(idZmianaLabel);
        frame.add(delUsersList);
        frame.add(usunUzytkownikaButton);

        frame.add(loginL);
        frame.add(opisL);
        frame.add(idL);
        frame.add(stanL);
        frame.add(opis);
        frame.add(id);
        frame.add(stan);
        frame.add(loginU);

        returnButton.setBounds(100, 600, 200, 80);
        returnButton.setFont(new Font("Arial", Font.PLAIN, 20));
        frame.add(returnButton);

        frame.repaint();
        frame.revalidate();
    }

    //przypisanie funckji do przycisków i listy rozwijalnej okna usunięcia użytkownika
    public void setUsunUzytkownikaAction(Login login1){
        usunUzytkownikaButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String text = usunUzytkownikaButton.getText().substring(5);
                int id = Integer.parseInt(text);
                //System.out.println(text);
                try {
                    if(!delUsersList.getSelectedItem().toString().equals(sLogin)){
                        login1.delUser(id);
                    }
                    else {
                        JOptionPane.showMessageDialog(frame, "Nie można usunąć swojego konta!");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                menuWindow();
            }
        });
    }

    //formularz aktualizacji danych konta użytkownika
    public void aktualizujUzytkownikaWindow(Login login1){
        frame.getContentPane().removeAll();

        ArrayList<String[]> changeUser =  new ArrayList<>();
        try {
            changeUser = login1.seeUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String[] ids = new String[changeUser.size()];
        for (int i = 0; i < changeUser.size(); i++) {
            ids[i] = changeUser.get(i)[0];
        }
        changeUsersList = new JComboBox<>(ids);
        changeUsersList.setFont(new Font("Arial", Font.PLAIN, 20));
        changeUsersList.setBounds(300, 100, 200, 50);

        returnButton.setBounds(100, 600, 200, 80);
        returnButton.setFont(new Font("Arial", Font.PLAIN, 20));
        frame.add(returnButton);

        JLabel idL = new JLabel("ID:");
        JLabel stanL =  new JLabel("Stanowisko:");
        JLabel loginL = new JLabel("Login:");
        JLabel opisL = new JLabel("Dodatkowe informacje:");
        JLabel phoneL= new JLabel("Numer telefonu:");

        JLabel loginA = new JLabel(changeUser.get(0)[2]);
        JLabel idA = new JLabel(changeUser.get(0)[0]);

        opisA = new JTextField(changeUser.get(0)[5]);
        phoneA = new JTextField(changeUser.get(0)[4]);

        ArrayList<String[]> finalChangeUser = changeUser;
        changeUsersList.addItemListener(new  ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e){
                if (e.getStateChange() == ItemEvent.SELECTED){
                    //System.out.println(e.getItem().toString());
                    for(int i = 0; i < finalChangeUser.size(); i++){
                        if(e.getItem().toString().equals(finalChangeUser.get(i)[0])){
                            idA.setText(finalChangeUser.get(i)[0]);
                            stanA.setSelectedItem(finalChangeUser.get(i)[1]);
                            loginA.setText(finalChangeUser.get(i)[2]);
                            opisA.setText(finalChangeUser.get(i)[5]);
                            phoneA.setText(finalChangeUser.get(i)[4]);
                            break;
                        }
                    }
                }
            }
        });

        idL.setFont(new Font("Arial", Font.PLAIN, 20));
        stanL.setFont(new Font("Arial", Font.PLAIN, 20));
        loginL.setFont(new Font("Arial", Font.PLAIN, 20));
        opisL.setFont(new Font("Arial", Font.PLAIN, 20));
        idA.setFont(new Font("Arial", Font.PLAIN, 20));
        stanA.setFont(new Font("Arial", Font.PLAIN, 20));
        loginA.setFont(new Font("Arial", Font.PLAIN, 20));
        opisA.setFont(new Font("Arial", Font.PLAIN, 20));
        phoneA.setFont(new Font("Arial", Font.PLAIN, 20));
        phoneL.setFont(new Font("Arial", Font.PLAIN, 20));

        idL.setBounds(300, 100, 250, 50);
        stanL.setBounds(300, 200, 250, 50);
        loginL.setBounds(300, 150, 250, 50);
        opisL.setBounds(300, 300, 250, 50);
        idA.setBounds(550, 100, 400, 50);
        stanA.setBounds(550, 200, 400, 50);
        loginA.setBounds(550, 150, 400, 50);
        opisA.setBounds(550, 300, 400, 50);
        phoneA.setBounds(550, 250, 400, 50);
        phoneL.setBounds(300, 250, 400, 50);

        aktualizujUzytkownikaButton.setFont(new Font("Arial", Font.PLAIN, 20));
        aktualizujUzytkownikaButton.setBounds(500, 600, 200, 50);

        frame.add(changeUsersList);
        frame.add(aktualizujUzytkownikaButton);
        frame.add(idA);
        frame.add(idL);
        frame.add(stanA);
        frame.add(stanL);
        frame.add(opisA);
        frame.add(opisL);
        frame.add(loginA);
        frame.add(loginL);
        frame.add(phoneA);
        frame.add(phoneL);

        frame.repaint();
        frame.revalidate();
    }

    //przypisanie przycisków okno aktualizacji konta użytkownika
    public void setAktualizujUzytkownikaAction(Login login1) {
        aktualizujUzytkownikaButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    login1.changeUser(Integer.parseInt(changeUsersList.getSelectedItem().toString()), stanA.getSelectedItem().toString(), phoneA.getText(), opisA.getText());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Niepoprawne dane!");
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    //formularz dodawania nowych zleceń
    public void formularzZleceniaWindow(Magazyn magazyn0) {
        frame.getContentPane().removeAll();

        returnButton.setBounds(100, 600, 200, 80);
        returnButton.setFont(new Font("Arial", Font.PLAIN, 20));
        frame.add(returnButton);

        JLabel form = new JLabel("Formularz Nowego Zlecenia");
        form.setFont(new Font("Arial", Font.PLAIN, 30));
        form.setBounds(400, 10, 600, 50);
        frame.add(form);

        JLabel daneKlienta = new JLabel("1. Dane Klienta");
        JLabel imieL = new JLabel("Imie:");
        JLabel nazwL = new JLabel("Nazwisko:");
        JLabel numerL = new JLabel("Numer:");
        JLabel mailL = new JLabel("Email:");
        daneKlienta.setFont(new Font("Arial", Font.BOLD, 25));
        imieL.setFont(new Font("Arial", Font.PLAIN, 20));
        nazwL.setFont(new Font("Arial", Font.PLAIN, 20));
        numerL.setFont(new Font("Arial", Font.PLAIN, 20));
        mailL.setFont(new Font("Arial", Font.PLAIN, 20));
        daneKlienta.setBounds(100, 50, 400, 50);
        imieL.setBounds(100, 85, 400, 50);
        nazwL.setBounds(100, 115, 400, 50);
        numerL.setBounds(100, 145, 400, 50);
        mailL.setBounds(100, 175, 400, 50);
        frame.add(daneKlienta);
        frame.add(imieL);
        frame.add(nazwL);
        frame.add(numerL);
        frame.add(mailL);

        imieF.setFont(new Font("Arial", Font.PLAIN, 20));
        nazwF.setFont(new Font("Arial", Font.PLAIN, 20));
        numerF.setFont(new Font("Arial", Font.PLAIN, 20));
        mailF.setFont(new Font("Arial", Font.PLAIN, 20));
        imieF.setBounds(300, 100, 400, 30);
        nazwF.setBounds(300, 130, 400, 30);
        numerF.setBounds(300, 160, 400, 30);
        mailF.setBounds(300, 190, 400, 30);
        frame.add(imieF);
        frame.add(nazwF);
        frame.add(numerF);
        frame.add(mailF);

        JLabel rower = new JLabel("2. Dane roweru");
        JLabel markaL = new JLabel("Marka:");
        JLabel ModelL = new JLabel("Model:");
        JLabel infoL = new JLabel("Info dodatkowe:");
        rower.setFont(new Font("Arial", Font.BOLD, 25));
        markaL.setFont(new Font("Arial", Font.PLAIN, 20));
        ModelL.setFont(new Font("Arial", Font.PLAIN, 20));
        infoL.setFont(new Font("Arial", Font.PLAIN, 20));
        rower.setBounds(100, 220, 400, 50);
        markaL.setBounds(100, 245, 400, 50);
        ModelL.setBounds(100, 275, 400, 50);
        infoL.setBounds(100, 305, 400, 50);
        frame.add(rower);
        frame.add(markaL);
        frame.add(ModelL);
        frame.add(infoL);

        markaF.setFont(new Font("Arial", Font.PLAIN, 20));
        modelF.setFont(new Font("Arial", Font.PLAIN, 20));
        infoF.setFont(new Font("Arial", Font.PLAIN, 20));
        markaF.setBounds(300, 260, 400, 30);
        modelF.setBounds(300, 290, 400, 30);
        infoF.setBounds(300, 320, 400, 30);
        frame.add(markaF);
        frame.add(modelF);
        frame.add(infoF);

        JLabel szczegoly = new JLabel("3. Szczegóły Zlecenia");
        JLabel opisUstL = new JLabel("Opis usterki:");
        szczegoly.setFont(new Font("Arial", Font.BOLD, 25));
        opisUstL.setFont(new Font("Arial", Font.PLAIN, 20));
        szczegoly.setBounds(100, 350, 400, 50);
        opisUstL.setBounds(100, 375, 400, 50);
        frame.add(szczegoly);
        frame.add(opisUstL);

        opisUstF.setFont(new Font("Arial", Font.PLAIN, 20));
        opisUstF.setBounds(300, 390, 800, 30);
        frame.add(opisUstF);

        JLabel miejsce = new  JLabel("4. Magazyn (WYMAGANE!)");
        JLabel dostL = new JLabel("Dostępność miejsca:");
        JLabel lokL = new JLabel("Lokalizacja (Wieszak):");
        miejsce.setFont(new Font("Arial", Font.BOLD, 25));
        lokL.setFont(new Font("Arial", Font.PLAIN, 20));
        dostL.setFont(new Font("Arial", Font.PLAIN, 20));
        miejsce.setBounds(100, 420, 400, 50);
        dostL.setBounds(100, 445, 400, 50);
        lokL.setBounds(100, 475, 400, 50);
        frame.add(miejsce);
        frame.add(lokL);
        frame.add(dostL);

        ArrayList <String[]> zlecenia = new ArrayList();

        try {
            zlecenia = magazyn0.magazynRowerow();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int dostepneMiejsca = 20 - zlecenia.size();
        JLabel dostMiejsca = new JLabel(String.valueOf(dostepneMiejsca));
        if (dostepneMiejsca <= 0) {
            dostMiejsca.setText("Brak");
        }

        dostMiejsca.setFont(new Font("Arial", Font.PLAIN, 20));
        dostMiejsca.setBounds(300, 445, 400, 50);


        frame.add(dostMiejsca);



        lokF.setFont(new Font("Arial", Font.PLAIN, 20));
        lokF.setBounds(300, 490, 400, 30);
        frame.add(lokF);

        przyjmijButton.setFont(new Font("Arial", Font.PLAIN, 20));
        przyjmijButton.setBounds(600, 600, 250, 80);
        frame.add(przyjmijButton);

        frame.repaint();
        frame.revalidate();
    }

    //przypisanie akcji do przycisków formularza przyjęcia zlecenia
    public void setFormularzZlecenAction(Magazyn magazyn){
        przyjmijButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    magazyn.przyjmijDoSerwisu(imieF.getText(), nazwF.getText(), numerF.getText(), mailF.getText(), markaF.getText(), modelF.getText(), infoF.getText(), opisUstF.getText(), lokF.getText());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Niepoprawne dane!");
                    throw new RuntimeException(ex);
                }
                menuWindow();
                imieF.setText("");
                nazwF.setText("");
                numerF.setText("");
                mailF.setText("");
                markaF.setText("");
                modelF.setText("");
                infoF.setText("");
                opisUstF.setText("");
                lokF.setText("");
            }
        });
    }

    //okno magazynu rowerów
    public void magazynRowerowWindow(ArrayList<String[]> magazynRowerow, Magazyn magazyn){
        frame.getContentPane().removeAll();

        JLabel label = new JLabel("Magazyn rowerów");
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        label.setBounds(500, 30, 400, 30);
        frame.add(label);

        JTable table = new JTable(magazynRowerow.size(), 3);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 20));
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(350);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(0).setHeaderValue("Miejsce");
        table.getColumnModel().getColumn(1).setHeaderValue("Rower");
        table.getColumnModel().getColumn(2).setHeaderValue("Czas postoju");
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 100, 600, 700);

        for (int i = 0; i < magazynRowerow.size(); i++) {
            table.setValueAt(magazynRowerow.get(i)[0], i, 0);
            table.setValueAt(magazynRowerow.get(i)[1] + " " + magazynRowerow.get(i)[2], i, 1);
            table.setValueAt(magazynRowerow.get(i)[3], i, 2);
        }
        frame.add(scrollPane);

        usunRowerButtons = new JButton[magazynRowerow.size()];
        przeniesRowerButtons = new JButton[magazynRowerow.size()];
        przeniesRowerLok = new  JTextField[magazynRowerow.size()];

        for (int i = 0; i < magazynRowerow.size(); i++) {
            usunRowerButtons[i] = new JButton("Usuń");
            usunRowerButtons[i].setFont(new Font("Arial", Font.PLAIN, 20));
            usunRowerButtons[i].setBounds(600, 120 + i * 30, 200, 30);
            przeniesRowerButtons[i] = new JButton("Przenieś");
            przeniesRowerButtons[i].setFont(new Font("Arial", Font.PLAIN, 20));
            przeniesRowerButtons[i].setBounds(1000, 120 + i * 30, 200, 30);
            przeniesRowerLok[i] = new JTextField("");
            przeniesRowerLok[i].setFont(new Font("Arial", Font.PLAIN, 20));
            przeniesRowerLok[i].setBounds(800, 120 + i * 30, 200, 30);

            int finalI = i;
            usunRowerButtons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        magazyn.deleteRower(Integer.parseInt(magazynRowerow.get(finalI)[0]));
                        magazynRowerowWindow(magazyn.magazynRowerow(), magazyn);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            przeniesRowerButtons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (!przeniesRowerLok[finalI].getText().equals("")) {
                        try {
                            magazyn.przeniesRower(Integer.parseInt(magazynRowerow.get(finalI)[4]), przeniesRowerLok[finalI].getText());
                            magazynRowerowWindow(magazyn.magazynRowerow(), magazyn);
                        } catch (SQLException ex) {

                            throw new RuntimeException(ex);
                        }
                    }
                }
            });

            frame.add(usunRowerButtons[i]);
            frame.add(przeniesRowerButtons[i]);
            frame.add(przeniesRowerLok[i]);
        }

        returnButton.setBounds(100, 0, 200, 80);
        returnButton.setFont(new Font("Arial", Font.PLAIN, 20));
        frame.add(returnButton);

        frame.repaint();
        frame.revalidate();
    }

    //okno przeglądu obecnie nie zakończonych zleceń
    public void przegladZlecenWindow(Magazyn magazyn) throws SQLException {
        frame.getContentPane().removeAll();

        ArrayList<String[]> zlecenia = magazyn.zlecenia();

        JLabel zleceniaLabel = new JLabel("Zlecenia");
        zleceniaLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        zleceniaLabel.setBounds(550, 20, 200, 30);
        frame.add(zleceniaLabel);

        JTable table = new JTable(zlecenia.size(), 6);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 20));
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);
        table.getColumnModel().getColumn(3).setPreferredWidth(50);
        table.getColumnModel().getColumn(4).setPreferredWidth(200);
        table.getColumnModel().getColumn(5).setPreferredWidth(150);
        table.getColumnModel().getColumn(0).setHeaderValue("Id");
        table.getColumnModel().getColumn(1).setHeaderValue("Klient");
        table.getColumnModel().getColumn(2).setHeaderValue("Rower");
        table.getColumnModel().getColumn(3).setHeaderValue("Lokalizacja");
        table.getColumnModel().getColumn(4).setHeaderValue("Serwisant");
        table.getColumnModel().getColumn(5).setHeaderValue("Status");
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 100, 1050, 700);

        for (int i = 0; i < zlecenia.size(); i++) {
            for (int j = 0; j < 6; j++) {
                table.setValueAt(zlecenia.get(i)[j], i, j);
            }
        }

        wejdzZlecenieButtons = new JButton[zlecenia.size()];
        for (int i = 0; i < zlecenia.size(); i++) {
            wejdzZlecenieButtons[i] = new JButton("Otwórz");
            wejdzZlecenieButtons[i].setFont(new Font("Arial", Font.PLAIN, 20));
            wejdzZlecenieButtons[i].setBounds(1050, 120 + 30 * i, 150, 30);
            frame.add(wejdzZlecenieButtons[i]);
            int finalI = i;
            wejdzZlecenieButtons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        widokZleceniaWindow(magazyn, zlecenia.get(finalI));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }

        returnButton.setBounds(100, 0, 200, 80);
        returnButton.setFont(new Font("Arial", Font.PLAIN, 20));
        frame.add(returnButton);

        frame.add(scrollPane);
        frame.repaint();
        frame.revalidate();
    }

    //okno do wyświetlania wybranego zlecenia
    public void widokZleceniaWindow(Magazyn magazyn, String [] zlecenie) throws SQLException {
        frame.getContentPane().removeAll();

        String labelText = "Zlecenie: " + zlecenie[0];
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        label.setBounds(550, 30, 200, 30);
        frame.add(label);

        JButton zlecenieReturnButton = new JButton("Powrót");
        zlecenieReturnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    przegladZlecenWindow(magazyn);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        zlecenieReturnButton.setBounds(100, 0, 200, 80);
        zlecenieReturnButton.setFont(new Font("Arial", Font.PLAIN, 20));
        frame.add(zlecenieReturnButton);

        JLabel klientLabel = new JLabel("Klient:");
        JLabel rowerLabel = new JLabel("Rower:");
        JLabel miejsceLabel = new JLabel("Miejsce:");
        JLabel serwisantLabel = new JLabel("Serwisant:");
        klientLabel.setBounds(30, 100, 200, 30);
        rowerLabel.setBounds(30, 150, 200, 30);
        miejsceLabel.setBounds(30, 200, 200, 30);
        serwisantLabel.setBounds(30, 250, 200, 30);
        klientLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        rowerLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        miejsceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        serwisantLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel klient =  new JLabel(zlecenie[1]);
        JLabel rower =  new JLabel(zlecenie[2]);
        JLabel miejsce =  new JLabel(zlecenie[3]);
        JLabel serwisant =  new JLabel(zlecenie[4]);
        klient.setFont(new Font("Arial", Font.PLAIN, 20));
        rower.setFont(new Font("Arial", Font.PLAIN, 20));
        miejsce.setFont(new Font("Arial", Font.PLAIN, 20));
        serwisant.setFont(new Font("Arial", Font.PLAIN, 20));
        klient.setBounds(130, 100, 200, 30);
        rower.setBounds(130, 150, 200, 30);
        miejsce.setBounds(130, 200, 200, 30);
        serwisant.setBounds(130, 250, 200, 30);

        frame.add(klientLabel);
        frame.add(rowerLabel);
        frame.add(miejsceLabel);
        frame.add(serwisantLabel);

        frame.add(klient);
        frame.add(rower);
        frame.add(miejsce);
        frame.add(serwisant);


        JButton przyjmij = new  JButton("Przyjmij zlecenie");
        przyjmij.setFont(new Font("Arial", Font.PLAIN, 20));
        przyjmij.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                zlecenie[4] = sLogin;
                frame.remove(przyjmij);
                try {
                    magazyn.setSerwisant(Integer.parseInt(zlecenie[0]), serwisId);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                serwisantLabel.setText("Serwisant: " + zlecenie[4]);
            }
        });
        przyjmij.setBounds(900, 100, 200, 80);

        if (zlecenie[4] == null && role.equals("Serwisant")) {
            frame.add(przyjmij);
        }

        ArrayList<String[]> listaCzesci = magazyn.getListaCzesci(Integer.parseInt(zlecenie[0]));

        JTable table = new JTable(listaCzesci.size(), 4);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getColumnModel().getColumn(0).setPreferredWidth(300);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(0).setHeaderValue("Nazwa");
        table.getColumnModel().getColumn(1).setHeaderValue("Cena/szt");
        table.getColumnModel().getColumn(2).setHeaderValue("Ilosc");
        table.getColumnModel().getColumn(3).setHeaderValue("Suma");

        for (int i = 0; i < listaCzesci.size(); i++) {
            for (int j = 0; j < 3; j++) {
                table.setValueAt(listaCzesci.get(i)[j], i, j);
            }
            table.setValueAt(Double.parseDouble(listaCzesci.get(i)[1]) * Double.parseDouble(listaCzesci.get(i)[2]), i, 3);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 300, 550, 400);

        frame.add(scrollPane);

        JButton dodajCzesci = new JButton("Dodaj Czesci");
        dodajCzesci.setFont(new Font("Arial", Font.PLAIN, 20));
        dodajCzesci.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    dodajCzesciWindow(magazyn.magazyn(), magazyn, zlecenie);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        dodajCzesci.setBounds(50, 700, 200, 50);
        frame.add(dodajCzesci);

        JButton rozlicz = new JButton("Zakończ zlecenie");
        rozlicz.setFont(new Font("Arial", Font.PLAIN, 20));
        rozlicz.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    magazyn.zamknijZlecenie(Integer.parseInt(zlecenie[0]));
                    przegladZlecenWindow(magazyn);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        rozlicz.setBounds(900, 200, 200, 80);
        frame.add(rozlicz);

        ArrayList<String[]> listaUslug = magazyn.getListaUslug(Integer.parseInt(zlecenie[0]));
        JTable tableUsl = new JTable(listaUslug.size(), 2);
        tableUsl.setFont(new Font("Arial", Font.PLAIN, 12));
        tableUsl.getColumnModel().getColumn(0).setPreferredWidth(400);
        tableUsl.getColumnModel().getColumn(1).setPreferredWidth(100);
        tableUsl.getColumnModel().getColumn(0).setHeaderValue("Nazwa usługi");
        tableUsl.getColumnModel().getColumn(1).setHeaderValue("Cena");

        for (int i = 0; i < listaUslug.size(); i++) {
            for (int j = 0; j < 2; j++) {
                tableUsl.setValueAt(listaUslug.get(i)[j], i, j);
            }
        }

        String[] statusy = magazyn.getStatusy();

        JComboBox zmienStatus = new  JComboBox(statusy);
        for (int i = 0; i < statusy.length; i++) {
            if (statusy[i].equals(zlecenie[5])) {
                zmienStatus.setSelectedIndex(i);
            }
        }
        zmienStatus.setFont(new Font("Arial", Font.PLAIN, 20));
        zmienStatus.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                try {
                    magazyn.zmienStatus(Integer.parseInt(zlecenie[0]), zmienStatus.getSelectedItem().toString());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        zmienStatus.setBounds(500, 100, 200, 50);
        frame.add(zmienStatus);

        JLabel cena =  new JLabel("Cena:");
        cena.setFont(new Font("Arial", Font.PLAIN, 20));
        cena.setBounds(500, 150, 200, 30);
        frame.add(cena);

        double koszt = magazyn.getKoszt(Integer.parseInt(zlecenie[0]));
        JLabel suma = new JLabel(String.valueOf(koszt));
        suma.setFont(new Font("Arial", Font.PLAIN, 20));
        suma.setBounds(570, 150, 200, 30);

        frame.add(suma);


        JScrollPane scrollPaneUsl = new JScrollPane(tableUsl);
        scrollPaneUsl.setBounds(650, 300, 500, 400);

        frame.add(scrollPaneUsl);

        JButton dodajUslugi = new JButton("Dodaj Usługi");
        dodajUslugi.setFont(new Font("Arial", Font.PLAIN, 20));
        dodajUslugi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    dodajUslugeWindow(magazyn.magazyn(), magazyn, zlecenie);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        dodajUslugi.setBounds(950, 700, 200, 50);
        frame.add(dodajUslugi);

        frame.repaint();
        frame.revalidate();
    }

    //okno dodawania części do zlecenia
    public void dodajCzesciWindow(ArrayList<String[]> magazyn, Magazyn magazyn2, String [] zlecenie) {
        frame.getContentPane().removeAll();

        JLabel label = new JLabel("Dodaj części");
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        label.setBounds(490, 35, 800, 50);
        JTable table = new JTable(magazyn.size(), 4);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(650);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(0).setHeaderValue("ID");
        table.getColumnModel().getColumn(1).setHeaderValue("Producent");
        table.getColumnModel().getColumn(2).setHeaderValue("Cena");
        table.getColumnModel().getColumn(3).setHeaderValue("Ilosc");

        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 20));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 100, 1200, 600);

        magazynCzesciReturn2.setFont(new Font("Arial", Font.PLAIN, 20));
        magazynCzesciReturn2.setBounds(950, 50, 200, 50);

        magazynCzesciReturn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    widokZleceniaWindow(magazyn2, zlecenie);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        frame.add(scrollPane);
        frame.add(label);
        frame.add(magazynCzesciReturn2);

        JButton dodajItem = new JButton("Zmień ilość");
        dodajItem.setFont(new Font("Arial", Font.PLAIN, 20));
        dodajItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < magazyn.size(); i++) {
                    System.out.println(table.getValueAt(i, 3));
                    if (table.getValueAt(i, 3) != null) {
                        String ilosc = table.getValueAt(i, 3).toString();
                        int iloscInt = Integer.parseInt(ilosc.replaceAll("\\D+", ""));
                        try {
                            magazyn2.dodajCzescDoZlecenia(Integer.parseInt(zlecenie[0]), Integer.parseInt(magazyn.get(i)[0]), iloscInt);
                            widokZleceniaWindow(magazyn2, zlecenie);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });
        dodajItem.setBounds(800, 50, 200, 50);

        frame.add(dodajItem);

        JScrollPane scrollPane12 = new  JScrollPane();
        scrollPane12.setBounds(900, 120, 300, 700);

        for(int i = 0; i < magazyn.size(); i++) {
            for (int j = 0; j < 3; j++) {
                table.setValueAt(magazyn.get(i)[j], i, j);
            }
        }

        frame.repaint();
        frame.revalidate();
    }

    //okno dodawania usług do zlecenia
    public void dodajUslugeWindow(ArrayList<String[]> magazyn, Magazyn magazyn2, String [] zlecenie) throws SQLException {
        frame.getContentPane().removeAll();

        JLabel label = new JLabel("Dodaj usługę");
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        label.setBounds(490, 35, 800, 50);

        magazynCzesciReturn2.setFont(new Font("Arial", Font.PLAIN, 20));
        magazynCzesciReturn2.setBounds(950, 50, 200, 50);

        magazynCzesciReturn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    widokZleceniaWindow(magazyn2, zlecenie);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        ArrayList<String[]> listaUslug = magazyn2.getUslugi();

        String [] typyUslug = new String [listaUslug.size()];
        for(int i = 0; i < listaUslug.size(); i++) {
            typyUslug[i] = listaUslug.get(i)[1];
        }
        JLabel cena = new JLabel(listaUslug.get(0)[3]);
        cena.setFont(new Font("Arial", Font.PLAIN, 20));
        cena.setBounds(100, 300, 200, 50);
        JComboBox usl =  new JComboBox(typyUslug);
        usl.setFont(new Font("Arial", Font.PLAIN, 20));
        usl.setBounds(100, 200, 400, 50);

        JButton dodaj =  new JButton("Dodaj usługe");

        usl.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                cena.setText(listaUslug.get(usl.getSelectedIndex())[3]);
                frame.repaint();
                frame.revalidate();
            }
        });

        dodaj.setFont(new Font("Arial", Font.PLAIN, 20));
        dodaj.setBounds(700, 400, 200, 50);

        dodaj.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    magazyn2.dodajUsluge(Integer.parseInt(zlecenie[0]), Integer.parseInt(listaUslug.get(usl.getSelectedIndex())[0]));
                    widokZleceniaWindow(magazyn2, zlecenie);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        frame.add(cena);
        frame.add(usl);
        frame.add(dodaj);
        frame.add(label);

        frame.repaint();
        frame.revalidate();
    }

    //okno wyświetlania wszystkich dostępnych usług
    public void listaUslugWindow(Magazyn magazyn) throws SQLException {
        frame.getContentPane().removeAll();

        JLabel label = new JLabel("Lista usług");
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        label.setBounds(550, 30, 200, 50);
        frame.add(label);

        ArrayList<String[]> listaUslug = magazyn.getUslugi();
        JTable table = new  JTable(listaUslug.size(), 4);
        table.setFont(new Font("Arial", Font.PLAIN, 20));
        table.setRowHeight(30);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        table.getColumnModel().getColumn(2).setPreferredWidth(750);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(0).setHeaderValue("Id");
        table.getColumnModel().getColumn(1).setHeaderValue("Nazwa");
        table.getColumnModel().getColumn(2).setHeaderValue("Opis");
        table.getColumnModel().getColumn(3).setHeaderValue("Cena");
        for (int i = 0; i < listaUslug.size(); i++) {
            for (int j = 0; j < 4; j++) {
                table.setValueAt(listaUslug.get(i)[j], i, j);
            }
        }

        JScrollPane scrollPane = new  JScrollPane(table);
        scrollPane.setBounds(0, 100, 1200, 700);
        frame.add(scrollPane);

        JButton dodajUsluge = new JButton("Dodaj usługe");
        dodajUsluge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    dodajUslugeWindow(magazyn);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        dodajUsluge.setFont(new Font("Arial", Font.PLAIN, 20));
        dodajUsluge.setBounds(700, 20, 200, 50);
        frame.add(dodajUsluge);

        returnButton.setBounds(100, 20, 200, 50);
        returnButton.setFont(new Font("Arial", Font.PLAIN, 20));
        frame.add(returnButton);

        frame.repaint();
        frame.revalidate();
    }

    //okno dodawania nowych usług do dostępnych w seerwisie usług
    public void dodajUslugeWindow(Magazyn magazyn) throws SQLException {
        frame.getContentPane().removeAll();

        JLabel label = new JLabel("Dodaj usługe");
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        label.setBounds(550, 30, 200, 50);
        frame.add(label);

        JLabel nazwa = new JLabel("Nazwa");
        nazwa.setFont(new Font("Arial", Font.PLAIN, 20));
        nazwa.setBounds(100, 100, 200, 50);
        frame.add(nazwa);
        JTextField nazwa1 = new JTextField("");
        nazwa1.setFont(new Font("Arial", Font.PLAIN, 20));
        nazwa1.setBounds(300, 100, 700, 50);
        frame.add(nazwa1);

        JLabel opis = new JLabel("Opis");
        opis.setFont(new Font("Arial", Font.PLAIN, 20));
        opis.setBounds(100, 150, 200, 50);
        frame.add(opis);
        JTextField opis1 = new JTextField("");
        opis1.setFont(new Font("Arial", Font.PLAIN, 20));
        opis1.setBounds(300, 150, 700, 50);
        frame.add(opis1);

        JLabel cena = new JLabel("Cena");
        cena.setFont(new Font("Arial", Font.PLAIN, 20));
        cena.setBounds(100, 200, 200, 50);
        frame.add(cena);
        JTextField cena1 = new JTextField("");
        cena1.setFont(new Font("Arial", Font.PLAIN, 20));
        cena1.setBounds(300, 200, 700, 50);
        frame.add(cena1);

        JButton dodaj =  new JButton("Dodaj");
        dodaj.setFont(new Font("Arial", Font.PLAIN, 20));
        dodaj.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    magazyn.dodaj_do_uslug(nazwa1.getText(), opis1.getText(), Double.parseDouble(cena1.getText()));
                    nazwa1.setText("");
                    opis1.setText("");
                    cena1.setText("");
                    listaUslugWindow(magazyn);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Niepoprawne dane!");
                    throw new RuntimeException(ex);
                }
            }
        });
        dodaj.setBounds(800, 30, 200, 50);
        frame.add(dodaj);

        JButton retur = new JButton("Powrót");
        retur.setFont(new Font("Arial", Font.PLAIN, 20));
        retur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    listaUslugWindow(magazyn);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        retur.setBounds(100, 30, 200, 50);
        frame.add(retur);

        frame.repaint();
        frame.revalidate();
    }

    //okno dodawania dostępnych części do magazynu
    public void dodajCzesciWindow(Magazyn magazyn) throws SQLException {
        frame.getContentPane().removeAll();

        JLabel label = new JLabel("Dodaj Części");

        JLabel nazwa = new JLabel("Producent");
        nazwa.setFont(new Font("Arial", Font.PLAIN, 20));
        nazwa.setBounds(100, 100, 200, 50);
        frame.add(nazwa);
        JTextField nazwa1 = new JTextField("");
        nazwa1.setFont(new Font("Arial", Font.PLAIN, 20));
        nazwa1.setBounds(300, 100, 700, 50);
        frame.add(nazwa1);

        JLabel cena = new JLabel("Cena");
        cena.setFont(new Font("Arial", Font.PLAIN, 20));
        cena.setBounds(100, 150, 200, 50);
        frame.add(cena);
        JTextField cena1 = new JTextField("");
        cena1.setFont(new Font("Arial", Font.PLAIN, 20));
        cena1.setBounds(300, 150, 700, 50);
        frame.add(cena1);

        JLabel opis = new JLabel("Minimalny stan");
        opis.setFont(new Font("Arial", Font.PLAIN, 20));
        opis.setBounds(100, 200, 200, 50);
        frame.add(opis);
        JTextField opis1 = new JTextField("");
        opis1.setFont(new Font("Arial", Font.PLAIN, 20));
        opis1.setBounds(300, 200, 700, 50);
        frame.add(opis1);


        JLabel cena3 = new JLabel("Aktualny stan");
        cena3.setFont(new Font("Arial", Font.PLAIN, 20));
        cena3.setBounds(100, 250, 200, 50);
        frame.add(cena3);
        JTextField cena4 = new JTextField("");
        cena4.setFont(new Font("Arial", Font.PLAIN, 20));
        cena4.setBounds(300, 250, 700, 50);
        frame.add(cena4);

        JButton dodaj =  new JButton("Dodaj");
        dodaj.setFont(new Font("Arial", Font.PLAIN, 20));
        dodaj.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    magazyn.dodaj_do_czesci(nazwa1.getText(), Double.parseDouble(cena1.getText()), Integer.parseInt(opis1.getText()), Integer.parseInt(cena4.getText()));
                    nazwa1.setText("");
                    opis1.setText("");
                    cena1.setText("");
                    cena4.setText("");
                    magazynWindow(magazyn.magazyn());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Niepoprawne dane!");
                    throw new RuntimeException(ex);
                }
            }
        });
        dodaj.setBounds(800, 30, 200, 50);
        frame.add(dodaj);

        JButton retur = new JButton("Powrót");
        retur.setFont(new Font("Arial", Font.PLAIN, 20));
        retur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    magazynWindow(magazyn.magazyn());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        retur.setBounds(100, 30, 200, 50);
        frame.add(retur);

        frame.repaint();
        frame.revalidate();
    }

    //okno do wyświetlania wszystkich zakończonych zleceń
    public void historiaZlecenWindow(Magazyn magazyn) throws SQLException {
        frame.getContentPane().removeAll();

        ArrayList<String[]> zlecenia = magazyn.stareZlecenia();

        JLabel zleceniaLabel = new JLabel("Zakończone zlecenia");
        zleceniaLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        zleceniaLabel.setBounds(550, 20, 200, 30);
        frame.add(zleceniaLabel);

        JTable table = new JTable(zlecenia.size(), 6);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 20));
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);
        table.getColumnModel().getColumn(4).setPreferredWidth(200);
        table.getColumnModel().getColumn(5).setPreferredWidth(150);
        table.getColumnModel().getColumn(0).setHeaderValue("Id");
        table.getColumnModel().getColumn(1).setHeaderValue("Klient");
        table.getColumnModel().getColumn(2).setHeaderValue("Rower");
        table.getColumnModel().getColumn(3).setHeaderValue("Data ukończenia");
        table.getColumnModel().getColumn(4).setHeaderValue("Serwisant");
        table.getColumnModel().getColumn(5).setHeaderValue("Cena");
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 100, 1200, 700);

        for (int i = 0; i < zlecenia.size(); i++) {
            for (int j = 0; j < 6; j++) {
                table.setValueAt(zlecenia.get(i)[j], i, j);
            }
        }

        returnButton.setBounds(100, 0, 200, 80);
        returnButton.setFont(new Font("Arial", Font.PLAIN, 20));
        frame.add(returnButton);

        frame.add(scrollPane);
        frame.repaint();
        frame.revalidate();
    }
}