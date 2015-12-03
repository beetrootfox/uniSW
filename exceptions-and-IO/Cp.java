import java.io.*;
import java.lang.System;

// Copies binary files
//
// Usage:
//         java Cp <source file> <target file>
//

class Cp {

    // Convention for System.exit()

    public static final int normalTermination = 0;
    public static final int abnormalTermination = 1;

    // There is no constructor for this class.

  public static void main(String [] args) {
    if (args.length != 2) {
        System.err.println("Usage: java Cp <source file> <target file>");
        System.exit(abnormalTermination);
    }

    // args[0] = source file name
    // args[1] = target file name

    // If target file exists, refuse to overwrite it:

    File targetFile = new File(args[1]);

    if (targetFile.exists()) {
        System.err.println("Target file " + targetFile.getName() + " exists");
        System.exit(abnormalTermination);
    }

    int exitCode = normalTermination; // We may change our mind.

    FileInputStream  source = null;
    FileOutputStream target = null;

    try {
      source = new FileInputStream (args[0]);
      target = new FileOutputStream(targetFile);

      for (int c = source.read(); c != -1; c = source.read())
          target.write(c);
     }
    catch (IOException e) {
        System.err.println("IO error " + args[0]);
        System.err.println(e.getMessage());

        exitCode = abnormalTermination; // We've changed our mind.
    }
    finally {
        try {
            source.close();
            target.close();
        }
        catch (IOException e) {
            System.err.println("Error while closing files");
            System.err.println(e.getMessage());

            exitCode = abnormalTermination; // We've changed our mind.
        }
    }

    System.exit(exitCode);
  }
}
