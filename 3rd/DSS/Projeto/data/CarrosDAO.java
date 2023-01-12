package data;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
// import java.util.TreeSet;

// import org.w3c.dom.views.AbstractView;

import src.Carros.Carro;

// import static java.util.stream.Collectors.toList;

// import static java.util.stream.Collectors.*;


/**
 * CarrosDAO
 */

public class CarrosDAO implements Map<String,Carro> {
    private static CarrosDAO singleton = null;

    private CarrosDAO() throws SQLException{
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS carro (" +
                    "ID INT NOT NULL PRIMARY KEY," +
                    "MARCA VARCHAR(25) NOT NULL," +
                    "MODELO VARCHAR(25) NOT NULL," +
                    "PAC FLOAT(3) NOT NULL," +
                    "FIABILIDADE FLOAT(3) NOT NULL," +
                    "CILINDRADA INT NOT NULL," +
                    "PNEUS VARCHAR(25) NOT NULL," +
                    "POTENCIA INT NOT NULL," +
                    "HIBRIDO BOOL NOT NULL)";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static CarrosDAO getInstance() throws SQLException {
        if (CarrosDAO.singleton == null) {
            CarrosDAO.singleton = new CarrosDAO();
        }
        return CarrosDAO.singleton;
    }

    @Override
    public int size() {
        int i=0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM carro")) {
            if(rs.next()) {
                i = rs.getInt(1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return i;
    }

    @Override
    public boolean isEmpty() {
        return this.size()==0;
    }

    @Override
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT ID FROM carro WHERE ID='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        Carro c = (Carro) value;
        String s = Integer.toString(c.getID());
        return this.containsKey(s);
    }

    @Override
    public Carro get(Object key) {
        Carro c = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM carro WHERE ID='"+key.toString()+"'")) {
            if (rs.next()) {  // A chave existe na tabela
                // Reconstruir a colecção de carross
                c = new Carro(rs.getInt("ID"),rs.getString("MARCA"),rs.getString("MODELO"),rs.getFloat("PAC"),rs.getFloat("FIABILIDADE"),rs.getInt("CILINDRADA"),rs.getString("PNEUS"),rs.getInt("POTENCIA"),rs.getBoolean("HIBRIDO"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return c;
    }

    @Override
    public Carro put(String key, Carro value) {
        Carro c = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            stm.executeUpdate("INSERT INTO carro VALUES ('"+value.getID()+"', '"+value.getMarca()+"', '"+value.getModelo()+"','"+value.getPAC()+"','"+value.getFiabilidade()+"','"+value.getCilindrada()+"','"+value.getPneus()+"','"+value.getPotencia()+"','"+value.getHibrido()+"')");

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return c;
    }

    @Override
    public Carro remove(Object key) {
        Carro c = this.get(key.toString());
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
                stm.executeUpdate("DELETE FROM carro WHERE ID='" + key.toString() + "'");
    } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return c;
    }

    @Override
    //nao sei se esta certo pois marca não é um valor unico tipo id
    public void putAll(Map<? extends String, ? extends Carro> m) {
        for(Carro c : m.values()) {
            String s = Integer.toString(c.getID()); 
            this.put(s, c);
        }
    }

    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.createStatement()) {
                stm.executeUpdate("TRUNCATE carro");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Carro> values() {
        Collection<Carro> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT ID FROM carro")) { // ResultSet com os ids de todas as turmas
            while (rs.next()) {
                String idp = Integer.toString(rs.getInt("ID")); // Obtemos um id de turma do ResultSet
                Carro p = this.get(idp);                    // Utilizamos o get para construir as turmas uma a uma
                res.add(p);                                 // Adiciona a turma ao resultado.
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Set<Entry<String, Carro>> entrySet() {
        return null;
    }

}