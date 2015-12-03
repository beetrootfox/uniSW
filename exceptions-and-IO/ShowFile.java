import java.io.FileReader;
import java.io.IOException;
import java.lang.System;

class ShowFile {

    public static void main(String [] args) {

     if (args.length != 1) {
         System.out.println("Usage: java ShowFile <filename>");
         System.exit(1);
     }
    
     FileReader inputFile = null;

     try {
         inputFile = new FileReader(args[0]);
     }
     catch (IOException e) {
         System.out.println("The file " + args[0] + " could not be opened");
         System.exit(1);
     }

     // In this example, the finally statement won't be executed if the
     // exception is caught in the out try. Why?

     try {
         for (int c = inputFile.read(); c != -1; c = inputFile.read())
             System.out.write(c);
     }
     catch (IOException e) {
         System.out.println("IO error" + e.getMessage());
         System.exit(0);
     }
     finally {
         try {
             inputFile.close();
         }
         catch (IOException e) {
             System.out.println("IO error" + e.getMessage());
             System.exit(0);
         }
     }
    }
}
