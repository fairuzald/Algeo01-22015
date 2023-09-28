package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Regression extends SPL {
  /* **** ATRIBUTE ***** */
  public Matrix allPoint;
  private float[] estimationPoint;

  /* **** METHODS ***** */
  /* Konstruktor Matrix */
  public Regression(int rowEff, int colEff) {
    super(rowEff, colEff);
  }

  public void readRegressionKeyboard(int m, int n) {
    /* KAMUS LOKAL */
    int i, j;
    Scanner input = new Scanner(System.in);
    this.estimationPoint = new float[n];

    /* ALGORITMA */
    try {
      Matrix mat = new Matrix(m, n + 1);

      for (i = 0; i < n; i++) {
        System.out.println("Masukkan nilai x" + (i + 1) + "i:");
        for (j = 0; j < m; j++) {
          float inputan = input.nextFloat();
          mat.setElmt(j, i, inputan);
        }
      }

      System.out.println("Masukkan nilai yi:");
      for (j = 0; j < m; j++) {
        float inputan = input.nextFloat();
        mat.setElmt(j, n, inputan);
      }

      System.out.println("Masukkan nilai xki (nilai x yang akan diuji):");
      for (j = 0; j < n; j++) {
        float inputan = input.nextFloat();
        this.estimationPoint[j] = inputan;
      }

      this.allPoint = mat.copyMatrix();
      input.close();
    } catch (Exception e) {
      System.err.println("Error saat readRegression.");
    }
  }

  public void readRegressionFile(final String fileName) throws FileNotFoundException {
    try {
      /* KAMUS */
      String directory = "./src/data/" + fileName;
      File file = new File(directory);
      int Nrow = 0;
      int Ncol = 0;
      int i, j; // Indeks

      Scanner scan = new Scanner(file);
      /* ALGORITMA */
      while (scan.hasNextLine()) {
        Nrow++;
        if (Nrow == 1) {
          while (scan.hasNextFloat()) {
            Ncol++;
          }
        }
        scan.nextLine();
      }
      scan.close();

      Matrix tempMatrix = new Matrix(Nrow, Ncol);

      scan = new Scanner(file);
      for (i = 0; i < Nrow - 1; i++) {
        for (j = 0; j < Ncol; j++) {
          setElmt(i, j, scan.nextFloat());
        }
      }

      this.allPoint = tempMatrix.copyMatrix();

    } catch (FileNotFoundException e) {
      System.err.printf("Error: File %s tidak ditemukan", fileName);
    }

  }

  public void compileMatrix() {
    /* KAMUS */
    int i, j;
    float[] sigmaAllPointOne;
    float[][] sigmaAllPointTwo;
    /* ALGORITMA */
    sigmaAllPointOne = new float[this.getColEff() - 1];
    sigmaAllPointTwo = new float[this.getRowEff() - 1][this.getColEff() - 1];

    for (i = 0; i < this.allPoint.getColEff(); i++) {
      sigmaAllPointOne[i] = SigmaRegressionOne(i);
    }

    for (i = 0; i < this.allPoint.getColEff() - 1; i++) {
      for (j = 0; j < this.allPoint.getColEff(); j++) {
        sigmaAllPointTwo[i][j] = SigmaRegressionTwo(i, j);
      }
    }

    for (i = 0; i < allPoint.getColEff(); i++) {
      for (j = 0; j < allPoint.getColEff() + 1; j++) {
        if (i == 0) {
          if (j == 0) {
            this.setElmt(i, j, allPoint.getRowEff());
          } else {
            this.setElmt(i, j, sigmaAllPointOne[j - 1]);
          }
        } else {
          if (j == 0) {
            this.setElmt(i, j, sigmaAllPointOne[i - 1]);
          } else {
            this.setElmt(i, j, sigmaAllPointTwo[i - 1][j - 1]);
          }
        }
      }
    }
  }

  public float estimate() {
    float result = 0;
    int i;
    for (i = 0; i < this.Solution.length; i++) {
      if (i == 0) {
        result = this.Solution[i];
      } else {
        result += this.Solution[i] * this.estimationPoint[i - 1];
      }
    }
    return result;
  }

  private float SigmaRegressionOne(int kolom) {
    /* KAMUS */
    float result = 0;
    int i;
    /* ALGORITMA */
    for (i = 0; i < this.allPoint.getRowEff(); i++) {
      result += this.allPoint.getElmt(i, kolom);
    }

    return result;
  }

  private float SigmaRegressionTwo(int kolom1, int kolom2) {
    /* KAMUS */
    float result = 0;
    int i;
    /* ALGORITMA */
    for (i = 0; i < this.allPoint.getRowEff(); i++) {
      result += this.allPoint.getElmt(i, kolom1) * this.allPoint.getElmt(i, kolom2);
    }

    return result;
  }
}
