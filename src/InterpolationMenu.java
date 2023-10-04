import java.util.Scanner;
import tools.Interpolation;

public class InterpolationMenu {

    public void calculatingPolinomialInterpolation() {
        // INISIALISASI
        int readInput;
        String saveStatus;
        Interpolation interpolasi = new Interpolation();
        Scanner globalScanner = new Scanner(System.in);

        // ALGORITMA
        try {

            // KONDISI UNTUK READ FILE
            Menu.InputFileOption("Interpolasi Polinomial");
            readInput = globalScanner.nextInt();
            // Looping jika input tidak sesuai
            while (readInput != 1 && readInput != 2 && readInput != 3) {
                Menu.WarnWrongInput();
                Menu.InputFileOption("Interpolasi Polinomial");
                readInput = globalScanner.nextInt();
            }

            if (readInput == 1) { // Input Dari Terminal
                System.out.println("-----------------------------------");

                interpolasi.readInputInterpolasi();

                System.out.println("-----------------------------------");
                System.out.println("Data Interpolasi Polinomial Berhasil Terbaca");
            }

            else if (readInput == 2) { // Input Dari File
                String dirPath, filePath = "";
                dirPath = System.getProperty("user.dir") + "\\test\\data\\";
                System.out.println("-----------------------------------");
                System.out.println("List file valid :");
                Menu.getAllDataFiles(dirPath, false);
                System.out.println("------------------------------------");
                filePath = Menu.getFilePath(globalScanner, dirPath);

                interpolasi.readFileMatrixInterpolasi(filePath);

                System.out.println("-----------------------------------");
                System.out.println("Data Interpolasi Polinomial Berhasil Terbaca");
            } else if (readInput == 3) {
                Menu.clearScreen();
                // MainMenu();
            }

            System.out.println("-----------------------------------");
            System.out.println("Perhitungan Interpolasi Polinomial diproses");
            interpolasi.buatMatrixSPL();
            interpolasi.cariTaksiranY();

            System.out.println("-----------------------------------");
            System.out.println("Hasil interpolasi polinomial sebagai berikut:");
            interpolasi.cetakOutputInterpolasi();

            // MAU DISIMPAN?
            System.out.println("-----------------------------------");
            System.out.print("Simpan Hasil? (y/n) : ");

            saveStatus = globalScanner.nextLine();
            while (!saveStatus.equals("y") && !saveStatus.equals("n")) {
                System.out.println("Ulangi! Input haruslah 'y' atau 'n'");
                System.out.print("Simpan Hasil? (y/n) : ");
                saveStatus = globalScanner.nextLine();
            }

            if (saveStatus.equals("y")) {
                System.out.println("-----------------------------------");
                String outputDir = System.getProperty("user.dir") + "\\test\\output\\";
                String outputPath = Menu.getOutputFileLoc(globalScanner, outputDir);

                interpolasi.writeFileInterpolation(outputPath);

            }

        } catch (Exception e) {
            System.out.println("Terjadi Error");
            e.printStackTrace();
        }
    }
}
