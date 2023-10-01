

import java.io.File;
import java.util.Scanner;

public class Menu {

  public static void WarnWrongInput() {
    System.out.println("Masukkan opsi angka dengan input benar");
  }

  public static void InputFileOption(String title) {
    System.out.println("-----------------------------------");
    System.out.println("               " + title);
    System.out.println("-----------------------------------");
    System.out.println("           PILIH CARA INPUT");
    System.out.println("-----------------------------------");
    System.out.println("1. Input Dari Terminal");
    System.out.println("2. Input Dari File");
    System.out.println("3. Kembali");
    System.out.println("-----------------------------------");
    System.out.print("Masukkan pilihan : ");
  }

  public static String getFilePath(Scanner input, String directory) {
    // Inisialisasi
    String filePath, fileName;
    File checkFile;

    do {
      System.out.print("Masukkan nama file (tanpa ekstensi file): ");
      // Input fileName dalam string
      fileName = input.nextLine();
      filePath = directory + fileName + ".txt";
      checkFile = new File(filePath);

      // Cek ketersediaan file
      if (!checkFile.exists()) {
        System.out.println("File yang anda masukkan tidak ditemukan");
      }

    } while (!checkFile.exists());

    return filePath;
  }


  public static void getAllDataFiles(String currentDir) {
    // Ambil semua nama file dalam current dir
    File dataFile = new File(currentDir);
    File[] listFile = dataFile.listFiles();
    for (File f : listFile) {
      System.out.println(f.getName());
    }
  }

  public static String getOutputFileLoc(Scanner input, String directory) {
    String fileName;
    String filePath;

    do {
      System.out.print("Masukkan nama file output(tanpa ekstensi file): ");
      fileName = input.nextLine();
      filePath = directory + fileName + ".txt";
      File output = new File(filePath);

      // Cek apakah file sudah ada
      if (output.exists()) {
        System.out
            .print("File yang Anda masukkan sudah ada. Apakah Anda ingin menindihnya? (y/n): ");
        String overwriteChoice = input.nextLine().toLowerCase();

        if (overwriteChoice.equals("n")) {
          continue; // Lanjutkan iterasi untuk meminta nama file yang berbeda
        } else if (!overwriteChoice.equals("y")) {
          System.out.println("Pilihan tidak valid. Harap masukkan 'y' atau 'n'.");
          continue; // Lanjutkan iterasi untuk meminta input yang valid
        }
      }
    } while (new File(directory + fileName).exists());

    return filePath;
  }

  public static void clearScreen() {
    try {
      new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void startLoadingAnimation() {
    String loadingText = "Loading";
    int maxDots = 6;
    int intervalMillis = 1000; // Interval waktu antara perubahan teks (dalam milidetik)

    while (true) {
      for (int i = 1; i <= maxDots; i++) {
        String dots = ".".repeat(i);
        System.out.print("\r" + loadingText + dots);
        try {
          Thread.sleep(intervalMillis);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }
}
