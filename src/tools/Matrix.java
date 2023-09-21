package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Matrix {
  /* ***** ATRIBUTE ***** */
  private float[][] matrix; // Inisialisasi matrix
  private int rowEff; // Ukuran baris terdefinisi
  private int colEff; // Ukuran kolom terdefinisi

  // * ***** METHOD ***** */

  /* *** Konstruktor create MATRIKS *** */
  public Matrix(int nRows, int nCols) {
    // Inisialisasi matrix
    this.matrix = new float[nRows][nCols];
    this.rowEff = nRows;
    this.colEff = nCols;
  }

  // ** SELEKTOR GETTER */

  // Mengembalikan eksplisit baris terdefinisi
  public int getRowEff() {
    return this.rowEff;
  }

  // Mengembalikan eksplisit kolom terdefinisi
  public int getColEff() {
    return this.colEff;
  }

  // Mengembalikan elemen matrix colom dan baris tertentu
  public float getElmt(final int i, final int j) {
    return this.matrix[i][j];
  }

  /* Mengembalikan indeks baris terakhir */
  public int getLastIdxRow() {
    return this.rowEff - 1;
  }

  /* Mengembalikan indeks kolom terakhir */
  public int getLastIdxCol() {
    return this.colEff - 1;
  }

  /* Mengembalikan indeks baris pertama */
  public int getFirstIdxRow() {
    return 0;
  }

  /* Mengembalikan indeks kolom pertama */
  public int getFirstIdxCol() {
    return 0;
  }

  /* Mengembalikan elemen diagonal! */
  public float getElmtDiagonal(final int i) {
    return isIdxEff(i, i) ? this.getElmt(i, i) : -1f;
  }

  /* Mengembalikan ukuran matrix */
  public int getSize() {
    return this.colEff * this.rowEff;
  }

  // ** VALIDATION **//
  // Mengembalikan boolean pengecekan elemen matriks apakah sama antara 2 matrix
  public static boolean isMatrixEqual(final Matrix m1, final Matrix m2) {
    // KAMUS
    int i, j;

    // ALGORITMA
    // Ukuran harus sama
    if (isMatrixSizeEqual(m1, m2)) {
      return false;
    }

    // Checking similaritas elemen
    for (i = m1.getFirstIdxRow(); i < m1.getRowEff(); i++) {
      for (j = m1.getFirstIdxCol(); j < m1.getColEff(); j++) {
        if (m1.getElmt(i, j) != m2.getElmt(i, j)) {
          return false;
        }
      }
    }

    return true;
  }

  // Mengembalikan boolean pengecekan kesamaan ukuran baris dan kolom antara 2 matrix
  public static boolean isMatrixSizeEqual(final Matrix m1, final Matrix m2) {
    return m1.getRowEff() == m2.getRowEff() && m1.getColEff() == m2.getColEff();
  }

  // Mengembalikan boolean pengecekan ukuran kolom dan baris nxn
  public boolean isSquare() {
    return this.rowEff == this.colEff;
  }

  // Mengembalikan boolean pengecekan kesamaan elemen berseberangan suatu matrix
  public boolean isSymmetric() {
    // KAMUS
    int i, j;

    // ALGORITMA
    if (!isSquare()) {
      return false;
    }

    for (i = this.getFirstIdxRow(); i < this.rowEff; i++) {
      for (j = this.getFirstIdxCol(); j < this.colEff; j++) {
        if (matrix[i][j] != matrix[j][i]) {
          return false;
        }
      }
    }

    return true;
  }

  // Mengembalikan boolean pengecekan semua elemen biner 1 atau 0
  public boolean isIdentity() {
    // KAMUS
    int i, j;

    // ALGORITMA
    if (!isSquare()) {
      return false;
    }

    for (i = 0; i < rowEff; i++) {
      for (j = 0; j < colEff; j++) {
        if (i == j && matrix[i][j] != 1) {
          return false;
        } else if (i != j && matrix[i][j] != 0) {
          return false;
        }
      }
    }

    return true;
  }


  // ** SELEKTOR SETTER */

  /**
   * setElmt melakukan assign value terhadapp suatu matriks pada baris dan kolom tertentu
   */
  public void setElmt(final int i, final int j, final float value) {
    this.matrix[i][j] = value;
  }

  // Memvalidasi apakah index yang diinputkan valid pada suatu matrix
  public boolean isMatrixIdxValid(final int i, final int j) {
    return i >= 0 && i < matrix.length && j >= 0 && j < matrix[0].length;
  }

  /* ********** Assignment Matrix ********** */
  /** Mengcopy nilai elemen suatu array matriks ke tempat lain */
  public float[][] copyElmtMatrix() {
    /* KAMUS */
    int i, j;
    final float[][] result = new float[this.rowEff][this.colEff];

    /* ALGORITMA */

    for (i = this.getFirstIdxRow(); i < this.rowEff; i++) {
      for (j = this.getFirstIdxCol(); j < this.colEff; j++) {
        result[i][j] = this.matrix[i][j];
      }
    }
    return result;
  }

  /** Mencopy matriks ke matriks lain */
  Matrix copyMatrix() {
    /* KAMUS */
    final Matrix mCopy = new Matrix(this.rowEff, this.colEff);
    /* ALGORITMA */
    mCopy.matrix = this.copyElmtMatrix();
    mCopy.rowEff = this.rowEff;
    mCopy.colEff = this.colEff;

    return mCopy;
  }

  // Memvalidasi apakah index yang diinputkan valid pada suatu matrix
  public boolean isIdxEff(int i, int j) {
    return i >= 0 && j >= 0 && i < rowEff && j < colEff;
  }

  // ** BACA TULIS METHOD */

  /**
   * readMatrix I.S. Membaca matrix pada layar F.S. Matrix terdefinisi nilai elemen efektifnya,
   * berukuran nxm
   */
  public void readMatrix() {
    int i, j;
    Scanner input = null;
    try {
      input = new Scanner(System.in);
      System.out.print("Masukkan banyak baris matriks : ");
      int nRows = input.nextInt();
      System.out.print("Masukkan banyak kolom matriks : ");
      int nCols = input.nextInt();
      Matrix result = new Matrix(nRows, nCols);

      System.out.println("Masukkan elemen matriks :");
      for (i = 0; i < result.rowEff; i++) {
        for (j = 0; j < result.colEff; j++) {
          result.matrix[i][j] = input.nextFloat();
        }
      }

      this.matrix = result.copyElmtMatrix();
      this.rowEff = result.rowEff;
      this.colEff = result.colEff;


    } catch (Exception e) {
      System.err.println("Error saat readMatrix");
    }
  }


  /**
   * readMatrix dari File Txt I.S. File txt berisi Array Matriks F.S. Terbaca Matriks dan disimpan
   * dalam variabel
   */
  public void readFileMatrix(final String fileName) {
    try {
      /* KAMUS */
      String directory = "./src/data/" + fileName;
      File file = new File(directory);
      int Nrow = 0;
      int Ncol = 0;
      int i, j; // Indeks

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
      while (line.hasNextFloat()) {
        Ncol++;
        line.nextFloat();
      }
      line.close();
      matrix.close();
      // Reopen the file for reading
      matrix = new Scanner(file);

      // Inisialisasi matrix temp
      float[][] tempMatrix = new float[Nrow][Ncol];

      // Read and populate the matrix
      for (i = 0; i < Nrow; i++) {
        for (j = 0; j < Ncol; j++) {
          if (matrix.hasNextFloat()) {
            tempMatrix[i][j] = matrix.nextFloat();
          }
        }
      }
      matrix.close();

      // Update the matrix and dimensions
      this.matrix = tempMatrix;
      this.rowEff = Nrow;
      this.colEff = Ncol;
    } catch (FileNotFoundException e) {
      System.err.printf("Error: File \"%s\" tidak ditemukan\n", fileName);
    }
  }


  /**
   * displayMatrix I.S. Matriks terdefinisi dan memiliki nilai F.S. Menampilkan matriks pada layar
   */
  public void displayMatrix() {
    int i, j;
    for (i = this.getFirstIdxRow(); i < this.getRowEff(); i++) {
      for (j = this.getFirstIdxCol(); j < this.getColEff(); j++) {
        if (j == this.getColEff() - 1) {
          System.out.println(matrix[i][j]);
        } else {
          System.out.print(matrix[i][j] + " ");
        }
      }
    }
  }

  /**
   * writeMatrix I.S. Matriks terdefinisi dan memiliki nilai F.S. Menyimpan matriks pada suatu file
   */
  public void writeFileMatrix(final String fileName) {
    try {
      /* KAMUS */
      String directory = "./test/" + fileName;
      FileWriter output = new FileWriter(directory);
      int i, j;

      for (i = this.getFirstIdxRow(); i < this.getRowEff(); i++) {
        for (j = this.getFirstIdxCol(); j < this.getColEff(); j++) {
          // Convert the float value to String and write it
          output.write(Float.toString(this.matrix[i][j]));

          // Tambahkan spasi sebagai separator
          if (j < this.colEff - 1) {
            output.write(" ");
          }
        }
        // tambahkan line baru tiap baris kecuali baris terakhir
        if (i < this.getLastIdxRow()) {
          output.write(System.lineSeparator());
        }

      }
      output.close();
      System.out.println("Berhasil menyimpan matriks pada folder test, file \"" + fileName + "\".");

    } catch (IOException e) {
      System.out.println("Error: Kesalahan Simpan File");
      e.printStackTrace();
    }
  }

  // ** OPERATION **//
  /**
   * addMatrix I.S Matriks terdefinisi dan memiliki nilai F.S Menambahkan dua matriks dan
   * mengembalikan hasil penjumlahannya.
   */
  public static Matrix addMatrix(final Matrix m1, final Matrix m2) {
    // KAMUS
    int i, j;
    int nRows = m1.getRowEff();
    int nCols = m1.getColEff();
    Matrix result = new Matrix(nRows, nCols);

    // ALGORITMA
    if (!isMatrixSizeEqual(m1, m2)) {
      throw new IllegalArgumentException("Ukuran matriks tidak sama.");
    }


    for (i = m1.getFirstIdxRow(); i < nRows; i++) {
      for (j = m1.getFirstIdxCol(); j < nCols; j++) {
        result.setElmt(i, j, m1.getElmt(i, j) + m2.getElmt(i, j));
      }
    }

    return result;
  }

  /**
   * substractMatrix Mengurangkan dua matriks dan mengembalikan hasil pengurangannya.
   */
  public static Matrix subtractMatrix(final Matrix m1, final Matrix m2) {
    // KAMUS
    int i, j;
    int nRows = m1.getRowEff();
    int nCols = m1.getColEff();
    Matrix result = new Matrix(nRows, nCols);

    // ALGORITMA
    if (!isMatrixSizeEqual(m1, m2)) {
      throw new IllegalArgumentException("Ukuran matriks tidak sama.");
    }


    for (i = m1.getFirstIdxRow(); i < nRows; i++) {
      for (j = m1.getFirstIdxCol(); j < nCols; j++) {
        result.setElmt(i, j, m1.getElmt(i, j) - m2.getElmt(i, j));
      }
    }

    return result;
  }

  /**
   * multiplyMatrix Mengalikan dua matriks dan mengembalikan hasil perkaliannya.
   */
  public static Matrix multiplyMatrix(final Matrix m1, final Matrix m2) {
    // KAMUS
    int i, j, k;
    int nRows = m1.getRowEff();
    int nCols = m2.getColEff();
    Matrix result = new Matrix(nRows, nCols);

    // ALGORITMA
    if (!isMatrixSizeEqual(m1, m2)) {
      throw new IllegalArgumentException("Dimensi matriks tidak memungkinkan perkalian.");
    }

    for (i = m1.getFirstIdxRow(); i < nRows; i++) {
      for (j = m1.getFirstIdxCol(); j < nCols; j++) {
        float sum = 0;
        for (k = 0; k < nCols; k++) {
          sum += m1.getElmt(i, k) * m2.getElmt(k, j);
        }
        result.setElmt(i, j, sum);
      }
    }

    return result;
  }

  /**
   * multiplyByConst I.S Matriks terdefinisi dan memiliki nilai F.S Mengalikan seluruh elemen
   */
  public void multiplyByConst(final int k) {
    // KAMUS
    int i, j;

    // ALGORITMA
    for (i = this.getFirstIdxRow(); i < this.getRowEff(); i++) {
      for (j = this.getFirstIdxCol(); j < this.getColEff(); j++) {
        this.matrix[i][j] *= k;
      }
    }
  }

  /**
   * negation I.S Matriks terdefinisi dan memiliki nilai F.S Mengubah semua nilai elemen matriks
   * menjadi negatif.
   */
  public void negation() {
    this.multiplyByConst(-1);
  }

  /**
   * tranapose Mengembalikan matriks transpose dari matriks saat ini.
   */
  public Matrix transpose() {
    // KAMUS
    int i, j;
    int nCols = this.colEff;
    int nRows = this.rowEff;
    Matrix result = new Matrix(nRows, nCols);

    // ALGORITMA
    if (this.isSquare()) {
      throw new IllegalArgumentException("Dimensi matriks tidak memungkinkan perkalian.");
    }

    for (i = this.getFirstIdxRow(); i < nRows; i++) {
      for (j = this.getFirstIdxCol(); j < nCols; j++) {
        result.setElmt(j, i, this.getElmt(i, j));
      }
    }

    return result;
  }



}
