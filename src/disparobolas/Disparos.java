/*
 * Asignatura: Programación de Aplicaciones Interactivas (PAI)
 * Curso: 2016
 * Autor: Alexis Daniel Fuentes Pérez
 * Contacto: alu0100816761@ull.edu.es
 * 
 * Clase Disparos. Panel donde se representa el juego.
 */

package disparobolas;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Disparos extends JPanel {
  
  final Color COLOR_FONDO = new Color (185, 185, 253);			// Color del fondo
  final int CIENTO_OCHENTA_GRADOS = 180;										// Gragos en los que se mueve el cañon
  final int ANGULO_DEFECTO = 90;														// Angulo en el que empieza el cañon
  final int TAM_VECTOR = 40;																// Tamaño del cañon
  final int INICIO_FLECHA = TAM_VECTOR / 2;									// Tamaño de las flechas del cañon
  final int SEPARACION_FLECHA = 5;													// Separacion del cañon
  final int TAM_BOLA = 35;
  final int SEPARACION_BOLAS = 0;
  final double TIEMPO_OBSERVACION = 0.1;
  final double VELOCIDAD = 50;															// Velocidad del disparo
  final double TOLERANCIA = 2;															// Tolerancia para determinar la colision
  
  private double angulo;
  private ArrayList<Bola> bolas;
  private ArrayList<Punto> disparo;
  private boolean inicializar;
  private MiTimer timer;
  private AudioClip audioFallo;
  private AudioClip audioLogrado;
  private int colisionados;																	// Numero de bolas colisionadas en un tiro
  private ArrayList<Integer> rebotes;
  
  public Disparos () {
    setBackground(COLOR_FONDO);
    angulo = ANGULO_DEFECTO;
    bolas = new ArrayList<Bola> ();
    disparo = new ArrayList<Punto> ();
    inicializar = true;
    colisionados = 0;
    timer = new MiTimer (this);
    rebotes = new ArrayList<Integer> ();
    
    URL urlFallo = this.getClass().getResource("fail.wav");
    audioFallo = Applet.newAudioClip (urlFallo);
    
    URL urlLogrado = this.getClass().getResource("success.wav");
    audioLogrado = Applet.newAudioClip (urlLogrado);
  }
  
  /**
   * Getters y setters
   */
  
  public int getOrigenX () {
    return getWidth() / 2;
  }
  
  public int getOrigenY () {
    return getHeight();
  }
  
  /**
   * Funcion que es llamada al pulsar Click Izquierdo del raton. Dispara una bola.
   */
  
  public void disparar () {
  	if(getTimer().getPreparado()) {
	    getTimer().setPreparado(false);
	    MovRectilineo mr = new MovRectilineo (VELOCIDAD, getAngulo());
	    realizarTrayectoria (mr);
  	}
  }
  
  /**
   * Getters y setters
   */
  
  public int getOrigenXBola () {
    return getOrigenX() - (TAM_BOLA / 2);
  }
  
  public int getOrigenYBola () {
    return getOrigenY() - (TAM_BOLA / 2);
  }
  
  /**
   * Realiza la trayectoria MRU del disparo
   */
  
  public void realizarTrayectoria (MovRectilineo mr) {
    ArrayList<Punto> trayectoria = getDisparo ();
    trayectoria.clear();
    
    int x = getOrigenX();
    int y = getOrigenY();
    double tiempo = 0;
    
    while((y > 0)) {
      x = (int)(getOrigenXBola() + mr.getPosicionX(tiempo));
      y = (int)(getOrigenYBola() - mr.getPosicionY(tiempo));
      if(x < 0) {
      	x = Math.abs(x);
      }
      if(x > getWidth()) {
      	x = getWidth() - (x % getWidth());
      }
      trayectoria.add(new Punto(Math.abs(x), Math.abs(y)));
      tiempo += TIEMPO_OBSERVACION;
    }
  }
  
  /**
   * Método para determinar si ha ocurrido una colisión, y de ser así la gestiona.
   * @param p Punto actual del disparo
   * @return true - false
   */
  
  public boolean hayColision (Punto p) {
    boolean colision = false;
    Bola bolaColisionada = new Bola (0, 0);
    setColisionados(0);
    
    for(int i = 0; i < getBolas().size(); i++) {
      Bola b = getBolas().get(i);
      if(distanciaEuclidea (p, b) <= TAM_BOLA + TOLERANCIA) {
        colision = true;
        bolaColisionada = b;
        setColisionados(getColisionados() + 1);
      }
    }
    
    if(getColisionados() == 1) {
    	if(bolaColisionada.getColor().equals(getTimer().getColor())) {
	      getAudioLogrado().play();
	      eliminarVecinas(bolaColisionada);
	      getBolas().remove(bolaColisionada);
    	} else {
    		getAudioFallo().play();
    		getBolas().add(new Bola (p.getX(), p.getY(), getTimer().getColor()));
    	}
    } else if(getColisionados () > 1){
      getAudioFallo().play();
    }
            
    return colision;
  }
  
  public void eliminarVecinas (Bola bola) {
  	for(int i = 0; i < getBolas().size(); i++) {
      Bola b = getBolas().get(i);
      if(distanciaEuclidea (bola, b) <= TAM_BOLA + TOLERANCIA) {
        if(b.getColor().equals(bola.getColor())) {
    			getBolas().remove(b);
    			eliminarVecinas(b);
        }
      }
    }
  }
  
  /**
   * Metodo para calcular la distancia euclidea entre el disparo y una bola
   * @param p Bola disparada
   * @param b Bola
   * @return Distancia
   */
  
  public double distanciaEuclidea (Punto p, Bola b) {
    Punto p1 = new Punto (p.getX() + (TAM_BOLA / 2), p.getY() + (TAM_BOLA / 2));
    Punto p2 = new Punto (b.getX() + (TAM_BOLA / 2), b.getY() + (TAM_BOLA / 2));
    
    return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
  }
  
  public double distanciaEuclidea (Bola p, Bola b) {
    Punto p1 = new Punto (p.getX() + (TAM_BOLA / 2), p.getY() + (TAM_BOLA / 2));
    Punto p2 = new Punto (b.getX() + (TAM_BOLA / 2), b.getY() + (TAM_BOLA / 2));
    
    return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
  }
  
  /**
   * Pinta el vector de lanzamiento
   * @param g
   */
  
  public void pintarVector (Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setStroke(new BasicStroke (2));
    g2d.setColor(Color.WHITE);
    
    Line2D linea = new Line2D.Double (0, 0, TAM_VECTOR, 0);
    Line2D flechaIz = new Line2D.Double (TAM_VECTOR, 0, INICIO_FLECHA, -SEPARACION_FLECHA);
    Line2D flechaDe = new Line2D.Double (TAM_VECTOR, 0, INICIO_FLECHA, +SEPARACION_FLECHA);
    
    AffineTransform original = g2d.getTransform();
    g2d.translate(getOrigenX(), getOrigenY());
    g2d.rotate(-Math.toRadians(getAngulo()));
    
    g2d.draw(linea);
    g2d.draw(flechaIz);
    g2d.draw(flechaDe);
    
    g2d.setTransform(original);
  }
  
  /**
   * Inicializa las bolas del juego
   */
  
  public void inicializarBolas () {
    int utilizado = 0;
    while ((utilizado + TAM_BOLA + SEPARACION_BOLAS) < getWidth()) {
      Bola b = new Bola (utilizado + SEPARACION_BOLAS, 0);
      getBolas().add(b);
      utilizado += SEPARACION_BOLAS + TAM_BOLA;
    }
  }
  
  /**
   * Dibuja la fila de bolas en la parte superior del panel
   * @param g
   */
  
  public void pintarBolas (Graphics g) {
    for (int i = 0; i < getBolas().size(); i++) {
      Bola b = getBolas().get(i);
      g.setColor(b.getColor());
      g.fillOval(b.getX(), b.getY(), TAM_BOLA, TAM_BOLA);
    }
  }
  
  /**
   * Sobreescribe paintComponet para dibujar el juego en el panel
   */
  
  protected void paintComponent (Graphics g) {
    super.paintComponent(g);
    
    pintarVector(g);
    
    if (getInicializar()) {
      inicializarBolas();
      setInicializar(false);
    }
    
    pintarBolas(g);
    
    if (getTimer().getPreparado()) {
      if (getTimer().getCambioColor())
        getTimer().setColor();
      g.setColor(getTimer().getColor());
      int x = getOrigenXBola();
      int y = getOrigenYBola();
      g.fillOval(x, y, TAM_BOLA, TAM_BOLA);
    }
  }
  
  /**
   * Getters y setters
   */
  
  public AudioClip getAudioFallo () {
    return audioFallo;
  }
  
  public AudioClip getAudioLogrado () {
    return audioLogrado;
  }
  
  public MiTimer getTimer () {
    return timer;
  }
  
  public double getAngulo () {
    return angulo;
  }
  
  public void setAngulo (double alfa) {
    angulo = alfa;
  }
  
  public boolean getInicializar () {
    return inicializar;
  }
  
  public void setInicializar (boolean estado) {
    inicializar = estado;
  }
  
  public ArrayList<Bola> getBolas () {
    return bolas;
  }
  
  public ArrayList<Punto> getDisparo () {
    return disparo;
  }
  
  public int getColisionados () {
    return colisionados;
  }
  
  public void setColisionados (int n) {
    colisionados = n;
  }
  
  public ArrayList<Integer> getRebotes () {
  	return rebotes;
  }
}
