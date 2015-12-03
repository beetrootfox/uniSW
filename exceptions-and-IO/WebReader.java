import java.io.*;
import java.net.URL;

public class WebReader
{
   public static void main (String[] args) throws Exception {
     BufferedReader page = 
                  new BufferedReader(
                        new InputStreamReader(
                                new URL(args[0]).openStream()));

     String line = page.readLine();

     while (line != null) {
          System.out.println(line);
          line = page.readLine(); 
     }

     page.close();
   }
}
