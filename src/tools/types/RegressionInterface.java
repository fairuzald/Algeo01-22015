package tools.types;



public interface RegressionInterface {

  // Metode untuk membaca input untuk persoalan Multiple Linear Regression dan titik yang diuji dari
  // Keyboard.
  // Input:
  // m = Banyak sampel
  // n = Banyak variabel peubah
  // Output: Semua titik sampel diterima dalam Matrix allPoint dan siap untuk dicari solusinya
  // Contoh Kasus:
  // - Meminta pengguna untuk memasukkan persoalan Regresi dari Keyboard.
  void readRegressionKeyboard(int m, int n);

  // Metode untuk membaca input untuk persoalan Multiple Linear Regression dan titik yang diuji dari
  // sebuah File.
  // Input:
  // fileName = Nama File yang akan dibaca
  // n = Banyak Variabel peubah
  // m = Banyak sampel
  // Output: Semua titik sampel diterima dalam Matrix allPoint dan siap untuk dicari solusinya
  // Contoh Kasus:
  // - Meminta pengguna untuk memasukkan persoalan Regresi dari File.
  void readRegressionFile(String fileName, int n, int m);

  // Metode untuk merubah elemen pada allPoint menjadi persamaan dengan Normal
  // Estimation Equation for Multiple Linear Regression
  // Input: -
  // Output: Matrix Regression memuat Normal Estimation Equation
  // Contoh Kasus:
  // - allPoint terdefinisi, hendak mencari Normal Estimation Equation
  void compileMatrix();

  // Metode untuk menaksir nilai yk yang dicari pengguna dengan memanfaatkan regresi
  // Input: -
  // Output: nilai float yaitu yk
  // Contoh Kasus:
  // - array Solution terdefinisi, hendak mencari estimasi nilai yk
  float estimate();

  // Metode untuk menampilkan solusi regresi dan nilai estimasi yk ke layar
  // Input: tidak ada
  // Output: Solusi Persamaan Regresi dan estimasi nilai yk tertulis di layar
  // Contoh Kasus:
  // - Perhitungan regresi dan estimasi selesai, hendak ditampilkan pada layar
  void displayRegression();

  // Metode untuk menuliskan solusi regresi dan nilai estimasi yk di sebuah File.
  // Input:
  // filePath = Path File tempat dituliskannya solusi
  // Output: File pada filePath terdefinisi dan berisi solusi regresi dan nilai yk.
  // Contoh Kasus:
  // - Perhitungan regresi dan estimasi selesai, hendak dituliskan pada suatu File.
  void writeFileRegression(String filePath);

  // Metode untuk memandu pengguna menyelesaikan persoalan regresi
  // Input: -
  // Output: Pengguna dapat melakukan input dan melihat output untuk persoalan regresi
  // Contoh Kasus:
  // - Saat pengguna ingin menggunakan program untuk menyelesaikan permasalahan regresi
  void regressionDriver();
}
