import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.SystemColor;

import javax.swing.JButton;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JEditorPane;
import javax.swing.DropMode;

import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class additionalForm implements DragGestureListener{
	
	
	public Boat returnBoat(){
		if(boat!=null)
			return boat;
		return null;
	}
	
	
	public void dragGestureRecognized(DragGestureEvent event) {
        Cursor cursor = null;
        JPanel panel = (JPanel) event.getComponent();

        Color color = panel.getBackground();

        if (event.getDragAction() == DnDConstants.ACTION_COPY) {
            cursor = DragSource.DefaultCopyDrop;
        }

        event.startDrag(cursor, new TransferableColor(color));
    }
	
	
	
	
	private class ListHandler extends TransferHandler {
        public boolean canImport(TransferSupport support) {
             if (!support.isDrop()) {
                 return false;
             }

             return support.isDataFlavorSupported(DataFlavor.stringFlavor);
         }

         public boolean importData(TransferSupport support) {
             if (!canImport(support)) {
               return false;
             }

             Transferable transferable = support.getTransferable();
             try {             
               switch((String) transferable.getTransferData(DataFlavor.stringFlavor)){
               case "Boat":
            	   AdditionalPanel.validate();
       			   AdditionalPanel.repaint();
            	   boat = new Boat(8, 5, 10, Color.gray);
            	   DrawBoat();
            	   break;
               case "Ship":
            	   AdditionalPanel.validate();
       			   AdditionalPanel.repaint();
            	   boat = new Sailing_ship(10, 20, 30, Color.CYAN, true, Color.PINK);
            	   ShipChose = true;
            	   DrawBoat();
               }               
             } catch (UnsupportedFlavorException | IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}

             
             return true;
         }
    }
	
	
	class MyDropTargetListener extends DropTargetAdapter {

        private DropTarget dropTarget;
        private JLabel accesor;

     public MyDropTargetListener(JLabel label) {
        this.accesor = label;

        dropTarget = new DropTarget(accesor, DnDConstants.ACTION_COPY, 
            this, true, null);
      }


      public void drop(DropTargetDropEvent event) {
        try {

          Transferable tr = event.getTransferable();
          Color color = (Color) tr.getTransferData(TransferableColor.colorFlavor);

            if (event.isDataFlavorSupported(TransferableColor.colorFlavor)) {                       
            	String labelColor = this.accesor.getText();
            	if(labelColor.equals("Main Color")){
            		//присвоить основной цвет
            		if (boat != null)
                    {
            			AdditionalPanel.validate();
            			AdditionalPanel.repaint();
                        boat.setMainColor(color);
                        DrawBoat();                     
                    }
            	}
            	else if(labelColor.equals("Add Color")){
            		//присвоить доп цвет
            		if (boat != null)
                    {       
                        if(ShipChose == true)
                        {
                        	AdditionalPanel.validate();
                			AdditionalPanel.repaint();
                            boat.SetDopColor(color);
                            DrawBoat();
                        }
                       
                    }
            	}

              event.acceptDrop(DnDConstants.ACTION_COPY);
              this.accesor.setBackground(color);
              event.dropComplete(true);
              return;
            }
          event.rejectDrop();
        } catch (Exception e) {
          e.printStackTrace();
          event.rejectDrop();
        }
      }
    }
		
	JFrame frame;
	JLabel lbl;
	JLabel dropLabel;
	JLabel dragLabel;
	JPanel AdditionalPanel;
	
	 Boat boat = null;
	 boolean ShipChose = false;
	 private JTextField txtBoat;
	 private JTextField textShip;
	 


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					additionalForm window = new additionalForm();
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
	public additionalForm() {
		initialize();
		drawTakeBox();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	
	public Graphics drawTakeBox(){
		
		BufferedImage bic = new BufferedImage(AdditionalPanel.getWidth(), AdditionalPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bic.createGraphics();
        ImageIcon newIcon = new ImageIcon(bic); 
        JLabel label1 = new JLabel(newIcon);
        AdditionalPanel.add(label1).setBounds(0,0,AdditionalPanel.getWidth(), AdditionalPanel.getHeight());      
        return g;      
	}
	void DrawBoat()
    {
		
        if (boat != null)
        {                   
            boat.setPosition(10, 50);
            boat.drawBoat(drawTakeBox());        
        }           
    }
	
	
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.menu);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		AdditionalPanel = new JPanel();
		AdditionalPanel.setTransferHandler(new ListHandler());		
		AdditionalPanel.setBackground(SystemColor.info);
		AdditionalPanel.setBounds(155, 41, 123, 157);
		frame.getContentPane().add(AdditionalPanel);
		
		JLabel lblBoat = new JLabel("Boat");
		TransferHandler transfer = new TransferHandler("text");
		lblBoat.setTransferHandler(transfer);


		lblBoat.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {

                
			}
		});
		lblBoat.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JLabel lblBoat = (JLabel)e.getSource();
				TransferHandler handle = lblBoat.getTransferHandler();
				handle.exportAsDrag(lblBoat, e, TransferHandler.COPY);				
			}
		});
		
		lblBoat.setBackground(Color.LIGHT_GRAY);
		lblBoat.setBounds(10, 23, 30, 14);
		frame.getContentPane().add(lblBoat);
		
		JLabel lblShip = new JLabel("Ship");
		lblShip.setBounds(51, 23, 46, 14);
		frame.getContentPane().add(lblShip);
		
		JButton Addbtn = new JButton("Add");
		Addbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				if(returnBoat()!=null){									
					try
					{
						int place = Form.port.PutInDock(returnBoat());
						Form.panel.validate();
	                	Form.panel.repaint();
	                	Form.DrawMarking();                   
	                    JOptionPane.showMessageDialog(null, "Ваше место:" + place);
					}
					catch(ParkingOverflowException ex)
					{
						JOptionPane.showMessageDialog(null, "Overflow");
					}
					catch (Exception ex)
					{
						JOptionPane.showMessageDialog(null, "Unknown error");
					}			
	       		} 											
			}
		});
		Addbtn.setBounds(10, 68, 89, 23);
		frame.getContentPane().add(Addbtn);
		
		JButton Cancelbtn = new JButton("Cancel");
		Cancelbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		Cancelbtn.setBounds(10, 101, 89, 23);
		frame.getContentPane().add(Cancelbtn);
		
		JLabel MainColorlbl = new JLabel("Main Color");
		MainColorlbl.setBackground(SystemColor.activeCaptionBorder);
		MainColorlbl.setBounds(155, 220, 67, 23);
		frame.getContentPane().add(MainColorlbl);
		new MyDropTargetListener(MainColorlbl);
		
		JLabel AddColorlabel = new JLabel("Add Color");
		AddColorlabel.setBackground(Color.GRAY);
		AddColorlabel.setBounds(227, 220, 67, 23);
		frame.getContentPane().add(AddColorlabel);
		new MyDropTargetListener(AddColorlabel);
		
		JLabel lblNewLabel = new JLabel("Colors:");
		lblNewLabel.setBounds(338, 23, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.PINK);
		panel_1.setBounds(307, 53, 46, 23);
		frame.getContentPane().add(panel_1);
		DragSource ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(panel_1,
            DnDConstants.ACTION_COPY, this);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.CYAN);
		panel_2.setBounds(363, 53, 46, 23);
		frame.getContentPane().add(panel_2);
		ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(panel_2,
            DnDConstants.ACTION_COPY, this);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.RED);
		panel_3.setBounds(307, 87, 46, 23);
		frame.getContentPane().add(panel_3);
		ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(panel_3,
            DnDConstants.ACTION_COPY, this);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.MAGENTA);
		panel_4.setBounds(363, 87, 46, 23);
		frame.getContentPane().add(panel_4);
		ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(panel_4,
            DnDConstants.ACTION_COPY, this);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.DARK_GRAY);
		panel_5.setBounds(307, 121, 46, 23);
		frame.getContentPane().add(panel_5);
		ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(panel_5,
            DnDConstants.ACTION_COPY, this);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.BLUE);
		panel_6.setBounds(363, 121, 46, 23);
		frame.getContentPane().add(panel_6);
		ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(panel_6,
            DnDConstants.ACTION_COPY, this);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(Color.ORANGE);
		panel_7.setBounds(307, 155, 46, 23);
		frame.getContentPane().add(panel_7);
		ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(panel_7,
            DnDConstants.ACTION_COPY, this);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBackground(Color.GREEN);
		panel_8.setBounds(363, 155, 46, 23);
		frame.getContentPane().add(panel_8);
		ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(panel_8,
            DnDConstants.ACTION_COPY, this);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.setContentType("color");
		editorPane.setBackground(Color.MAGENTA);
		editorPane.setBounds(10, 155, 106, 88);
		frame.getContentPane().add(editorPane);
		editorPane.setDragEnabled(true);
		
		txtBoat = new JTextField();
		txtBoat.setEditable(false);
		txtBoat.setText("Boat");
		txtBoat.setBounds(10, 41, 30, 20);
		frame.getContentPane().add(txtBoat);
		txtBoat.setColumns(10);
		txtBoat.setDragEnabled(true);
		
		textShip = new JTextField();
		textShip.setEditable(false);
		textShip.setText("Ship");
		textShip.setDragEnabled(true);
		textShip.setColumns(10);
		textShip.setBounds(61, 41, 30, 20);
		frame.getContentPane().add(textShip);
	}	
}
class TransferableColor implements Transferable {
	 
    protected static DataFlavor colorFlavor =
        new DataFlavor(Color.class, "A Color Object");

    protected static DataFlavor[] supportedFlavors = {
        colorFlavor,
        DataFlavor.stringFlavor,
    };

    Color color;

    public TransferableColor(Color color) { this.color = color; }

    public DataFlavor[] getTransferDataFlavors() { return supportedFlavors; }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
    if (flavor.equals(colorFlavor) || 
        flavor.equals(DataFlavor.stringFlavor)) return true;
    return false;
  }


   public Object getTransferData(DataFlavor flavor) 
        throws UnsupportedFlavorException
   {
     if (flavor.equals(colorFlavor))
         return color;
     else if (flavor.equals(DataFlavor.stringFlavor)) 
         return color.toString();
     else 
         throw new UnsupportedFlavorException(flavor);
   }
}

