import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.omg.CORBA.Environment;

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
	public int PutInDock(ITransport ship) throws ParkingOverflowException
    {
    	return port.get(currentDock).pseudoPlus(port.get(currentDock), ship);

    }
    @SuppressWarnings("static-access")
	public ITransport PutOutDock(int dockNumber) throws ParkingIndexOutOfRangeException
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
        g.setColor(Color.BLACK);
        g.drawString("L" + (currentDock),(countDocks / 5) * DockWidth - 70, 485);
        g.drawRect(0, 0, (countDocks / 5) * DockWidth, 500);
        for (int i = 0; i < countDocks / 5; i++)
        {
            g.drawLine( DockWidth * i, 0, DockWidth * i, 500);
            for (int j = 0; j < 5; ++j)
            {
                g.drawLine( (DockWidth * i), (DockHeight * j), (DockWidth * i) + 100, (DockHeight * j));
                if (j < 5)
                {
                    g.drawString((i * 5 + j )+"", i * DockWidth + 30, j * DockHeight + 20);
                }
            }
        }       
    }
    public void DrawItAll(Graphics g, int width, int height)
    {
        DrawDocks(g);
        if(currentDock<0)
        	currentDock=0;
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
    public Boolean SaveData(File filename) throws IOException
    {
    	if (filename.exists()) {   	    
	    	filename.delete();
	    	filename.createNewFile();  	    
    	}      
       FileOutputStream fs = new FileOutputStream(filename, false);      
       String record = "";
            
            byte[] info = record.getBytes(Charset.forName("UTF-8"));
            record = record + "CountLeveles:" + port.size() + "\r\n";
            for (Docks<ITransport> stage : port)
            {
                record = record + "Level" + "\r\n";
                for(int i=0; i<countDocks; i++)
                {
                    ITransport boat = stage.getShip(i);
                    if (boat != null)
                    {                   
                        if(boat.getName() == "Boat")
                        {
                        	record = record + "Boat:";
                        }
                        if (boat.getName() == "Sailing_ship")
                        {
                        	record = record + "Sailing_ship:";
                        }
                        record = record + boat.getInfo() + "\r\n";
                    }
                }
            }
            info = record.getBytes(Charset.forName("UTF-8"));  // to the end
            fs.write(info, 0, info.length);
        return true;
    }
    
    
    public Boolean LoadData(File filename) throws IOException, ParkingOverflowException
    {       
        if (!filename.exists())
        {
            return false;
        }
       FileInputStream fs = new FileInputStream(filename);           
            String s = "";
            Path path = Paths.get(filename.getAbsolutePath());               
            byte[] b = new byte[fs.available()];
            b = Files.readAllBytes(path);
            ByteArrayInputStream bAr = new ByteArrayInputStream(b);
            String record = new String(b, StandardCharsets.UTF_8);
            while (bAr.read(b, 0, b.length) > 0)
            {
                s += record;
            }  
            fs.close();
            s = s.replace("\r", "");
            String[] strs = s.split("\n");
            if (strs[0].contains("CountLeveles"))
            {//Считываем количество уровней
                int count = Integer.parseInt(strs[0].split(":")[1]);
                if (port != null)
                {
                    port.clear();
                }
                port = new ArrayList<Docks<ITransport>>(count);
            }
            else
            {
                return false;
            }
            int counter = -1;
            for (int i = 0; i < strs.length; ++i)
            {//шагаем по считанным записям
                if (strs[i].equals("Level"))
                {//начинаем новый уровень
                    counter++;
                    port.add(new Docks<ITransport>(countDocks, null));
                }
                else if (strs[i].split(":")[0].equals("Boat"))
                {
                    ITransport boat = new Boat(strs[i].split(":")[1]);
                    int number = port.get(counter).pseudoPlus(port.get(counter), boat);
                    
                    if (number == -1)
                    {
                        return false;
                    }
                }
                else if (strs[i].split(":")[0].equals("Sailing_ship"))
                {
                    ITransport boat = new Sailing_ship(strs[i].split(":")[1]);
                    int number = port.get(counter).pseudoPlus(port.get(counter), boat);
                    if (number == -1)
                    {
                        return false;
                    }
                }
            }
        
        return true;
    }
}
