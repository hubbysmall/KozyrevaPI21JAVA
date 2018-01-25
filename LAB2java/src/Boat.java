import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


public class Boat extends Water_transport  {
	
	@Override
	public int getMaxCountCargos(){
    	return MaxCountCargos; 
    }
	@Override
    protected void setMaxCountCargos(int MCC){
    	if (MCC > 0 && MCC < 7500)
    		MaxCountCargos = MCC;
        else
        	MaxCountCargos = 3000;
    }
	
	@Override
	 public int getMaxSpeed(){
	    	return MaxSpeed; 
	    }
	@Override
    protected void setMaxSpeed(int MS){
		if (MS > 0 && MS < 40)
            MaxSpeed = MS;
        else
            MaxSpeed = 30;
    }
	
	@Override
	public double getWeight(){
    	return Weight; 
    }
	@Override
    protected void setWeight(double W){
		if (W > 1000 & W < 10000)
            Weight = W;
        else
            Weight = 5000;
    }

    public Boat(int maxSpeed, int maxCountCargo,
        double weight, Color color) {
        this.MaxSpeed = maxSpeed;
        this.MaxCountCargos = maxCountCargo;
        this.BodyColor = color;
        this.Weight = weight;
        this.countCargos = 0;
        Random rand = new Random();
        startPosX = rand.nextInt(200) + 10;
        startPosY = rand.nextInt(200) + 10;
    }
    
    @Override
    public  void moveBoat(Graphics g)
    {       
        startPosX += (MaxSpeed * 50 / (float)Weight) /
            (countCargos == 0 ? 1 : countCargos);
        drawBoat(g);
        
    }
    
    @Override
    public  void drawBoat(Graphics g)
    {
        drawSailingShip(g);
    }
    
    protected  void drawSailingShip(Graphics g) {

    	   g.setColor(BodyColor);
           g.fillArc( (int)startPosX, (int)startPosY, 70, 50, 0, -180);
           g.drawArc( (int)startPosX, (int)startPosY, 70, 50, 0, -180);

    }
    public void SetDopColor(Color color)
    {
    }
	@Override
	public String getInfo() {
		return MaxSpeed + ";" + MaxCountCargos + ";" + BodyColor.getRed() + "," + BodyColor.getGreen() + "," + BodyColor.getBlue() + ";" + Weight;
	}
	
	public Boat(String info)
    {
        String[] strs = info.split(";");
        if (strs.length == 4)
        {
            MaxSpeed = Integer.parseInt(strs[0]);
            MaxCountCargos = Integer.parseInt(strs[1]);
            int r = Integer.parseInt(strs[2].split(",")[0]);
			int g = Integer.parseInt(strs[2].split(",")[1]);
			int b = Integer.parseInt(strs[2].split(",")[2]);
            BodyColor = new Color(r, g, b);
            Weight = Double.parseDouble(strs[3]);
        }
       
        this.countCargos = 0;
        Random rand = new Random();
        startPosX = rand.nextInt(200) + 10;
        startPosY = rand.nextInt(200) + 10;
    }
	@Override
	public String getName() {
		return "Boat";
	}
}
