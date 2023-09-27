package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import tools.types.BicubicSplineInterface;

public class BicubicSpline extends Matrix implements BicubicSplineInterface {
  public BicubicSpline(int nRows, int nCols) {
    super(nRows, nCols);
  }

  public void readBicubicSpline() {
    int k, x, y;
    double predictedValue;
    Scanner globalScanner = null;

    try {
      globalScanner = new Scanner(System.in);

      double[] toPredict = new double[2];
      Matrix valueY = new Matrix(16, 1);
      Matrix coefficients;
      System.out.println("Masukkan nilai sesuai koordinat yang dinyatakan: ");
      for (k = 0; k < 16; k++) {
        x = (k % 2);
        y = (k % 4 > 1) ? 1 : 0;

        switch (k / 4) {
          case 0:
            System.out.print("f(" + x + ", " + y + "): ");
            break;
          case 1:
            System.out.print("fx(" + x + ", " + y + "): ");
          case 2:
            System.out.print("fy(" + x + ", " + y + "): ");
          default:
            System.out.print("fxy(" + x + ", " + y + "): ");

        }
        valueY.setElmt(k, 0, globalScanner.nextDouble());
      }


      System.out.println("Masukkan titik yang ingin diprediksi nilainya: ");
      System.out.print("x: ");
      toPredict[0] = globalScanner.nextDouble();
      System.out.print("y: ");
      toPredict[1] = globalScanner.nextDouble();

      coefficients = getCoefficientA(this.getBicubicSplineXMatrix(), valueY);
      predictedValue = predictBicubicSplineValue(toPredict[0], toPredict[1], coefficients);
      System.out.println("Nilai yang diprediksi: " + predictedValue);
      globalScanner.close();
    } catch (Exception e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

  }

  public void readFileBicubicSpline(final String filePath) {
    /* KAMUS */
    double predictedValue;
    double[] toPredict = new double[2];
    Matrix valueY = new Matrix(16, 1);
    Matrix coefficients;
    File file = new File(filePath);
    int Nrow = 0;
    int Ncol = 0;
    int i, j; // Indeks
    try {

      Scanner matrix = new Scanner(file); // Untuk ambil matrix

      /* ALGORITMA */

      // Menghitung total baris matrix
      while (matrix.hasNextLine()) {
        Nrow++;
        matrix.nextLine();
      }
      matrix.close();

      matrix = new Scanner(file);
      Scanner line = new Scanner(matrix.nextLine());
      // Menghitung total kolom baris matrix
      while (line.hasNextDouble()) {
        Ncol++;
        line.nextDouble();
      }
      line.close();
      matrix.close();
      // Reopen the file for reading
      matrix = new Scanner(file);

      // Inisialisasi matrix temp
      Matrix tempMatrix = new Matrix(Nrow, Ncol);

      // Read and populate the matrix
      for (i = 0; i < Nrow; i++) {
        for (j = 0; j < Ncol; j++) {
          if (matrix.hasNextDouble()) {
            tempMatrix.setElmt(i, j, matrix.nextDouble());
          }
        }
      }
      matrix.close();

      coefficients = getCoefficientA(this.getBicubicSplineXMatrix(), valueY);
      predictedValue = predictBicubicSplineValue(toPredict[0], toPredict[1], coefficients);
      System.out.println("Nilai yang diprediksi: " + predictedValue);
    } catch (FileNotFoundException e) {
      System.err.printf("Error: File \"%s\" tidak ditemukan\n", filePath);
    }

  }


  public Matrix getBicubicSplineXMatrix() {
    Matrix mKoef = new Matrix(16, 16);
    int x = 0;
    int y = 0;
    int row = 0;
    double a, b, insert;
    int i, j, k, h;

    for (k = 0; k < 16; k++) {
      x = (k % 2);
      y = (k % 4 > 1) ? 1 : 0;
      int col = 0;

      switch (k / 4) {
        case 0:
          for (i = 0; i <= 3; i++) {
            for (j = 0; j <= 3; j++) {
              col = j * 4 + i;
              a = Math.pow(x, i);
              b = Math.pow(y, j);
              insert = (a != 0 && b != 0) ? a * b : 0;
              mKoef.setElmt(k, col, insert);
            }
          }
          break;

        case 1:
          for (i = 1; i <= 3; i++) {
            for (j = 0; j <= 3; j++) {
              col = j * 4 + i;
              h = i - 1;
              a = Math.pow(x, h);
              b = Math.pow(y, j);
              insert = (a != 0 && b != 0 && i != 0) ? a * b * i : 0;
              mKoef.setElmt(k, col, insert);
            }
          }
          break;

        case 2:
          for (i = 0; i <= 3; i++) {
            for (j = 1; j <= 3; j++) {
              col = j * 4 + i;
              h = j - 1;
              a = Math.pow(x, i);
              b = Math.pow(y, h);
              insert = (a != 0 && b != 0 && j != 0) ? a * b * j : 0;
              mKoef.setElmt(k, col, insert);
            }
          }
          break;

        default:
          for (i = 0; i <= 3; i++) {
            for (j = 0; j <= 3; j++) {
              col = j * 4 + i;
              h = j - 1;
              row = i - 1;
              a = Math.pow(x, row);
              b = Math.pow(y, h);
              insert = (a != 0 && b != 0 && i != 0 && j != 0) ? a * b * i * j : 0;
              mKoef.setElmt(k, col, insert);
            }
          }
          break;
      }
    }
    return mKoef;
  }

  public Matrix getCoefficientA(Matrix X, Matrix Y) {
    Matrix aCoefficient = new Matrix(1, 16);
    Matrix xInvers = X.inversAdjoin();
    aCoefficient = xInvers.multiplyMatrix(xInvers, Y);
    aCoefficient = aCoefficient.transpose();

    return aCoefficient;
  }

  public double predictBicubicSplineValue(double x, double y, Matrix aCoefficient) {
    double result = 0, a, b;
    int i, j = 0, k = 0;

    for (i = 0; i <= 3; i++) {
      for (j = 0; j <= 3; j++) {
        a = Math.pow(x, i);
        b = Math.pow(y, j);
        result += (aCoefficient.getElmt(0, k) * a * b);
        k += 1;
      }
    }

    return result;
  }

}
