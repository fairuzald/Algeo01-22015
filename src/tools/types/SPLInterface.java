package tools.types;

import tools.Matrix;

public interface SPLInterface {
  // Metode untuk membaca SPL
  // Input: Tidak ada input khusus
  // Output: SPL akan dibaca dari input pengguna atau file, tergantung implementasi.
  // Contoh Kasus:
  // - Meminta pengguna untuk memasukkan SPL dari keyboard.
  // - Membaca SPL dari file yang ditentukan pengguna.
  void readSPL();

  // Metode untuk membaca SPL dari file
  // Input: Nama file yang berisi SPL
  // Output: SPL akan dibaca dari file yang ditentukan.
  // Contoh Kasus:
  // - Membaca SPL dari file dengan nama "input.txt".
  void readFileSPL(String fileName);

  // Metode untuk menampilkan SPL
  // Input: Tidak ada input khusus
  // Output: Menampilkan SPL ke layar atau output yang ditentukan.
  // Contoh Kasus:
  // - Menampilkan SPL ke layar.
  void displaySPL();

  // Metode untuk menyimpan SPL ke file
  // Input: Nama file tujuan untuk menyimpan SPL
  // Output: SPL akan disimpan ke file yang ditentukan.
  // Contoh Kasus:
  // - Menyimpan SPL ke dalam file dengan nama "output.txt".
  void writeFileSPL(String filePath);

  // Metode untuk memeriksa apakah semua koefisien matriks diagonal satu
  // Input: Tidak ada input khusus
  // Output: Mengembalikan true jika semua koefisien matriks diagonal adalah satu, sebaliknya false.
  // Contoh Kasus:
  // - Memeriksa apakah semua koefisien matriks diagonal adalah satu.
  boolean isAllCoefMatrixDiagonalOne();

  // Metode untuk memeriksa apakah semua koefisien dalam baris matriks nol
  // Input: Nomor baris yang akan diperiksa
  // Output: Mengembalikan true jika semua koefisien dalam baris matriks adalah nol, sebaliknya
  // false.
  // Contoh Kasus:
  // - Memeriksa apakah semua koefisien dalam baris matriks ke-3 adalah nol.
  boolean isAllRowCoefMatrixZero(int row);

  // Metode untuk memeriksa apakah vektor baris adalah nol
  // Input: Nomor baris yang akan diperiksa
  // Output: Mengembalikan true jika vektor baris adalah nol, sebaliknya false.
  // Contoh Kasus:
  // - Memeriksa apakah vektor baris ke-2 adalah nol.
  boolean isRowVectorConstantZero(int row);

  // Metode untuk menentukan jenis solusi SPL
  // Input: Tidak ada input khusus
  // Output: Mengembalikan kategori jenis solusi (UNIQUE, PARAMETRIX, atau UNDEFINED).
  // Contoh Kasus:
  // - Menentukan jenis solusi SPL berdasarkan matriks koefisien.
  categorySolution solutionType();

  // Metode untuk menyelesaikan SPL dengan metode Gauss
  // Input: Tidak ada input khusus
  // Output: Menentukan solusi SPL dan mengisi array Solution dan Equation.
  // Contoh Kasus:
  // - Menyelesaikan SPL dengan metode Gauss dan mengisi solusi.
  void gaussMethodSPL();

  // Metode untuk menginisialisasi status solusi
  // Input: Tidak ada input khusus
  // Output: Menginisialisasi status solusi ke dalam array Status.
  // Contoh Kasus:
  // - Menginisialisasi status solusi SPL.
  void initializationStatus();

  // Metode untuk menemukan solusi SPL menggunakan metode Gauss
  // Input: Tidak ada input khusus
  // Output: Menentukan solusi SPL dan mengisi array Solution dan Equation.
  // Contoh Kasus:
  // - Menentukan solusi SPL dengan metode Gauss dan mengisi solusi.
  void gaussSolution();

  // Metode untuk menyelesaikan SPL dengan metode Gauss-Jordan
  // Input: Tidak ada input khusus
  // Output: Menentukan solusi SPL dan mengisi array Solution dan Equation.
  // Contoh Kasus:
  // - Menyelesaikan SPL dengan metode Gauss-Jordan dan mengisi solusi.
  void gJordanMethodSPL();

  // Metode untuk mendapatkan matriks koefisien SPL
  // Input: Tidak ada input khusus
  // Output: Mengembalikan matriks koefisien dari SPL.
  // Contoh Kasus:
  // - Mendapatkan matriks koefisien dari SPL.
  Matrix getKoefMatrix();

  // Metode untuk mendapatkan vektor konstan SPL
  // Input: Tidak ada input khusus
  // Output: Mengembalikan vektor konstan dari SPL.
  // Contoh Kasus:
  // - Mendapatkan vektor konstan dari SPL.
  Matrix getVectorConstant();

  // Metode untuk menghitung numerator determinan Cramer
  // Input: Indeks kolom yang akan digunakan
  // Output: Mengembalikan nilai numerator determinan Cramer untuk kolom tertentu.
  // Contoh Kasus:
  // - Menghitung numerator determinan Cramer untuk kolom ke-2.
  double determinanNumerator(int colIdx);

  // Metode untuk memeriksa apakah vektor konstan SPL adalah nol
  // Input: Tidak ada input khusus
  // Output: Mengembalikan true jika vektor konstan SPL adalah nol, sebaliknya false.
  // Contoh Kasus:
  // - Memeriksa apakah vektor konstan SPL adalah nol.
  boolean isAllVectorConstantZero();

  // Metode untuk menyelesaikan SPL dengan metode invers
  // Input: Tidak ada input khusus
  // Output: Menentukan solusi SPL dan mengisi array Solution dan Equation.
  // Contoh Kasus:
  // - Menyelesaikan SPL dengan metode invers dan mengisi solusi.
  void inversMethodSPL();

  // Metode untuk menyelesaikan SPL dengan metode Cramer
  // Input: Tidak ada input khusus
  // Output: Menentukan solusi SPL dan mengisi array Solution dan Equation.
  // Contoh Kasus:
  // - Menyelesaikan SPL dengan metode Cramer dan mengisi solusi.
  void cramerMethodSPL();

  enum categorySolution {
    PARAMETRIX, UNIQUE, UNDEFINED, SUBSTITABLE
  }
}
