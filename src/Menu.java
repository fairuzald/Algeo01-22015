

import java.io.File;
import java.io.FilenameFilter;
import java.util.Scanner;

public class Menu {

  public static void WarnWrongInput() {
    System.out.println("Masukkan opsi angka dengan input benar");
  }

  public static void delay(long milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt(); // Melempar ulang interrupted exception
    }
  }

  public static void InputFileOption(String title) {
    System.out.println("-----------------------------------");
    System.out.println("             " + title);
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
    String filePath, fileName;
    File checkFile;

    input.nextLine();
    while (true) {
      System.out.print("Masukkan nama file (tanpa ekstensi file): ");
      fileName = input.nextLine();
      filePath = directory + fileName + ".txt";
      checkFile = new File(filePath);

      if (checkFile.exists()) {
        return filePath; // Mengembalikan filePath jika file sudah ada
      } else {
        System.out.println("File yang Anda masukkan tidak ditemukan");
      }
    }
  }



  public static void getAllDataFiles(String currentDir, boolean isImageFile) {
    // Ambil semua nama file dalam current dir
    File dataFile = new File(currentDir);
    File[] listFile = dataFile.listFiles(new FilenameFilter() {
      public boolean accept(File dir, String name) {
        // Filter file berdasarkan apakah itu file gambar atau bukan
        if (isImageFile) {
          // Filter file gambar berdasarkan ekstensi yang sesuai (png, jpg)
          return name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg");
        } else {
          // Filter file bukan gambar berdasarkan ekstensi yang sesuai (txt)
          return name.toLowerCase().endsWith(".txt");
        }
      }
    });

    if (listFile != null) {
      for (File f : listFile) {
        System.out.println(f.getName());
      }
    }
  }


  public static String getOutputFileLoc(Scanner input, String directory) {
    String fileName;
    String filePath = directory;
    boolean shouldContinue = true; // Inisialisasi shouldContinue sebagai true

    while (shouldContinue) {
      System.out.print("Masukkan nama file output (tanpa ekstensi file): ");
      fileName = input.nextLine();
      filePath = directory + fileName + ".txt";
      File output = new File(filePath);

      // Cek apakah file sudah ada
      if (output.exists()) {
        System.out
            .print("File yang Anda masukkan sudah ada. Apakah Anda ingin menindihnya? (y/n): ");
        String overwriteChoice = input.nextLine().toLowerCase();
        if (overwriteChoice.equals("y")) {
          shouldContinue = false; // Keluar dari perulangan jika memilih 'y' untuk menindih
        } else if (!overwriteChoice.equals("n")) {
          System.out.println("Pilihan tidak valid. Harap masukkan 'y' atau 'n'.");
        }
      } else {
        shouldContinue = false; // Keluar dari perulangan jika file tidak ada
      }
    }

    return filePath;
  }



  public static void clearScreen() {
    try {
      new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
