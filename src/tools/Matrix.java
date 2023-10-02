package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import tools.types.MatrixInterface;

public class Matrix implements MatrixInterface {
  /* ***** ATRIBUTE ***** */
  private double[][] matrix; // Inisialisasi matrix
  private int rowEff; // Ukuran baris terdefinisi
  private int colEff; // Ukuran Colom terdefinisi

  // * ***** METHOD ***** */

  /* *** Konstruktor create MATRIKS *** */
  public Matrix(int nRows, int nCols) {
    // Inisialisasi matrix
    this.matrix = new double[nRows][nCols];
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
  public double getElmt(final int i, final int j) {
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
  public double getElmtDiagonal(final int i) {
    return this.getElmt(i, i);
  }

  /* Mengembalikan ukuran matrix */
  public int getSize() {
    return this.colEff * this.rowEff;
  }

  // Mengembalikan boolean pengecekan kesamaan ukuran baris dan kolom antara 2 matrix
  public static boolean isMatrixSizeEqual(final Matrix m1, final Matrix m2) {
    return m1.getRowEff() == m2.getRowEff() && m1.getColEff() == m2.getColEff();
  }

  // Mengembalikan boolean pengecekan ukuran kolom dan baris nxn
  public boolean isSquare() {
    return this.rowEff == this.colEff;
  }

  // ** SELEKTOR SETTER */

  /**
   * setElmt melakukan assign value terhadapp suatu matriks pada baris dan kolom tertentu
   */
  public void setElmt(final int i, final int j, final double value) {
    this.matrix[i][j] = value;
  }

  /* ********** Assignment Matrix ********** */
  /** Mengcopy nilai elemen suatu array matriks ke tempat lain */
  public double[][] copyElmtMatrix() {
    /* KAMUS */
    int i, j;
    final double[][] result = new double[this.rowEff][this.colEff];

    /* ALGORITMA */

    for (i = this.getFirstIdxRow(); i < this.rowEff; i++) {
      for (j = this.getFirstIdxCol(); j < this.colEff; j++) {
        result[i][j] = this.matrix[i][j];
      }
    }
    return result;
  }

  /** Mencopy matriks ke matriks lain */
  public Matrix copyMatrix() {
    /* KAMUS */
    final Matrix mCopy = new Matrix(this.rowEff, this.colEff);
    /* ALGORITMA */
    mCopy.matrix = this.copyElmtMatrix();
    mCopy.rowEff = this.rowEff;
    mCopy.colEff = this.colEff;

    return mCopy;
  }

  // ** BACA TULIS METHOD */

  /**
   * readMatrix I.S. Membaca matrix pada layar F.S. Matrix terdefinisi nilai elemen efektifnya,
   * berukuran nxm
   */
  public void readMatrix() {
    int i, j;
    Scanner input = new Scanner(System.in);
    System.out.print("Masukkan banyak baris matriks : ");
    int nRows = input.nextInt();
    System.out.print("Masukkan banyak kolom matriks : ");
    int nCols = input.nextInt();
    Matrix result = new Matrix(nRows, nCols);

    System.out.println("Masukkan elemen matriks :");
    for (i = 0; i < result.rowEff; i++) {
      for (j = 0; j < result.colEff; j++) {
        result.matrix[i][j] = input.nextDouble();
      }
    }

    this.matrix = result.copyElmtMatrix();
    this.rowEff = result.rowEff;
    this.colEff = result.colEff;


  }


  /**
   * readMatrix dari File Txt I.S. File txt berisi Array Matriks F.S. Terbaca Matriks dan disimpan
   * dalam variabel
   */
  public void readFileMatrix(final String filePath) {
    try {
      /* KAMUS */

      File file = new File(filePath);
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
      while (line.hasNextDouble()) {
        Ncol++;
        line.nextDouble();
      }
      line.close();
      matrix.close();
      // Reopen the file for reading
      matrix = new Scanner(file);

      // Inisialisasi matrix temp
      double[][] tempMatrix = new double[Nrow][Ncol];

      // Read and populate the matrix
      for (i = 0; i < Nrow; i++) {
        for (j = 0; j < Ncol; j++) {
          if (matrix.hasNextDouble()) {
            tempMatrix[i][j] = matrix.nextDouble();
          }
        }
      }
      matrix.close();

      // Update the matrix and dimensions
      this.matrix = tempMatrix;
      this.rowEff = Nrow;
      this.colEff = Ncol;
    } catch (FileNotFoundException e) {
      System.err.printf("Error: File \"%s\" tidak ditemukan\n", filePath);
    }
  }

  /**
   * displayMatrix I.S. Matriks terdefinisi dan memiliki nilai F.S. Menampilkan matriks pada layar
   */
  public void displayMatrix() {
    int i, j;
    for (i = this.getFirstIdxRow(); i < this.getRowEff(); i++) {
      for (j = this.getFirstIdxCol(); j < this.getColEff(); j++) {
        if (j == this.getLastIdxCol()) {
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
  public void writeFileMatrix(final String filePath) {
    try {
      /* KAMUS */
      FileWriter output = new FileWriter(filePath);
      int i, j;

      for (i = this.getFirstIdxRow(); i < this.getRowEff(); i++) {
        for (j = this.getFirstIdxCol(); j < this.getColEff(); j++) {
          // Convert the double value to String and write it
          output.write(Double.toString(this.matrix[i][j]));

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
      System.out.println("Berhasil menyimpan matriks pada" + filePath + "\".");

    } catch (IOException e) {
      System.out.println("Error: Kesalahan Simpan File");
      e.printStackTrace();
    }
  }

  public void writeFileDeterminantCofactor(final String filePath) {
    try {
      FileWriter output = new FileWriter(filePath);
      int i, j;
      output.write("Determinan dari matriks berikut");
      output.write(System.lineSeparator());

      // Tulis matriksnya
      for (i = this.getFirstIdxRow(); i < this.getRowEff(); i++) {
        for (j = this.getFirstIdxCol(); j < this.getColEff(); j++) {
          // Convert nilai double ke String dan tulis
          output.write(Double.toString(this.matrix[i][j]));

          // Tambahkan spasi sebagai pemisah
          if (j < this.getColEff() - 1) {
            output.write(" ");
          }
        }
        // Tambahkan baris baru setiap baris kecuali baris terakhir
        if (i < this.getLastIdxRow()) {
          output.write(System.lineSeparator());
        }
      }

      output.write(System.lineSeparator());
      if (this.isSquare()) {
        output.write("adalah " + this.determinantCofactor());
      } else {
        output.write("adalah undefined karena bukan matrix persegi.");
      }
      System.out.println("Berhasil menyimpan hasil determinan pada " + filePath + ".");
      output.close();
    } catch (IOException e) {
      System.out.println("Error: Kesalahan Simpan File");
      e.printStackTrace();
    }
  }


  public void writeFileDeterminantUpperTriangle(final String filePath) {
    try {
      /* KAMUS */
      FileWriter output = new FileWriter(filePath);
      int i, j;

      output.write("Determinan dari matriks berikut");
      output.write(System.lineSeparator());
      // Nulis matriksnya
      for (i = this.getFirstIdxRow(); i < this.getRowEff(); i++) {
        for (j = this.getFirstIdxCol(); j < this.getColEff(); j++) {
          // Convert the double value to String and write it
          output.write(Double.toString(this.matrix[i][j]));

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
      output.write(System.lineSeparator());
      if (this.isSquare()) {
        output.write("adalah " + this.determinantCofactor());
      } else {
        output.write("adalah undefined karena bukan matrix persegi.");
      }
      System.out.println(
          "Berhasil menyimpan hasil determinan pada folder test, file \"" + filePath + ".");
      output.close();

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
  public Matrix addMatrix(final Matrix m1, final Matrix m2) {
    // KAMUS
    // ALGORITMA
    if (!isMatrixSizeEqual(m1, m2)) {
      System.out.println("Ukuran matriks tidak sama.");
      return this;
    }

    int i, j;
    Matrix result = new Matrix(m1.getRowEff(), m1.getColEff());
    for (i = m1.getFirstIdxRow(); i < m1.getRowEff(); i++) {
      for (j = m1.getFirstIdxCol(); j < m1.getColEff(); j++) {
        result.setElmt(i, j, m1.getElmt(i, j) + m2.getElmt(i, j));
      }
    }

    return result;
  }

  /**
   * substractMatrix Mengurangkan dua matriks dan mengembalikan hasil pengurangannya.
   */
  public Matrix subtractMatrix(final Matrix m1, final Matrix m2) {
    // ALGORITMA
    if (!isMatrixSizeEqual(m1, m2)) {
      System.out.println("Ukuran matriks tidak sama.");
      return this;
    }

    // KAMUS
    int i, j;
    Matrix result = new Matrix(m1.getRowEff(), m1.getColEff());

    for (i = m1.getFirstIdxRow(); i < m1.getRowEff(); i++) {
      for (j = m1.getFirstIdxCol(); j < m1.getColEff(); j++) {
        result.setElmt(i, j, m1.getElmt(i, j) - m2.getElmt(i, j));
      }
    }

    return result;
  }

  /**
   * multiplyMatrix Mengalikan dua matriks dan mengembalikan hasil perkaliannya.
   */
  public Matrix multiplyMatrix(final Matrix m1, final Matrix m2) {

    // Periksa apakah kedua matriks dapat dikalikan
    if (m1.getColEff() != m2.getRowEff()) {
      System.out.println("Kedua matriks tidak dapat dikalikan.");
      return this;
    }

    // Buat matriks hasil dengan ukuran yang sesuai
    int i, j, k;
    Matrix result = new Matrix(m1.getRowEff(), m2.getColEff());

    // Lakukan perkalian matriks
    for (i = 0; i < m1.getRowEff(); i++) {
      for (j = 0; j < m2.getColEff(); j++) {
        for (k = 0; k < m1.getColEff(); k++) {
          result.setElmt(i, j, (result.getElmt(i, j) + (m1.getElmt(i, k) * m2.getElmt(k, j))));
        }
      }
    }

    return result;
  }

  /**
   * multiplyByConst I.S Matriks terdefinisi dan memiliki nilai F.S Mengalikan seluruh elemen
   */
  public void multiplyByConst(final double k) {
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
    Matrix result = new Matrix(this.getColEff(), this.getRowEff());

    // ALGORITMA

    for (i = this.getFirstIdxRow(); i < this.getRowEff(); i++) {
      for (j = this.getFirstIdxCol(); j < this.getColEff(); j++) {
        result.setElmt(j, i, this.getElmt(i, j));
      }
    }

    return result;
  }


  public Matrix createIdentityMatrix(int n) {
    if (n <= 0) {
      System.out.println("Size matriks harus lebih dari 0");
    }
    Matrix identityMatrix = new Matrix(n, n);
    int i;
    for (i = 0; i < n; i++) {
      identityMatrix.setElmt(i, i, 1.0d);
    }

    return identityMatrix;
  }


  public void OBEPlusRow(final int idxRowOrigin, final int idxRowTarget, final double factor) {
    // Diperuntukkan operasi antara 2 baris
    int i;
    double value;
    for (i = this.getFirstIdxCol(); i <= this.getLastIdxCol(); i++) {
      value = this.getElmt(idxRowTarget, i) + (factor * this.getElmt(idxRowOrigin, i));
      if (value == -0.0f) {
        value = 0.0f;
      }
      this.setElmt(idxRowTarget, i, value);
    }
  }

  public void OBESwapRow(final int idxRowOrigin, final int idxRowTarget) {
    // Diperuntukkan operasi pertukaran elemen antara 2 baris
    int i;
    double temp;
    for (i = this.getFirstIdxCol(); i <= this.getLastIdxCol(); i++) {
      temp = this.getElmt(idxRowOrigin, i);
      this.setElmt(idxRowOrigin, i, this.getElmt(idxRowTarget, i));
      this.setElmt(idxRowTarget, i, temp);
    }
  }

  public void OBEDivisionFactor(final int i, final double factor) {
    /* Membagi baris i dengan konstanta factor untuk membuat 1 utama */
    int j;
    double value;
    for (j = this.getFirstIdxCol(); j <= this.getLastIdxCol(); j++) {
      value = (this.getElmt(i, j) / factor);
      if (value == -0.0f) {
        value = 0.0f;
      }
      this.setElmt(i, j, value);
    }
  }

  public void gaussElimination() {
    int j, k;
    double factor;
    int i = this.getFirstIdxRow();
    // Iterasi kolom dengan for loop, tapi iterasi baris disesuaikan dengan case
    for (j =
        this.getFirstIdxCol(); ((i <= this.getLastIdxRow()) && (j < this.getLastIdxCol())); j++) {
      boolean isOBEProcess = true; // Untuk Proses OBEPlusRow
      boolean isAllZero = false; // untuk pergeseran i jika tidak semua elemen baris 0

      // dilakukan pengecekan per baris
      if (this.getElmt(i, j) == 0) {
        k = i + 1;
        boolean found = false;
        while (!found && k <= this.getLastIdxRow()) {
          // lakukan perulangan sampai ditemukan elemen kolom j yang != 0
          if (this.getElmt(k, j) != 0) {
            found = true;
            this.OBESwapRow(i, k);
          } else {
            k += 1;
          }
        }
        // Jika ridak ditemukan elemen !=0 pada suatu baris
        if (!found) {
          isOBEProcess = false;
          isAllZero = true;
        }
      }

      if (isOBEProcess) {
        this.OBEDivisionFactor(i, this.getElmt(i, j));
        // Making elemen dibawah ini harus semuanya 0;
        for (k = i + 1; k <= this.getLastIdxRow(); k++) {

          factor = -(this.getElmt(k, j) / this.getElmt(i, j));
          this.OBEPlusRow(i, k, factor);
        }
      }
      // Penggeseran pengecekan baris
      if (!isAllZero) {
        i += 1;
      }
    }
  }


  public void gJordanElimination() {
    int j, k;
    double factor;
    int i = this.getFirstIdxRow();
    // Iterasi kolom dengan for loop, tapi iterasi baris disesuaikan dengan case
    for (j =
        this.getFirstIdxCol(); ((i <= this.getLastIdxRow()) && (j < this.getLastIdxCol())); j++) {
      boolean isOBEProcess = true; // Untuk Proses OBEPlusRow
      boolean isAllZero = false; // untuk pergeseran i jika tidak semua elemen baris 0

      // dilakukan pengecekan per baris
      if (this.getElmt(i, j) == 0) {
        k = i + 1;
        boolean found = false;
        while (!found && k <= this.getLastIdxRow()) {
          // lakukan perulangan sampai ditemukan elemen kolom j yang != 0
          if (this.getElmt(k, j) != 0) {
            found = true;
            this.OBESwapRow(i, k);
          } else {
            k += 1;
          }
        }
        // Jika ridak ditemukan elemen !=0 pada suatu baris
        if (!found) {
          isOBEProcess = false;
          isAllZero = true;
        }
      }

      if (isOBEProcess) {
        this.OBEDivisionFactor(i, this.getElmt(i, j));
        // Making elemen dibawah ini harus semuanya 0;
        for (k = this.getFirstIdxRow(); k <= this.getLastIdxRow(); k++) {
          if (k != i) {
            factor = -(this.getElmt(k, j) / this.getElmt(i, j));
            this.OBEPlusRow(i, k, factor);
          }
        }
      }
      // Penggeseran pengecekan baris
      if (!isAllZero) {
        i += 1;
      }
    }

  }

  public double determinantCofactor() {
    if (!this.isSquare()) {
      System.out.println("Matrix is not square. Determinant is undefined.");
      return Double.NaN;
    }
    Matrix subMatrix;
    int i, j, k, rowSub;
    if (this.getSize() == 1) {
      return this.getElmt(0, 0);
    }

    double determinan = 0.0f;

    for (i = this.getFirstIdxRow(); i <= this.getLastIdxRow(); i++) {
      // Assign submatrix
      subMatrix = new Matrix(this.getRowEff() - 1, this.getColEff() - 1);

      // Fill the subMatrix with the corresponding elements
      for (j = 1; j <= this.getLastIdxCol(); j++) {
        for (k = this.getFirstIdxRow(), rowSub = 0; k <= this.getLastIdxRow(); k++) {
          if (k != i) {
            subMatrix.setElmt(rowSub, j - 1, this.getElmt(k, j));
            rowSub++;
          }
        }
      }

      double cofactor = this.getElmt(i, 0) * subMatrix.determinantCofactor();
      if (i % 2 == 1) {
        cofactor = -cofactor;
      }

      determinan += cofactor;
    }

    return determinan;
  }

  public double determinantUpperTriangle() {
    if (!this.isSquare()) {
      System.out.println("Matrix is not square. Determinant is undefined.");
      return Double.NaN;
    }
    double det = 1.0d;
    int i, j;
    double factor;
    Matrix mCopy = this.copyMatrix(); // Buat copy matrix

    for (i = mCopy.getFirstIdxRow(); i <= mCopy.getLastIdxRow(); i++) {
      // Apakah ada elemen diagonal bernilai 0
      if (mCopy.getElmtDiagonal(i) == 0) {
        boolean found = false;
        j = i + 1;

        // Swap jika ada elemen dibawah diagonal yang tidak nol
        while (j <= mCopy.getLastIdxRow() && !found) {
          if (mCopy.getElmt(i, j) != 0) {
            mCopy.OBESwapRow(j, i);
            det *= -1.0d;
            found = true;
          } else {
            j += 1;
          }
        }

        // determinan bernilai 0 jika ada baris yang elemennya semua 0
        if (!found) {
          return 0.0f;
        }
      }
      // Make elements below the diagonal zero
      for (j = i + 1; j <= mCopy.getLastIdxRow(); j++) {
        if (mCopy.getElmt(j, i) != 0) {
          factor = -(mCopy.getElmt(j, i) / mCopy.getElmt(i, i));
          mCopy.OBEPlusRow(i, j, factor);

        }
      }
      det *= mCopy.getElmt(i, i); // Multiply by the diagonal element
    }
    return det;
  }

  public Matrix cofactor() {
    int i, j, k, l, rowSub, colSub;
    Matrix mCofactor = new Matrix(this.getRowEff(), this.getColEff());
    Matrix mSub = new Matrix(this.getRowEff() - 1, this.getColEff() - 1);
    // Looping big matrix
    for (i = 0; i <= this.getLastIdxRow(); i++) {
      for (j = 0; j <= this.getLastIdxCol(); j++) {


        // Set Submatrix
        for (k = 0, rowSub = 0; k <= mSub.getLastIdxRow(); k++) {
          if (rowSub == i) {
            rowSub += 1;
          }
          for (l = 0, colSub = 0; l <= mSub.getLastIdxCol(); l++) {
            if (colSub == j) {
              colSub += 1;
            }

            mSub.setElmt(k, l, this.getElmt(rowSub, colSub));
            colSub += 1;

          }
          rowSub += 1;
        }

        // Assign value ke mCofactor
        if ((i + j) % 2 == 0) {
          mCofactor.setElmt(i, j, mSub.determinantCofactor());
        } else {
          mCofactor.setElmt(i, j, -1.0d * mSub.determinantCofactor());

        }
      }
    }

    return mCofactor;
  }

  public Matrix adjoin() {
    Matrix mAdj = this.cofactor().transpose();
    return mAdj;
  }

  public Matrix inversAdjoin() {
    Matrix mInvers = this.adjoin();
    double determinan = this.determinantUpperTriangle();
    if (determinan == 0) {
      System.out.println("Natrix tidak memiliki invers karena nilai determinan = 0.");
    } else {
      mInvers.multiplyByConst(1.0d / determinan);
    }
    return mInvers;
  }

  public Matrix inversGJordan() {
    Matrix mTemp = new Matrix(this.getRowEff(), this.getColEff() * 2);
    Matrix mIdentity = this.createIdentityMatrix(this.getRowEff());
    Matrix mInvers = new Matrix(this.getRowEff(), this.getColEff());
    int i, j;

    // Merge identity with mTemp
    double determinant = this.determinantUpperTriangle();

    if (determinant == 0) {
      System.out.println("Matrix does not have an invers because the determinant is 0.");
    } else {
      for (i = 0; i < this.getRowEff(); i++) {
        for (j = 0; j < this.getColEff(); j++) {
          mTemp.setElmt(i, j, this.getElmt(i, j));
          mTemp.setElmt(i, j + this.getColEff(), mIdentity.getElmt(i, j));
        }
      }
      mTemp.gJordanElimination();

      for (i = 0; i < mTemp.getRowEff(); i++) {
        for (j = this.getColEff(); j < mTemp.getColEff(); j++) {
          mInvers.setElmt(i, j - this.getColEff(), mTemp.getElmt(i, j));
        }
      }
    }

    return mInvers;
  }

}
