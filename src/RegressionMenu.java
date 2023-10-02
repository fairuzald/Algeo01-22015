import java.io.File;
import java.util.Scanner;
import tools.Regression;

public class RegressionMenu {
  private void selamatDatang() {
    System.out.println("-----------------------------------");
    System.out.println("--------Multiple Regression--------");
    System.out.println("-----------------------------------");
    System.out.println("1. Run Multiple Regression");
    System.out.println("2. Kembali");
    System.out.println("-----------------------------------");
  }

  public void calculateRegression() {
    /* KAMUS */
    int pilihanInput, pilihanMulai;
    String saveStatus;
    Scanner scan;
    /* ALGORITMA */

    try {
      scan = new Scanner(System.in);
      selamatDatang();
      System.out.print("Masukkan pilihan : ");
      pilihanMulai = scan.nextInt();

      if (pilihanMulai == 1) {
        /* KAMUS */
        Regression reg = new Regression(0, 0);
        /* ALGORITMA */

        // Menerima metode Input
        Menu.InputFileOption("Regression");
        pilihanInput = scan.nextInt();
        while (pilihanInput != 1 && pilihanInput != 2) {
          Menu.WarnWrongInput();
          Menu.InputFileOption("Regression");
          pilihanInput = scan.nextInt();
        }


        switch (pilihanInput) {
          case 1: // OPSI INPUT DARI TERMINAL
            int n = 0, m = 0;
            System.out.println("-----------------------------------");
            System.out.println("      Metode Input Terminal");
            System.out.print("Masukkan jumlah peubah x : ");
            n = scan.nextInt();

            System.out.print("Masukkan banyak sampel : ");
            m = scan.nextInt();

            reg = new Regression(n + 1, n + 2);
            reg.readRegressionKeyboard(m, n);

            System.out.println("-----------------------------------");
            System.out.println("Data Matriks Berhasil Terbaca");

            reg.compileMatrix();
            reg.gaussMethodSPL();
            break;
          case 2: // OPSI INPUT DARI FILE
            n = 0;
            m = 0;
            System.out.println("-----------------------------------");
            System.out.println("        Metode Input File");

            String dirPath, filePath = "";
            dirPath = System.getProperty("user.dir") + "\\test\\data\\";
            System.out.println("-----------------------------------");
            System.out.println("List file valid :");
            Menu.getAllDataFiles(dirPath, false);
            System.out.println("------------------------------------");
            filePath = Menu.getFilePath(scan, dirPath);
            File file = new File(filePath);

            try {
              Scanner scanFile = new Scanner(file);

              // Menghitung banyak sampel m
              while (scanFile.hasNextLine()) {
                m++;
                scanFile.nextLine();
              }
              scanFile.close();

              // Menghitung banyak peubah x n

              scanFile = new Scanner(file);
              Scanner scanLine = new Scanner(scanFile.nextLine());
              while (scanLine.hasNextDouble()) {
                n++;
                scanLine.nextDouble();
              }

              scanFile.close();
              scanLine.close();

              reg = new Regression(n, n + 1);
              reg.readRegressionFile(filePath, n - 1, m - 1);
              reg.compileMatrix();

              System.out.println();
              reg.allPoint.displayMatrix();
              System.out.println();
              reg.displayMatrix();

              reg.gaussMethodSPL();

            } catch (Exception e) {
              e.printStackTrace();
            }
            break;
        }

        reg.displayRegression();

        // MAU DISIMPAN?
        if (pilihanInput == 1 || pilihanInput == 2) {
          System.out.println("-----------------------------------");
          System.out.print("Simpan Hasil? (y/n) : ");
          saveStatus = scan.nextLine();
          while (!saveStatus.equals("y") && !saveStatus.equals("n")) {
            System.out.println("Ulangi! Input haruslah 'y' atau 'n'");
            System.out.print("Simpan Hasil? (y/n) : ");
            saveStatus = scan.nextLine();
          }
          if (saveStatus.equals("y")) {
            System.out.println("-----------------------------------");
            String outputDir = System.getProperty("user.dir") + "\\test\\output\\";
            String outputPath = Menu.getOutputFileLoc(scan, outputDir);
            reg.writeFileRegression(outputPath);
          }
        }
      }
    } catch (Exception e) {
      System.out.println("Terjadi Error");
      e.printStackTrace();
    }
  }
}

