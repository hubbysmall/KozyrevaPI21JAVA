import java.awt.Color;
import java.awt.Graphics;


public abstract class Water_transport implements ITransport {
	protected float startPosX;
    protected float startPosY;
    protected int countCargos;
    public  int MaxCountCargos;
    public  int MaxSpeed;
    public Color BodyColor;
    public  double Weight;

    public int getMaxCountCargos(){
    	return MaxCountCargos; 
    }
    protected void setMaxCountCargos(int MCC){
    	this.MaxCountCargos = MCC;
    }
    
    public int getMaxSpeed(){
    	return MaxSpeed; 
    }
    protected void setMaxSpeed(int MS){
    	this.MaxSpeed = MS;
    }
    
    public Color getBodyColor(){
    	return BodyColor; 
    }
    protected void setBodyColor(Color C){
    	this.BodyColor = C;
    }
    
    public double getWeight(){
    	return Weight; 
    }
    protected void setWeight(double W){
    	this.Weight = W;
    }

	
	public abstract void drawBoat(Graphics g);

	public abstract void moveBoat(Graphics g);
	
	
	public  void setPosition(int x, int y) {
		startPosX = x;
        startPosY = y;

	}
	@Override
	public void loadCargo(int count) {
		if (countCargos + count < MaxCountCargos) {
            countCargos += count;}
	}
	@Override
	public int relieveCargo() {
		int count = countCargos;
        countCargos = 0;
        return count;
	}
	@Override
	public  void setMainColor(Color color)
    {
        BodyColor = color;
    }

}
