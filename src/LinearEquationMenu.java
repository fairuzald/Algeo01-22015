import java.util.Scanner;
import tools.SPL;

public class LinearEquationMenu {
  Scanner input = new Scanner(System.in);

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

  public void LinearEquation() {
    // INISIALISASI
    int inputMethod, readInput;
    String saveStatus;
    SPL mSPL = new SPL(0, 0);


    // ALGORITMA
    try {
      // PILIHAN METHOD
      this.MenuSPLOption();
      inputMethod = input.nextInt();
      // Looping jika input tidak sesuai
      while (inputMethod != 1 && inputMethod != 2 && inputMethod != 3 && inputMethod != 4
          && inputMethod != 5) {
        Menu.WarnWrongInput();
        this.MenuSPLOption();
        inputMethod = input.nextInt();
      }
      if (inputMethod == 1 | inputMethod == 2 | inputMethod == 3 | inputMethod == 4) {
        // KONDISI UNTUK READ FILE
        Menu.InputFileOption("SPL");
        readInput = input.nextInt();
        // Looping jika input tidak sesuai
        while (readInput != 1 && readInput != 2 && readInput != 3) {
          Menu.WarnWrongInput();
          Menu.InputFileOption("SPL");
          readInput = input.nextInt();
        }

        if (readInput == 1) {
          System.out.println("-----------------------------------");

          mSPL.readSPL();
          input.nextLine();

          System.out.println("-----------------------------------");
          System.out.println("Data matriks SPL Berhasil Terbaca");
        }

        else if (readInput == 2) {
          String dirPath, filePath = "";
          dirPath = System.getProperty("user.dir") + "\\test\\data\\";
          System.out.println("-----------------------------------");
          System.out.println("List file valid :");
          Menu.getAllDataFiles(dirPath, false);
          System.out.println("------------------------------------");
          filePath = Menu.getFilePath(input, dirPath);

          mSPL.readFileSPL(filePath);

          System.out.println("-----------------------------------");
          System.out.println("Data matriks SPL Berhasil Terbaca");
        } else if (readInput == 3) {
          Menu.clearScreen();
          return;
          // MainMenu();
        }


        System.out.println("Solusi sistem persamaan linier: ");
        System.out.println("-----------------------------------");
        switch (inputMethod) {
          case 1 -> {
            System.out.println("Metode Eliminasi Gauss");
            mSPL.gaussMethodSPL();
            mSPL.displayMatrix();
          }
          case 2 -> {
            System.out.println("Metode Eliminasi Gauss Jordan");
            mSPL.gJordanMethodSPL();
          }
          case 3 -> {
            System.out.println("Metode Matriks Balikan");
            mSPL.inversMethodSPL();
          }
          case 4 -> {
            System.out.println("Metode Kaidah Cramer");
            mSPL.cramerMethodSPL();
          }

        }
        // MAU DISIMPAN?
        if (inputMethod == 1 || inputMethod == 2 || inputMethod == 3 || inputMethod == 4) {
          System.out.println("-----------------------------------");
          mSPL.displaySPL();
          System.out.println("-----------------------------------");
          System.out.print("Simpan Hasil? (y/n) : ");

          saveStatus = input.nextLine();
          while (!saveStatus.equals("y") && !saveStatus.equals("n")) {
            System.out.println("Ulangi! Input haruslah 'y' atau 'n'");
            System.out.print("Simpan Hasil? (y/n) : ");
            saveStatus = input.nextLine();
          }
          if (saveStatus.equals("y")) {
            System.out.println("-----------------------------------");
            String outputDir = System.getProperty("user.dir") + "\\test\\output\\";
            String outputPath = Menu.getOutputFileLoc(input, outputDir);
            mSPL.writeFileSPL(outputPath);

          }
        }
      }

      else {
        Menu.clearScreen();
      }
    } catch (Exception e) {
      System.out.println("Terjadi Error");
      e.printStackTrace();
    }
  }

}
