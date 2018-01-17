import java.awt.Color;
import java.awt.Graphics;


public class Sailing_ship extends Boat {

	private boolean sail;
    private Color addColor;

    public Sailing_ship(int maxSpeed, int maxCount_cargo, double weight, Color color, boolean Sail, Color addiColor) 
    {
    	super(maxSpeed, maxCount_cargo, weight, color);
        this.sail = Sail;
        this.addColor = addiColor;

    }
    
    @Override
    protected  void drawSailingShip(Graphics g)
    {
        super.drawSailingShip(g);
        if (sail) {
    
            g.setColor(addColor);
            g.fillArc((int) startPosX+25,(int)startPosY-40, 50, 70, -90, 180);
            g.drawArc((int)startPosX+25,(int) startPosY-40, 50, 70, -90, 180);
        }
    }
}
