/*
 * Asignatura: Programación de Aplicaciones Interactivas (PAI)
 * Curso: 2016
 * Autor: Alexis Daniel Fuentes Pérez
 * Contacto: alu0100816761@ull.edu.es
 * 
 * Clase MiTimer. Timer que se utiliza para dibujar en la interfaz.
 */

package disparobolas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

/**
 * Clase MiTimer
 * @author Alexis Daniel Fuentes Pérez
 */

public class MiTimer {
	
	final int TAM_PROYECTIL = 3;
	final Color[] COLORES = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.CYAN, new Color (252, 62, 226)};
	final Color COLOR_FONDO = new Color (185, 185, 253);
	final int NUM_COLORES = 6;
	final int DELAY = 5;                    // Delay del timer
	final int TAM_BOLA = 35;
	final int UN_SEGUNDO = 1500;
	
	private Disparos disparos;
	private Graphics g;
	private Integer indice;
	private Timer timer;
	private Color colorDisp;
	private boolean preparado;
	private boolean cambioColor;
	
	public MiTimer (Disparos p) {
		timer = new Timer(DELAY, new TimerListener());
		disparos = p;
		indice = 0;
		timer.start();
		preparado = true;
		cambioColor = true;
	}
	
	/**
	 * Getters y setters
	 */
	
	public Disparos getPanelDisparos () {
		return disparos;
	}
	
	public Integer getIndice () {
		return indice;
	}
	
	public void setIndice (int i) {
		indice = i;
	}
	
	public void setGraphics (Graphics gr) {
		g = gr;
	}
	
	public Graphics getGraphics () {
		return g;
	}
	
	public Color getColor () {
	  return colorDisp;
	}
	
	public void setColor () {
	  ArrayList<Bola> bolas = getPanelDisparos().getBolas();
	  Random rand = new Random ();
	  boolean existe = false;
	  while(!existe) {
	    colorDisp = COLORES[rand.nextInt(NUM_COLORES)];
	    for(int i = 0; i < bolas.size(); i++) {
	      if(bolas.get(i).getColor().equals(colorDisp)) {
	        existe = true;
	        i = bolas.size();
	      }
	    }
	  }
	  setCambioColor(false);
	}
	
	public boolean getPreparado () {
	  return preparado;
	}
	
	public void setPreparado (boolean estado) {
	  preparado = estado;
	}
	
	public boolean getCambioColor () {
    return cambioColor;
  }
  
  public void setCambioColor (boolean estado) {
    cambioColor = estado;
  }
  
  /**
   * Método para dormir el timer
   * @param delay
   */
  
  public void dormir (int delay) {
    try {
      Thread.sleep(delay);
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    }
  }
		
	/**
	 * Clase interna TimerListener. Dibuja el trayecto del disparo
	 * @author Alexis Daniel Fuentes Pérez
	 */
	
	private class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) { 
			if(getIndice() < getPanelDisparos().getDisparo().size()) {
			  setGraphics(getPanelDisparos().getGraphics());
			  getGraphics().setColor(getColor());

			  ArrayList<Punto> puntos = getPanelDisparos().getDisparo();
			  Punto p = puntos.get(getIndice());
			  getGraphics().fillOval(p.getX(), p.getY(), TAM_BOLA, TAM_BOLA);
			  
			  //if(p.getY() <= TAM_BOLA) {
  			  if(getPanelDisparos().hayColision(p)) {
  			    setIndice(puntos.size() - 1);
  			  }
			  //}
			  
			  dormir (DELAY);

			  if (getIndice() == (puntos.size() - 1)) {
			    setIndice(0);
			    puntos.clear();
			    if(getPanelDisparos().getBolas().size() > 0) {
			    	setPreparado(true);
			    } else {
			    	setPreparado(false);
			    }
			    setCambioColor(true);
			    getPanelDisparos().repaint();
			  } else {
			    setIndice(getIndice() + 1);
			    getPanelDisparos().repaint();
          getGraphics().fillOval(p.getX(), p.getY(), TAM_BOLA, TAM_BOLA);
			  }
			}
    }
	}
}
