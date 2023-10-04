import java.util.Scanner;
import tools.Matrix;

public class DeterminantMenu {
    Scanner globalScanner = new Scanner(System.in);

    // Method untuk menampilkan menu opsi metode determinan
    public void menuOpsiMetodeDeterminan() {
        System.out.println("-----------------------------------");
        System.out.println("---------Determinant Matrix--------");
        System.out.println("-----------------------------------");
        System.out.println("           PILIH METODE");
        System.out.println("-----------------------------------");
        System.out.println("1. Metode Ekspansi Kofaktor");
        System.out.println("2. Metode Reduksi Baris");
        System.out.println("3. Kembali");
        System.out.println("-----------------------------------");
        System.out.print("Masukkan pilihan : ");
    }

    // Method untuk memilih opsi masukan (file/terminal), melakukan proses
    // perhitungan, menampilkan output di terminal, dan menyimpan output di file
    public void calculatingDeterminant() {
        // INISIALISASI
        int inputMethod, readInput;
        String saveStatus;
        Matrix determinanMatrix = new Matrix(0, 0);

        // ALGORITMA
        try {
            // PILIHAN METHOD
            this.menuOpsiMetodeDeterminan();
            inputMethod = globalScanner.nextInt();
            // Looping jika input tidak sesuai
            while (inputMethod != 1 && inputMethod != 2 && inputMethod != 3) {
                Menu.WarnWrongInput();
                this.menuOpsiMetodeDeterminan();
                inputMethod = globalScanner.nextInt();
            }
            if (inputMethod == 1 | inputMethod == 2) {
                // KONDISI UNTUK READ FILE
                Menu.InputFileOption("Determinan");
                readInput = globalScanner.nextInt();
                // Looping jika input tidak sesuai
                while (readInput != 1 && readInput != 2 && readInput != 3) {
                    Menu.WarnWrongInput();
                    Menu.InputFileOption("Determinan");
                    readInput = globalScanner.nextInt();
                }

                if (readInput == 1) { // Input Dari Terminal
                    System.out.println("-----------------------------------");

                    determinanMatrix.readMatrix();
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

                    determinanMatrix.readFileMatrix(filePath);

                    System.out.println("-----------------------------------");
                    System.out.println("Data Matriks Berhasil Terbaca");
                } else if (readInput == 3) {
                    Menu.clearScreen();
                    return;
                }

                System.out.println("Perhitungan Determinan: ");
                System.out.println("-----------------------------------");
                switch (inputMethod) {
                    case 1 -> {
                        System.out.println("Metode Ekspansi Kofaktor:");
                        if (determinanMatrix.isSquare()) {
                            System.out.println("Determinan dari matriks berikut");
                            determinanMatrix.displayMatrix();
                            System.out.println("adalah " + determinanMatrix.determinantCofactor());
                        } else {
                            determinanMatrix.determinantCofactor();
                        }
                    }
                    case 2 -> {
                        System.out.println("Metode Reduksi Baris:");
                        if (determinanMatrix.isSquare()) {
                            System.out.println("Determinan dari matriks berikut");
                            determinanMatrix.displayMatrix();
                            System.out.println(
                                    "adalah " + determinanMatrix.determinantUpperTriangle());
                        } else {
                            determinanMatrix.determinantUpperTriangle();
                        }
                    }
                    case 3 -> {
                        Menu.clearScreen();
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
                        if (inputMethod == 1) { // Metode Ekspansi Kofaktor
                            determinanMatrix.writeFileDeterminantCofactor(outputPath);
                        } else if (inputMethod == 2) { // Metode Reduksi Baris
                            determinanMatrix.writeFileDeterminantUpperTriangle(outputPath);
                        }
                    }
                }

            } else {
                Menu.clearScreen();
            }
        } catch (Exception e) {
            System.out.println("Terjadi Error");
            e.printStackTrace();
        }
    }
}
