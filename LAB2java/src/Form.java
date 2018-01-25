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
import javax.swing.JFileChooser;
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
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Form {
	
	Logger log;
	
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
		log = Logger.getLogger(Form.class.getName());
		
		try {
			FileHandler fh = new FileHandler("C:/Users/Мария/Desktop/LOG");
			log.addHandler(fh);
			
		} catch (SecurityException e) {
			log.log(Level.SEVERE,
					"Не удалось создать файл лога из-за политики безопасности.", 
					e);
		} catch (IOException e) {
			log.log(Level.SEVERE,
					"Не удалось создать файл лога из-за ошибки ввода-вывода.",
					e);
		}					
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
       btnOrderBoat.setBounds(20, 564, 178, 23);
       frame.getContentPane().add(btnOrderBoat);
       
       JMenuBar menuBar = new JMenuBar();
       frame.setJMenuBar(menuBar);
       
       JMenu mnFile = new JMenu("File");
       menuBar.add(mnFile);
       
       JButton btnLoad = new JButton("LOAD");
       btnLoad.addActionListener(new ActionListener() {
       	public void actionPerformed(ActionEvent e) {
       		JFileChooser fileChooser = new JFileChooser();
       		fileChooser.setDialogTitle("Выберите файл");
       		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt","*.*");
       		fileChooser.setFileFilter(filter);
       		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
       		int result = fileChooser.showOpenDialog(null);
       		if (result == JFileChooser.APPROVE_OPTION ){
       			try {       				      	
					port.LoadData(fileChooser.getSelectedFile());							
				} catch (IOException | ParkingOverflowException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
       			JOptionPane.showMessageDialog(null, fileChooser.getSelectedFile());
       		}               
       	}
       });
       mnFile.add(btnLoad);
       
       JButton Savebtn = new JButton("SAVE");
       Savebtn.addActionListener(new ActionListener() {
       	public void actionPerformed(ActionEvent e) {       		
       		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt","*.*");
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(filter);
            if ( fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
                //call method from Port
            	try {
					port.SaveData(fc.getSelectedFile());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
       	}
       });
       mnFile.add(Savebtn);
       
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
		frame.setBounds(100, 100, 900, 760);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		TakeBoatPanel = new JPanel();
		TakeBoatPanel.setBounds(208, 555, 175, 132);
		frame.getContentPane().add(TakeBoatPanel);
		
		 panel = new JPanel();
		panel.setBounds(10, 11, 868, 533);
		frame.getContentPane().add(panel);
				
		formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(161, 632, 37, 20);
		frame.getContentPane().add(formattedTextField);
		
		JButton TakeShipBtn = new JButton("Take");
		TakeShipBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								
				if (list.getSelectedIndex() > -1)
	            {
					String stage = ""+ list.getSelectedIndex();
					if (formattedTextField.getText() != "")
		            {											
						try
						{
							TakeBoatPanel.validate();
							TakeBoatPanel.repaint();		                
			                ITransport boat = port.PutOutDock(Integer.parseInt(formattedTextField.getText()));
			                boat.setPosition(5, 45);
			                boat.drawBoat(drawTakeBox());
			                panel.validate();
			                panel.repaint();
			                DrawMarking();
						}
						catch(ParkingIndexOutOfRangeException ex)
						{					
							JOptionPane.showMessageDialog(null, "Such a place does not contain a car");
						}
						catch (Exception ex)
						{
							JOptionPane.showMessageDialog(null, "Unknown error");
						}					
		            }
	            }
			}
		});
		TakeShipBtn.setBounds(30, 598, 168, 23);
		frame.getContentPane().add(TakeShipBtn);
		
		JLabel lblNewLabel_1 = new JLabel("Number");
		lblNewLabel_1.setBounds(40, 634, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		list = new JList();
		list.setBounds(393, 567, 189, 88);
		frame.getContentPane().add(list);
		
		JButton Prevbtn = new JButton("<<");
		Prevbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				port.LevelDown();
	            list.setSelectedIndex(port.getCurrentDock());
	            panel.validate();
                panel.repaint();
	            DrawMarking();
	            log.info("Successful going to the previous level");
			}
		});
		Prevbtn.setBounds(393, 664, 89, 23);
		frame.getContentPane().add(Prevbtn);
		
		JButton Nextbutton = new JButton(">>");
		Nextbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				port.LevelUp();
	            list.setSelectedIndex(port.getCurrentDock());	            
	            panel.validate();
                panel.repaint();
                DrawMarking();
                log.info("Successful going to the next level");	            
			}
		});
		Nextbutton.setBounds(493, 664, 89, 23);
		frame.getContentPane().add(Nextbutton);				
	}
}
