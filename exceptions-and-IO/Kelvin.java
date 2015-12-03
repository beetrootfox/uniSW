public class Kelvin {
    private double k;

    public Kelvin (double k)  throws  TemperatureException {
	if (k < 0.0) // 0.0 Kelvin is the minimum possible temperature
	    throw new TemperatureException("You tried soomething colder than possible");

	this.k = k;
    }

    public double get() {
	return k;
    }

    public static void main (String [] args) throws TemperatureException {

	Kelvin k = new Kelvin(-3);

    }
}
