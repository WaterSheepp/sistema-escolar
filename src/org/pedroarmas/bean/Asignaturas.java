package org.pedroarmas.bean;

/**
 *
 * @author programacion
 */
public class Asignaturas {
    private int codigoAsignatura;
    private String Asignatura;
    private int codigoGrado;

    public Asignaturas() {
    }

    public Asignaturas(int codigoAsignatura, String Asignatura, int codigoGrado) {
        this.codigoAsignatura = codigoAsignatura;
        this.Asignatura = Asignatura;
        this.codigoGrado = codigoGrado;
    }

    public int getCodigoAsignatura() {
        return codigoAsignatura;
    }

    public void setCodigoAsignatura(int codigoAsignatura) {
        this.codigoAsignatura = codigoAsignatura;
    }

    public String getAsignatura() {
        return Asignatura;
    }

    public void setAsignatura(String Asignatura) {
        this.Asignatura = Asignatura;
    }

    public int getCodigoGrado() {
        return codigoGrado;
    }

    public void setCodigoGrado(int codigoGrado) {
        this.codigoGrado = codigoGrado;
    }

    @Override
    public String toString() {
        return "" + codigoAsignatura;
    }
    
}
