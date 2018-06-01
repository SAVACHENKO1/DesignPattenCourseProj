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

import DAO.Products_DAO;
import DAO.purchase_DAO;
import common.LoggerClass;
import common.factoryOfGUI;
import interfaces.purchase_inter;

public class purchase_window extends JFrame{
	
	private static final long serialVersionUID = 7062331812790637486L;
	
	//two of the following change as the program add/delete tuples
	private JScrollPane Scrolltable = null;
	JTable t =null;
	purchase_inter purchase_db = new purchase_DAO();
	JTextField paypalnumber;
	JTextField hour;
	JTextField date_field;
	JTextField ssn_field;
	JTextField code_field;
	
	//create basic window with buttons and textfields
	public purchase_window()
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
		
		JLabel title = new JLabel("purchase window");
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
		
		JLabel code_label = new JLabel("product code:");
		code_label.setBounds(40, 480, 220, 30);
		code_field = new  JTextField();
		code_field.setBounds(40, 500, 220, 30);
		p.add(code_field);
		p.add(code_label);
		JLabel ssn_label = new JLabel("ssn:");
		ssn_label.setBounds(40,540, 220, 30);
		ssn_field = new  JTextField();
		ssn_field.setBounds(40,560, 220, 30);
		p.add(ssn_field);
		p.add(ssn_label);
		JLabel date_label = new JLabel("date code:");
		date_label.setBounds(280, 480, 220, 30);
		date_field = new  JTextField();
		date_field.setBounds(280, 500, 220, 30);
		p.add(date_field);
		p.add(date_label);
		JLabel hour_label = new JLabel("hour:");
		hour_label.setBounds(280,540, 220, 30);
		hour = new  JTextField();
		hour.setBounds(280, 560, 220, 30);
		p.add(hour);
		p.add(hour_label);
		JLabel paypalnumber_label = new JLabel("paypalnumber:");
		paypalnumber_label.setBounds(40,601, 220, 30);
		paypalnumber = new  JTextField();
		paypalnumber.setBounds(40, 622, 220, 30);
		p.add(paypalnumber);
		p.add(paypalnumber_label);
		
		
		JButton b2 = new JButton("click here to add");
		b2.setBounds(280, 622, 220, 30);
		b2.setFont(new Font("TimesRoman", Font.PLAIN, 22));
		b2.setBackground(Color.yellow);
		p.add(b2);
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoggerClass loggerWrapper = LoggerClass.getInstance();
				if(paypalnumber.getText().isEmpty()|| hour.getText().isEmpty() 	|| date_field.getText().isEmpty() ||ssn_field.getText().isEmpty()||code_field.getText().isEmpty()){
					JOptionPane.showConfirmDialog (null, "all fields must be field?","Warning", JOptionPane.WARNING_MESSAGE);
					LoggerClass.myLogger.log(Level.WARNING, "user left field empty filling purchase details");
				}
				else if(testCode(Integer.parseInt(code_field.getText()))==false){
					JOptionPane.showConfirmDialog (null, "illegade product code","Warning", JOptionPane.WARNING_MESSAGE);
					LoggerClass.myLogger.log(Level.WARNING, "user made mistake filling product details");
				}
				else{
					purchase_db.addTuple(code_field.getText(),paypalnumber.getText(),hour.getText(),date_field.getText(),ssn_field.getText());
					build_table();
					LoggerClass.myLogger.log(Level.WARNING, "user added new  purchase tuple for "+code_field.getText()+"by "+ssn_field.getText());
				}
			}

			private boolean testCode(int code) {
				ArrayList<Object> names = new ArrayList<Object>();
				names.add(code);
				names = Products_DAO.get_names(names);
				if(names.isEmpty())
					return false;
				return true;
			}
		});
		
	}
	
		
	
	//create the table and put it into scrollpanel component
	private void build_table() {
		ArrayList<Object> items = purchase_db.all();
		ArrayList<Object> names = new ArrayList<Object>();
		for(int i=0;i<items.size();i+=4){
			names.add(items.get(i));
		}
		names = Products_DAO.get_names(names);
	    
		JTable table = new JTable(new MyTableModel(items,names));
	
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
						int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to delete "+(String)table.getModel().getValueAt(row, 1)+"?","Warning", JOptionPane.YES_NO_OPTION);
						if(dialogResult == JOptionPane.YES_OPTION){
							purchase_db.deleteTuple((int)table.getModel().getValueAt(row, 0), 
														(String)table.getModel().getValueAt(row, 2), (String)table.getModel().getValueAt(row, 3));
							build_table();
							LoggerClass loggerWrapper = LoggerClass.getInstance();
							LoggerClass.myLogger.log(Level.FINE, "user deleted purchase tuple for "+(int)table.getModel().getValueAt(row, 0));
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

	private String[] columnNames = { "product code","product name", "ssn", "date",
        "paypalnumber"};

    private Object[][] data;
    
    
    /**
	 * @constructor
	 * create abstract table for JTable creation.
	 * @param items which is an instance containing all the rating of selected book.
	 */
    public MyTableModel(ArrayList<Object> items,ArrayList<Object> names)
    {
    	data=new Object[items.size()/4][5];
    	
    	for(int i=0;i<items.size(); i+=4){
    		int code = (int) items.get(i);
    		data[i/4][0] = code;
    		for(int j=0;j<names.size();j+=2)
    		{
    			if(code == (int)names.get(j))
    				data[i/4][1] = names.get(j+1);
    		}
    		data[i/4][2] = items.get(i+1);
    		data[i/4][3] = items.get(i+2);
    		data[i/4][4] = items.get(i+3);
        }

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
