import java.io.File;
import java.util.Scanner;
import tools.ImageScaling;
import tools.Matrix;

public class ImageScallingMenu {

  // Method untuk menampilkan menu opsi metode determinan
  public void menuOptionScalingImage() {
    System.out.println("-----------------------------------");
    System.out.println("----------Image Scalling----------");
    System.out.println("-----------------------------------");
    System.out.println("1. Run Image Scalling Processing");
    System.out.println("2. Kembali");
    System.out.println("-----------------------------------");
  }

  public String validateFileName(Scanner scanner, String dirPath) {
    String fileName;
    boolean validFile = false;

    do {
      System.out.println("Masukkan nama file beserta ekstensinya: ");
      fileName = scanner.nextLine();
      String filePath = dirPath + fileName;
      File file = new File(filePath);

      // Validasi extension file
      if (fileName.toLowerCase().endsWith(".png") || fileName.toLowerCase().endsWith(".jpg")) {
        if (!file.exists()) {
          System.out.println("File tidak ditemukan. Masukkan nama file yang valid.");
        } else {
          validFile = true;
        }
      } else {
        System.out.println("Ekstensi file tidak valid. Harus berupa .png atau .jpg.");
      }
    } while (!validFile);

    return fileName;
  }

  public static String getOutputFilePath(Scanner scanner, String directory, String imgExtension) {
    String outputFileName = "";
    String filePath = "";
    File outputFile;

    do {
      System.out.print("Masukkan nama file hasil perbesaran (tanpa ekstensi): ");
      outputFileName = scanner.nextLine();
      filePath = directory + outputFileName + "." + imgExtension;

      outputFile = new File(filePath);

      // Cek apakah file sudah ada
      if (outputFile.exists()) {
        System.out
            .print("File yang Anda masukkan sudah ada. Apakah Anda ingin menindihnya? (y/n): ");
        String overwriteChoice = scanner.nextLine().toLowerCase();

        if (overwriteChoice.equals("n")) {
          // Jika pengguna memilih "n", lakukan loop untuk meminta nama file yang berbeda
          continue;
        } else if (!overwriteChoice.equals("y")) {
          System.out.println("Pilihan tidak valid. Harap masukkan 'y' atau 'n'.");
          continue; // Lanjutkan iterasi untuk meminta input yang valid
        }
      }
    } while (outputFile.exists());

    return filePath;
  }

  public static String getImageExtension(Scanner scanner) {
    String imageExtension = "";

    do {
      System.out.print("\nMasukkan jenis gambar output yang ingin dihasilkan (jpg/png): ");
      imageExtension = scanner.nextLine();

      if (!(imageExtension.equals("png") || imageExtension.equals("jpg"))) {
        System.out.println("Jenis gambar yang Anda masukkan tidak valid!");
      }
    } while (!(imageExtension.equals("png") || imageExtension.equals("jpg")));

    return imageExtension;
  }


  public void runImageScaledProcedure() {
    Scanner globalScanner = new Scanner(System.in);
    String outputFilePath = "", outputFileName, imageExtension;
    ImageScaling scaling = new ImageScaling(0, 0);
    try {
      this.menuOptionScalingImage();

      int inputMethod;
      do {
        System.out.print("Masukkan pilihan : ");
        inputMethod = Integer.parseInt(globalScanner.nextLine());
        if (inputMethod != 1 && inputMethod != 2) {
          Menu.WarnWrongInput();
        }
      } while (inputMethod != 1 && inputMethod != 2);

      if (inputMethod == 1) {
        String dirPath, filePath = "";

        dirPath = System.getProperty("user.dir") + "\\test\\data\\";
        Menu.getAllDataFiles(dirPath, true);
        System.out.println("-----------------------------------");

        String fileName = validateFileName(globalScanner, dirPath);
        filePath = dirPath + fileName;

        // Validasi input skala pembesaran
        double n = 0;
        do {
          System.out.print("\nMasukkan faktor pembesar (bilangan bulat positif): ");
          while (!globalScanner.hasNextInt()) {
            System.out.println("Input harus berupa bilangan bulat positif.");
            globalScanner.next(); // Konsumsi input yang tidak valid
          }
          n = globalScanner.nextInt();
          globalScanner.nextLine(); // Konsumsi newline character
        } while (n <= 0);

        // ALGORITMA PEMROSESAN
        // get Matrix color from image
        Matrix MatrixAlpha = scaling.getMatrixColor("alpha", filePath);
        Matrix MatrixRed = scaling.getMatrixColor("red", filePath);
        Matrix MatrixGreen = scaling.getMatrixColor("green", filePath);
        Matrix MatrixBlue = scaling.getMatrixColor("blue", filePath);

        // Identify the image format type
        int imageType = scaling.getImageFormatType(filePath);

        // Create a border matrix to interpolate
        Matrix borderedMatrixAlpha = scaling.getPaddingImageMatrix(MatrixAlpha);
        Matrix borderedMatrixRed = scaling.getPaddingImageMatrix(MatrixRed);
        Matrix borderedMatrixGreen = scaling.getPaddingImageMatrix(MatrixGreen);
        Matrix borderedMatrixBlue = scaling.getPaddingImageMatrix(MatrixBlue);

        // Scaled matrix
        Matrix scaledImageMatrixAlpha = scaling.getScaledImageMatrix(borderedMatrixAlpha, n);
        Matrix scaledImageMatrixRed = scaling.getScaledImageMatrix(borderedMatrixRed, n);
        Matrix scaledImageMatrixGreen = scaling.getScaledImageMatrix(borderedMatrixGreen, n);
        Matrix scaledImageMatrixBlue = scaling.getScaledImageMatrix(borderedMatrixBlue, n);

        String outputDir = System.getProperty("user.dir") + "\\test\\output\\";
        imageExtension = getImageExtension(globalScanner);
        outputFilePath = getOutputFilePath(globalScanner, outputDir, imageExtension);


        scaling.saveImageToFile(scaledImageMatrixAlpha, scaledImageMatrixRed,
            scaledImageMatrixGreen, scaledImageMatrixBlue, outputFilePath, imageType,
            imageExtension);
      } else {
        // Menu.clearScreen();
      }
      globalScanner.close();
    } catch (Exception e) {
      System.out.println("An error occurred.");
    }
  }

}
