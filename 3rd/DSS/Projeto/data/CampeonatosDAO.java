package data;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
// import java.util.TreeSet;

import src.RMFacade;

// import org.w3c.dom.views.AbstractView;

// import static java.util.stream.Collectors.toList;

// import static java.util.stream.Collectors.*;

// import src.IRMFacade;
import src.Campeonatos.Campeonato;


/**
 * PilotoDAO
 */

public class CampeonatosDAO implements Map<String,Campeonato> {
    private static CampeonatosDAO singleton = null;

    private CampeonatosDAO() throws SQLException{
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS campeonatos (" +
                         "NOME VARCHAR(20) NOT NULL PRIMARY KEY, "+
                         "CIRCUITOSIDS VARCHAR(30))";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static CampeonatosDAO getInstance() throws SQLException {
        if (CampeonatosDAO.singleton == null) {
            CampeonatosDAO.singleton = new CampeonatosDAO();
        }
        return CampeonatosDAO.singleton;
    }

    @Override
    public int size() {
        int i=0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM campeonatos")) {
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
                     stm.executeQuery("SELECT NOME FROM campeonatos WHERE NOME='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        Campeonato p = (Campeonato) value;
        return this.containsKey(p.getNome());
    }

    @Override
    public Campeonato get(Object key) {
        Campeonato p = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM campeonatos WHERE NOME='"+key.toString()+"'")) {
            if (rs.next()) {  // A chave existe na tabela
                // Reconstruir a colecção de pilotos
                p = new Campeonato(rs.getString("NOME"), rs.getString("CIRCUITOSIDS"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return p;
    }

    @Override
    public Campeonato put(String key, Campeonato value) {
        Campeonato res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // Actualizar a turma
            stm.executeUpdate("INSERT INTO campeonatos VALUES ('"+value.getNome()+"')");

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Campeonato remove(Object key) {
        Campeonato p = this.get(key.toString());
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
                stm.executeUpdate("DELETE FROM campeonatos WHERE NOME='" + key.toString() + "'");
    } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return p;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Campeonato> m) {
        for(Campeonato p : m.values()) {
            this.put(p.getNome(), p);
        }
    }

    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.createStatement()) {
                stm.executeUpdate("TRUNCATE campeonatos");
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
    public Collection<Campeonato> values() {
        Collection<Campeonato> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT NOME FROM campeonatos")) { // ResultSet com os ids de todas as turmas
            while (rs.next()) {
                String idp = rs.getString("NOME"); // Obtemos um id de turma do ResultSet
                Campeonato p = this.get(idp);                    // Utilizamos o get para construir as turmas uma a uma
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
    public Set<Entry<String, Campeonato>> entrySet() {
        return null;
    }

}