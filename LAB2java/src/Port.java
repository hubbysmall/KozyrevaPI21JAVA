import java.awt.Color;
import java.awt.Graphics;


public class Port {
	
	
	Docks<ITransport> port;

    int countDocks = 20;
    int DockWidth = 210;
    int DockHeight = 100;

    public Port()
    {
        port = new Docks<ITransport>(countDocks, null);
    }

    public int PutInDock(ITransport ship)
    {

    	return port.pseudoPlus(port, ship);
    }

    public ITransport PutOutDock(int dockNumber)
    {
    	return port.pseudoMinus(port, dockNumber);
    }

    public void DrawDocks(Graphics g)
    {
        g.setColor(Color.BLACK);
        for (int i = 0; i < countDocks / 5; i++)
        {
            g.drawLine( DockWidth * i, 0, DockWidth * i, 400);
            for (int j = 0; j < 5; ++j)
            {
                g.drawLine( (DockWidth * i), (DockHeight * j), (DockWidth * i) + 100, (DockHeight * j));
            }
        }
    }

    public void DrawItAll(Graphics g, int width, int height)
    {
        DrawDocks(g);
        for (int i = 0; i < countDocks; i++)
        {
            ITransport ship = port.getShip(i);
            if (ship != null)
            {

                ship.setPosition(5 + i / 5 * DockWidth + 5, i % 5 * DockHeight + 45);
                ship.drawBoat(g);
            }

        }
    }

}
