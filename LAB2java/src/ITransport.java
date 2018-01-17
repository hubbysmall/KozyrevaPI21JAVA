import java.awt.Color;
import java.awt.Graphics;


public interface ITransport {
	
	void drawBoat(Graphics g);
    void moveBoat(Graphics g);
    void setPosition(int x, int y);
    void loadCargo(int count);
    int relieveCargo();
    void setMainColor(Color color);

}
