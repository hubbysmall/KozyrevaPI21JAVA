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
    @Override
    public void SetDopColor(Color color)
    {
        addColor = color;
    }
    public Sailing_ship(String info) 
    {
    	super(info);
        String[] strs = info.split(";");
        if (strs.length == 6)
        {
            MaxSpeed = Integer.parseInt(strs[0]);
            MaxCountCargos = Integer.parseInt(strs[1]);
            Weight = Double.parseDouble(strs[2]);
            int r = Integer.parseInt(strs[3].split(",")[0]);
			int g = Integer.parseInt(strs[3].split(",")[1]);
			int b = Integer.parseInt(strs[3].split(",")[2]);
            BodyColor = new Color(r, g, b);
            sail = Boolean.parseBoolean(strs[4]);
            r = Integer.parseInt(strs[5].split(",")[0]);
			g = Integer.parseInt(strs[5].split(",")[1]);
			b = Integer.parseInt(strs[5].split(",")[2]);
            addColor = new Color(r, g, b);           
        }
    }
    @Override
    public  String getInfo()
    {
        return MaxSpeed + ";" + MaxCountCargos + ";" + Weight + ";" +BodyColor.getRed() + "," + BodyColor.getGreen() + "," + BodyColor.getBlue()+ ";" + sail + ";"+
        		addColor.getRed() + "," + addColor.getGreen() + "," + addColor.getBlue();
    }
    @Override
	public String getName() {
		return "Sailing_ship";
	}
    
}
