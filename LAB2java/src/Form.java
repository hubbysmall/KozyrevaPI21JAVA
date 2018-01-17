import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Label;

import javax.swing.JCheckBox;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;



public class Form {

	private JFrame frame;
	private JTextField MaxSpeedtextField;
	private JTextField MaxCargotextField;
	private JTextField WeighttextField;
	JPanel panel;
	static BufferedImage bic;
	static ImageIcon newIcon;
	
	Color color;
    Color addColor;
    int maxSpeed;
    int maxCountCargo;
    int weight;
    
    private ITransport interFace;


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

	}
	private boolean Check_input()
    {
		String test = "";
		test = MaxCargotextField.getText();
		try{
			//код, который мы "отслеживаем"
			int foo = Integer.parseInt(test);
			maxCountCargo = foo;
			}
			catch( Exception e ){
			//если происходить ошибка, которая соответствует классу  Exception
				return false;
			}
		test = MaxSpeedtextField.getText();
		try{
			//код, который мы "отслеживаем"
			int foo = Integer.parseInt(test);
			maxSpeed = foo;
			}
			catch( Exception e ){
			//если происходить ошибка, которая соответствует классу  Exception
				return false;
			}
		test = WeighttextField.getText();
		try{
			//код, который мы "отслеживаем"
			int foo = Integer.parseInt(test);
			weight = foo;
			}
			catch( Exception e ){
			//если происходить ошибка, которая соответствует классу  Exception
				return false;
			}
        return true;
    }



	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 602, 493);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel MaxSpeedlbl = new JLabel("Max speed");
		MaxSpeedlbl.setBounds(10, 399, 67, 14);
		frame.getContentPane().add(MaxSpeedlbl);
		
		MaxSpeedtextField = new JTextField();
		MaxSpeedtextField.setBounds(68, 396, 37, 20);
		frame.getContentPane().add(MaxSpeedtextField);
		MaxSpeedtextField.setColumns(10);
		
		Label label = new Label("Max cargo");
		label.setBounds(10, 419, 58, 22);
		frame.getContentPane().add(label);
		
		MaxCargotextField = new JTextField();
		MaxCargotextField.setBounds(68, 421, 37, 20);
		frame.getContentPane().add(MaxCargotextField);
		MaxCargotextField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Weight");
		lblNewLabel.setBounds(110, 399, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		WeighttextField = new JTextField();
		WeighttextField.setBounds(148, 396, 37, 20);
		frame.getContentPane().add(WeighttextField);
		WeighttextField.setColumns(10);
		
		JCheckBox Sailchckbx = new JCheckBox("Sail on");
		Sailchckbx.setBounds(109, 419, 97, 23);
		frame.getContentPane().add(Sailchckbx);
		
		JButton Boatbtn = new JButton("New boat");
		Boatbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Check_input()) {
	                interFace = new Boat(maxSpeed, maxCountCargo, weight,color);
	       
	                bic = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
	                Graphics g = bic.createGraphics();
	                newIcon = new ImageIcon(bic); 
	                JLabel label1 = new JLabel(newIcon);
	        		panel.add(label1).setBounds(0,0,panel.getWidth(), panel.getHeight()); 
 
	                interFace.drawBoat(g);
	               
	    
	            }
				
			}
		});
		Boatbtn.setBounds(205, 395, 89, 23);
		frame.getContentPane().add(Boatbtn);
		
		JButton shipbtn = new JButton("New sailship");
		shipbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Check_input())
	            {
	                interFace = new Sailing_ship(maxSpeed, maxCountCargo, weight, color,Sailchckbx.isSelected(), addColor);
	                bic = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
	                Graphics g = bic.createGraphics();
	                newIcon = new ImageIcon(bic); 
	                JLabel label1 = new JLabel(newIcon);
	        		panel.add(label1).setBounds(0,0,panel.getWidth(), panel.getHeight()); 
	                interFace.drawBoat(g);
	               
	            }
			}
		});
		shipbtn.setBounds(295, 418, 105, 23);
		frame.getContentPane().add(shipbtn);
		
		 panel = new JPanel();
		panel.setBounds(10, 11, 547, 368);
		frame.getContentPane().add(panel);
		
		JButton MainColorbtn = new JButton("Main color");
		MainColorbtn.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				JColorChooser colCh = new JColorChooser();

				color = colCh.showDialog(frame, "select", Color.BLUE);
				
			}
		});
		MainColorbtn.setBounds(426, 390, 89, 23);
		frame.getContentPane().add(MainColorbtn);
		
		JButton addColbtn = new JButton("Add color");
		addColbtn.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				JColorChooser colCh = new JColorChooser();

				addColor = colCh.showDialog(frame, "select", Color.BLUE);
			}
		});
		addColbtn.setBounds(426, 419, 89, 23);
		frame.getContentPane().add(addColbtn);
	}
}
