import java.io.*;
import java.lang.System;

// Copies binary files
//
// Usage:
//         java Copy <source file> <target file>
//

class Copy {

  // There is no constructor for this class.

  // Exit-code conventions:

  private static final int noError = 0;
  private static final int targetFileExists = 1;
  private static final int couldntOpenSourceFile = 2;
  private static final int otherIOerror = 3;
  private static final int badUsage = 4;
  // Copy method.

  public static void main(String [] args) {
    if (args.length != 2) {
        System.err.println("Usage: java Copy <source file> <target file>");
        System.exit(badUsage);
    }

    // args[0] = source file name
    // args[1] = target file name

    // If target file exists, refuse to overwrite it:

    File targetFile = new File(args[1]);

    if (targetFile.exists()) {
        System.err.println("Target file " + targetFile.getName() + " exists");
        System.exit(targetFileExists);
    }

    FileInputStream source = null;

    try {
      source = new FileInputStream(args[0]);
    }
    catch (IOException e) {
        System.err.println("Couldn't open source file " + args[0]);
        System.err.println(e.getMessage());
        System.exit(couldntOpenSourceFile);
    }

    // Source file successfully opened for reading!

    // We now should be able to open the target file for writing.
    // But who knows; e.g. maybe we don't have write permissions,
    // or there isn't enough disk space, or \dots. So we have to
    // watch potential exceptions. 

    FileOutputStream target = null;
    
    try {
      target = new FileOutputStream(targetFile);
    }
    catch (IOException e) {
      System.err.println("Unable to create target file");
      System.err.println(e.getMessage());

      try {
        source.close();
      }
      catch (IOException d) {
          System.err.println("Unable to close source file");
      }

      System.exit(otherIOerror);
    }

    // Source and target files successfully open at this point.

    int exitCode = noError; // At least not yet.

    try {
        for (int c = source.read(); c != -1; c = source.read())
          target.write(c);
    }
    catch (IOException e) {
        System.err.println("Error during copy:" + e.getMessage());
        exitCode = otherIOerror; // flags error
    }
    finally {
      try {
        source.close();
        target.close();
      }
      catch (IOException e) {
        System.err.println("Error closing files:" + e.getMessage());
        exitCode = otherIOerror;
      }
    }

    System.exit(exitCode); 
  }
}
