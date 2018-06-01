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
import DAO.Shipments_DAO;
import common.LoggerClass;
import common.factoryOfGUI;
import interfaces.Shipment_inter;

public class supplies_window  extends JFrame{
	
	private static final long serialVersionUID = 7062331812790637486L;
	
	//two of the following change as the program add/delete tuples
	private JScrollPane Scrolltable = null;
	JTable t =null;
	
	Shipment_inter shipment_db=new Shipments_DAO();
	
	JTextField supllier;
	JTextField quantity;
	JTextField date_field;
	JTextField code_field;
	
	//create basic window with buttons and textfields
	public supplies_window ()
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
		
		JLabel title = new JLabel("suppliers window");
		title.setFont(new Font("Times New Roman",Font.BOLD,30));
		title.setForeground(Color.RED);
		title.setBounds(80,  50, 480, 50);
		p.add(title);
		build_table();
		this.Scrolltable.setBounds(10, 150, 680, 200);
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
		JLabel date_label = new JLabel("duedate:");
		date_label.setBounds(280, 480, 220, 30);
		date_field = new  JTextField();
		date_field.setBounds(280, 500, 220, 30);
		p.add(date_field);
		p.add(date_label);
		JLabel quantity_label = new JLabel("quantity:");
		quantity_label.setBounds(280,540, 220, 30);
		quantity = new  JTextField();
		quantity.setBounds(280, 560, 220, 30);
		p.add(quantity);
		p.add(quantity_label);
		JLabel supllier_label = new JLabel("supllier:");
		supllier_label.setBounds(40,540, 220, 30);
		supllier = new  JTextField();
		supllier.setBounds(40, 560, 220, 30);
		p.add(supllier);
		p.add(supllier_label);
		
		
		
		JButton b2 = new JButton("click here to add");
		b2.setBounds(280, 622, 220, 30);
		b2.setFont(new Font("TimesRoman", Font.PLAIN, 22));
		b2.setBackground(Color.yellow);
		p.add(b2);
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoggerClass loggerWrapper = LoggerClass.getInstance();
				if(supllier.getText().isEmpty()|| quantity.getText().isEmpty() 	|| date_field.getText().isEmpty() ||supllier.getText().isEmpty()||code_field.getText().isEmpty()){
					JOptionPane.showConfirmDialog (null, "all fields must be field?","Warning", JOptionPane.WARNING_MESSAGE);
					LoggerClass.myLogger.log(Level.WARNING, "user left field empty filling supply");
				}
				else if(testCode(Integer.parseInt(code_field.getText()))==false){
					JOptionPane.showConfirmDialog (null, "illegade product code","Warning", JOptionPane.WARNING_MESSAGE);
					LoggerClass.myLogger.log(Level.WARNING, "user added supply for "+ Integer.valueOf(code_field.getText())+" on " +date_field.getText());
				}
				else{
					LoggerClass.myLogger.log(Level.FINE, "user added supply for "+ Integer.valueOf(code_field.getText())+" on " +date_field.getText());
					shipment_db.addTuple(Integer.valueOf(code_field.getText()),Integer.valueOf(quantity.getText()),date_field.getText(),supllier.getText());
					build_table();
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
		ArrayList<Object> items = shipment_db.all();
		ArrayList<Object> names = new ArrayList<Object>();
		for(int i=0;i<items.size();i+=5){
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
					int col = table.columnAtPoint(po);
					if(row>=0)
					{
						LoggerClass loggerWrapper = LoggerClass.getInstance();
						if(col==5 && ((String)table.getModel().getValueAt(row, 5)).equals("arrived")==false)
						{
							int dialogResult = JOptionPane.showConfirmDialog (null, "click to approve the product arrived by "+(String)table.getModel().getValueAt(row, 4)+" at "+(String)table.getModel().getValueAt(row, 3)+"!!","Warning", JOptionPane.YES_NO_OPTION);
							if(dialogResult == JOptionPane.YES_OPTION){
								shipment_db.updateShipmentArrival(
										(int)table.getModel().getValueAt(row, 0)
										,(String)table.getModel().getValueAt(row, 3)
										,(String)table.getModel().getValueAt(row, 4)
										,Integer.parseInt((String) table.getModel().getValueAt(row, 2)));
								LoggerClass.myLogger.log(Level.FINE, "user approved arrival of "+(int)table.getModel().getValueAt(row, 0));
								build_table();
								
						}}
						else if(col<5)
						{
							int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to delete "+(String)table.getModel().getValueAt(row, 1)+"?","Warning", JOptionPane.YES_NO_OPTION);
							if(dialogResult == JOptionPane.YES_OPTION){
								LoggerClass.myLogger.log(Level.FINE, "user deleted tuple for "+(int)table.getModel().getValueAt(row, 0));
								shipment_db.deleteTuple((int)table.getModel().getValueAt(row, 0), 
														(String)table.getModel().getValueAt(row, 4), (String)table.getModel().getValueAt(row, 3));
								
								build_table();	
							}
						}
					}}
			
	    });
	}
		
	/**
	 * class mendatory for creating JTable and its
	 * content and function meant to ease the creation 
	 * of JTable
	 */
  class MyTableModel extends AbstractTableModel {
    
	private static final long serialVersionUID = 1L;

	private String[] columnNames = {"productcode","name", "quantity", "duedate",
	        "supllier","arrived?" };

    private Object[][] data;
    
    
    /**
	 * @constructor
	 * create abstract table for JTable creation.
	 * @param items which is an instance containing all the rating of selected book.
	 */
    public MyTableModel(ArrayList<Object> items,ArrayList<Object> names)
    {
    	data=new Object[items.size()/5][6];
    	
    	for(int i=0;i<items.size(); i+=5){
    		int code = (int) items.get(i);
    		data[i/5][0] = code;
    		for(int j=0;j<names.size();j+=2)
    		{
    			if(code == (int)names.get(j))
    				data[i/5][1] = names.get(j+1);
    		}
    		data[i/5][2] = items.get(i+1);
    		data[i/5][3] = items.get(i+2);
    		data[i/5][4] = items.get(i+3);
    		if((int)items.get(i+4)!=0)
    			data[i/5][5]="arrived";
	    	else
	    		data[i/5][5]="click";
	    	
    	
    		
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
