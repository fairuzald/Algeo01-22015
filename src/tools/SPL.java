package tools;

import java.io.IOException;
import java.io.FileWriter;


public class SPL extends Matrix {
  /* ***** ATRIBUTE ***** */
  public enum categorySolution {
    PARAMETRIX, UNIQUE, UNDEFINED, SUBSTITABLE
  }

  // UNtuk menyimpan solusi unique dan koefisien parametrik
  public float[] Solution;

  // MEnyimpan hasil solusi suatu persamaan
  public String[] Equation;

  // Identifikasi solusi unique, paratmetrix atau undefined
  public categorySolution[] Status;


  /* ***** METHODS ***** */

  /* *** Konstruktor membentuk MATRIKS AUGMENTED SPL *** */
  public SPL(int rowEff, int colEff) {
    super(rowEff, colEff);
  }

  public void readSPL() {
    readMatrix();
  }

  public void readFileSPL(final String fileName) {
    readFileMatrix(fileName);
  }

  public void displaySPL() {
    int i;
    int countParametrix = 0;
    String sentence;
    if (this.Status[0] == categorySolution.UNDEFINED) {
      System.out.println("SPL tidak memiliki solusi.");
    } else {

      for (i = 0; i < this.Equation.length; i++) {
        // JIka solusi adalah parametrix maka hitung jumlahnya
        if (this.Status[i] == categorySolution.PARAMETRIX) {
          countParametrix++;
        }
        sentence = "x_" + (i + 1) + "=" + this.Equation[i];
        System.out.println(sentence);

      }
      if (countParametrix > 0) {
        char params = 'a';
        sentence = "Dengan";
        for (i = countParametrix; i > 0; i--) {
          sentence += String.valueOf(params);
          params++;
          if (countParametrix > 1) {
            sentence += ",";
          }
        }
        sentence += "anggota bilangan real";
        System.out.println(sentence);
      }
    }
  }

  public void writeFileSPL(final String fileName) {
    int i;
    int countParametrix = 0;
    String sentence;
    FileWriter output = null;
    try {
      String directory = "./test/" + fileName;
      output = new FileWriter(directory);
      if (this.Status[0] == categorySolution.UNDEFINED) {
        output.write("SPL tidak memiliki solusi.");
      } else {
        for (i = 0; i < this.Equation.length; i++) {
          // Jika solusi adalah parametrix maka hitung jumlahnya
          if (this.Status[i] == categorySolution.PARAMETRIX) {
            countParametrix++;
          }
          sentence = "x_" + (i + 1) + "=" + this.Equation[i];
          output.write(sentence);
          if (i != this.Equation.length - 1) {
            output.write(System.lineSeparator());

          }
        }
      }
      // Tambahkan kalimat parametrix solution
      if (countParametrix > 0) {
        char params = 'a';
        sentence = "Dengan ";
        for (i = countParametrix; i > 0; i--) {
          sentence += String.valueOf(params);
          params++;
          if (i > 1) {
            sentence += ", ";
          }
        }
        sentence += " anggota bilangan real";

        output.write(sentence + "\n");
      }

      output.close();
      System.out.println("Berhasil menyimpan SPL pada folder test, file \"" + fileName + "\".");
    } catch (IOException e) {
      System.out.println("Something went wrong.");
      e.printStackTrace();
    }
  }

