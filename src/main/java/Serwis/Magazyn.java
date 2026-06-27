package Serwis;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class Magazyn {

    Connection conn;

    public Magazyn(Connection conn) throws SQLException {
        this.conn = conn;
    }

    //wypisanie wszystkich dostępnych w magazynie części
    public ArrayList<String[]> magazyn() throws SQLException {
        String sql = "select * from czesc";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<String[]> listaCzesci = new ArrayList<>();
        while (rs.next()) {
            String[] czesci = new String[5];
            for (int i = 0; i < 5; i++) {
                czesci[i] = rs.getString(i + 1);
            }
            listaCzesci.add(czesci);
        }
        rs.close();
        ps.close();
        return listaCzesci;
    }

    //wypisanie wszystkich znajdujących się w magazynie rowerów rowerów
    public ArrayList<String[]> magazynRowerow() throws SQLException {
        String sql = "select m.lokalizacja, r.marka, r.model, m.czas_postoju, m.rower_id from Magazyn m join Rower r on m.rower_id = r.rower_id";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<String[]> listaRowerow = new ArrayList<>();
        while (rs.next()) {
            String[] rowery = new String[5];
            for (int i = 0; i < 5; i++) {
                rowery[i] = rs.getString(i + 1);
            }
            listaRowerow.add(rowery);
        }
        rs.close();
        ps.close();
        return listaRowerow;
    }

    //przyjęcie zlecenia do serwisu
    public void przyjmijDoSerwisu(String imie, String nazwisko, String nr_telefonu, String email, String marka, String model, String opis, String zlecenie, String miejsce) throws SQLException {
        //sprawdzenie czy klient istnieje
        String sqlSzukajKlienta = "select * from Klient where imie = ? and nazwisko = ? and nr_telefonu = ? and email = ?";
        PreparedStatement psSzukajKlienta = conn.prepareStatement(sqlSzukajKlienta);
        psSzukajKlienta.setString(1, imie);
        psSzukajKlienta.setString(2, nazwisko);
        psSzukajKlienta.setString(3, nr_telefonu);
        psSzukajKlienta.setString(4, email);
        ResultSet rSzukajKlienta = psSzukajKlienta.executeQuery();
        Boolean klient = false;
        int klient_id = -1;
        while (rSzukajKlienta.next()) {
            klient = true;
            klient_id = rSzukajKlienta.getInt(1);
            System.out.println("Klient " + rSzukajKlienta.getString(2) + " " + rSzukajKlienta.getString(3));
        }
        //jeśli klient nie istnieje dodanie go do bazy klientów
        if (!klient) {
            String sqlDodajKlienta = "select dodaj_klienta(?, ?, ?, ?)";
            PreparedStatement psDodajKlienta = conn.prepareStatement(sqlDodajKlienta);
            psDodajKlienta.setString(1, imie);
            psDodajKlienta.setString(2, nazwisko);
            psDodajKlienta.setString(3, nr_telefonu);
            psDodajKlienta.setString(4, email);
            ResultSet rDodajKlienta = psDodajKlienta.executeQuery();
            while (rDodajKlienta.next()) {
                klient_id = rDodajKlienta.getInt(1);
                System.out.println("Dodano klienta: " +  rDodajKlienta.getString(1));
            }
        }
        //dodanie roweru do bazy rowerów
        String sqlDodajRower = "select dodaj_rower(?, ?, ?, ?)";
        PreparedStatement psDodajRower = conn.prepareStatement(sqlDodajRower);
        psDodajRower.setInt(1, klient_id);
        psDodajRower.setString(2, marka);
        psDodajRower.setString(3, model);
        psDodajRower.setString(4, opis);
        ResultSet rDodajRower = psDodajRower.executeQuery();
        int rower_id = -1;
        while (rDodajRower.next()) {
            rower_id = rDodajRower.getInt(1);
            System.out.println("Dodano rower: " + rDodajRower.getString(1));
        }
        rDodajRower.close();
        psDodajRower.close();
        //dodanie zlecenia do bazy zleceń
        String sqlUtworzZlecenie = "select utworz_zlecenie(?, ?)";
        PreparedStatement psUtworzZlecenie = conn.prepareStatement(sqlUtworzZlecenie);
        psUtworzZlecenie.setInt(1, rower_id);
        psUtworzZlecenie.setString(2, zlecenie);
        ResultSet rsUtworzZlecenie = psUtworzZlecenie.executeQuery();
        while (rsUtworzZlecenie.next()) {
            int zlecenieiD = rsUtworzZlecenie.getInt(1);
        }
        //dodanie roweru do magazynu rowerów
        String sqlDoMagazynu = "call przyjmij_do_magazynu(?, ?)";
        PreparedStatement psDoMagazynu = conn.prepareStatement(sqlDoMagazynu);
        psDoMagazynu.setInt(1, rower_id);
        psDoMagazynu.setString(2, miejsce);
        psDoMagazynu.execute();

        rsUtworzZlecenie.close();
        psUtworzZlecenie.close();
        rSzukajKlienta.close();
        psSzukajKlienta.close();
        psDoMagazynu.close();
        psDodajRower.close();
    }

    //usunięcie roweru z bazy magazyn rowerów
    public void deleteRower(int rower_id) throws SQLException {
        String sql = "call wydaj_z_magazynu(?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, rower_id);
        ps.execute();
        ps.close();
    }

    //zmiana lokalizacji roweru
    public void przeniesRower(int rower_id, String lok) throws SQLException {
        String sql = "call zmien_lokalizacje(?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, rower_id);
        ps.setString(2, lok);
        ps.execute();
        ps.close();
    }

    //wypisanie wszystkich zleceń które nie mają daty zakończenia
    public ArrayList<String[]> zlecenia() throws SQLException {
        String sql = "select z.zlecenie_id, k.imie, k.nazwisko, r.marka, r.model, m.lokalizacja, u.login, st.nazwa_statusu from zlecenie z join Rower r on z.rower_id = r.rower_id join klient k on r.klient_id = k.klient_id left join magazyn m on m.rower_id = z.rower_id left join serwisant s on z.serwisant_id = s.serwisant_id left join uzytkownik u on s.user_id = u.user_id join statusZlecenia st on z.status_id = st.status_id where z.data_zakonczenia is NULL";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<String[]> zlecenia = new ArrayList<>();
        while (rs.next()) {
            String[] zlecenie = new String[6];
            zlecenie[0] = rs.getString(1);
            zlecenie[1] = rs.getString(2) + " " + rs.getString(3);
            zlecenie[2] = rs.getString(4) + " " + rs.getString(5);
            zlecenie[3] = rs.getString(6);
            zlecenie[4] = rs.getString(7);
            zlecenie[5] = rs.getString(8);
            zlecenia.add(zlecenie);
        }
        rs.close();
        ps.close();
        return  zlecenia;
    }

    //dodanie serwisanta do zlecenia
    public void setSerwisant(int zlId, int serId) throws SQLException {
        String sql = "call przypisz_serwisanta(?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, zlId);
        ps.setInt(2, serId);
        ps.execute();
        ps.close();
    }

    //pobranie listy części znajdujących się w zleceniu
    public ArrayList<String[]> getListaCzesci(int zlId) throws SQLException {
        String sql = "select c.producent, c.cena_jednostkowa, z.ilosc from zlecenieCzesci z join czesc c on z.czesc_id = c.czesc_id where z.zlecenie_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, zlId);
        ResultSet rs = ps.executeQuery();
        ArrayList<String[]> listaCzesci = new ArrayList<>();
        while (rs.next()) {
            String[] zlecenie = new String[4];
            zlecenie[0] = rs.getString(1);
            zlecenie[1] = rs.getString(2);
            zlecenie[2] = rs.getString(3);
            zlecenie[3] = String.valueOf(rs.getInt(3) * rs.getDouble(2));
            listaCzesci.add(zlecenie);
        }
        rs.close();
        ps.close();
        return  listaCzesci;
    }

    //pobranie listy usług znajdujących się w magazynie
    public ArrayList<String[]> getListaUslug(int zlecenieId) throws SQLException {
        ArrayList<String[]> listaUslug = new ArrayList<>();
        String sql = "select u.nazwa_uslugi, u.cena_uslugi from zlecenie_uslugi z join usluga u on z.usluga_id = u.usluga_id where z.zlecenie_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, zlecenieId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String[] uslugi = new String[2];
            uslugi[0] = rs.getString(1);
            uslugi[1] = rs.getString(2);
            listaUslug.add(uslugi);
        }
        rs.close();
        ps.close();
        return listaUslug;
    }

    //dodanie części do zlecenia
    public void dodajCzescDoZlecenia(int idZlecenia, int idCzesci, int iloscInt) throws SQLException {
        String sql = "call dodaj_czesc_do_zlecenia(?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idZlecenia);
        ps.setInt(2, idCzesci);
        ps.setInt(3, iloscInt);
        ps.execute();
        ps.close();

        String sql2 = "update czesc set aktualny_stan = COALESCE(aktualny_stan, 0) - ? where czesc_id = ?";
        PreparedStatement ps2 = conn.prepareStatement(sql2);
        ps2.setInt(1, iloscInt);
        ps2.setInt(2, idCzesci);
        ps2.execute();
        ps2.close();
    }

    //pobranie wsszystkich dostępnych usług
    public ArrayList<String[]> getUslugi() throws SQLException {
        String sql = "select * from usluga";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<String[]> listaUslug = new ArrayList<>();
        while (rs.next()) {
            String[] uslugi = new String[4];
            uslugi[0] = rs.getString(1);
            uslugi[1] = rs.getString(2);
            uslugi[2] = rs.getString(3);
            uslugi[3] = rs.getString(4);
            listaUslug.add(uslugi);
        }
        rs.close();
        ps.close();
        return listaUslug;
    }

    //dodanie usług do zlecenia
    public void dodajUsluge(int idZlecenia, int idUslugi) throws SQLException {
        String sql = "call dodaj_usluge_do_zlecenia(?, ?)";
        PreparedStatement ps = conn.prepareCall(sql);
        ps.setInt(1, idZlecenia);
        ps.setInt(2, idUslugi);
        ps.execute();
        ps.close();
    }

    //pobranie listy możliwych statusów zleceń
    public String[] getStatusy() throws SQLException {
        String sql = "select nazwa_statusu from statusZlecenia";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<String> listaStatus = new ArrayList<>();
        while (rs.next()) {
            listaStatus.add(rs.getString(1));
        }
        rs.close();
        ps.close();
        return listaStatus.toArray(new String[listaStatus.size()]);
    }

    //zmiana statusu zlecenia
    public void zmienStatus(int id, String status) throws SQLException {
        String sql = "call zmien_status(?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setString(2, status);
        ps.execute();
        ps.close();
    }

    //oblicznie i pobranie kosztów zlecenia
    public double getKoszt(int id) throws SQLException {
        String sqlOblicz = "call przelicz_koszt(?)";
        PreparedStatement ps = conn.prepareStatement(sqlOblicz);
        ps.setInt(1, id);
        ps.execute();
        String sql = "select koszt_zlecenia from zlecenie where zlecenie_id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        Double koszt = new Double(0);
        while (rs.next()) {
            koszt = rs.getDouble(1);
        }
        rs.close();
        pst.close();
        ps.close();
        return koszt;
    }

    //zakonczenie zlecenia
    public void zamknijZlecenie(int id) throws SQLException {
        String sql = "call zamknij_zlecenie(?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
        ps.close();
    }

    //dodanie nowej usługi do listy dostepnych usług
    public void dodaj_do_uslug(String nazwa, String opis, Double cena) throws  SQLException {
        String sql = "insert into usluga (nazwa_uslugi, opis, cena_uslugi) values (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nazwa);
        ps.setString(2, opis);
        ps.setDouble(3, cena);
        ps.execute();
        ps.close();
    }

    //dodanie nowej części do listy dostępnych części
    public void dodaj_do_czesci(String nazwa, Double cena, int mIlosc, int ilosc) throws  SQLException {
        String sql = "insert into czesc (producent, cena_jednostkowa, minimalny_stan, aktualny_stan) values (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nazwa);
        ps.setDouble(2, cena);
        ps.setInt(3, mIlosc);
        ps.setInt(4, ilosc);
        ps.execute();
        ps.close();
    }

    //zwiększenie ilości aktualnych części do dwukrotności minimalnej liczby części
    public void zakup_czesci() throws SQLException {
        String sql = "update czesc set aktualny_stan = minimalny_stan * 2 where aktualny_stan < minimalny_stan";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.execute();
        ps.close();
    }

    //pobranie listy zleceń z datą zakończnia
    public ArrayList<String[]> stareZlecenia() throws SQLException {
        String sql = "select z.zlecenie_id, k.imie, k.nazwisko, r.marka, r.model, z.data_zakonczenia, u.login, z.koszt_zlecenia from zlecenie z join Rower r on z.rower_id = r.rower_id join klient k on r.klient_id = k.klient_id left join magazyn m on m.rower_id = z.rower_id left join serwisant s on z.serwisant_id = s.serwisant_id left join uzytkownik u on s.user_id = u.user_id join statusZlecenia st on z.status_id = st.status_id where z.data_zakonczenia is not NULL";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<String[]> zlecenia = new ArrayList<>();
        while (rs.next()) {
            String[] zlecenie = new String[6];
            zlecenie[0] = rs.getString(1);
            zlecenie[1] = rs.getString(2) + " " + rs.getString(3);
            zlecenie[2] = rs.getString(4) + " " + rs.getString(5);
            zlecenie[3] = rs.getString(6);
            zlecenie[4] = rs.getString(7);
            zlecenie[5] = rs.getString(8);
            zlecenia.add(zlecenie);
        }
        rs.close();
        ps.close();
        return  zlecenia;
    }
}
