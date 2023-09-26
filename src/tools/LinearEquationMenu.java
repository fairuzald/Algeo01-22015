package tools;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class linearEquation {
  public void MenuOption() {
    System.out.println("-----------------------------------");
    System.out.println("------Sistem Persamaan Linier------");
    System.out.println("-----------------------------------");
    System.out.println("           PILIH METODE");
    System.out.println("-----------------------------------");
    System.out.println("1. Metode Eliminasi Gauss");
    System.out.println("2. Metode Eliminasi Gauss Jordan");
    System.out.println("3. Metode Matriks Balikan");
    System.out.println("4. Metode Kaidah Cramer");
    System.out.println("4. Kembali");
    System.out.println("-----------------------------------");
    System.out.print("Masukkan pilihan : ");
  }

  public void WarnWrongInput() {
    System.out.println("Masukkan opsi angka dengan input benar");
  }

  public void InputOption() {
    System.out.println("-----------------------------------");
    System.out.println("               MENU SPL");
    System.out.println("-----------------------------------");
    System.out.println("           PILIH CARA INPUT");
    System.out.println("-----------------------------------");
    System.out.println("1. Input Dari Terminal");
    System.out.println("2. Input Dari File");
    System.out.println("3. Kembali");
    System.out.println("-----------------------------------");
  }

  public String getFilePath(Scanner input, String directory) {
    String dirPath, fileName;
    File checkFile;

    do {
      System.out.print("Masukkan nama file dalam txt: ");
      fileName = input.nextLine();
      dirPath = directory + fileName;
      checkFile = new File(dirPath);

      if (!checkFile.exists()) {
        System.out.println("File yang anda masukkan tidak ada");
      }

    } while (!checkFile.exists());

    return dirPath;
  }

  /** List input file yang valid */
  public static void getAllDataFiles(String curDir) {
    File dataFile = new File(curDir);
    File[] listFile = dataFile.listFiles();
    for (File f : listFile) {
      System.out.println(f.getName());
    }
  }

  public String getOutputFileLoc(Scanner input, String fileDir) {
    String fileName;

    do {
      System.out.print("Masukkan nama file output: ");
      fileName = input.nextLine();
      String dirPath = fileDir + fileName;
      File output = new File(dirPath);

      if (output.exists()) {
        System.out.println("File yang anda masukkan sudah ada");
      }
    } while (new File(fileDir + fileName).exists());

    return fileDir + fileName;
  }

  public void outputFile(String content, String fileName) {
    try {
      PrintWriter writer = new PrintWriter(fileName, StandardCharsets.UTF_8);
      writer.println(content);
      writer.close();
    } catch (IOException e) {
      System.out.println("An error occurred");
      e.printStackTrace();
    }
  }

  public void clearScreen() {
    try {
      new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void LinearEquation() {
    // INISIALISASI
    int inputMethod, readInput;
    char saveStatus;
    Scanner input;
    SPL mSPL = new SPL(0, 0);

    // ALGORITMA
    try {
      input = new Scanner(System.in);

      // PILIHAN METHOD
      this.MenuOption();
      inputMethod = input.nextInt();
      input.nextLine();
      // Looping jika input tidak sesuai
      while (inputMethod != 1 && inputMethod != 2 && inputMethod != 3 && inputMethod != 4
          && inputMethod != 5) {
        this.WarnWrongInput();
        this.MenuOption();
        inputMethod = input.nextInt();
        input.nextLine();
      }
      if (inputMethod == 1 | inputMethod == 2 | inputMethod == 3 | inputMethod == 4) {
        // KONDISI UNTUK READ FILE
        this.InputOption();
        readInput = input.nextInt();
        input.nextLine();
        // Looping jika input tidak sesuai
        while (readInput != 1 && readInput != 2 && readInput != 3) {
          this.WarnWrongInput();
          this.InputOption();
          readInput = input.nextInt();
          input.nextLine();
        }

        if (readInput == 1) {
          System.out.println("-----------------------------------");

          mSPL.readSPL();

          System.out.println("-----------------------------------");
          System.out.println("Data matriks SPL Berhasil Terbaca");
        }

        else if (readInput == 2) {
          String dirPath, filePath = "";
          dirPath = System.getProperty("user.dir") + "\\test\\data\\";
          System.out.println("-----------------------------------");
          System.out.println("List file valid :");
          getAllDataFiles(dirPath);
          System.out.println("----- ------------------------------");
          System.out.print("Masukkan nama file data matriks SPL : ");
          filePath = getFilePath(input, dirPath);

          mSPL.readFileSPL(filePath);

          System.out.println("-----------------------------------");
          System.out.println("Data matriks SPL Berhasil Terbaca");
        } else if (readInput == 3) {
          clearScreen();
          // MainMenu();
        }


        System.out.println("Solusi sistem persamaan linier: ");
        System.out.println("-----------------------------------");
        switch (inputMethod) {
          case 1 -> {
            System.out.println("Metode Eliminasi Gauss");
            mSPL.gaussMethodSPL();
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
        if (inputMethod == 1 || inputMethod == 2) {
          System.out.println("-----------------------------------");
          mSPL.displaySPL();
          System.out.println("-----------------------------------");
          System.out.print("Simpan Hasil? (y/n) : ");

          saveStatus = input.next().charAt(0);
          if (saveStatus == 'y') {
            System.out.println("-----------------------------------");
            String outputDir = System.getProperty("user.dir") + "\\test\\output\\";
            String outputPath = getOutputFileLoc(input, outputDir);
            mSPL.writeFileSPL(outputPath);
          }
        }
      }

      else {
        clearScreen();
      }
    } catch (Exception e) {
      System.out.println("Terjadi Error");
      e.printStackTrace();
    }

  }
}
