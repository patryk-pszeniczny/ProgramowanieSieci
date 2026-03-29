/*
 *  Koszalin 2004
 *  Klient graficzny Stock - Callback Demo
 *  Dariusz Rataj (C)
 */
 
package rmi.RMICallback;

import java.awt.*;
import java.applet.*;
import java.util.*;
import java.net.*;
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

public class StockClientGraph extends Applet implements StockUpdate {

  Panel gorny;
  ChartCanvas canvas;
  int licznik=0;
  String wskID = "";

  public void init()
   {
    setLayout(new BorderLayout());
    gorny = new Panel();
    gorny.setBackground(new Color(0xdd,0xdd,0xdd));
    gorny.add(new Label("TPSA"));
    canvas = new ChartCanvas();
    add("North",gorny);
    canvas.setType("line", true);
    canvas.setType("bar", false);
    canvas.setType("point", true);
    add("Center",canvas);
   }
 
 /* metoda zdalna */
 public synchronized void updatePrice(String name, String price) throws RemoteException {
  licznik++;
  canvas.addPoint("y"+licznik, Float.valueOf(price).floatValue());
  System.out.println(" Aktualizacja danych: papier " + name + " = " + price + " zl");
 } 

 public static void main(String args[]) {
   StockClientGraph client = new StockClientGraph();
   client.init();
   Frame f = new Frame();
   f.add("Center", client);
   f.setSize(400,200);
   f.show();
    
    try {
      UnicastRemoteObject.exportObject(client);  // aktywacja obiektu client
      StockInterface stockObject = (StockInterface)Naming.lookup("rmi://127.0.0.1:1099/StockTpsa");
      stockObject.regCallback(client);
    } catch (Exception ex) {
      System.err.println("Blad: " + ex);
      System.exit(2);
    }
  } 
}	// StockClientGraph

/* --------------------------------------------------------------------------------- */
class ChartCanvas extends Canvas
{
 Hashtable points = new Hashtable();
 boolean bar = false, line = false, point = false;
 int leftmargin = 40, topmargin = 20, hskok = 20, vskok = 20;
 float min, max;
 Rectangle r;
 int hlines = 10, vlines = 10;

 public ChartCanvas()
  {
   setBackground(new Color(0xee,0xee,0xee));
  }

 public void addPoint(String name, float value) 
 {
  points.put(name, new Float(value));
  repaint();
 }
 
 public void setType(String name, boolean state) 
 {
  if (name.equals("bar")) bar = state;
  if (name.equals("line")) line = state;
  if (name.equals("point")) point = state;
 }
 
 public void paintSiatka(Graphics g) 
 {
  g.setColor(new Color(0xcc,0xcc,0xcc));
  // poziome
  for (int i = 0; i <= hlines; i++) { 
   g.drawLine(leftmargin, topmargin + i * hskok, r.width-leftmargin, topmargin + i * hskok);
  }
  // pionowe
  for (int i = 0; i <= vlines; i++) {
   g.drawLine(leftmargin + i * vskok, topmargin, leftmargin + i * vskok, r.height-topmargin);
  }
 }
 
  public void paintUklad(Graphics g) 
 {
  g.setColor(Color.black);
  g.drawLine(leftmargin, r.height-topmargin, r.width, r.height-topmargin);
  g.drawLine(leftmargin, 0, leftmargin, r.height-topmargin);
  g.drawLine(leftmargin, 0, leftmargin-2, 10);
  g.drawLine(leftmargin, 0, leftmargin+2, 10);
  g.drawLine(r.width, r.height-topmargin, r.width-10, r.height-topmargin - 2);
  g.drawLine(r.width, r.height-topmargin, r.width-10, r.height-topmargin + 2);
 }
 
 public void minmax() 
 {int i; float y;
  if (points.size()<1) return;
  min = ((Float)points.get("y1")).floatValue();
  max = min;
  for (i=1;i<=points.size();i++)
   {
    y = ((Float)points.get("y"+i)).floatValue();
    if (y>max) max=y;
    if (y<min) min=y;
   }
 }
 
 public void paintWykres(Graphics g) 
 {
  int i, ystart;
  float y, yold = 0.0f;
  int voffset, starty;
//  voffset = (int)((r.width - 2 * leftmargin)/(points.size()-0.5));
  voffset = (int)((r.width - leftmargin)/(points.size()+1));
  minmax();
  
  float wsp;
  wsp=(r.height - 2 * topmargin)/(max-min);
  starty = topmargin;
  g.setColor(Color.black);
  g.drawLine(leftmargin, r.height-starty, r.width-leftmargin, r.height-starty);
  g.drawString(" maks = "+max, leftmargin+10, topmargin-2);
  g.drawString(" min = "+min,leftmargin+10, r.height-starty + 16);
  for (i=1;i<=points.size();i++)
   {
    y = ((Float)points.get("y"+i)).floatValue()-min;
    // BAR
    if (bar)
    {
      // voffset/2
      g.setColor(Color.blue);
      g.fillRect((int)(leftmargin+(i-1)*voffset),(int)(r.height - starty - y*wsp), 5, (int)(y*wsp));
      g.setColor(Color.black);
      g.drawRect((int)(leftmargin+(i-1)*voffset),(int)(r.height - starty - y*wsp), 5, (int)(y*wsp));
     } 
    // LINE
    if (line)
    {
    g.setColor(Color.red);
    if (i>1){
      g.drawLine((int)(leftmargin+(i-1)*voffset)+3,(int)(r.height - starty - y*wsp),
                (int)(leftmargin+(i-2)*voffset)+3,(int)(r.height - starty - yold*wsp ));
      yold=y;
      }
    }
    // POINT         
    if (point)
    {
    g.setColor(Color.red);
    g.fillRect((int)(leftmargin+(i-1)*voffset),(int)(r.height - starty - y*wsp -3),6,6);
    g.setColor(Color.white);
    g.drawRect((int)(leftmargin+(i-1)*voffset),(int)(r.height - starty - y*wsp -3),6,6);
    }
    g.setColor(Color.black);
    g.drawLine((int)(leftmargin+(i-1)*voffset)+3,r.height - topmargin - 2,
                (int)(leftmargin+(i-1)*voffset)+3,r.height - topmargin +2);

    yold=y;
   }
 }
  
 public void paint(Graphics g) 
  {
   Font font = g.getFont();
   
   if (points.size()<1) return;
   r = getBounds();
   vskok = (r.width - 2*leftmargin)/vlines;
   hskok = (r.height - 2*topmargin)/hlines;

   paintSiatka(g); 
   paintUklad(g); 
   paintWykres(g);
  }
 
}
