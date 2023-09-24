package tools;

// Interface SPLInterface yang mendefinisikan metode dan enum tipe solusi SPL
public interface SPLInterface {
  // Metode untuk membaca SPL
  // Input: Tidak ada input khusus
  // Output: SPL akan dibaca dari input pengguna atau file, tergantung implementasi.
  // Contoh Kasus:
  // - Meminta pengguna untuk memasukkan SPL dari keyboard.
  // - Membaca SPL dari file yang ditentukan pengguna.
  public void readSPL();

  // Metode untuk membaca SPL dari file
  // Input: Nama file yang berisi SPL
  // Output: SPL akan dibaca dari file yang ditentukan.
  // Contoh Kasus:
  // - Membaca SPL dari file "input_spl.txt".
  public void readFileSPL(String fileName);

  // Metode untuk menampilkan SPL
  // Input: Tidak ada input khusus
  // Output: SPL akan ditampilkan ke layar.
  // Contoh Kasus:
  // - Menampilkan SPL yang telah dibaca kepada pengguna.
  public void displaySPL();

  // Metode untuk menulis SPL ke dalam file
  // Input: Nama file tujuan untuk menyimpan SPL
  // Output: SPL akan ditulis ke dalam file yang ditentukan.
  // Contoh Kasus:
  // - Menyimpan SPL ke dalam file "output_spl.txt".
  public void writeFileSPL(final String fileName);

  // Enum yang berisi nilai-nilai tipe solusi yang mungkin
  public categorySolution solutionType();

  // Metode untuk memeriksa apakah semua koefisien matriks diagonal adalah 1
  // Input: Tidak ada input khusus
  // Output: Mengembalikan true jika semua koefisien matriks diagonal adalah 1, false sebaliknya.
  // Contoh Kasus:
  // - Memeriksa apakah semua elemen diagonal matriks adalah 1.
  public boolean isAllCoefMatrixDiagonalOne();

  // Metode untuk memeriksa apakah semua koefisien dalam satu baris matriks adalah 0
  // Input: Indeks baris yang akan diperiksa
  // Output: Mengembalikan true jika semua koefisien dalam baris tertentu adalah 0, false
  // sebaliknya.
  // Contoh Kasus:
  // - Memeriksa apakah semua elemen dalam baris ke-2 adalah 0.
  public boolean isAllRowCoefMatrixZero(final int row);

  // Metode untuk memeriksa apakah hasil vektor dalam satu baris adalah 0
  // Input: Indeks baris yang akan diperiksa
  // Output: Mengembalikan true jika hasil vektor dalam baris tertentu adalah 0, false sebaliknya.
  // Contoh Kasus:
  // - Memeriksa apakah hasil vektor dalam baris ke-3 adalah 0.
  public boolean isRowVectorResultZero(final int row);

  // Metode untuk menjalankan metode eliminasi Gauss pada SPL
  // Input: Tidak ada input khusus
  // Output: Mengubah matriks SPL menjadi bentuk tereduksi setelah eliminasi Gauss.
  // Contoh Kasus:
  // - Melakukan eliminasi Gauss pada SPL.
  public void gaussMethodSPL();

  // Metode untuk menghitung solusi SPL setelah eliminasi Gauss
  // Input: Tidak ada input khusus
  // Output: Menghitung dan menyimpan solusi SPL setelah eliminasi Gauss.
  // Contoh Kasus:
  // - Menghitung solusi SPL setelah eliminasi Gauss.
  public void gaussSolution();

  // Metode untuk menjalankan metode eliminasi Gauss-Jordan pada SPL (belum diimplementasikan)
  // Input: Tidak ada input khusus
  // Output: Belum diimplementasikan.
  // Contoh Kasus:
  // - (Belum diimplementasikan)
  public void gJordanMethodSPL();

  // Metode untuk menjalankan metode invers pada SPL (belum diimplementasikan)
  // Input: Tidak ada input khusus
  // Output: Belum diimplementasikan.
  // Contoh Kasus:
  // - (Belum diimplementasikan)
  public void inversMethodSPL();

  // Metode untuk menjalankan metode Cramer pada SPL (belum diimplementasikan)
  // Input: Tidak ada input khusus
  // Output: Belum diimplementasikan.
  // Contoh Kasus:
  // - (Belum diimplementasikan)
  public void cramerMethodSPL();

  // Enum yang berisi nilai-nilai tipe solusi yang mungkin
  public enum categorySolution {
    PARAMETRIX, UNIQUE, UNDEFINED, SUBSTITABLE
  }
}
