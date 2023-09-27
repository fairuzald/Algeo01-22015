package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import tools.types.BicubicSplineInterface;

public class BicubicSpline extends Matrix implements BicubicSplineInterface {
  public double predictedValue;
  public double[] pointToPredict;
  public Matrix knownPoint;

  public BicubicSpline(int nRows, int nCols) {
    super(nRows, nCols);
  }

  public void readBicubicSpline() {
    int k, x, y;
    Scanner globalScanner = null;

    try {
      globalScanner = new Scanner(System.in);

      double[] toPredict = new double[2];
      Matrix valueY = new Matrix(16, 1);
      System.out.println("Masukkan nilai sesuai koordinat yang dinyatakan: ");
      for (k = 0; k < 16; k++) {
        x = (k % 2);
        y = (k % 4 > 1) ? 1 : 0;

        switch (k / 4) {
          case 0:
            System.out.print("f(" + x + "," + y + "): ");
            break;
          case 1:
            System.out.print("fx(" + x + "," + y + "): ");
            break;
          case 2:
            System.out.print("fy(" + x + "," + y + "): ");
            break;
          default:
            System.out.print("fxy(" + x + "," + y + "): ");

        }
        valueY.setElmt(k, 0, globalScanner.nextDouble());
      }


      System.out.println("Masukkan titik yang ingin diprediksi nilainya: ");
      System.out.print("x: ");
      toPredict[0] = globalScanner.nextDouble();
      System.out.print("y: ");
      toPredict[1] = globalScanner.nextDouble();
      this.pointToPredict = toPredict;
      this.knownPoint = valueY.copyMatrix();
      globalScanner.close();
    } catch (Exception e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

  }

  public void readFileBicubicSpline(final String filePath) {
    /* KAMUS */
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
      // Menghitung total kolom baris
      // matrix
      while (line.hasNextDouble()) {
        Ncol++;
        line.nextDouble();
      }
      line.close();
      matrix.close();

      // Reopen the file for reading
      matrix = new Scanner(file);

      // Inisialisasi matrix temp
      Matrix valueY = new Matrix(Ncol * (Nrow - 1), 1);
      // Read and populate the matrix
      for (i = 0; i < Nrow - 1; i++) {
        for (j = 0; j < Ncol; j++) {
          if (matrix.hasNextDouble()) {
            valueY.setElmt(4 * i + j, 0, matrix.nextDouble());
          }
        }
      }

      // Initialize pointToPredict array
      this.pointToPredict = new double[2];

      // Read the last row as the point to predict
      if (matrix.hasNextDouble()) {
        this.pointToPredict[0] = matrix.nextDouble();
      }
      if (matrix.hasNextDouble()) {
        this.pointToPredict[1] = matrix.nextDouble();
      }

      // Close the matrix file
      matrix.close();

      // Set the knownPoint matrix to valueY
      this.knownPoint = valueY.copyMatrix();

      // Call your predictBicubicSplineValue method here with this.pointToPredict[0]
      // and this.pointToPredict[1] to compute the predicted value.
      // Replace the last row of knownPoint with the predicted values.

    } catch (FileNotFoundException e) {
      System.err.printf("Error: File \"%s\" tidak ditemukan\n", filePath);
    }
  }

  public void displayBicubicSpline() {
    System.out.println("Solusi Predicted Value untuk f(" + this.pointToPredict[0] + ","
        + this.pointToPredict[1] + "): " + this.predictedValue);
  }

  public void writeFileBicubicSpline(final String filePath) {
    try {
      FileWriter output = new FileWriter(filePath);
      output.write("Solusi Predicted Value untuk f(" + this.pointToPredict[0] + ","
          + this.pointToPredict[1] + "): " + this.predictedValue);

      output.close();
      System.out.println("Berhasil menyimpan matriks pada " + filePath + ".");

    } catch (IOException e) {
      System.out.println("Error: Kesalahan Simpan File");
      e.printStackTrace();
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
    Matrix xInvers = X.inversGJordan();
    aCoefficient = xInvers.multiplyMatrix(xInvers, Y);
    aCoefficient = aCoefficient.transpose();
    return aCoefficient;
  }

  public void predictBicubicSplineValue() {
    double result = 0, a, b;
    int i, j = 0;
    Matrix aCoefficient = getCoefficientA(this.getBicubicSplineXMatrix(), this.knownPoint);;
    double x = this.pointToPredict[0];
    double y = this.pointToPredict[1];
    for (i = 0; i <= 3; i++) {
      for (j = 0; j <= 3; j++) {
        a = Math.pow(x, i);
        b = Math.pow(y, j);
        result += (aCoefficient.getElmt(0, 4 * i + j) * a * b);
      }
    }
    this.predictedValue = result;
  }

}
