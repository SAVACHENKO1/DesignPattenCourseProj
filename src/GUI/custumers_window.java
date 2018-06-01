package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import DAO.Custumers_DAO;
import common.LoggerClass;
import common.factoryOfGUI;
import interfaces.custumers_inter;

public class custumers_window  extends JFrame{
	
	private static final long serialVersionUID = 5817276828219748875L;;
	
	//two of the following change as the program add/delete tuples
	private JScrollPane Scrolltable = null;
	JTable t =null;
	
	custumers_inter custumers_db = new Custumers_DAO();
	JTextField SSN;
	JTextField phone;
	JTextField adress_field;
	JTextField city_field;
	JTextField country_field;
	
	//create basic window with buttons and textfields
	public custumers_window()
	{
		this.Scrolltable = new JScrollPane();
		JPanel p = new JPanel();
		p.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		p.setBackground(Color.ORANGE);	
		
		this.add(p);
		this.setSize(700, 700);
		p.setBorder(null);
		
		JButton backbutt = new JButton("back");
		backbutt.setBounds(20, 20, 150, 30);
		backbutt.setFont(new Font("TimesRoman", Font.PLAIN, 22));
		backbutt.setBackground(Color.green);
		p.add(backbutt);
		backbutt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				factoryOfGUI.getGui_by_title("Employee_window");
			}
		});
		
		JLabel title = new JLabel("custumers window");
		title.setFont(new Font("Times New Roman",Font.BOLD,30));
		title.setForeground(Color.RED);
		title.setBounds(80,  50, 480, 50);
		p.add(title);
		build_table();
		this.Scrolltable.setBounds(100, 200, 400, 200);
		p.add(Scrolltable);
		
		JLabel add_title = new JLabel("update/add if doesnt exists");
		add_title.setFont(new Font("Times New Roman",Font.BOLD,30));
		add_title.setBounds(40,  420, 440, 50);
		p.add(add_title);
		
		JLabel country_label = new JLabel("country:");
		country_label.setBounds(40, 480, 220, 30);
		country_field = new  JTextField();
		country_field.setBounds(40, 500, 220, 30);
		p.add(country_field);
		p.add(country_label);
		JLabel city_label = new JLabel("city:");
		city_label.setBounds(40,540, 220, 30);
		city_field = new  JTextField();
		city_field.setBounds(40,560, 220, 30);
		p.add(city_field);
		p.add(city_label);
		JLabel adress_label = new JLabel("address:");
		adress_label.setBounds(280, 480, 220, 30);
		adress_field = new  JTextField();
		adress_field.setBounds(280, 500, 220, 30);
		p.add(adress_field);
		p.add(adress_label);
		JLabel phone_label = new JLabel("phone:");
		phone_label.setBounds(280,540, 220, 30);
		phone = new  JTextField();
		phone.setBounds(280, 560, 220, 30);
		p.add(phone);
		p.add(phone_label);
		JLabel SSN_label = new JLabel("SSN:");
		SSN_label.setBounds(40,601, 220, 30);
		SSN = new  JTextField();
		SSN.setBounds(40, 622, 220, 30);
		p.add(SSN);
		p.add(SSN_label);
		
		
		JButton b2 = new JButton("click here to add");
		b2.setBounds(280, 622, 220, 30);
		b2.setFont(new Font("TimesRoman", Font.PLAIN, 22));
		b2.setBackground(Color.yellow);
		p.add(b2);
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoggerClass loggerWrapper = LoggerClass.getInstance();
				if(SSN.getText().isEmpty()|| phone.getText().isEmpty() 	
						|| adress_field.getText().isEmpty() 
						||city_field.getText().isEmpty()
						||country_field.getText().isEmpty())
				{
					JOptionPane.showConfirmDialog (null, "all fields must be field?","Warning",
							JOptionPane.WARNING_MESSAGE);
				LoggerClass.myLogger.log(Level.FINE, "user entered illegal ssn");
				}
				else if(custumers_db.checkSSN(SSN.getText())==true)//ssn exists
				{
					custumers_db.updateTuple(country_field.getText(),SSN.getText()
							,phone.getText(),adress_field.getText()
							,city_field.getText());
					LoggerClass.myLogger.log(Level.FINE, "user updated details for "+SSN.getText());
				}		
				else{
					custumers_db.addTuple(country_field.getText()
							,SSN.getText(),phone.getText()
							,adress_field.getText()
							,city_field.getText());
					LoggerClass.myLogger.log(Level.FINE, "user deleted ssn "+SSN.getText());

				}
				build_table();
			}

			
		});
		
	}
	
	//create the table and put it into jscrollpanel component
	private void build_table() {
		ArrayList<Object> items = custumers_db.all();
	    
		JTable table = new JTable(new MyTableModel(items));
	
		table.setAutoCreateRowSorter(true);
	    table.setPreferredScrollableViewportSize(new Dimension(500, 70));
	    
	    this.t=table;
        //Create the scroll pane and add the table to it.
	    Scrolltable.getViewport().add(table);
	    
	   
	    
	    table.addMouseListener(new MouseAdapter() 
	    {
			public void mousePressed(MouseEvent me) 
			{
					JTable table =(JTable) me.getSource();
					Point po = me.getPoint();
					int row = table.rowAtPoint(po);
					if(row>=0)
					{
						int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to delete ssn: "+(String)table.getModel().getValueAt(row, 0)+"?","Warning", JOptionPane.YES_NO_OPTION);
						if(dialogResult == JOptionPane.YES_OPTION){
							custumers_db.deleteTuple((String)table.getModel().getValueAt(row, 0));
							build_table();
						}
					}
			}
	    });
	    
	}
		
	/**
	 * class mendatory for creating JTable and its
	 * content and function meant to ease the creation 
	 * of JTable
	 */
  class MyTableModel extends AbstractTableModel {
    
	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "SSN", "phone", "adress",
	        "city","country" };

    private Object[][] data;
    
    
    /**
	 * @constructor
	 * create abstract table for JTable creation.
	 * @param items which is an instance containing all the rating of selected book.
	 */
    public MyTableModel(ArrayList<Object> items)
    {
    	data=new Object[items.size()/5][5];
    	
    	for(int i=0;i<items.size(); i++)
    		data[i/5][i%5] = items.get(i);
    	

    }

    /**
	 * necessary override which was request by IDE.
	 */
    public int getColumnCount() {
      return columnNames.length;
    }
    /**
	 * necessary override which was request by IDE.
	 */
    public int getRowCount() {
      return data.length;
    }
    /**
	 * necessary override which was request by IDE.
	 */
    public String getColumnName(int col) {
      return columnNames[col];
    }
    /**
	 * necessary override which was request by IDE.
	 */
    public Object getValueAt(int row, int col) {
      
  
      return data[row][col];    
      }
  
    /**
     * JTable uses this method to determine the default renderer/ editor for
     * each cell. If we didn't implement this method, then the last column
     * would contain text ("true"/"false"), rather than a check box.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int c) {
      return getValueAt(0, c).getClass();
    }

    /**
     * returns if the cell is editable or not.
     */
    public boolean isCellEditable(int row, int col) {
      if (col < 4) {
        return false;
      } else {
    	  
        return true;
      }
      
    }
  }
  
}
