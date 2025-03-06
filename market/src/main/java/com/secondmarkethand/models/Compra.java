package com.secondmarkethand.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreatedDate
    private LocalDateTime fechaCompra; 

    @ManyToOne
    private Usuario propietario;

    public Compra() {
    }

    public Compra(Usuario propietario) {
        this.propietario = propietario;
        this.fechaCompra = LocalDateTime.now(); 
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((fechaCompra == null) ? 0 : fechaCompra.hashCode());
        result = prime * result + ((propietario == null) ? 0 : propietario.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Compra other = (Compra) obj;
        if (id != other.id)
            return false;
        if (fechaCompra == null) {
            if (other.fechaCompra != null)
                return false;
        } else if (!fechaCompra.equals(other.fechaCompra))
            return false;
        if (propietario == null) {
            if (other.propietario != null)
                return false;
        } else if (!propietario.equals(other.propietario))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Compra [id=" + id + ", fechaCompra=" + fechaCompra + ", propietario=" + propietario + "]";
    }
}
