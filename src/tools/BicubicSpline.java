package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import tools.types.BicubicSplineInterface;

public class BicubicSpline extends Matrix implements BicubicSplineInterface {
  private double predictedValue;
  private double[] pointToPredict;
  private Matrix knownPoint;


  public BicubicSpline(final int nRows, final int nCols) {
    super(nRows, nCols);
  }

  public void setPredictedValue(double predictedValue) {
    this.predictedValue = predictedValue;
  }

  public void setPointToPredict(final double x, final double y) {
    this.pointToPredict = new double[2];
    this.pointToPredict[0] = x;
    this.pointToPredict[1] = y;
  }

  public void setKnownPoint(final Matrix knownPoint) {
    this.knownPoint = knownPoint;
  }

  public double getPredictedValue() {
    return this.predictedValue;
  }

  public double[] getPointToPredict() {
    return this.pointToPredict;
  }

  public Matrix getKnownPoint() {
    return this.knownPoint;
  }

  public void readBicubicSpline(Scanner inputScanner) {
    int k, x, y;
    double xPredict, yPredict;

    try {
      Matrix knownPoint = new Matrix(16, 1);

      System.out.println("Masukkan nilai sesuai koordinat yang dinyatakan: ");
      for (k = 0; k < 16; k++) {
        x = (k % 2);
        y = (k % 4 > 1) ? 1 : 0;

        // Mengambil data untuk knownPoint
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

        knownPoint.setElmt(k, 0, inputScanner.nextDouble());
      }

      // Memasukkan koordinat yang ingin diprediksi
      System.out.println("Masukkan titik yang ingin diprediksi nilainya: ");
      System.out.print("x: ");
      xPredict = inputScanner.nextDouble();

      System.out.print("y: ");
      yPredict = inputScanner.nextDouble();

      // Setter atribute
      this.setPointToPredict(xPredict, yPredict);
      setKnownPoint(knownPoint);
    } catch (Exception e) {
      System.out.println("Error: Terjadi kesalahan saat memproses input.");
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
      Matrix knownPoint = new Matrix(Ncol * (Nrow - 1), 1);
      // Read and populate the matrix
      for (i = 0; i < Nrow - 1; i++) {
        for (j = 0; j < Ncol; j++) {
          if (matrix.hasNextDouble()) {
            knownPoint.setElmt(4 * i + j, 0, matrix.nextDouble());
          }
        }
      }

      // Initialize pointToPredict array
      double xPredict = 0.0, yPredict = 0.0;

      // Read the last row as the point to predict
      if (matrix.hasNextDouble()) {
        xPredict = matrix.nextDouble();
      }
      if (matrix.hasNextDouble()) {
        yPredict = matrix.nextDouble();
      }

      this.setPointToPredict(xPredict, yPredict);
      // Close the matrix file
      matrix.close();
      // Set the knownPoint matrix to knownPoint
      setKnownPoint(knownPoint);

    } catch (FileNotFoundException e) {
      System.err.printf("Error: File \"%s\" tidak ditemukan\n", filePath);
    }
  }

  public void displayBicubicSpline() {
    System.out.println("Solusi Predicted Value untuk f(" + this.getPointToPredict()[0] + ","
        + this.getPointToPredict()[1] + "): " + this.getPredictedValue());
  }

  public void writeFileBicubicSpline(final String filePath) {
    try {
      FileWriter output = new FileWriter(filePath);
      output.write("Solusi Predicted Value untuk f(" + this.getPointToPredict()[0] + ","
          + this.getPointToPredict()[1] + "): " + this.getPredictedValue());

      output.close();
      System.out.println("Berhasil menyimpan matriks pada " + filePath + ".");

    } catch (IOException e) {
      System.out.println("Error: Kesalahan Simpan File");
      e.printStackTrace();
    }
  }

  public Matrix getBicubicSplineXMatrix() {
    Matrix coefMatrix = new Matrix(16, 16);
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
        // f(x,y)
        case 0:
          for (i = 0; i <= 3; i++) {
            for (j = 0; j <= 3; j++) {
              col = j * 4 + i;
              a = Math.pow(x, i);
              b = Math.pow(y, j);
              insert = (a != 0 && b != 0) ? a * b : 0;
              coefMatrix.setElmt(k, col, insert);
            }
          }
          break;

        // fx(x,y)
        case 1:
          for (i = 1; i <= 3; i++) {
            for (j = 0; j <= 3; j++) {
              col = j * 4 + i;
              h = i - 1;
              a = Math.pow(x, h);
              b = Math.pow(y, j);
              insert = (a != 0 && b != 0 && i != 0) ? a * b * i : 0;
              coefMatrix.setElmt(k, col, insert);
            }
          }
          break;

        // fy(x,y)
        case 2:
          for (i = 0; i <= 3; i++) {
            for (j = 1; j <= 3; j++) {
              col = j * 4 + i;
              h = j - 1;
              a = Math.pow(x, i);
              b = Math.pow(y, h);
              insert = (a != 0 && b != 0 && j != 0) ? a * b * j : 0;
              coefMatrix.setElmt(k, col, insert);
            }
          }
          break;

        // fxy(x,y)
        default:
          for (i = 0; i <= 3; i++) {
            for (j = 0; j <= 3; j++) {
              col = j * 4 + i;
              h = j - 1;
              row = i - 1;
              a = Math.pow(x, row);
              b = Math.pow(y, h);
              insert = (a != 0 && b != 0 && i != 0 && j != 0) ? a * b * i * j : 0;
              coefMatrix.setElmt(k, col, insert);
            }
          }
          break;
      }
    }
    return coefMatrix;
  }

  public Matrix getCoefficientA(final Matrix X, final Matrix Y) {
    Matrix aCoefficient = new Matrix(1, 16);
    Matrix xInvers = X.inversGJordan();
    aCoefficient = xInvers.multiplyMatrix(xInvers, Y);
    aCoefficient = aCoefficient.transpose();
    return aCoefficient;
  }

  public void predictBicubicSplineValue() {
    double result = 0, a, b;
    int i, j = 0, k = 0;
    double x = this.getPointToPredict()[0];
    double y = this.getPointToPredict()[1];
    Matrix aCoefficient = getCoefficientA(this.getBicubicSplineXMatrix(), this.knownPoint);;
    // Sigma i,j [0..3] coef
    while (j <= 3) {
      i = 0;
      while (i <= 3) {
        a = Math.pow(x, i);
        b = Math.pow(y, j);
        result += (aCoefficient.getElmt(0, k) * a * b);
        k += 1;
        i += 1;
      }
      j += 1;
    }
    setPredictedValue(result);
  }

  public double predictBicubicSplineImage(final double x, final double y, final Matrix coef) {
    double result = 0, a, b;
    int i, j = 0, k = 0;
    // Sigma i,j [0..3] coef
    while (j <= 3) {
      i = 0;
      while (i <= 3) {
        a = Math.pow(x, i);
        b = Math.pow(y, j);
        result += (coef.getElmt(k, 0) * a * b);
        k += 1;
        i += 1;
      }
      j += 1;
    }
    setPredictedValue(result);
    return result;
  }

}
