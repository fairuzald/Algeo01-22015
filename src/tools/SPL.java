package tools;

import java.io.IOException;
import tools.types.SPLInterface;
import java.io.FileWriter;

public class SPL extends Matrix implements SPLInterface {
  /* ***** ATRIBUTE ***** */
  /* ***** ATRIBUTE ***** */


  // UNtuk menyimpan solusi unique dan koefisien parametrik
  public double[] Solution;

  // MEnyimpan hasil solusi suatu persamaan
  public String[] Equation;

  // Identifikasi solusi unique, paratmetrix atau undefined
  public categorySolution[] Status;

  /* *** Konstruktor membentuk MATRIKS AUGMENTED SPL *** */
  public SPL(int rowEff, int colEff) {
    super(rowEff, colEff);
  }

  public void readSPL() {
    readMatrix();
  }

  public void readFileSPL(final String filePath) {
    readFileMatrix(filePath);
  }

  public void displaySPL() {
    int i;
    String sentence;
    if (this.Status[0] == categorySolution.UNDEFINED) {
      System.out.println("SPL tidak memiliki solusi.");
    } else if (this.Status[0] == categorySolution.ZERODETERMINANTCRAMER) {
      System.out.println("Determinan matrix 0 maka solusi tidak bisa dicari dengan metode cramer");
    } else if (this.Status[0] == categorySolution.ZERODETERMINANTINVERS) {
      System.out.println(
          "Determinan matrix 0 maka solusi tidak bisa dicari dengan metode invers karena tidak punya invers");
    } else {

      for (i = 0; i < this.Equation.length; i++) {
        // JIka solusi adalah parametrix maka hitung jumlahnya

        sentence = "x_" + (i + 1) + "=" + this.Equation[i];
        System.out.println(sentence);

      }

    }
  }

  public void writeFileSPL(final String filePath) {
    int i;
    String sentence;
    FileWriter output = null;
    try {
      output = new FileWriter(filePath);
      if (this.Status[0] == categorySolution.UNDEFINED) {
        output.write("SPL tidak memiliki solusi.");
      } else if (this.Status[0] == categorySolution.ZERODETERMINANTCRAMER) {
        output.write("Determinan matrix 0 maka solusi tidak bisa dicari dengan metode cramer");
      } else if (this.Status[0] == categorySolution.ZERODETERMINANTINVERS) {
        output.write(
            "Determinan matrix 0 maka solusi tidak bisa dicari dengan metode invers karena tidak punya invers");
      } else {
        for (i = 0; i < this.Equation.length; i++) {
          sentence = "x_" + (i + 1) + "=" + this.Equation[i];
          output.write(sentence);
          if (i != this.Equation.length - 1) {
            output.write(System.lineSeparator());

          }
        }
      }
      output.close();
      System.out.println("Berhasil menyimpan SPL pada folder test, file \"" + filePath + "\".");
    } catch (IOException e) {
      System.out.println("Something went wrong.");
      e.printStackTrace();
    }
  }

  public boolean isAllCoefMatrixDiagonalOne() {
    int i;
    for (i = this.getFirstIdxRow(); i <= this.getLastIdxRow(); i++) {
      if (this.getElmtDiagonal(i) != 1) {
        return false;
      }
    }
    return true;
  }

  public boolean isAllRowCoefMatrixZero(final int row) {
    int j;
    for (j = this.getFirstIdxCol(); j < this.getLastIdxCol(); j++) {
      if (this.getElmt(row, j) != 0) {
        return false;
      }
    }
    return true;
  }

  public boolean isRowVectorConstantZero(final int row) {
    return this.getElmt(row, this.getLastIdxCol()) == 0;
  }

