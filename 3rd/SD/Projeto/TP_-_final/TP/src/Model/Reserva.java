package Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Reserva implements Serializable {
    private String codigoReserva;
    private String idCliente;
    private int idTrotinete;
    private LocalDateTime date;

    public Reserva(String idReserva, String idCliente, int i,LocalDateTime date) {
        this.codigoReserva = idReserva;
        this.idCliente = idCliente;
        this.idTrotinete = i;
        this.date = date;
    }

    public String getIdReserva() {
        return this.codigoReserva;
    }

    public void setIdReserva(String idReserva) {
        this.codigoReserva = idReserva;
    }

    public String getIdCliente() {
        return this.idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdTrotinete() {
        return idTrotinete;
    }

    public void setIdTrotinete(int idTrotinete) {
        this.idTrotinete = idTrotinete;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void serialize(DataOutputStream out) throws IOException{
        out.writeUTF(this.codigoReserva);
        out.writeUTF(this.idCliente);
        out.writeInt(this.idTrotinete);
        out.writeUTF(this.date.toString());
    }

    public static Reserva deserialize(DataInputStream in) throws IOException{
        String idReserva = in.readUTF();
        String idCliente = in.readUTF();
        int idTrotinete = in.readInt();
        LocalDateTime date = LocalDateTime.parse(in.readUTF());
        return new Reserva(idReserva, idCliente, idTrotinete,date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return idTrotinete == reserva.idTrotinete && Objects.equals(codigoReserva, reserva.codigoReserva) && Objects.equals(idCliente, reserva.idCliente) && Objects.equals(date, reserva.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoReserva, idCliente, idTrotinete, date);
    }

    @Override
    public String toString() {
        return "Reserva:" +
                "ID=" + codigoReserva +
                ", idCliente=" + idCliente +
                ", idTrotinete=" + idTrotinete +
                ", date=" + date;
    }
}

