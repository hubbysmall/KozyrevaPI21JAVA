
public class Docks<T extends ITransport>    {
	
	 private ITransport[] places;
     private T defaultVal;

     public Docks(int size, T defV)
     {
         defaultVal = defV;
         places = new ITransport[size];
         for (int i = 0; i < places.length; i++)
         {
             places[i] = defaultVal;
         }
     }

     public static int pseudoPlus (Docks<ITransport> plc, ITransport ship)
     {
         for (int i = 0; i < plc.places.length; i++)
         {
             if (plc.checkIfFree(i))
             {
                 plc.places[i] = ship;
                 return i;
             }

         }
         return -1;
     }

     public static ITransport pseudoMinus(Docks<ITransport> plc, int index)
     {
         if (!plc.checkIfFree(index))
         {
        	 ITransport ship = plc.places[index];
             plc.places[index] = plc.defaultVal;
             return ship;
         }
         return plc.defaultVal;
     }

     private boolean checkIfFree(int index)
     {
         if (index < 0 || index > places.length)
             return false;
         if (places[index] == null)
             return true;
         if (places[index].equals(defaultVal))
             return true;
         return false;
     }

     public ITransport getShip(int index)
     {
         if (index >= 0 && index < places.length)
             return places[index];
         return defaultVal;
     }
}