  // Validasi awal
  public categorySolution solutionType() {
    if (this.isAllCoefMatrixDiagonalOne() && (this.getRowEff() == this.getColEff() - 1)) {
      return categorySolution.UNIQUE;
    } else if (this.isAllCoefMatrixDiagonalOne() && (this.getRowEff() != this.getColEff() - 1)) {
      return categorySolution.PARAMETRIX;
    } else {
      for (int i = this.getLastIdxRow(); i >= this.getFirstIdxRow(); i--) {
        if (isAllRowCoefMatrixZero(i) && !isRowVectorConstantZero(i)) {
          return categorySolution.UNDEFINED;
        }
      }
    }
    return categorySolution.PARAMETRIX;
  }

  public void gaussMethodSPL() {
    this.gaussElimination();
    this.Solution = new double[this.getColEff() - 1];
    this.Equation = new String[this.getColEff() - 1];
    this.Status = new categorySolution[this.getColEff() - 1];
    categorySolution typeSolution = this.solutionType();
    if (typeSolution == categorySolution.UNIQUE || typeSolution == categorySolution.PARAMETRIX) {
      this.gaussSolution();
    } else {
      this.Status[0] = categorySolution.UNDEFINED;
    }

  }

  public void initializationStatus() {
    int i, j;
    this.Status = new categorySolution[this.getColEff() - 1];
    for (i = this.getLastIdxRow(); i >= this.getFirstIdxRow(); i--) {
      j = this.getFirstIdxCol();
      // Skip all zero
      while (this.isAllRowCoefMatrixZero(i)) {
        i--;
      }
      // Get Leading one first di suatu baris
      while (j < this.getLastIdxCol() && this.getElmt(i, j) != 1) {
        j++;
      }
      this.Status[j] = categorySolution.UNIQUE;
    }


    for (i = 0; i < this.getLastIdxCol(); i++) {
      if (this.Status[i] != categorySolution.UNIQUE) {

        this.Status[i] = categorySolution.PARAMETRIX;
      }
    }

  }

  public void gaussSolution() {
    int i, j;
    int k, h;
    char params = 'a';
    this.initializationStatus();
    // Substitusi ke belakang
    for (i = this.getLastIdxRow(); i >= this.getFirstIdxRow(); i--) {
      String stringPersamaan = "";
      // Lewati baris dengan semua koefisien dalam matriks nol
      while (this.isAllRowCoefMatrixZero(i)) {
        i--;
      }
      j = this.getFirstIdxCol();
      while (this.getElmt(i, j) != 1 && j < this.getLastIdxCol()) {
        this.Equation[j] = "Bukan faktor solusi";
        j++;
      }
      // Cari leading one untuk matriks koefisien

      boolean isElmtAfterZero = true;
      // Cek apakah semua elemen setelah leading one adalah nol
      for (k = j + 1; k < this.getLastIdxCol(); k++) {
        if (this.getElmt(i, k) != 0) {
          isElmtAfterZero = false;
          break;
        }
      }
      // Ketika solusi eksak
      if (isElmtAfterZero) {
        this.Solution[j] = this.getElmt(i, this.getLastIdxCol());
        this.Equation[j] = String.valueOf(this.getElmt(i, this.getLastIdxCol()));
      }
      double sum = this.getElmt(i, this.getLastIdxCol());
      for (k = j + 1; k < this.getLastIdxCol(); k++) {
        if (this.getElmt(i, k) != 0 && this.Status[k] == categorySolution.PARAMETRIX) {
          this.Status[j] = categorySolution.PARAMETRIX;
          boolean isBelowZero = true;
          for (h = i + 1; h <= this.getLastIdxRow(); h++) {
            if (this.getElmt(h, k) != 0) {
              isBelowZero = false;
              break;
            }
          }
          if (isBelowZero) {
            this.Equation[k] = String.valueOf(params);
            params++;
          }
        }
        // Kalau substitable
        else if (this.getElmt(i, k) != 0 && this.Status[k] == categorySolution.UNIQUE) {
          sum -= (this.getElmt(i, k) * this.Solution[k]);
          this.Solution[j] = sum;
          this.Equation[j] = Double.toString(sum);
        }
      }

      if (sum != 0) {
        stringPersamaan += Double.toString(sum);
      }

      for (k = j + 1; k < this.getLastIdxCol(); k++) {
        boolean isBelowZero = true;
        for (h = i + 1; h <= this.getLastIdxRow(); h++) {
          if (this.getElmt(h, k) != 0) {
            isBelowZero = false;
            break;
          }
        }
        if (this.Status[k] == categorySolution.PARAMETRIX) {

          if (this.getElmt(i, k) > 0) {
            if (isBelowZero) {

              if (Math.abs(this.getElmt(i, k)) == 1) {
                stringPersamaan += "-" + "(" + this.Equation[k] + ")";
                this.Equation[j] = stringPersamaan;
              } else {
                stringPersamaan +=
                    "-" + "(" + Math.abs(this.getElmt(i, k)) + this.Equation[k] + ")";
                this.Equation[j] = stringPersamaan;
              }
            } else {
              if (Math.abs(this.getElmt(i, k)) == 1) {
                stringPersamaan += "-" + "(" + this.Equation[k] + ")";
                this.Equation[j] = stringPersamaan;
              } else {
                stringPersamaan +=
                    "-" + "(" + Math.abs(this.getElmt(i, k)) + "(" + this.Equation[k] + "))";
                this.Equation[j] = stringPersamaan;
              }
            }

          }

          else if ((this.getElmt(i, k)) < 0) {
            if (isBelowZero) {
              if (Math.abs(this.getElmt(i, k)) == 1) {
                stringPersamaan += "+" + this.Equation[k];
                this.Equation[j] = stringPersamaan;
              } else {
                stringPersamaan += "+" + Math.abs(this.getElmt(i, k)) + this.Equation[k];
                this.Equation[j] = stringPersamaan;
              }
            } else {
              if (Math.abs(this.getElmt(i, k)) == 1) {
                stringPersamaan += "+" + this.Equation[k];
                this.Equation[j] = stringPersamaan;
              } else {
                stringPersamaan +=
                    "+" + Math.abs(this.getElmt(i, k)) + "(" + this.Equation[k] + ")";
                this.Equation[j] = stringPersamaan;
              }
            }

          }

        }
      }


    }
  }

