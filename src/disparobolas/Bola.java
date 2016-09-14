/*
 * Asignatura: Programación de Aplicaciones Interactivas (PAI)
 * Curso: 2016
 * Autor: Alexis Daniel Fuentes Pérez
 * Contacto: alu0100816761@ull.edu.es
 * 
 * Clase Bola. Representa una bola.
 */

package disparobolas;

import java.awt.Color;
import java.util.Random;

public class Bola {
  final Color[] COLORES = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.CYAN, new Color (252, 62, 226)};
  final int CANTIDAD_COLORES = 6;
  
	private int coordX;
	private int coordY;
	private Color color;
	
	public Bola (int x, int y) {
		coordX = x;
		coordY = y;
		Random rand = new Random ();
		color = COLORES[rand.nextInt(CANTIDAD_COLORES)];
	}
	
	public Bola (int x, int y, Color c) {
		coordX = x;
		coordY = y;
		Random rand = new Random ();
		color = c;
	}
	
	public Bola () {
		coordX = 0;
		coordY = 0;
	}
	
	/**
	 * Getters y setters
	 */
	
	public int getX () {
		return coordX;
	}
	
	public int getY () {
		return coordY;
	}
	
	public void setX (int x) {
		coordX = x;
	}
	
	public void setY (int y) {
		coordY = y;
	}
	
	public Color getColor () {
	  return color;
	}
}
