/*
 * Asignatura: Programación de Aplicaciones Interactivas (PAI)
 * Curso: 2016
 * Autor: Alexis Daniel Fuentes Pérez
 * Contacto: alu0100816761@ull.edu.es
 * 
 * Clase MovRectilineo. Representa al MRU y tienes los metodos necesarios para realizarlo
 */

package disparobolas;

public class MovRectilineo {
	
	private double velocidad;
	private double angulo;
	
	public MovRectilineo (double v, double alfa) {
		velocidad = v;
		angulo = Math.toRadians(alfa);
	}
	
	/**
	 * Calcula el espacio X para la velocidad de X
	 * @param tiempo
	 * @return Espacio x
	 */
	
	public double getPosicionX (double tiempo) {
		return (getVelocidadX() * tiempo);
	}
	
	/**
	 * Calcula el espacio Y para la velocidad de Y
	 * @param tiempo
	 * @return Espacio y
	 */
	
	public double getPosicionY (double tiempo) {
		return (getVelocidadY() * tiempo);
	}
	
	/**
	 * Getters y setters
	 */
	
	public double getVelocidadX () {
		return (velocidad * Math.cos(getAngulo()));
	}
	
	public double getVelocidadY () {
		return (velocidad * Math.sin(getAngulo()));
	}
	
	public double getAngulo () {
		return angulo;
	}
	
	public void setAngulo (double alfa) {
		angulo = Math.toRadians(alfa);
	}
	
	public void setVelocidad (double v) {
		velocidad = v;
	}
	
	public double getVelocidad () {
		return velocidad;
	}
}