  public void gJordanMethodSPL() {
    this.gJordanElimination();
    this.Solution = new double[this.getColEff() - 1];
    this.Equation = new String[this.getColEff() - 1];
    this.Status = new categorySolution[this.getColEff() - 1];
    categorySolution typeSolution = this.solutionType();
    if (typeSolution == categorySolution.UNIQUE || typeSolution == categorySolution.PARAMETRIX) {
      this.gaussSolution();
    } else {
      this.Status[0] = categorySolution.UNDEFINED;
    }
  }

  public Matrix getKoefMatrix() {
    Matrix koefMatrix = new Matrix(this.getRowEff(), this.getColEff() - 1);
    int i, j;
    for (i = this.getFirstIdxRow(); i <= this.getLastIdxRow(); i++) {
      for (j = this.getFirstIdxCol(); j < this.getLastIdxCol(); j++) {
        koefMatrix.setElmt(i, j, this.getElmt(i, j));
      }
    }
    return koefMatrix;

  }

  public Matrix getVectorConstant(int row) {
    Matrix vectorConstant = new Matrix(row, 1);
    int i;
    for (i = this.getFirstIdxRow(); i < row; i++) {
      vectorConstant.setElmt(i, this.getFirstIdxCol(), this.getElmt(i, this.getLastIdxCol()));
    }
    return vectorConstant;
  }

  public double determinanNumerator(int colIdx, Matrix koefMatrix) {
    int i;
    double[] temp = new double[this.getLastIdxCol()];
    Matrix vectorConstant = this.getVectorConstant(this.getRowEff());

    double determinant;

    for (i = koefMatrix.getFirstIdxRow(); i <= koefMatrix.getLastIdxRow(); i++) {
      temp[i] = koefMatrix.getElmt(i, colIdx);
      koefMatrix.setElmt(i, colIdx, vectorConstant.getElmt(i, 0));
    }
    determinant = koefMatrix.determinantCofactor();
    for (i = koefMatrix.getFirstIdxRow(); i <= koefMatrix.getLastIdxRow(); i++) {
      koefMatrix.setElmt(i, colIdx, temp[i]);
    }
    return determinant;
  }

