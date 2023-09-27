import java.util.Scanner;
import tools.BicubicSpline;

public class BicubicMenu {
  public void MenuSPLOption() {
    System.out.println("-----------------------------------");
    System.out.println("------Sistem Persamaan Linier------");
    System.out.println("-----------------------------------");
    System.out.println("           PILIH METODE");
    System.out.println("-----------------------------------");
    System.out.println("1. Metode Eliminasi Gauss");
    System.out.println("2. Metode Eliminasi Gauss Jordan");
    System.out.println("3. Metode Matriks Balikan");
    System.out.println("4. Metode Kaidah Cramer");
    System.out.println("5. Kembali");
    System.out.println("-----------------------------------");
    System.out.print("Masukkan pilihan : ");
  }

  public void BicubicSpline(Scanner inputScanner) {
    int fileOption;
    BicubicSpline bic = new BicubicSpline(16, 16);
    Menu.InputFileOption("Interpolasi Bicubic Spline");
    fileOption = inputScanner.nextInt();
    // Looping jika input tidak sesuai
    while (fileOption != 1 && fileOption != 2 && fileOption != 3) {
      Menu.WarnWrongInput();
      Menu.InputFileOption("Interpolasi Bicubic Spline");
      fileOption = inputScanner.nextInt();
    }

    if (fileOption == 1) {
      System.out.println("-----------------------------------");

      bic.readBicubicSpline();

      System.out.println("-----------------------------------");
      System.out.println("Data Bicubic Berhasil Terbaca");
    }

    else if (fileOption == 2) {
      String dirPath, filePath = "";
      dirPath = System.getProperty("user.dir") + "\\test\\data\\";
      System.out.println("-----------------------------------");
      System.out.println("List file valid :");
      Menu.getAllDataFiles(dirPath);
      System.out.println("------------------------------------");
      filePath = Menu.getFilePath(inputScanner, dirPath);

      bic.readFileBicubicSpline(filePath);

      System.out.println("-----------------------------------");
      System.out.println("Data matriks SPL Berhasil Terbaca");
    } else if (fileOption == 3) {
      Menu.clearScreen();
      // MainMenu();
    }

    // MAU DISIMPAN?
    if (fileOption == 1 || fileOption == 2) {
      System.out.println("-----------------------------------");
      bic.displayBicubicSpline();
      System.out.println("-----------------------------------");
      System.out.print("Simpan Hasil? (y/n) : ");

      char saveStatus = inputScanner.next().charAt(0);
      if (saveStatus == 'y') {
        System.out.println("-----------------------------------");
        String outputDir = System.getProperty("user.dir") + "\\test\\output\\";
        String outputPath = Menu.getOutputFileLoc(inputScanner, outputDir);
        bic.writeFileBicubicSpline(outputPath);
      }
    }

  }
}
