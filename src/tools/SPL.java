package tools;

import java.io.IOException;
import java.io.FileWriter;

interface SPLInterface {
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


public class SPL extends Matrix implements SPLInterface {
  /* ***** ATRIBUTE ***** */
  public float[] Solution;

  public String[] Equation;

  public categorySolution[] Status;


  /* ***** METHODS ***** */

  /* *** Konstruktor membentuk MATRIKS AUGMENTED SPL *** */
  SPL(int rowEff, int colEff) {
    super(rowEff, colEff);
  }

  public void readSPL() {
    readMatrix();
  }

  public void readFileSPL(final String fileName) {
    readFileSPL(fileName);
  }

  public void displaySPL() {
    int i;
    int countParametrix = 0;
    String sentence;
    if (this.Status[0] == categorySolution.UNDEFINED) {
      System.out.println("SPL tidak memiliki solusi.");
    } else {

      for (i = 0; i <= this.getLastIdxCol(); i++) {
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
        for (i = countParametrix; i < 0; i--) {
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
      String directory = "./tes/hasil/" + fileName;
      output = new FileWriter(directory);
      if (this.Status[0] == categorySolution.UNDEFINED) {
        output.write("SPL tidak memiliki solusi.");
      } else {
        for (i = 0; i <= this.getLastIdxCol(); i++) {
          // Jika solusi adalah parametrix maka hitung jumlahnya
          if (this.Status[i] == categorySolution.PARAMETRIX) {
            countParametrix++;
          }
          sentence = "x_" + (i + 1) + "=" + this.Equation[i];
          output.write(sentence + "\n");
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

  public boolean isRowVectorResultZero(final int row) {
    return this.getElmt(row, this.getLastIdxCol()) == 0;
  }

  public categorySolution solutionType() {
    if (this.isAllCoefMatrixDiagonalOne() && (this.rowEff == this.colEff - 1)) {
      return categorySolution.UNIQUE;
    } else if (this.isAllCoefMatrixDiagonalOne() && (this.rowEff != this.colEff - 1)) {
      return categorySolution.PARAMETRIX;
    } else {
      for (int i = this.getLastIdxRow(); i >= this.getFirstIdxRow(); i--) {
        if (isAllRowCoefMatrixZero(i) && !isRowVectorResultZero(i)) {
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
    float vectorResult;
    String param;
    int k;
    char params = 'a';

    // Back subtitution
    for (i = this.getLastIdxRow(); i >= this.getFirstIdxRow(); i++) {
      // Skip baris koef matriks yang 0
      while (this.isAllRowCoefMatrixZero(i)) {
        i--;
      }
      j = this.getFirstIdxCol();
      // Cari leading one untuk koef matrix
      while (this.getElmt(i, j) != 1 && j < this.getLastIdxCol()) {
        j++;
        if (j == this.getLastIdxCol()) {
          System.out.println("Tidak ditemukan leading one.");
        }
      }

      k = j;
      this.Status[k] = categorySolution.UNIQUE;
      j++;
      vectorResult = this.getElmt(i, this.getLastIdxCol());
      // Looping elemen kolom setelah leading one
      // Handle kasus subtitable
      while (j < this.getLastIdxCol()) {
        // Skip yang elemennya 0
        if (this.getElmt(i, j) != 0) {
          if (this.Status[j] != categorySolution.UNIQUE) {
            this.Status[k] = categorySolution.SUBSTITABLE;
          }
          // Proses subtitusi nilai
          if (this.Status[j] == categorySolution.UNIQUE) {
            vectorResult -= this.getElmt(i, j) * this.Solution[j];
          }
          // Aksi ketika ternyata baris dibawah undefined solution, maka handle kasus parametrix
          else if (this.Status[j] == categorySolution.UNDEFINED) {
            this.Status[j] = categorySolution.PARAMETRIX;
            this.Equation[j] = String.valueOf(params);
            params++;
          }
        }
        // Lanjut subtitusi kolom berikutnya
        j++;
      }
      this.Solution[k] = vectorResult;
      j = k + 1;

      // Handle subtita
      if (this.Status[k] == categorySolution.SUBSTITABLE) {

      }
    }

  }

  public void gJordanMethodSPL() {}

  public void inversMethodSPL() {}

  public void cramerMethodSPL() {}
}
