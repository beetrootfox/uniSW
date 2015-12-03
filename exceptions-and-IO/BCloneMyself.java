import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.System;
import java.io.BufferedReader;
import java.io.PrintWriter;

class BCloneMyself {

    // We don't need a constructor.

    public static void main(String [] args) {
    int exitCode = 0; // to be changed if there is an IO error

    FileReader myself = null;
    FileWriter clone = null;

    try {
      myself = new FileReader("BCloneMyself.java");
      clone = new FileWriter("BCloneMyself.java.clone"); 

      BufferedReader bmyself = new BufferedReader(myself);
      PrintWriter bclone = new PrintWriter(clone);

      for (String line = bmyself.readLine(); 
           line != null; 
           line = bmyself.readLine())
          {
          if (line != null)
              bclone.println(line);
          }
    }
    catch (IOException e) {
      System.err.println("Something wrong" + e.getMessage());
      exitCode = 1; 
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
