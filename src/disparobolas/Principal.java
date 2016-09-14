/*
 * Asignatura: Programación de Aplicaciones Interactivas (PAI)
 * Curso: 2016
 * Autor: Alexis Daniel Fuentes Pérez
 * Contacto: alu0100816761@ull.edu.es
 * 
 * Clase Principal. Juego de disparar bolas. Puede ser ejecutador como Applet y Standalone.
 */

package disparobolas;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Principal {
    
  final static int ANCHO = 800;
  final static int ALTO = 300;
  
  public static void main(String[] args) {
    JFrame frame = new JFrame ("Disparo de Bolas");
    
    Vista applet = new Vista ();
    
    applet.setStandalone(true);
    
    //applet.getParametrosLineaComandos(args);
    
    frame.add(applet, BorderLayout.CENTER);
    
    applet.init();
    applet.start();
    
    frame.setSize(ANCHO, ALTO);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

}
