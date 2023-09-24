package tools;

public interface SPLInterface {
  public void readSPL();

  public void readFileSPL(String fileName);

  public void displaySPL();

  public void writeFileSPL(String fileName);

  public categorySolution solutionType();

  public boolean isAllCoefMatrixDiagonalOne();

  public boolean isAllRowCoefMatrixZero(int row);

  public boolean isRowVectorResultZero(int row);

  public void gaussMethodSPL();

  public void gaussSolution();

  public void gJordanMethodSPL();

  public void inversMethodSPL();

  public void cramerMethodSPL();


  public enum categorySolution {
    PARAMETRIX, UNIQUE, UNDEFINED, SUBSTITABLE
  }


}
