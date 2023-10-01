// import java.io.File;
// import java.util.Scanner;
// import tools.ImageScaling;
// import tools.Matrix;

// public class ImageScallingMenu {
// Scanner globalScanner = new Scanner(System.in);

// // Method untuk menampilkan menu opsi metode determinan
// public void menuOptionScalingImage() {
// System.out.println("-----------------------------------");
// System.out.println("----------Image Scalling----------");
// System.out.println("-----------------------------------");
// System.out.println("1. Run Image Scalling Processing");
// System.out.println("2. Kembali");
// System.out.println("-----------------------------------");
// System.out.print("Masukkan pilihan : ");
// }

// public void runImageScaledProcedure(Scanner globalScanner) {
// ImageScaling scaling = new ImageScaling(0, 0);
// String outputFilePath = "", outputFileName, imageExtension;
// this.menuOptionScalingImage();

// int inputMethod = globalScanner.nextInt();
// // Looping jika input tidak sesuai
// while (inputMethod != 1 && inputMethod != 2) {
// Menu.WarnWrongInput();
// this.menuOptionScalingImage();
// inputMethod = globalScanner.nextInt();
// }

// if (inputMethod == 1) {
// String dirPath, filePath = "";
// dirPath = System.getProperty("user.dir") + "\\test\\data\\";
// System.out.println("-----------------------------------");
// filePath = dirPath + "tes.png";

// double n = 0;
// do {
// System.out.print("\nMasukkan faktor pembesar (bilangan bulat positif): ");
// while (!globalScanner.hasNextInt()) {
// System.out.println("Input harus berupa bilangan bulat positif.");
// globalScanner.next(); // Konsumsi input yang tidak valid
// }
// n = globalScanner.nextInt();
// globalScanner.nextLine(); // Konsumsi newline character
// } while (n <= 0);
// // get Matrix color from image
// Matrix MatrixAlpha = scaling.getMatrixColor("alpha", filePath);
// Matrix MatrixRed = scaling.getMatrixColor("red", filePath);
// Matrix MatrixGreen = scaling.getMatrixColor("green", filePath);
// Matrix MatrixBlue = scaling.getMatrixColor("blue", filePath);

// // Identify the image format type
// int imageType = scaling.getImageFormatType(filePath);

// // Create a border matrix to interpolate
// Matrix borderedMatrixAlpha = scaling.getPaddingImageMatrix(MatrixAlpha);
// Matrix borderedMatrixRed = scaling.getPaddingImageMatrix(MatrixRed);
// Matrix borderedMatrixGreen = scaling.getPaddingImageMatrix(MatrixGreen);
// Matrix borderedMatrixBlue = scaling.getPaddingImageMatrix(MatrixBlue);

// // Scaled matrix
// Matrix scaledImageMatrixAlpha = scaling.getScaledImageMatrix(borderedMatrixAlpha, n);
// Matrix scaledImageMatrixRed = scaling.getScaledImageMatrix(borderedMatrixRed, n);
// Matrix scaledImageMatrixGreen = scaling.getScaledImageMatrix(borderedMatrixGreen, n);
// Matrix scaledImageMatrixBlue = scaling.getScaledImageMatrix(borderedMatrixBlue, n);

// String outputDir = System.getProperty("user.dir") + "\\test\\output\\";

// do {
// System.out.print("\nMasukkan nama file hasil perbesaran (tanpa ekstensi): ");
// outputFileName = globalScanner.nextLine();
// System.out.print("\nMasukkan jenis gambar (jpg/png): ");
// imageExtension = globalScanner.nextLine();

// if (!(imageExtension.equals("png") || imageExtension.equals("jpg"))) {
// System.out.println("Jenis gambar yang Anda masukkan tidak valid!");
// continue; // Continue the loop to re-enter valid input
// }

// outputFilePath = outputDir + outputFileName + "." + imageExtension;

// File output = new File(outputFilePath);

// // Check if the file already exists
// if (output.exists()) {
// System.out
// .print("File yang Anda masukkan sudah ada. Apakah Anda ingin menindihnya? (y/n): ");
// String overwriteChoice = globalScanner.nextLine().toLowerCase();

// if (overwriteChoice.equals("n")) {
// continue; // Continue the loop to enter a different file name
// } else if (!overwriteChoice.equals("y")) {
// System.out.println("Pilihan tidak valid. Harap masukkan 'y' atau 'n'.");
// continue; // Continue the loop to re-enter valid input
// }
// }
// } while (new File(outputFilePath).exists());

// scaling.convertMatrixImage(scaledImageMatrixAlpha, scaledImageMatrixRed,
// scaledImageMatrixGreen, scaledImageMatrixBlue, outputFilePath, imageType, imageExtension);
// System.out.println("\nGambar berhasil diperbesar!\n");

// } else {
// Menu.clearScreen();

// }
// }

// }