  public boolean isAllVectorConstantZero() {
    int i;
    boolean isAllRowVectorConstantZero = true;
    i = 0;
    while (i < this.getRowEff() && isAllRowVectorConstantZero) {
      if (this.getElmt(i, this.getLastIdxCol()) != 0) {
        isAllRowVectorConstantZero = false;
      } else {
        i++;
      }
    }
    return isAllRowVectorConstantZero;
  }

  public void inversMethodSPL() {
    // Split koefisien matrix dan vector solution
    int i;
    Matrix vectorSolution;

    Matrix koefMatrix = this.getKoefMatrix();
    if (koefMatrix.getColEff() < koefMatrix.getRowEff()) {
      koefMatrix = koefMatrix.copyMatrix(koefMatrix.getColEff(), koefMatrix.getColEff());
    }
    Matrix vectorConstant = this.getVectorConstant(koefMatrix.getRowEff());
    double determinant = koefMatrix.determinantCofactor();
    if (determinant == 0) {
      this.Solution = new double[1];
      this.Equation = new String[1];
      this.Status = new categorySolution[1];
      // Handle the case where the determinant is zero or numerator is zero
      this.Status[0] = categorySolution.ZERODETERMINANTINVERS;
    } else if (!koefMatrix.isSquare()) {
      System.out.println(
          "Bukan matrix singular sehingga solusi SPL tidak ditemukan dengan metode invers");
    } else {

      koefMatrix = koefMatrix.inversAdjoin();
      vectorSolution = this.multiplyMatrix(koefMatrix, vectorConstant);
      vectorSolution = vectorSolution.transpose();

      // Assign solution
      this.Solution = new double[vectorSolution.getColEff()];
      this.Equation = new String[vectorSolution.getColEff()];
      this.Status = new categorySolution[vectorSolution.getColEff()];

      for (i = vectorSolution.getFirstIdxCol(); i < vectorSolution.getColEff(); i++) {
        this.Solution[i] = vectorSolution.getElmt(0, i);
        this.Equation[i] = String.format("%.3f", vectorSolution.getElmt(0, i));
        this.Status[i] = categorySolution.UNIQUE;
      }

    }
  }

  public void cramerMethodSPL() {
    Matrix mEntry = this.getKoefMatrix();
    int i;
    // Handle jika persamaan yang diberikan terlalu banyak yang diketahui
    if (mEntry.getColEff() < mEntry.getRowEff()) {
      mEntry = mEntry.copyMatrix(mEntry.getColEff(), mEntry.getColEff());
    }
    double determinantDenominator = mEntry.determinantCofactor();
    double determinanNumerator;
    this.Solution = new double[this.getColEff() - 1];
    this.Equation = new String[this.getColEff() - 1];
    this.Status = new categorySolution[this.getColEff() - 1];
    for (i = mEntry.getFirstIdxCol(); i <= mEntry.getLastIdxCol(); i++) {
      determinanNumerator = this.determinanNumerator(i, mEntry);

      if (determinantDenominator == 0) {
        this.Solution = new double[1];
        this.Equation = new String[1];
        this.Status = new categorySolution[1];
        // Handle the case where the determinant is zero or numerator is zero
        this.Status[0] = categorySolution.ZERODETERMINANTCRAMER;
        return;
      } else if (!mEntry.isSquare()) {
        System.out.println(
            "Bukan matrix singular sehingga solusi SPL tidak ditemukan dengan metode cramer");
        return;
      } else {
        double solution = determinanNumerator / determinantDenominator;

        this.Solution[i] = solution;
        String formattedSolution;
        if (solution == (int) solution) {
          formattedSolution = String.valueOf((int) solution); // No formatting for integers
        } else {
          formattedSolution = String.format("%.3f", solution);
        }
        this.Equation[i] = formattedSolution;
        this.Status[i] = categorySolution.UNIQUE;
      }
    }
  }

}
