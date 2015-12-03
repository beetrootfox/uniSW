import java.util.*;

class Beatles {

    private boolean isRaining;

    Beatles (boolean b) {
	isRaining = b;
    }

    void a() {
	System.out.println("Method a() says hello");

	try { b (); }
	catch (X x) { 
	    System.out.println("Method a() caught Exception X: " +
			       x.getMessage());
	}
	catch (Y y) { 
	    System.out.println("Method a() caught Exception Y: " + 
			       y.getMessage());
	} 

	System.out.println("Method a() says good bye");
    }

    void b() throws X, Y {
	System.out.println("Method b() says hello");

	c (); 

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
	Beatles e = new Beatles(true);

	e.a();
    }

}
