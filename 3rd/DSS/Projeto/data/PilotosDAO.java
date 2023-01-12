package data;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
// import java.util.TreeSet;

// import org.w3c.dom.views.AbstractView;

// import static java.util.stream.Collectors.toList;

// import static java.util.stream.Collectors.*;

// import src.IRMFacade;
import src.Pilotos.Piloto;


/**
 * PilotoDAO
 */

public class PilotosDAO implements Map<String,Piloto> {
    private static PilotosDAO singleton = null;

    private PilotosDAO() throws SQLException{
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS pilotos (" +
                    "NOME VARCHAR(25) NOT NULL PRIMARY KEY," +
                    "AVAILABLE BOOL DEFAULT NULL," +
                    "CTS FLOAT(3) DEFAULT 0, " +
                    "SVA FLOAT(3) DEFAULT 0)";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static PilotosDAO getInstance() throws SQLException {
        if (PilotosDAO.singleton == null) {
            PilotosDAO.singleton = new PilotosDAO();
        }
        return PilotosDAO.singleton;
    }

    @Override
    public int size() {
        int i=0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM pilotos")) {
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
                     stm.executeQuery("SELECT NOME FROM pilotos WHERE NOME='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        Piloto p = (Piloto) value;
        return this.containsKey(p.getNome());
    }

    @Override
    public Piloto get(Object key) {
        Piloto p = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM pilotos WHERE NOME='"+key.toString()+"'")) {
            if (rs.next()) {  // A chave existe na tabela
                // Reconstruir a colecção de pilotos
                p = new Piloto(rs.getString("NOME"),rs.getBoolean("AVAILABLE"),rs.getFloat("CTS"),rs.getFloat("SVA"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return p;
    }

    @Override
    public Piloto put(String key, Piloto value) {
        Piloto res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // Actualizar a turma
            stm.executeUpdate("INSERT INTO pilotos VALUES ('"+value.getNome()+"', '"+value.getAvailable()+"', '"+value.getCTS()+"','"+value.getSVA()+"')");

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Piloto remove(Object key) {
        Piloto p = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
                stm.executeUpdate("DELETE FROM pilotos WHERE NOME='" + key + "'");
    } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return p;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Piloto> m) {
        for(Piloto p : m.values()) {
            this.put(p.getNome(), p);
        }
    }

    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.createStatement()) {
                stm.executeUpdate("TRUNCATE pilotos");
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
    public Collection<Piloto> values() {
        Collection<Piloto> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT NOME FROM pilotos")) { // ResultSet com os ids de todas as turmas
            while (rs.next()) {
                String idp = rs.getString("NOME"); // Obtemos um id de turma do ResultSet
                Piloto p = this.get(idp);                    // Utilizamos o get para construir as turmas uma a uma
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
    public Set<Entry<String, Piloto>> entrySet() {
        return null;
    }

}