import java.util.Scanner;
import tools.BicubicSpline;

public class BicubicSplineMenu {
  public void BicubicSpline() {
    int fileOption;
    String saveStatus;
    BicubicSpline bic = new BicubicSpline(16, 16);
    try {
      Scanner inputScanner = new Scanner(System.in);
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

        bic.readBicubicSpline(inputScanner);

        inputScanner.nextLine();
        System.out.println("-----------------------------------");
        System.out.println("Data interpolasi bicubic spline berhasil terbaca");
        System.out.println("-----------------------------------");

        bic.predictBicubicSplineValue();
        bic.displayBicubicSpline();


      }

      else if (fileOption == 2) {
        String dirPath, filePath = "";
        dirPath = System.getProperty("user.dir") + "\\test\\data\\";
        System.out.println("-----------------------------------");
        System.out.println("List file valid :");
        Menu.getAllDataFiles(dirPath, false);
        System.out.println("------------------------------------");
        filePath = Menu.getFilePath(inputScanner, dirPath);

        bic.readFileBicubicSpline(filePath);

        System.out.println("-----------------------------------");
        System.out.println("Data interpolasi file bicubic spline berhasil terbaca");
        System.out.println("-----------------------------------");

        bic.predictBicubicSplineValue();
        bic.displayBicubicSpline();
      } else if (fileOption == 3) {
        Menu.clearScreen();
        inputScanner.close();
        Menu.clearScreen();
        return;
        // MainMenu();
      }

      // MAU DISIMPAN?
      if (fileOption == 1 || fileOption == 2) {
        System.out.println("-----------------------------------");
        System.out.print("Simpan Hasil? (y/n) : ");


        saveStatus = inputScanner.nextLine();
        while (!saveStatus.equals("y") && !saveStatus.equals("n")) {
          System.out.println("Ulangi! Input haruslah 'y' atau 'n'");
          System.out.print("Simpan Hasil? (y/n) : ");
          saveStatus = inputScanner.nextLine();
        }
        if (saveStatus.equals("y")) {
          System.out.println("-----------------------------------");
          String outputDir = System.getProperty("user.dir") + "\\test\\output\\";
          String outputPath = Menu.getOutputFileLoc(inputScanner, outputDir);
          bic.writeFileBicubicSpline(outputPath);
        }
      } else {
        Menu.clearScreen();
      }

    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