  public boolean isAllCoefMatrixDiagonalOne() {
    int i;
    for (i = this.getFirstIdxRow(); i < this.getLastIdxRow(); i++) {
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
    if (this.isAllCoefMatrixDiagonalOne() && (this.rowEff == this.colEff - 1)) {
      return categorySolution.UNIQUE;
    } else if (this.isAllCoefMatrixDiagonalOne() && (this.rowEff != this.colEff - 1)) {
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
    this.Solution = new float[this.colEff - 1];
    this.Equation = new String[this.colEff - 1];
    this.Status = new categorySolution[this.colEff - 1];
    categorySolution typeSolution = this.solutionType();
    if (typeSolution == categorySolution.UNIQUE || typeSolution == categorySolution.PARAMETRIX) {
      this.gaussSolution();
    }

  }

  public void gaussSolution() {
    int i, j;
    float vektorKonstan;
    String stringPersamaan;
    int k;
    char params = 'a';

    // Substitusi ke belakang
    for (i = this.getLastIdxRow(); i >= this.getFirstIdxRow(); i--) { // Gunakan "--" bukan "++"
      // Lewati baris dengan semua koefisien dalam matriks nol
      while (this.isAllRowCoefMatrixZero(i)) {
        i--;
      }
      j = this.getFirstIdxCol();
      // Cari leading one untuk matriks koefisien
      while (this.getElmt(i, j) != 1 && j < this.getLastIdxCol()) {
        j++;
        if (j == this.getLastIdxCol()) {
          System.out.println("Tidak ditemukan leading one.");
        }
      }

      k = j;
      // Asumsi awal: solusi unik pada leading one
      this.Status[k] = categorySolution.UNIQUE;
      j++;
      vektorKonstan = this.getElmt(i, this.getLastIdxCol());
      // Loop melalui elemen dalam kolom setelah leading one
      // Tangani kasus substitusi
      while (j < this.getLastIdxCol()) {
        // Lewati elemen yang nol
        if (this.getElmt(i, j) != 0) {
          // Proses substitusi nilai
          if (this.Status[j] == categorySolution.UNIQUE) {
            vektorKonstan -= this.getElmt(i, j) * this.Solution[j];
          }
          // Aksi ketika baris di bawahnya memiliki solusi yang tidak terdefinisi, tangani kasus
          // parametrik
          else if (this.Status[j] == categorySolution.UNDEFINED) {
            this.Status[j] = categorySolution.PARAMETRIX;
            this.Equation[j] = String.valueOf(params);
            params++;
          }
        }
        // Lanjut ke kolom berikutnya untuk substitusi
        j++;
      }
      this.Solution[k] = vektorKonstan;
      j = k + 1;
      if (params != 0 || this.Status[k] == categorySolution.UNIQUE) {
        stringPersamaan = params + " ";
      } else {
        stringPersamaan = "";
      }
      // Tangani kasus substitusi ketika kolom setelah leading one tidak eksak
      if (this.Status[k] == categorySolution.SUBSTITABLE) { // Diperbaiki dari SUBSTITUSI
        // Lewati kolom setelah leading one yang nol
        if (this.getElmt(i, j) != 0) {
          if (this.Status[k] == categorySolution.PARAMETRIX) {
            if (this.getElmt(i, j) > 0) {
              if (Math.abs(this.getElmt(i, j)) == 1) {
                stringPersamaan += "- " + this.Equation[j] + " ";
              } else {
                stringPersamaan += "- " + Math.abs(this.getElmt(i, j)) + this.Equation[j] + " ";
              }
            } else {
              if (Math.abs(this.getElmt(i, j)) == 1) {
                stringPersamaan += "+ " + this.Equation[j] + " ";
              } else {
                stringPersamaan += "+ " + Math.abs(this.getElmt(i, j)) + this.Equation[j] + " ";
              }
            }
          } else if (this.Status[k] == categorySolution.SUBSTITABLE) {
            // Tangani kasus lain jika diperlukan
          }
        }
      }
    }
  }


  public void gJordanMethodSPL() {}

  public Matrix getKoefMatrix() {
    Matrix koefMatrix = new Matrix(this.rowEff, this.colEff - 1);
    int i, j;
    for (i = this.getFirstIdxRow(); i <= this.getLastIdxRow(); i++) {
      for (j = this.getFirstIdxCol(); j < this.getLastIdxCol(); j++) {
        koefMatrix.setElmt(i, j, this.getElmt(i, j));
      }
    }
    return koefMatrix;

  }

  public Matrix getVectorConstant() {
    Matrix vectorConstant = new Matrix(this.rowEff, 1);
    int i;
    for (i = this.getFirstIdxRow(); i <= this.getLastIdxRow(); i++) {
      vectorConstant.setElmt(i, this.getFirstIdxCol(), this.getElmt(i, this.getLastIdxCol()));
    }
    return vectorConstant;
  }

  public float determinanNumerator(int colIdx) {
    int i;
    float[] temp = new float[this.getLastIdxCol()];
    Matrix koefMatrix = this.getKoefMatrix();
    Matrix vectorConstant = this.getVectorConstant();
    float determinant;

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
    int i, j;
    Matrix vectorSolution;

    Matrix koefMatrix = this.getKoefMatrix();
    Matrix vectorConstant = this.getVectorConstant();

    float determinant = koefMatrix.determinantCofactor();
    if (determinant == 0) {
      this.Solution = new float[1];
      this.Equation = new String[1];
      this.Status = new categorySolution[1];
      // Handle the case where the determinant is zero or numerator is zero
      boolean isAllRowVectorConstantZero = this.isAllVectorConstantZero();
      if (isAllRowVectorConstantZero) {
        this.Status[0] = categorySolution.PARAMETRIX;
        System.out.println(
            "Determinan matrix 0 dan vector konstan semua bernilai 0 maka solusi parametrix tidak bisa dicari dengan metode cramer karena tidak punya invers");
      } else {
        this.Status[0] = categorySolution.UNDEFINED;
        System.out.println(
            "Determinan matrix 0 dan vector konstan tidak bernilai 0 maka solusi tidak ada.");
      }
    } else if (!koefMatrix.isSquare()) {
      System.out.println(
          "Bukan matrix singular sehingga solusi SPL tidak ditemukan dengan metode invers");
    } else {

      koefMatrix = koefMatrix.inversAdjoin();
      vectorSolution = this.multiplyMatrix(koefMatrix, vectorConstant);
      vectorSolution = vectorSolution.transpose();

      // Assign solution
      this.Solution = new float[vectorSolution.getColEff()];
      this.Equation = new String[vectorSolution.getColEff()];
      this.Status = new categorySolution[vectorSolution.getColEff()];

      for (i = vectorSolution.getFirstIdxCol(); i < vectorSolution.getColEff(); i++) {
        this.Solution[i] = vectorSolution.getElmt(0, i);
        this.Equation[i] = Float.toString(vectorSolution.getElmt(0, i));
        this.Status[i] = categorySolution.UNIQUE;
      }

    }
  }

  public void cramerMethodSPL() {
    Matrix mEntry = this.getKoefMatrix();
    int i;
    float determinantDenominator = mEntry.determinantCofactor();
    float determinanNumerator;
    this.Solution = new float[this.getColEff() - 1];
    this.Equation = new String[this.getColEff() - 1];
    this.Status = new categorySolution[this.getColEff() - 1];
    for (i = mEntry.getFirstIdxCol(); i <= mEntry.getLastIdxCol(); i++) {
      determinanNumerator = this.determinanNumerator(i);

      if (determinantDenominator == 0) {
        this.Solution = new float[1];
        this.Equation = new String[1];
        this.Status = new categorySolution[1];
        // Handle the case where the determinant is zero or numerator is zero
        boolean isAllRowVectorConstantZero = this.isAllVectorConstantZero();
        if (isAllRowVectorConstantZero) {
          this.Status[0] = categorySolution.PARAMETRIX;
          System.out.println(
              "Determinan matrix 0 dan vector konstan semua bernilai 0 maka solusi parametrix tidak bisa dicari dengan metode cramer karena tidak punya invers");
        } else {
          this.Status[0] = categorySolution.UNDEFINED;
          System.out.println(
              "Determinan matrix 0 dan vector konstan tidak bernilai 0 maka solusi tidak ada");
        }
      } else if (!mEntry.isSquare()) {
        System.out.println(
            "Bukan matrix singular sehingga solusi SPL tidak ditemukan dengan metode invers");
      } else {
        float solution = determinanNumerator / determinantDenominator;
        System.out.println(solution);

        this.Solution[i] = solution;
        this.Equation[i] = Float.toString(solution);
        this.Status[i] = categorySolution.UNIQUE;
      }
    }
  }

}
