package tools.types;

import java.util.Scanner;
import tools.Matrix;

public interface BicubicSplineInterface {
  /**
   * Mengatur nilai yang diprediksi.
   *
   * @param predictedValue Nilai yang akan diatur.
   */
  void setPredictedValue(double predictedValue);

  /**
   * Mengatur titik yang akan diprediksi.
   *
   * @param x Koordinat x dari titik.
   * @param y Koordinat y dari titik.
   */
  void setPointToPredict(double x, double y);

  /**
   * Mengatur matriks titik-titik yang diketahui.
   *
   * @param knownPoint Matriks yang berisi titik-titik yang diketahui.
   */
  void setKnownPoint(Matrix knownPoint);

  /**
   * Mendapatkan nilai yang diprediksi.
   *
   * @return Nilai yang diprediksi.
   */
  double getPredictedValue();

  /**
   * Mendapatkan titik yang akan diprediksi sebagai array [x, y].
   *
   * @return Titik yang akan diprediksi.
   */
  double[] getPointToPredict();

  /**
   * Mendapatkan matriks dari titik-titik yang diketahui.
   *
   * @return Matriks dari titik-titik yang diketahui.
   */
  Matrix getKnownPoint();

  /**
   * Membaca data masukan untuk perhitungan Bicubic Spline. Metode ini menggunakan masukan dari
   * pengguna untuk mengisi titik-titik yang diketahui dan titik yang akan diprediksi.
   */
  void readBicubicSpline(Scanner globalScanner);

  /**
   * Membaca data masukan dari file untuk perhitungan Bicubic Spline.
   *
   * @param filePath Jalur ke file yang berisi data masukan.
   */
  void readFileBicubicSpline(String filePath);

  /**
   * Menampilkan hasil perhitungan Bicubic Spline. Metode ini mencetak nilai yang diprediksi untuk
   * titik yang diberikan.
   */
  void displayBicubicSpline();

  /**
   * Menulis hasil perhitungan Bicubic Spline ke file.
   *
   * @param filePath Jalur ke file untuk menulis hasil.
   */
  void writeFileBicubicSpline(String filePath);

  /**
   * Menghitung dan mengembalikan matriks X yang digunakan dalam perhitungan Bicubic Spline.
   *
   * @return Matriks X.
   */
  Matrix getBicubicSplineXMatrix();

  /**
   * Menghitung dan mengembalikan matriks koefisien A yang digunakan dalam perhitungan Bicubic
   * Spline.
   *
   * @param X Matriks X.
   * @param Y Matriks nilai yang diketahui.
   * @return Matriks koefisien A.
   */
  Matrix getCoefficientA(Matrix X, Matrix Y);

  /**
   * Memprediksi nilai untuk titik yang diberikan menggunakan interpolasi Bicubic Spline.
   *
   * @param x Koordinat x dari titik yang akan diprediksi.
   * @param y Koordinat y dari titik yang akan diprediksi.
   * @param knownPoint Matriks dari titik-titik yang diketahui.
   * @return Nilai yang diprediksi pada titik (x, y) yang diberikan.
   */
  void predictBicubicSplineValue();

  double predictBicubicSplineImage(double x, double y, Matrix knownPoint);
}
