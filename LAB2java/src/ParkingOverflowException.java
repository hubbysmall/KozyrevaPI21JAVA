
public class ParkingOverflowException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParkingOverflowException(){
		super("No free places");
	}

}
