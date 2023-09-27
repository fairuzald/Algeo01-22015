package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Regression extends SPL {
  /* **** ATRIBUTE ***** */
  private Matrix allPoint;

  /* **** METHODS ***** */
  /* Konstruktor Matrix */
  public Regression(int rowEff, int colEff) {
    super(rowEff, colEff);
  }

  public void readRegression(int m, int n) {
    /* KAMUS */
    int i, j;
    Scanner input = new Scanner(System.in);

    /* ALGORITMA */
    try {

      Matrix mat = new Matrix(m, n + 1);

      for (i = 0; i < n; i++) {
        System.out.println("Masukkan nilai x" + i + "i:");
        for (j = 0; j < m; j++) {
          float inputan = input.nextFloat();
          mat.setElmt(i, j, inputan);
        }
      }

      System.out.println("Masukkan nilai yi:");
      for (j = 0; j < m; j++) {
        float inputan = input.nextFloat();
        mat.setElmt(j, n, inputan);
      }

      this.allPoint = mat.copyMatrix();
      input.close();
    } catch (Exception e) {
      System.err.println("Error saat readRegression.");
    }
  }

  public void readFileRegression(final String fileName) throws FileNotFoundException {
    try { /* KAMUS */
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
    /* ALGORITMA */
    for (i = 0; i < allPoint.getRowEff(); i++) {
      for (j = 0; j < allPoint.getColEff(); j++) {
        if (i == 0) {
          if (j == 0) {
            setElmt(i, j, allPoint.getRowEff());
          } else {
            setElmt(i, j, SigmaRegressionOne(j));
          }
        } else {
          if (j == 0) {
            setElmt(i, j, SigmaRegressionOne(i));
          } else {
            setElmt(i, j, SigmaRegressionTwo(i, j));
          }
        }
      }
    }
  }

  private float SigmaRegressionOne(int kolom) {
    /* KAMUS */
    float result = 0;
    int i;
    /* ALGORITMA */
    for (i = 0; i < allPoint.getRowEff(); i++) {
      result += allPoint.getElmt(i, kolom);
    }

    return result;
  }

  private float SigmaRegressionTwo(int kolom, int kolomPengali) {
    /* KAMUS */
    float result = 0;
    int i;
    /* ALGORITMA */
    for (i = 0; i < allPoint.getRowEff(); i++) {
      // result += this.allPoint[i][kolom] * this.allPoint[i][kolomPengali];
      result += allPoint.getElmt(i, kolom) * allPoint.getElmt(i, kolomPengali);
    }

    return result;
  }

}
