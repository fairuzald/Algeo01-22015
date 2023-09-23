import tools.Matrix;

public class Test {
  public static void main(String[] args) {
    // Contoh penggunaan fungsi-fungsi matriks

    // Membuat dua matriks
    Matrix matrix1 = new Matrix(3, 3);
    Matrix matrix2 = new Matrix(3, 3);

    // Mengisi matriks dengan nilai-nilai tertentu
    matrix1.setElmt(0, 0, 3.0f);
    matrix1.setElmt(0, 1, 2.0f);
    matrix1.setElmt(0, 2, -1.0f);
    matrix1.setElmt(1, 0, 1.0f);
    matrix1.setElmt(1, 1, 6.0f);
    matrix1.setElmt(1, 2, 3.0f);
    matrix1.setElmt(2, 0, 2.0f);
    matrix1.setElmt(2, 1, -4.0f);
    matrix1.setElmt(2, 2, 0.0f);
    // matrix1.gJordanElimination();
    // matrix1.displayMatrix();
    float det = matrix1.determinantUpperTriangle();
    float det2 = matrix1.determinantCofactor();
    Matrix sub = new Matrix(3, 3);
    Matrix adj = new Matrix(3, 3);
    Matrix inv = new Matrix(3, 3);
    Matrix inv2 = new Matrix(3, 3);
    sub = matrix1.cofactor();
    adj = matrix1.adjoin();
    inv = matrix1.inversAdjoin();
    inv2 = matrix1.inversGJordan();

    sub.displayMatrix();
    System.out.println();
    adj.displayMatrix();
    System.out.println();
    inv.displayMatrix();
    System.out.println();
    inv2.displayMatrix();

    // ... mengisi nilai-nilai lainnya ...

    // matrix2.setElmt(0, 0, 4.0f);
    // matrix2.setElmt(0, 1, 5.0f);
    // matrix2.setElmt(0, 2, 6.0f);
    // // ... mengisi nilai-nilai lainnya ...

    // // Menampilkan matriks
    // System.out.println("Matriks 1:");
    // matrix1.displayMatrix();

    // System.out.println("Matriks 2:");
    // matrix2.displayMatrix();

    // // Memeriksa ukuran matriks sebelum menjumlahkan
    // if (!Matrix.isMatrixSizeEqual(matrix1, matrix2)) {
    // System.out.println("Ukuran matriks tidak sama. Tidak bisa menjumlahkan.");
    // } else {
    // // Melakukan operasi penjumlahan matriks
    // Matrix hasilPenjumlahan = Matrix.addMatrix(matrix1, matrix2);
    // System.out.println("Hasil Penjumlahan:");
    // hasilPenjumlahan.displayMatrix();
    // }

    // // Melakukan operasi pengurangan matriks
    // Matrix hasilPengurangan = Matrix.subtractMatrix(matrix1, matrix2);
    // System.out.println("Hasil Pengurangan:");
    // hasilPengurangan.displayMatrix();

    // // Melakukan operasi perkalian matriks
    // Matrix hasilPerkalian = Matrix.multiplyMatrix(matrix1, matrix2);
    // System.out.println("Hasil Perkalian:");
    // hasilPerkalian.displayMatrix();

    // // Menyimpan matriks ke dalam file
    // matrix1.writeFileMatrix("matrix1.txt");
    // matrix2.writeFileMatrix("matrix2.txt");

    // // Membaca matriks dari input pengguna
    // Matrix matriksDibaca = new Matrix(0, 0);
    // System.out.println("Masukkan matriks baru:");
    // matriksDibaca.readMatrix();
    // System.out.println("Matriks yang Anda masukkan:");
    // matriksDibaca.displayMatrix();

    // // Membaca matriks dari file
    // Matrix matriksDariFile = new Matrix(0, 0);
    // matriksDariFile.readFileMatrix("matrix1.txt");
    // System.out.println("Matriks dari file:");
    // matriksDariFile.displayMatrix();
  }
}
