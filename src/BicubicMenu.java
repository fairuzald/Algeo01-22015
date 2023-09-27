import java.io.File;
import java.util.Scanner;

public class BicubicMenu {
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

  public void InputOption(String title) {
    System.out.println("-----------------------------------");
    System.out.println("               " + title);
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

  public String getOutputFileLoc(Scanner input, String directory) {
    String fileName;

    do {
      System.out.print("Masukkan nama file output: ");
      fileName = input.nextLine();
      String dirPath = directory + fileName;
      File output = new File(dirPath);

      if (output.exists()) {
        System.out.println("File yang anda masukkan sudah ada");
      }
    } while (new File(directory + fileName).exists());

    return directory + fileName;
  }

  public void clearScreen() {
    try {
      new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
