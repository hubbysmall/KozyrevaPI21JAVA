import java.util.HashMap;
import java.util.Map;


public class Docks<T extends ITransport>    {
	
     private T defaultVal;
     private int maxCount;
     private Map <Integer, T> docks; 


     public Docks(int size, T defV)
     {
         defaultVal = defV;
         docks = new HashMap<Integer,T>();
         maxCount = size;
        
     }

     public static int pseudoPlus (Docks<ITransport> plc, ITransport ship)
     {
    	 if (plc.docks.size() == plc.maxCount)
             return -1;
         for (int i=0; i<plc.docks.size(); i++)
         {
             if (plc.checkIfFree(i))
             {
                 plc.docks.put(i, ship);
                 return i;
             }

         }
         plc.docks.put(plc.docks.size(), ship);
         return plc.docks.size() - 1;         
     }

     public static ITransport pseudoMinus(Docks<ITransport> plc, int index)
     {       
         if (plc.docks.containsKey(index))
         {
        	 ITransport ship = plc.docks.get(index);
             plc.docks.remove(index);
             return ship;
         }
         return plc.defaultVal;     
     }

     private boolean checkIfFree(int index)
     {
        return !docks.containsKey(index);
     }
     
     public ITransport getShip(int indx)
    {      
            if (docks.containsKey(indx))
                return docks.get(indx);
            return defaultVal;       
    }

  
}
