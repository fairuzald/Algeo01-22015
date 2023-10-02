import java.util.Scanner;
import tools.Matrix;

public class InversMenu {
    Scanner globalScanner = new Scanner(System.in);

    // Method untuk menampilkan menu opsi metode determinan
    public void menuOpsiMetodeInvers() {
        System.out.println("-----------------------------------");
        System.out.println("----------Matriks Balikan----------");
        System.out.println("-----------------------------------");
        System.out.println("           PILIH METODE");
        System.out.println("-----------------------------------");
        System.out.println("1. Metode Gauss Jordan dengan Matriks Identitas");
        System.out.println("2. Metode Matriks Adjoin");
        System.out.println("3. Kembali");
        System.out.println("-----------------------------------");
        System.out.print("Masukkan pilihan : ");
    }

    // Method untuk memilih opsi masukan (file/terminal), melakukan proses
    // perhitungan, menampilkan output di terminal, dan menyimpan output di file
    public void calculatingInversMatrix() {
        // INISIALISASI
        int inputMethod, readInput;
        String saveStatus;
        Matrix masukkanMatrix = new Matrix(0, 0);

        // ALGORITMA
        try {
            // PILIHAN METHOD
            this.menuOpsiMetodeInvers();
            inputMethod = globalScanner.nextInt();
            // Looping jika input tidak sesuai
            while (inputMethod != 1 && inputMethod != 2 && inputMethod != 3) {
                Menu.WarnWrongInput();
                this.menuOpsiMetodeInvers();
                inputMethod = globalScanner.nextInt();
            }
            if (inputMethod == 1 | inputMethod == 2) {
                // KONDISI UNTUK READ FILE
                Menu.InputFileOption("Matriks Balikan");
                readInput = globalScanner.nextInt();
                // Looping jika input tidak sesuai
                while (readInput != 1 && readInput != 2 && readInput != 3) {
                    Menu.WarnWrongInput();
                    Menu.InputFileOption("Matriks Balikan");
                    readInput = globalScanner.nextInt();
                }

                if (readInput == 1) { // Input Dari Terminal
                    System.out.println("-----------------------------------");

                    masukkanMatrix.readMatrix();
                    globalScanner.nextLine();

                    System.out.println("-----------------------------------");
                    System.out.println("Data Matriks Berhasil Terbaca");
                }

                else if (readInput == 2) { // Input Dari File
                    String dirPath, filePath = "";
                    dirPath = System.getProperty("user.dir") + "\\test\\data\\";
                    System.out.println("-----------------------------------");
                    System.out.println("List file valid :");
                    Menu.getAllDataFiles(dirPath, false);
                    System.out.println("------------------------------------");
                    filePath = Menu.getFilePath(globalScanner, dirPath);

                    masukkanMatrix.readFileMatrix(filePath);

                    System.out.println("-----------------------------------");
                    System.out.println("Data Matriks Berhasil Terbaca");
                } else if (readInput == 3) {
                    Menu.clearScreen();
                    // MainMenu();
                }

                System.out.println("Perhitungan Matriks Balikan: ");
                System.out.println("-----------------------------------");
                switch (inputMethod) {
                    case 1 -> {
                        System.out.println("Metode Gauss Jordan dengan Matriks Identitas:");
                        if (masukkanMatrix.isSquare()) {
                            System.out.println("Balikan dari matriks berikut");
                            masukkanMatrix.displayMatrix();
                            System.out.println("adalah ");
                            Matrix inv = masukkanMatrix.inversGJordan();
                            if (inv != null)
                                inv.displayMatrix();
                            System.out.println("\n");
                        } else {
                            System.out.println(
                                    "Bukan merupakan matrix persegi sehingga tidak dapat dicari balikannya");
                        }
                    }
                    case 2 -> {
                        System.out.println("Metode Matriks Adjoin:");
                        if (masukkanMatrix.isSquare()) {
                            System.out.println("Balikan dari matriks berikut");
                            masukkanMatrix.displayMatrix();
                            System.out.println("adalah ");
                            Matrix inv = masukkanMatrix.inversAdjoin();
                            if (inv != null)
                                inv.displayMatrix();
                            System.out.println("\n");
                        } else {
                            System.out.println(
                                    "Bukan merupakan matrix persegi sehingga tidak dapat dicari balikannya");
                        }
                    }
                    case 3 -> {
                        Menu.clearScreen();
                        // Main Menu
                    }

                }
                // MAU DISIMPAN?
                if (inputMethod == 1 || inputMethod == 2) {
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
                        if (inputMethod == 1) { // Metode Gauss Jordan dengan Matriks Identitas
                            masukkanMatrix.writeFileInversGJordan(outputPath);
                        } else if (inputMethod == 2) { // Metode Matriks Adjoin
                            masukkanMatrix.writeFileInversAdjoin(outputPath);
                        }
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
