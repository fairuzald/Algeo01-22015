package tools;

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
    public void readFileMatrixInterpolasi(int n, float[] xlist, float[] ylist, double x) { // Pahamin cara baca file buat dijadiin input
        Matrix matrixKoordinat = new Matrix(n, n);
        matrixKoordinat.readFileMatrix("matrix1.txt");      // ini harusnya sesuai sama masukkan user mau filename nya apa
        this.koordinat = matrixKoordinat;
        this.n = matrixKoordinat.getRowEff()-1;
        // this.xDitaksir =        // Ini masih bingung masukkan nya drmn, nunggu jawaban QnA
    }

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
            hasil += this.matrixSPL.Solution[i];
            System.out.printf("a%d = %f\n", i, this.matrixSPL.Solution[i]);
        }
        this.hasilTaksiranX = hasil;
    }

    // Method untuk nyetak hasil taksiran
    public void cetakHasilTaksiran() {
        System.out.printf("Hasil taksiran dari x = %f adalah %f\n", this.xDitaksir, this.hasilTaksiranX);
    }
}
