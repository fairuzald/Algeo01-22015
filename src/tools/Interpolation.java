package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Interpolation {

    public Matrix koordinat;
    public SPL matrixSPL;
    public int n;
    public double xDitaksir;
    public double hasilTaksiranX;

    // Method untuk membaca masukkan user dari terminal
    public void readInputInterpolasi() {
        // I.S. n dan koordinat sudah di inisialisasi tanpa nilai ataupun ukuran
        // F.S. n dan koordinat terdefinisi dengan nilainya
        System.out.print("Masukkan n, jumlah titik yang akan diberikan: ");
        Scanner input = new Scanner(System.in);
        int nInput = input.nextInt();
        int baris = nInput+1;
        int kolom = 2;
        double masukkanTitik;
        Matrix matrixKoordinat = new Matrix(baris, kolom);
        System.out.printf("Masukkan titik koordinat sebanyak %d kali: \n", nInput+1);
        for (int i=0; i<nInput+1; i++) 
        {
            for (int j=0; j<2; j++)
            {
                masukkanTitik = input.nextDouble();
                matrixKoordinat.setElmt(i, j, masukkanTitik);
            }
        }
        System.out.print("Masukkan nilai x yang ingin ditaksir nilai fungsinya: ");  // anyng kalo disini code nya, jadi kaga error
        double masukanX;
        masukanX = input.nextDouble();
        this.xDitaksir = masukanX;
        input.close();
        this.koordinat = matrixKoordinat;
        this.n = nInput;
        
    }
    
    // Method untuk membaca masukkan user dari file
    public void readFileMatrixInterpolasi(final String filePath) {
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
      Nrow --;
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
      Matrix matrixTemp = new Matrix(Nrow, Ncol);

      // Read and populate the matrix
      for (i = 0; i < Nrow; i++) {
        for (j = 0; j < Ncol; j++) {
          if (matrix.hasNextDouble()) {
            matrixTemp.setElmt(i, j, matrix.nextDouble());
          }
        }
      }
      this.xDitaksir = matrix.nextDouble();
      matrix.close();

      // Update the matrix and dimensions
      this.koordinat = matrixTemp;
    } catch (FileNotFoundException e) {
      System.err.printf("Error: File \"%s\" tidak ditemukan\n", filePath);
    }
  }

// INI SALAH
    // public void readFileMatrixInterpolasi(int n, float[] xlist, float[] ylist, double x) { // Pahamin cara baca file buat dijadiin input
    //     Matrix matrixKoordinat = new Matrix(n, n);
    //     matrixKoordinat.readFileMatrix("matrix1.txt");      // ini harusnya sesuai sama masukkan user mau filename nya apa
    //     this.koordinat = matrixKoordinat;
    //     this.n = matrixKoordinat.getRowEff()-1;
    //     // this.xDitaksir =        // Ini masih bingung masukkan nya drmn, nunggu jawaban QnA
    // }

    // Method untuk membuat matrix SPL
    public void buatMatrixSPL()
    {
        SPL tempMatrix = new SPL(this.koordinat.getRowEff(), this.n+2);
        for (int i=0; i<tempMatrix.getRowEff(); i++)
        {
            for (int j=0; j<tempMatrix.getColEff(); j++)
            {
                if (j==0)
                {
                    tempMatrix.setElmt(i, j, 1);
                }
                else if (j == tempMatrix.getLastIdxCol())
                {
                    tempMatrix.setElmt(i, j, this.koordinat.getElmt(i, 1));
                }
                else 
                {
                    tempMatrix.setElmt(i, j, (float) Math.pow(this.koordinat.getElmt(i, 0), j));
                }
            }
        }
        this.matrixSPL = tempMatrix;
    }

    // Method untuk mencari solusi SPL
    public void cariTaksiranY() {
        // Selesein matrix (dapetin semua nilai a nya sehingga di dapet sebuah fungsi f(x) untuk mencari nilai y)
        this.matrixSPL.inversMethodSPL();   // Cari Solution[]

        // Masukin nilai x, yang pengen ditaksir, ke fungsi f(x)
        double hasil=0;
        for (int i=0; i<this.matrixSPL.getColEff()-1; i++)
        {
            hasil += this.matrixSPL.Solution[i]*Math.pow(this.xDitaksir, i);
            // System.out.printf("a%d = %f\n", i, this.matrixSPL.Solution[i]);
        }
        this.hasilTaksiranX = hasil;
    }

    // Method untuk nyetak hasil taksiran
    public void cetakOutputInterpolasi() {
        String output = "f(x) = ";
        String tempstr;
        for (int i=this.matrixSPL.getColEff()-1; i>=0; i--)
        {
            tempstr = Double.toString(this.matrixSPL.Solution[i]);
            if (i==0)
            {
                output = output + tempstr + ", f("+ this.xDitaksir +") = ";                
            } else{
                output = output + tempstr + "x^("+i+") + ";
            }
        }
        output = output + this.hasilTaksiranX;
        System.out.println(output);
    }
}
