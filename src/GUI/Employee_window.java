package GUI;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import common.LoggerClass;
import common.factoryOfGUI;

public class Employee_window extends JFrame{

	private static final long serialVersionUID = -4136526480607645476L;

	public Employee_window()
	{
		JPanelWithBackground pic = null;
		try {
			pic = new JPanelWithBackground();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JPanel p=new JPanel();
		p.setBackground(Color.ORANGE);
		p.setLayout(null);
		
		
		
		JButton b1 = new JButton("purchase_window");
		b1.setBounds(20, 20, 220, 30);
		b1.setFont(new Font("TimesRoman", Font.PLAIN, 22));
		b1.setBackground(Color.green);
		p.add(b1);
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				factoryOfGUI.getGui_by_title("purchase_window");
				LoggerClass loggerWrapper = LoggerClass.getInstance();
				loggerWrapper.myLogger.log(Level.FINE, "user opened purchase window");
			}
		});
		
		JButton b2 = new JButton("suppliers");
		b2.setBounds(270, 20, 220, 30);
		b2.setFont(new Font("TimesRoman", Font.PLAIN, 22));
		b2.setBackground(Color.green);
		p.add(b2);
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				factoryOfGUI.getGui_by_title("supplies_window");
				LoggerClass loggerWrapper = LoggerClass.getInstance();
				loggerWrapper.myLogger.log(Level.FINE, "user opened supllies window");
			}
		});
		
		JButton b3 = new JButton("custumers");
		b3.setBounds(530, 20, 220, 30);
		b3.setFont(new Font("TimesRoman", Font.PLAIN, 22));
		b3.setBackground(Color.green);
		p.add(b3);
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				factoryOfGUI.getGui_by_title("custumers_window");
				@SuppressWarnings("unused")
				LoggerClass loggerWrapper = LoggerClass.getInstance();
				LoggerClass.myLogger.log(Level.FINE, "user opened sells window");
			}
		});
		JButton b4 = new JButton("products");
		b4.setBounds(270, 90, 220, 30);
		b4.setFont(new Font("TimesRoman", Font.PLAIN, 22));
		b4.setBackground(Color.green);
		p.add(b4);
		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				factoryOfGUI.getGui_by_title("products_window");
				LoggerClass loggerWrapper = LoggerClass.getInstance();
				loggerWrapper.myLogger.log(Level.FINE, "user opened products window");
			}
		});
		
		this.add(p);
		pic.setBounds(170, 150, 400, 400);
		p.add(pic);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(770, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	
	@SuppressWarnings("serial")
	public class JPanelWithBackground extends JPanel {

		  private Image backgroundImage=null;

		  // Some code to initialize the background image.
		  // Here, we use the constructor to load the image. This
		  // can vary depending on the use case of the panel.
		  public JPanelWithBackground() throws IOException {
			  try{
				  backgroundImage = ImageIO.read(new File("c:/misc/images.jpg"));
				  backgroundImage = backgroundImage.getScaledInstance(400, 400, 100);
			  }
			  catch(Exception e)
			  {
				  JLabel err = new JLabel("photo is missing");
				  this.add(err);
			  }
		  }

		  public void paintComponent(Graphics g) {
		    super.paintComponent(g);

		    // Draw the background image.
		    if(backgroundImage!=null)
		    	g.drawImage(backgroundImage, 0, 0, this);
		    else{
		    	 	Font f = new Font("Helvetica", Font.BOLD,20);
	                g.setFont(f);
	                g.drawString("Keep Smiling!!!", 170, 290);
	                g.drawOval(60, 60, 200, 200);
	                g.fillOval(90, 120, 50, 20);
	                g.fillOval(190, 120, 50, 20);
	                g.drawLine(165, 125, 165, 175);
	                g.drawArc(110, 130, 95, 95, 0, -180);
		    }
		  }
		}
}
