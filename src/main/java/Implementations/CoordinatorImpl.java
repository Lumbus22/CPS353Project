package Implementations;
import Interfaces.CoordinatorInterface;
import datasystem.Datasystem;
import datasystem.Datasystem.ReadRequest;
import datasystem.DataSystemGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CoordinatorImpl implements CoordinatorInterface {

  private String sourceFilePath;
  private final DataSystemGrpc.DataSystemBlockingStub dataSystemStub;

  public CoordinatorImpl(String serverAddress, int serverPort) {
    ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort)
            .usePlaintext()
            .build();
    this.dataSystemStub = DataSystemGrpc.newBlockingStub(channel);
  }


  @Override
  public boolean startComputation(String destinationFilePath) {
    Datasystem.ReadResponse readResponse = dataSystemStub.readFromFile(ReadRequest.newBuilder()
            .setInputFilePath(sourceFilePath)
            .build());

    String concatenatedStrings = String.join("\n", readResponse.getNumberStringsList());

    ComputationImpl computation = new ComputationImpl(concatenatedStrings);
    long[][] results = computation.performDigitFactorial();

    Datasystem.WriteRequest.Builder writeRequestBuilder = Datasystem.WriteRequest.newBuilder()
            .setOutputFilePath(destinationFilePath);

    for (long[] resultRow : results) {
      String line = convertResultRowToString(resultRow);
      writeRequestBuilder.addNumbers(line);
    }

    Datasystem.WriteResponse writeResponse = dataSystemStub.writeToFile(writeRequestBuilder.build());

    return writeResponse.getSuccess();
  }

  @Override
  public boolean startComputationCustDelimiter(String destinationFilePath, String delimiter) {
    Datasystem.ReadResponse readResponse = dataSystemStub.readFromFile(ReadRequest.newBuilder()
            .setInputFilePath(sourceFilePath)
            .build());

    String concatenatedStrings = String.join(delimiter, readResponse.getNumberStringsList());

    ComputationImpl computation = new ComputationImpl(concatenatedStrings);
    long[][] results = computation.performDigitFactorial();

    Datasystem.WriteRequest.Builder writeRequestBuilder = Datasystem.WriteRequest.newBuilder()
            .setOutputFilePath(destinationFilePath)
            .setDelimiter(delimiter);

    for (long[] resultRow : results) {
      String line = convertResultRowToString(resultRow);
      writeRequestBuilder.addNumbers(line);
    }

    Datasystem.WriteResponse writeResponse = dataSystemStub.writeToFile(writeRequestBuilder.build());

    return writeResponse.getSuccess();
  }

  @Override
  public String setSource(String inputFile) {
    this.sourceFilePath = inputFile;
    return this.sourceFilePath;
  }

  private String convertResultRowToString(long[] resultRow) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < resultRow.length; i++) {
      if (i > 0) {
        sb.append(",");
      }
      sb.append(resultRow[i]);
    }
    return sb.toString();
  }
}


