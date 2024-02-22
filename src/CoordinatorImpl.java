import java.io.IOException;

public class CoordinatorImpl implements CoordinatorInterface {

  private String sourceFilePath;
  private DataSystem dataSystem;

  public CoordinatorImpl() {

  }

  // add functionality to let user create their own output file at some point

  public static void main(String[] args) {
    CoordinatorImpl coordinator = new CoordinatorImpl();
    String sourceFilePath = "/Users/davidvenuto/Desktop/TestCodeShit/ComputerEngine/document.csv";
    coordinator.setSource(sourceFilePath);
    String destinationFilePath = "/Users/davidvenuto/Desktop/TestCodeShit/ComputerEngine/document2.csv";
    boolean isSuccess = coordinator.startComputationCustDelimiter(destinationFilePath, "/");
    if (isSuccess) {
      System.out.println("Computation completed successfully and results are written to " + destinationFilePath);
    } else {
      System.err.println("Computation failed.");
    }
  }

  @Override
  public boolean startComputation(String destinationFilePath) {
    try {
      this.dataSystem = new DataSystem(sourceFilePath);
      this.dataSystem.readFromFile();
      ComputationImpl computation = new ComputationImpl(sourceFilePath);
      computation.receiveDataForComputation();
      long[][] results = computation.performDigitFactorial();
      this.dataSystem.setDestination(destinationFilePath);
      this.dataSystem.writeToFile(results, null);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean startComputationCustDelimiter(String destinationFilePath, String delimiter) {
    try {
      this.dataSystem = new DataSystem(sourceFilePath);
      this.dataSystem.readFromFile();
      ComputationImpl computation = new ComputationImpl(sourceFilePath);
      computation.receiveDataForComputation();
      long[][] results = computation.performDigitFactorial();
      this.dataSystem.setDestination(destinationFilePath);
      this.dataSystem.writeToFile(results, delimiter);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  // Allows user to set inputFile source (in form of csv for now)
  @Override
  public String setSource(String inputFile) {
    this.sourceFilePath = inputFile;
    return this.sourceFilePath;
  }

}
