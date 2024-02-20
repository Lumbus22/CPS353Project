import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class UserToComputerEngineImpl implements UserToComputerEngineInterface {

  private String sourceFilePath;
  private String destinationFilePath;
  private static Scanner scanner = new Scanner(System.in);

  // Test Script
  public static void main(String[] args) {
    UserToComputerEngineImpl engine = new UserToComputerEngineImpl();

    engine.setSource("inputFilepath.csv");

    engine.setDestination("outputFilepath.csv");

    try {
      long[][] outputDefault = engine.exCompDefaultDelim();
      long[][] outputCustom = engine.exCompCustomDelim(":");
      } catch (FileNotFoundException e) {
      System.err.println("The specified file was not found: " + e.getMessage());
    }
    catch (IOException e) {
      System.err.println("No input" + e.getMessage());
    }
    scanner.close();
  }


  // Allows user to set inputFile source (in form of csv for now) 
  @Override
  public String setSource(String inputFile) {
    this.sourceFilePath = inputFile;
    return this.sourceFilePath;
  }

  // Allows user to set outputFile source (in form of csv for now)
  @Override
  public String setDestination(String outputFile) {
    this.destinationFilePath = outputFile;
    return this.destinationFilePath;
  }

  // Carries out the computation of the digit factorial, output formatted with default delimiter
  @Override
  public long[][] exCompDefaultDelim() throws FileNotFoundException, IOException {
    DigitFactorialCalculator calculator = new DigitFactorialCalculator(this.sourceFilePath);
    calculator.receiveDataForComputation();
    long[][] results = calculator.performDigitFactorial(); // Perform calculation
    
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFilePath))) { // Format results
        for (long[] row : results) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < row.length; i++) {
                sb.append(row[i]);
                if (i < row.length - 1) {
                    sb.append(", ");
                }
            }
            writer.write(sb.toString()); // write results to output file
            writer.newLine(); 
        }
    } 

  return results;
}

  // Carries out the computation of the digit factorial, output formatted with custom delimiter
  @Override
  public long[][] exCompCustomDelim(String customDelim) throws FileNotFoundException {
    DigitFactorialCalculator calculator = new DigitFactorialCalculator(this.sourceFilePath);
    calculator.receiveDataForComputation();
    long[][] results = calculator.performDigitFactorial();
    
    for (long[] row : results) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < row.length; i++) {
          sb.append(row[i]);
          if (i < row.length - 1) {
              sb.append(customDelim);
          }
      }
      System.out.println(sb.toString());
  }
  return results;
}
}