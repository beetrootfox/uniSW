import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.System;

class CloneMyself {

  // We don't need a constructor.

    public static void main(String [] args) {
    int exitCode = 0; // to be changed if there is an IO error

    FileReader myself = null;
    FileWriter clone = null;

    try {
      myself = new FileReader("CloneMyself.java");
      clone = new FileWriter("CloneMyself.java.clone"); 

      int c;

      do {
          c = myself.read();
          if (c != -1)
              clone.write(c);

      } while (c != -1);
    }
    catch (IOException e) {
      System.err.println("Something wrong" + e.getMessage());
      exitCode = 1; // flags error
    }
    finally {
        try {
            myself.close();
            clone.close();
        }
        catch (IOException e) {
            System.err.println("Something wrong" + e.getMessage());
            exitCode = 1; 
        }
    }

    System.exit(exitCode);
  }
}
