import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;



public class Port {
	
	ArrayList<Docks<ITransport>> port;

    int countDocks = 20;
    int DockWidth = 210;
    int DockHeight = 100;
    int currentDock;
    
    public int getCurrentDock() { 
    	return currentDock;
    	}

    public Port(int countStages)
    {     
        port = new ArrayList<Docks<ITransport>>(countStages);
        for(int i=0; i<countStages; i++)
        {
            Docks<ITransport> oneDockStage = new Docks<ITransport>(20,null);
            port.add(oneDockStage);
        }
    }

    @SuppressWarnings("static-access")
	public int PutInDock(ITransport ship)
    {
    	return port.get(currentDock).pseudoPlus(port.get(currentDock), ship);

    }

    @SuppressWarnings("static-access")
	public ITransport PutOutDock(int dockNumber)
    {
    	return port.get(currentDock).pseudoMinus(port.get(currentDock), dockNumber);
    }
    
    public void LevelUp() {
        if (currentDock + 1 < port.size())
            currentDock++;
    }

    public void LevelDown() {
        if (currentDock > 0)
            currentDock--;
    }

    public void DrawDocks(Graphics g)
    {
      //  Pen pen = new Pen(Color.BLACK, 4);
        g.setColor(Color.BLACK);
        g.drawString("L" + (currentDock),(countDocks / 5) * DockWidth - 70, 420);
        g.drawRect(0, 0, (countDocks / 5) * DockWidth, 400);
        for (int i = 0; i < countDocks / 5; i++)
        {
            g.drawLine( DockWidth * i, 0, DockWidth * i, 400);
            for (int j = 0; j < 5; ++j)
            {
                g.drawLine( (DockWidth * i), (DockHeight * j), (DockWidth * i) + 100, (DockHeight * j));
                if (j < 4)
                {
                    g.drawString((i * 5 + j )+"", i * DockWidth + 30, j * DockHeight + 20);
                }
            }
        }       
    }

    public void DrawItAll(Graphics g, int width, int height)
    {
        DrawDocks(g);
        for (int i = 0; i < countDocks; i++)
        {
        	ITransport ship = port.get(currentDock).getShip(i);
            if (ship != null)
            {

                ship.setPosition(5 + i / 5 * DockWidth + 5, i % 5 * DockHeight + 45);
                ship.drawBoat(g);
            }

        }
    }

}
