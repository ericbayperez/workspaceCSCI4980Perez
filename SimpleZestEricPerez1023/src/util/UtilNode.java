package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class UtilNode {

   public static List<String> readNodeNames(String filePath) {
      Set<String> contents = new HashSet<String>();
      File file = new File(filePath);
      Scanner scanner = null;
      try {
         scanner = new Scanner(file);
         while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (String iNode : line.split(",")) {
               // TODO: Class Exercise
                contents.add(iNode);
            }
         }
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } finally {
         if (scanner != null)
            scanner.close();
      }
      return new ArrayList<String>(contents);
   }

   public static List<String[]> readConnectionInfo(String filePath) {
      Set<String[]> contents = new HashSet<String[]>();
      File file = new File(filePath);
      Scanner scanner = null;
      try {
         scanner = new Scanner(file);
         while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String iNode1 = line.split(",")[0];
            String iNode2 = line.split(",")[1];
            String[] nodes = {iNode1, iNode2};
            // TODO: Class Exercise
             contents.add(nodes);
         }
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } finally {
         if (scanner != null)
            scanner.close();
      }
      return new ArrayList<String[]>(contents);
   }
}
