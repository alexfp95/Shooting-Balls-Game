/*
 * Asignatura: Programación de Aplicaciones Interactivas (PAI)
 * Curso: 2016
 * Autor: Alexis Daniel Fuentes Pérez
 * Contacto: alu0100816761@ull.edu.es
 * 
 * Clase Vista. Representa la vista de la aplicación. Puede ser ejecutado como Applet y Standalone
 */

package disparobolas;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Vista extends JApplet {
  
  final int HGAP = 20;
  final int VGAP = 20;
  final Color COLOR_FONDO = new Color (185, 185, 253);            
  final int DIMENSION_INFO = 40;                           // Dimension del boton de informacion
  final int AJUSTE_ICONO = 6;                              // Ajuste para el icono del boton de información
  
  private Disparos panelDisparos;
  private JPanel panelInferior;
  private JButton informacion;
  private Informacion ventanaInfo;
  private AudioClip audioDisparo;
  
  private boolean esStandalone = false;
  
  public void init () {
    
    if(!getStandalone()) {
      // Obtener parametros
    }
    
    setLayout(new BorderLayout (HGAP, 0));
    setBackground(COLOR_FONDO);
    
    panelDisparos = new Disparos ();
    add(panelDisparos, BorderLayout.CENTER);
    
    panelInferior = new JPanel ();
    panelInferior.setLayout(new FlowLayout (FlowLayout.RIGHT, 0, 0));
    panelInferior.setBackground(COLOR_FONDO);
    
    informacion = new JButton ();
    informacion.setPreferredSize(new Dimension (DIMENSION_INFO, DIMENSION_INFO));
    ImageIcon ayudaIcon = new ImageIcon ("src/disparobolas/info.png");
    Icon icono = new ImageIcon(ayudaIcon.getImage().getScaledInstance(DIMENSION_INFO - AJUSTE_ICONO, DIMENSION_INFO - AJUSTE_ICONO, Image.SCALE_DEFAULT));
    informacion.setIcon(icono);
    
    panelInferior.add(informacion);
    add(panelInferior, BorderLayout.SOUTH);
    
    ventanaInfo = new Informacion ();
    
    crearAudios();
    
    insertarListeners();
  }
  
  /**
   * Método que crea los audios de la vista
   */
  
  public void crearAudios () {
      URL url = this.getClass().getResource("shoot.wav");
      audioDisparo = Applet.newAudioClip (url);
  }
  
  /**
   * Método que inserta los listeners en los objetos necesarios
   */
  
  public void insertarListeners () {
    getBotonInfo().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        getVentanaInfo().setVisible(true);
      }
    });
    getPanelDisparos().addMouseListener(new MouseAdapter() {
      public void mouseClicked (MouseEvent e) {
      	if(e.getButton() == MouseEvent.BUTTON1) {
	        getAudioDisparo().play();
	        getPanelDisparos().disparar();
      	}
      }
    });
    getPanelDisparos().addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved (MouseEvent e) {
        double angulo = -(Math.atan2(e.getY() - getPanelDisparos().getOrigenY(), e.getX() - getPanelDisparos().getOrigenX()));
        getPanelDisparos().setAngulo(Math.toDegrees(angulo));
        getPanelDisparos().repaint();
      }
    });
  }
  
  /**
   * Getters y setters
   */
  
  public AudioClip getAudioDisparo () {
    return audioDisparo;
  }
    
  public Disparos getPanelDisparos () {
    return panelDisparos;
  }
  
  public Informacion getVentanaInfo () {
    return ventanaInfo;
  }
  
  public JButton getBotonInfo () {
    return informacion;
  }
  
  public void setStandalone (boolean estado) {
    esStandalone = estado;
  }
  
  public boolean getStandalone () {
    return esStandalone;
  }
  
  public void getParametrosLineaComandos (String[] args) {
    // Obtener parametros
  }
}
