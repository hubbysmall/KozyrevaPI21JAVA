import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Graphics;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Spring;

import java.awt.Label;

import javax.swing.JCheckBox;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JFormattedTextField;
import javax.swing.JList;

public class Form {
	
	additionalForm adF;
	private JFrame frame;
	static JPanel panel;
	JPanel TakeBoatPanel;
	JFormattedTextField formattedTextField;
	static BufferedImage bic;
	static ImageIcon newIcon;
	static JList list;
	
	Color color;
    Color addColor;
    int maxSpeed;
    int maxCountCargo;
    int weight;
    
    
    private ITransport interFace;
    
    static Port port;
  
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Form window = new Form();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Form() {
		initialize();
		color = Color.WHITE;
        addColor = Color.YELLOW;
        maxSpeed = 30;
        maxCountCargo = 3000;
        weight = 5000;
        
       port = new Port(4);
       DefaultListModel listModel = new DefaultListModel ();
       list.setModel(listModel);
       for(int i=0; i<4; i++)
       {
    	   listModel.addElement("subport" + i);   	  
       }

       list.setSelectedIndex(port.getCurrentDock());
       
       JButton btnOrderBoat = new JButton("OrderBoat");
       btnOrderBoat.addActionListener(new ActionListener() {
       	public void actionPerformed(ActionEvent e) {
       		additionalForm adF = new additionalForm();
       		adF.frame.setVisible(true);
       		
       	}
       });
       btnOrderBoat.setBounds(20, 589, 191, 23);
       frame.getContentPane().add(btnOrderBoat);
       
       DrawMarking();
       drawTakeBox();

	}
	
	public Graphics drawTakeBox(){
	
        bic = new BufferedImage(TakeBoatPanel.getWidth(), TakeBoatPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bic.createGraphics();
        newIcon = new ImageIcon(bic); 
        JLabel label1 = new JLabel(newIcon);
        TakeBoatPanel.add(label1).setBounds(0,0,TakeBoatPanel.getWidth(), TakeBoatPanel.getHeight());      
        return g;      
	}
	
	public static void DrawMarking(){
		
		if (list.getSelectedIndex() > -1)
        {
         bic = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
         Graphics g = bic.createGraphics();
         newIcon = new ImageIcon(bic); 
         JLabel label1 = new JLabel(newIcon);
 		 panel.add(label1).setBounds(0,0,panel.getWidth(), panel.getHeight());     
         port.DrawItAll(g, panel.getWidth(), panel.getHeight());  
        }
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 671);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		TakeBoatPanel = new JPanel();
		TakeBoatPanel.setBounds(263, 489, 175, 132);
		frame.getContentPane().add(TakeBoatPanel);
		
		 panel = new JPanel();
		panel.setBounds(10, 11, 868, 478);
		frame.getContentPane().add(panel);
		
		JButton PutBoatbtn = new JButton("PutBoat");
		PutBoatbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
	            
	            JColorChooser colCh = new JColorChooser();
				color = colCh.showDialog(frame, "select", Color.BLUE);
				if(colCh.getVerifyInputWhenFocusTarget()){
					Boat boat = new Boat(8, 180, 200, color);
	                int place = port.PutInDock(boat);
	                DrawMarking();	           	        
	                JOptionPane.showMessageDialog(null, "Судно в доке с номером:" + place);
	               
				}
	            
	            
			}
		});
		PutBoatbtn.setBounds(20, 521, 89, 23);
		frame.getContentPane().add(PutBoatbtn);
		
		JButton putShipButton = new JButton("PutShip");
		putShipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JColorChooser colCh = new JColorChooser();
				color = colCh.showDialog(frame, "select", Color.BLUE);
	            if (colCh.getVerifyInputWhenFocusTarget())
	            {
	            	addColor = colCh.showDialog(frame, "select", Color.BLUE);
	                if (colCh.getVerifyInputWhenFocusTarget())
	                {
	                    Sailing_ship sail_boat = new Sailing_ship(20, 1000, 1500, color, true, addColor);
	                    int place = port.PutInDock(sail_boat);
	                      DrawMarking();	        
	                    JOptionPane.showMessageDialog(null, "Парусник в доке с номером:" + place);
	                }

	            }
			}
		});
		putShipButton.setBounds(20, 555, 89, 23);
		frame.getContentPane().add(putShipButton);
		
		
		
		formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(174, 556, 37, 20);
		frame.getContentPane().add(formattedTextField);
		
		JButton TakeShipBtn = new JButton("Take");
		TakeShipBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				if (list.getSelectedIndex() > -1)
	            {
					String stage = ""+ list.getSelectedIndex();
					if (formattedTextField.getText() != "")
		            {						
						TakeBoatPanel.validate();
						TakeBoatPanel.repaint();		                
		                ITransport boat = port.PutOutDock(Integer.parseInt(formattedTextField.getText()));
		                if (boat != null)
	                    {
			                boat.setPosition(5, 45);
			                boat.drawBoat(drawTakeBox());
			                panel.validate();
			                panel.repaint();
			                DrawMarking();
	                    }
		                else{
		                	JOptionPane.showMessageDialog(null, "Тут ничего нет");
		                }
		            }
	            }
			}
		});
		TakeShipBtn.setBounds(141, 521, 89, 23);
		frame.getContentPane().add(TakeShipBtn);
		
		JLabel lblNewLabel_1 = new JLabel("Number");
		lblNewLabel_1.setBounds(127, 559, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		list = new JList();
		list.setBounds(448, 500, 175, 88);
		frame.getContentPane().add(list);
		
		JButton Prevbtn = new JButton("<<");
		Prevbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				port.LevelDown();
	            list.setSelectedIndex(port.getCurrentDock());
	            panel.validate();
                panel.repaint();
	            DrawMarking();
			}
		});
		Prevbtn.setBounds(448, 598, 89, 23);
		frame.getContentPane().add(Prevbtn);
		
		JButton Nextbutton = new JButton(">>");
		Nextbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				port.LevelUp();
	            list.setSelectedIndex(port.getCurrentDock());
	            panel.validate();
                panel.repaint();
	            DrawMarking();
			}
		});
		Nextbutton.setBounds(536, 598, 89, 23);
		frame.getContentPane().add(Nextbutton);
		
		
	}
}
