import java.util.*;

public class Raining {

    private boolean isRaining;

    public Raining (boolean b) {
	isRaining = b;
    }

    void a() {
        System.out.println("Method a() says hello");
                
	try { b (); }
	catch (X x) { 
	    System.out.println("Method a() caught Exception X: " +
			       x.getMessage());
	} 
	finally {
	    System.out.println("Method a() says finally");
	}

	System.out.println("Method a() says good bye");
    }

    void b() throws X {
        System.out.println("Method b() says hello");

	try { c (); }
	catch (Y y) { 
	    System.out.println("Method b() caught Exception Y: " + 
			       y.getMessage());
	}
	finally {
	    System.out.println("Method b() says finally");
	}

	System.out.println("Method b() says good bye");
    }

    void c() throws X, Y {
        System.out.println("Method c() says hello");

	if (isRaining)
	    throw new X("It is raining");
        
	if (!isRaining)
	    throw new Y("It is not raining");

        System.out.println("Method c() says good bye");
    }

    public static void main(String args[]) {
	Raining e = new Raining(true);

	e.a();
    }

}