public class UserToComputerEngineEmpty implements UserToComputerEngineInterface {

  private ComputerEngineEmpty computerEngine;

  @Override
  public Response setSourceAndDest(String sourceURL, String destinationURL) {
    return new Response();
  }

  @Override
  public Request exCompDefaultDelim(String source) {
    return new Request();
  }

  @Override
  public Request exCompCustomDelim(String delim, String source) {
    return new Request();
  }
}
