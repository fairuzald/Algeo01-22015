package tools.types;

import tools.Matrix;

public interface MatrixInterface {

    // Mendapatkan jumlah baris matriks
    int getRowEff();
    // Input: Tidak ada
    // Output: Jumlah baris pada matriks
    // Contoh Case:
    // Jika matriks memiliki 3 baris, maka getRowEff() akan mengembalikan nilai 3.

    // Mendapatkan jumlah kolom matriks
    int getColEff();
    // Input: Tidak ada
    // Output: Jumlah kolom pada matriks
    // Contoh Case:
    // Jika matriks memiliki 4 kolom, maka getColEff() akan mengembalikan nilai 4.

    // Mendapatkan elemen matriks pada baris i dan kolom j
    double getElmt(int i, int j);
    // Input: Baris (i) dan Kolom (j)
    // Output: Nilai elemen pada baris i dan kolom j
    // Contoh Case:
    // Jika kita ingin mendapatkan elemen pada baris 2 dan kolom 3 dari matriks,
    // maka getElmt(2, 3) akan mengembalikan nilai elemen tersebut.

    // Mendapatkan indeks baris terakhir
    int getLastIdxRow();
    // Input: Tidak ada
    // Output: Indeks baris terakhir pada matriks
    // Contoh Case:
    // Jika matriks memiliki 3 baris, maka getLastIdxRow() akan mengembalikan nilai 2 (indeks
    // dimulai dari 0).

    // Mendapatkan indeks kolom terakhir
    int getLastIdxCol();
    // Input: Tidak ada
    // Output: Indeks kolom terakhir pada matriks
    // Contoh Case:
    // Jika matriks memiliki 4 kolom, maka getLastIdxCol() akan mengembalikan nilai 3 (indeks
    // dimulai dari 0).

    // Mendapatkan indeks baris pertama
    int getFirstIdxRow();
    // Input: Tidak ada
    // Output: Indeks baris pertama pada matriks
    // Contoh Case:
    // Jika matriks memiliki 3 baris, maka getFirstIdxRow() akan mengembalikan nilai 0.

    // Mendapatkan indeks kolom pertama
    int getFirstIdxCol();
    // Input: Tidak ada
    // Output: Indeks kolom pertama pada matriks
    // Contoh Case:
    // Jika matriks memiliki 4 kolom, maka getFirstIdxCol() akan mengembalikan nilai 0.

    // Mendapatkan elemen diagonal pada baris i
    double getElmtDiagonal(int i);
    // Input: Baris (i)
    // Output: Nilai elemen diagonal pada baris i
    // Contoh Case:
    // Jika kita ingin mendapatkan elemen diagonal pada baris ke-2 dari matriks,
    // maka getElmtDiagonal(2) akan mengembalikan nilai elemen diagonal tersebut.

    // Mendapatkan ukuran (jumlah sel) matriks
    int getSize();
    // Input: Tidak ada
    // Output: Jumlah sel (baris * kolom) dalam matriks
    // Contoh Case:
    // Jika matriks memiliki 3 baris dan 4 kolom, maka getSize() akan mengembalikan nilai 12.

    // Memeriksa apakah matriks adalah matriks persegi (jumlah baris = jumlah kolom)
    boolean isSquare();
    // Input: Tidak ada
    // Output: true jika matriks persegi, false jika tidak
    // Contoh Case:
    // Jika matriks memiliki 3 baris dan 3 kolom, maka isSquare() akan mengembalikan true.

    // Memeriksa apakah matriks adalah matriks simetris
    boolean isSymmetric();
    // Input: Tidak ada
    // Output: true jika matriks simetris, false jika tidak
    // Contoh Case:
    // Jika matriks adalah matriks simetris, maka isSymmetric() akan mengembalikan true.

    // Memeriksa apakah matriks adalah matriks identitas
    boolean isIdentity();
    // Input: Tidak ada
    // Output: true jika matriks identitas, false jika tidak
    // Contoh Case:
    // Jika matriks adalah matriks identitas, maka isIdentity() akan mengembalikan true.

    // Menetapkan nilai elemen matriks pada baris i dan kolom j
    void setElmt(int i, int j, double value);
    // Input: Baris (i), Kolom (j), dan Nilai (value)
    // Output: Tidak ada
    // Contoh Case:
    // Jika kita ingin mengatur nilai elemen pada baris 1 dan kolom 1 menjadi 5,
    // maka kita akan memanggil setElmt(0, 0, 5).

    // Memeriksa apakah indeks baris dan kolom valid pada matriks
    boolean isMatrixIdxValid(int i, int j);
    // Input: Baris (i) dan Kolom (j)
    // Output: true jika indeks valid, false jika tidak valid
    // Contoh Case:
    // Jika matriks memiliki 3 baris dan 4 kolom, maka isMatrixIdxValid(2, 3) akan mengembalikan
    // true.

    // Menyalin elemen-elemen matriks ke dalam array 2D baru
    double[][] copyElmtMatrix();
    // Input: Tidak ada
    // Output: Array 2D baru yang berisi salinan elemen-elemen matriks
    // Contoh Case:
    // Fungsi akan menghasilkan array 2D baru dengan elemen-elemen yang sama dengan matriks.

    // Menyalin seluruh matriks
    Matrix copyMatrix();
    // Input: Tidak ada
    // Output: Salinan matriks
    // Contoh Case:
    // Fungsi akan menghasilkan salinan matriks.

    // Membaca matriks dari input pengguna
    void readMatrix();
    // Input: Matriks dari input pengguna (misalnya, melalui keyboard)
    // Output: Tidak ada
    // Contoh Case:
    // Pengguna diminta untuk memasukkan nilai-nilai matriks, dan hasilnya akan disimpan dalam
    // matriks.


    void displayMatrix();

    void writeFileMatrix(String fileName);

    Matrix addMatrix(Matrix m1, Matrix m2);

    Matrix subtractMatrix(Matrix m1, Matrix m2);

    Matrix multiplyMatrix(Matrix m1, Matrix m2);

    void multiplyByConst(double k);

    void negation();

    Matrix transpose();

    Matrix createIdentityMatrix(int rows, int cols);

    void OBEPlusRow(int idxRowOrigin, int idxRowTarget, double factor);

    void OBESwapRow(int idxRowOrigin, int idxRowTarget);

    void OBEDivisionFactor(int i, double factor);

    void gaussElimination();

    void gJordanElimination();

    double determinantCofactor();

    double determinantUpperTriangle();

    Matrix cofactor();

    Matrix adjoin();

    Matrix inversAdjoin();

    Matrix inversGJordan();
}
