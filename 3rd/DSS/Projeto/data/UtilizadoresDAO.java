package data;
import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
// import java.util.TreeSet;

//import javax.rmi.CORBA.Util;

// import org.w3c.dom.views.AbstractView;

import src.Utilizadores.Utilizador;

// import static java.util.stream.Collectors.toList;
// import static java.util.stream.Collectors.*;

public class UtilizadoresDAO implements Map<String,Utilizador>{
    private static UtilizadoresDAO singleton = null;

    private UtilizadoresDAO() throws SQLException{
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS utilizadores (" +
                    "NOME_UTILIZADOR VARCHAR(25) NOT NULL PRIMARY KEY," +
                    "PASSWORD VARCHAR(25) NOT NULL," +
                    "TIPO_USER CHAR NOT NULL)";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static UtilizadoresDAO getInstance() throws SQLException {
        if (UtilizadoresDAO.singleton == null) {
            UtilizadoresDAO.singleton = new UtilizadoresDAO();
        }
        return UtilizadoresDAO.singleton;
    }

    @Override
    public int size() {
        int i=0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM utilizadores")) {
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
                     stm.executeQuery("SELECT NOME_UTILIZADOR FROM utilizadores WHERE NOME_UTILIZADOR='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        Utilizador u = (Utilizador) value;
        return this.containsKey(u.getNome_utilizador());
    }

    @Override
    public Utilizador get(Object key) {
        Utilizador u = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM utilizadores WHERE NOME_UTILIZADOR='"+key.toString()+"'")) {
            if (rs.next()) {  // A chave existe na tabela
                // Reconstruir a colecção de pilotos
                u = new Utilizador(rs.getString("NOME_UTILIZADOR"),rs.getString("PASSWORD"),rs.getString("TIPO_USER").charAt(0));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return u;
    }

    @Override
    public Utilizador put(String key, Utilizador value) {
        Utilizador res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // Actualizar a turma
            stm.executeUpdate("INSERT INTO utilizadores VALUES ('"+value.getNome_utilizador()+"', '"+value.getPassword()+"', '"+value.getTipoDeUser()+"')");

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Utilizador remove(Object key) {
        Utilizador u = this.get(key.toString());
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
                stm.executeUpdate("DELETE FROM utilizadores WHERE NOME_UTILIZADOR='" + key.toString() + "'");
    } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return u;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Utilizador> m) {
        for(Utilizador u : m.values()) {
            this.put(u.getNome_utilizador(), u);
        }
    }

    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.createStatement()) {
                stm.executeUpdate("TRUNCATE utilizadores");
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
    public Collection<Utilizador> values() {
        Collection<Utilizador> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT NOME_UTILIZADOR FROM utilizadores")) { // ResultSet com os ids de todas as turmas
            while (rs.next()) {
                String idu = rs.getString("NOME_UTILIZADOR"); // Obtemos um id de turma do ResultSet
                Utilizador u = this.get(idu);                    // Utilizamos o get para construir as turmas uma a uma
                res.add(u);                                 // Adiciona a turma ao resultado.
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Set<Entry<String, Utilizador>> entrySet() {
        return null;
    }

}
