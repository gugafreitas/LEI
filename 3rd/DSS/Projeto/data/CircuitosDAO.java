package data;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
// import java.util.TreeSet;
// import java.util.Map.Entry;
// import java.util.HashMap;
// import java.util.Iterator;


// import org.w3c.dom.views.AbstractView;

import src.Circuitos.Circuito;

// import static java.util.stream.Collectors.toList;

// import static java.util.stream.Collectors.*;

public class CircuitosDAO implements Map<String,Circuito> {
    private static CircuitosDAO singleton = null;
    
    private CircuitosDAO() throws SQLException{
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS circuitos (" +
                  "NOME VARCHAR(25) NOT NULL PRIMARY KEY," +
                  "DISTANCIA INTEGER(3) DEFAULT 0," +
                  "VOLTAS INTEGER(2) DEFAULT 0, " +
                  "ELEMENTOS VARCHAR(500), " +
                  "MAPID INT(3) DEFAULT 0, " +
                  "NCURVAS INT(3) DEFAULT 0, " +
                  "NCHICANES INT(3) DEFAULT 0, " +
                  "NRETAS INT(3) DEFAULT 0)";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static CircuitosDAO getInstance() throws SQLException {
        if (CircuitosDAO.singleton == null) {
            CircuitosDAO.singleton = new CircuitosDAO();
        }
        return CircuitosDAO.singleton;
    }

    @Override
    public int size() {
        int i=0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM circuitos")) {
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
                     stm.executeQuery("SELECT NOME FROM circuitos WHERE NOME='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        Circuito p = (Circuito) value;
        return this.containsKey(p.getNome());
    }

    @Override
    public Circuito get(Object key) {
        Circuito p = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM circuitos WHERE NOME='"+key.toString()+"'")) {
            if (rs.next()) {  // A chave existe na tabela
                // Reconstruir a colecção de pilotos
                p = new Circuito(rs.getString("NOME"),rs.getInt("DISTANCIA"),rs.getInt("VOLTAS"),rs.getString("ELEMENTOS"),rs.getInt("MAPID"),rs.getInt("NCURVAS"),rs.getInt("NCHICANES"),rs.getInt("NRETAS"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return p;
    }

    @Override
    public Circuito put(String key, Circuito value) {
        Circuito res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // Actualizar a turma
            stm.executeUpdate("INSERT INTO circuitos VALUES ('"+value.getNome()+"', '"+value.getDistancia()+"', '"+value.getVoltas()+"', '"+value.getComprimentoString()+"', '"+value.getMapID()+"', '"+value.getNCurvas()+"', '"+value.getNChicanes()+"', '"+value.getNRetas()+"')");

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Circuito remove(Object key) {
        Circuito p = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
                stm.executeUpdate("DELETE FROM circuitos WHERE NOME='" + key.toString() + "'");
    } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return p;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Circuito> m) {
        for(Circuito p : m.values()) {
            this.put(p.getNome(), p);
        }
    }

    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.createStatement()) {
                stm.executeUpdate("TRUNCATE circuitos");
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
    public Collection<Circuito> values() {
        Collection<Circuito> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT NOME FROM circuitos")) { // ResultSet com os ids de todas as turmas
            while (rs.next()) {
                String idp = rs.getString("NOME"); // Obtemos um id de turma do ResultSet
                Circuito p = this.get(idp);                    // Utilizamos o get para construir as turmas uma a uma
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
    public Set<Entry<String, Circuito>> entrySet() {
        return null;
    }
}
