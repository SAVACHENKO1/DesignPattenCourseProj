package GUI;

import java.awt.Color;
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
import common.LoggerClass;
import common.factoryOfGUI;
import interfaces.products_inter;

public class Products_window extends JFrame{
	
	private static final long serialVersionUID = 7062331812790637486L;
	
	private JScrollPane Scrolltable = null;
	
	products_inter product_db = new Products_DAO();
	
	private JTextField quantity;
	private JTextField price;
	private JTextField description;
	private JTextField company_field;
	private JTextField name_field;
	
	//create basic window with buttons and textfields
	public Products_window()
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
		
		JLabel title = new JLabel("Products window");
		title.setFont(new Font("Times New Roman",Font.BOLD,30));
		title.setForeground(Color.RED);
		title.setBounds(80,  50, 480, 50);
		p.add(title);
		
		build_table();
		this.Scrolltable.setBounds(10, 100, 670, 200);
		p.add(Scrolltable);
		
		JLabel add_title = new JLabel("add product");
		add_title.setFont(new Font("Times New Roman",Font.BOLD,30));
		add_title.setBounds(40,  420, 440, 50);
		p.add(add_title);
		
		JLabel quantity_label = new JLabel("quantity:");
		quantity_label.setBounds(40, 480, 220, 30);
		quantity = new  JTextField();
		quantity.setBounds(40, 500, 220, 30);
		p.add(quantity);
		p.add(quantity_label);
		JLabel name_label = new JLabel("name:");
		name_label.setBounds(40,540, 220, 30);
		name_field = new  JTextField();
		name_field.setBounds(40,560, 220, 30);
		p.add(name_field);
		p.add(name_label);
		JLabel company_label = new JLabel("company:");
		company_label.setBounds(280, 480, 220, 30);
		company_field = new  JTextField();
		company_field.setBounds(280, 500, 220, 30);
		p.add(company_field);
		p.add(company_label);
		JLabel description_label = new JLabel("description:");
		description_label.setBounds(280,540, 220, 30);
		description = new  JTextField();
		description.setBounds(280, 560, 220, 30);
		p.add(description);
		p.add(description_label);
		JLabel price_label = new JLabel("price:");
		price_label.setBounds(40,601, 220, 30);
		price = new  JTextField();
		price.setBounds(40, 622, 220, 30);
		p.add(price);
		p.add(price_label);
	
		JButton b2 = new JButton("click here to add");
		b2.setBounds(280, 622, 220, 30);
		b2.setFont(new Font("TimesRoman", Font.PLAIN, 22));
		b2.setBackground(Color.yellow);
		p.add(b2);
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				LoggerClass loggerWrapper = LoggerClass.getInstance();
				if(price.getText().isEmpty()|| description.getText().isEmpty() 	
						|| company_field.getText().isEmpty() ||name_field.getText().isEmpty()
						||quantity.getText().isEmpty()){
					
					JOptionPane.showConfirmDialog (null, "all fields must be field?","Warning", JOptionPane.WARNING_MESSAGE);
					LoggerClass.myLogger.log(Level.WARNING, "user left one of the fields empty");
				}
				else{
					product_db.addTuple(company_field.getText(),name_field.getText(),
							description.getText(),Float.valueOf(price.getText()),Integer.valueOf(quantity.getText()));
							
					LoggerClass.myLogger.log(Level.WARNING, "user added "+ name_field.getText());
					build_table();
				}		
		}});}
	
		
	
	//create the table and put it into scrollpanel component
	private void build_table() {
		ArrayList<Object> items = product_db.all();
		
		JTable table = new JTable(new MyTableModel(items));
	
		table.setAutoCreateRowSorter(true);
	    table.getColumnModel().getColumn(3).setPreferredWidth(600);
	    table.getColumnModel().getColumn(1).setPreferredWidth(120);
	    table.setRowHeight(20);
	   
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
						int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to delete "+(String)table.getModel().getValueAt(row, 2)+"?","Warning", JOptionPane.YES_NO_OPTION);
						if(dialogResult == JOptionPane.YES_OPTION){
							product_db.deleteTuple((int)table.getModel().getValueAt(row, 0));
							build_table();
						}
					}
			}});
	}
		
	/**
	 * class mendatory for creating JTable and its
	 * content and function meant to ease the creation 
	 * of JTable
	 */
  class MyTableModel extends AbstractTableModel {
    
	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "product code","product name", "company","description", "price",
        "quantity"};

    private Object[][] data;
    
    
    /**
	 * @constructor
	 * create abstract table for JTable creation.
	 * @param items which is an instance containing all the rating of selected book.
	 */
    public MyTableModel(ArrayList<Object> items)
    {
    	data=new Object[items.size()/6][6];
    	
    	for(int i=0;i<items.size(); i++){
    		data[i/6][i%6] = items.get(i);
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
