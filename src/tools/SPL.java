package tools;

interface SPLInterface {
  public void readSPL();

  public void readFileSPL();

  public void displaySPL();

  public void writeFileSPL();

  public categorySolution solutionType();

  boolean isAllCoefMatrixDiagonalOne();

  boolean isRowCoefMatrixZero(int row);

  boolean isRowVectorResultZero(int row);

  void gaussMethodSPL();

  void gaussSolution();

  void gJordanMethodSPL();

  void inversMethodSPL();

  void cramerMethodSPL();


  public enum categorySolution {
    PARAMETRIX, UNIQUE, UNDEFINED, SUBSTITABLE
  }

}

