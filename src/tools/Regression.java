package tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import tools.types.RegressionInterface;

public class Regression extends SPL implements RegressionInterface {
  /* **** ATRIBUTE ***** */
  public Matrix allPoint; // Matrix yang menyimpan semua titik yang dibutuhkan dalam proses regresi
  private float[] estimationPoint; // array of float yang menyimpan semua titik yang akan diuji

  /* **** METHODS ***** */
  /* Konstruktor Matrix */
  public Regression(int rowEff, int colEff) {
    super(rowEff, colEff);
  }

  public void readRegressionKeyboard(int m, int n) {
    /* KAMUS LOKAL */
    int i, j;
    Scanner input = new Scanner(System.in);
    this.estimationPoint = new float[n];

    /* ALGORITMA */
    try {
      Matrix mat = new Matrix(m, n + 1);

      for (i = 0; i < n; i++) {
        System.out.println("Masukkan nilai x" + (i + 1) + "i:");
        for (j = 0; j < m; j++) {
          float inputan = input.nextFloat();
          mat.setElmt(j, i, inputan);
        }
      }

      System.out.println("Masukkan nilai yi:");
      for (j = 0; j < m; j++) {
        float inputan = input.nextFloat();
        mat.setElmt(j, n, inputan);
      }

      System.out.println("Masukkan nilai xki (nilai x yang akan diuji):");
      for (j = 0; j < n; j++) {
        float inputan = input.nextFloat();
        this.estimationPoint[j] = inputan;
      }

      this.allPoint = mat.copyMatrix();
      input.close();
    } catch (Exception e) {
      System.err.println("Error saat readRegression.");
    }
  }

  public void readRegressionFile(final String filePath, int n, int m) {
    try {
      /* KAMUS */
      File file = new File(filePath);
      int i, j;

      Scanner scan = new Scanner(file);

      /* ALGORITMA */
      Matrix mat = new Matrix(m, n + 1);
      this.estimationPoint = new float[n];

      scan.nextLine();

      for (i = 0; i < m; i++) {
        for (j = 0; j < n + 1; j++) {
          if (scan.hasNextFloat()) {
            float inputan = scan.nextFloat();
            mat.setElmt(i, j, inputan);
          }
        }
        scan.nextLine();
      }

      for (j = 0; j < n; j++) {
        if (scan.hasNextFloat()) {
          float inputan = scan.nextFloat();
          this.estimationPoint[j] = inputan;
        }
      }

      this.allPoint = mat.copyMatrix();
      scan.close();
    } catch (Exception e) {
      e.printStackTrace();;
    }

  }

  public void compileMatrix() {
    /* KAMUS */
    int i, j;
    float[] sigmaAllPointOne;
    float[][] sigmaAllPointTwo;
    /* ALGORITMA */
    sigmaAllPointOne = new float[this.allPoint.getColEff()];
    sigmaAllPointTwo = new float[this.allPoint.getColEff() - 1][this.allPoint.getColEff()];

    for (i = 0; i < this.allPoint.getColEff(); i++) {
      sigmaAllPointOne[i] = SigmaRegressionOne(i);
    }

    for (i = 0; i < this.allPoint.getColEff() - 1; i++) {
      for (j = 0; j < this.allPoint.getColEff(); j++) {
        sigmaAllPointTwo[i][j] = SigmaRegressionTwo(i, j);
      }
    }

    for (i = 0; i < this.allPoint.getColEff(); i++) {
      for (j = 0; j < this.allPoint.getColEff() + 1; j++) {
        if (i == 0) {
          if (j == 0) {
            this.setElmt(i, j, allPoint.getRowEff());
          } else {
            this.setElmt(i, j, sigmaAllPointOne[j - 1]);
          }
        } else {
          if (j == 0) {
            this.setElmt(i, j, sigmaAllPointOne[i - 1]);
          } else {
            this.setElmt(i, j, sigmaAllPointTwo[i - 1][j - 1]);
          }
        }
      }
    }
  }

  public float estimate() {
    float result = 0;
    int i;
    for (i = 0; i < this.Solution.length; i++) {
      if (i == 0) {
        result = this.Solution[i];
      } else {
        result += this.Solution[i] * this.estimationPoint[i - 1];
      }
    }
    return result;
  }

  private float SigmaRegressionOne(int kolom) {
    /* KAMUS */
    float result = 0;
    int i;
    /* ALGORITMA */
    for (i = 0; i < this.allPoint.getRowEff(); i++) {
      result += this.allPoint.getElmt(i, kolom);
    }

    return result;
  }

  private float SigmaRegressionTwo(int kolom1, int kolom2) {
    /* KAMUS */
    float result = 0;
    int i;
    /* ALGORITMA */
    for (i = 0; i < this.allPoint.getRowEff(); i++) {
      result += this.allPoint.getElmt(i, kolom1) * this.allPoint.getElmt(i, kolom2);
    }

    return result;
  }

  public void displayRegression() {
    int i;
    int countParametrix = 0;
    String sentence;

    System.out.println("\nSolusi dari Multiple Linear Regression ini adalah: ");
    if (this.Status[0] == categorySolution.UNDEFINED) {
      System.out.println("Permasalahan Regresi tidak memiliki solusi.");
    } else {
      for (i = 0; i < this.Equation.length; i++) {
        // JIka solusi adalah parametrix maka hitung jumlahnya
        if (this.Status[i] == categorySolution.PARAMETRIX) {
          countParametrix++;
        }
        sentence = "B_" + (i) + " = " + this.Equation[i];
        System.out.println(sentence);

      }
      if (countParametrix > 0) {
        char params = 'a';
        sentence = "\nDengan";
        for (i = countParametrix; i > 0; i--) {
          sentence += String.valueOf(params);
          params++;
          if (countParametrix > 1) {
            sentence += ",";
          }
        }
        sentence += "anggota bilangan real";
        System.out.println(sentence);
      }
    }

    System.out.println("Dengan nilai estimasi titik yang diuji adalah " + this.estimate() + "\n");
  }

  public void writeFileRegression(final String filePath) {
    int i;
    int countParametrix = 0;
    String sentence;
    FileWriter output = null;
    try {
      output = new FileWriter(filePath);
      output.write("Solusi dari Multiple Linear Regression ini adalah: \n");
      if (this.Status[0] == categorySolution.UNDEFINED) {
        output.write("Permasalahan Regresi tidak memiliki solusi.");
      } else {
        for (i = 0; i < this.Equation.length; i++) {
          // Jika solusi adalah parametrix maka hitung jumlahnya
          if (this.Status[i] == categorySolution.PARAMETRIX) {
            countParametrix++;
          }
          sentence = "B_" + (i) + " = " + this.Equation[i];
          output.write(sentence);
          if (i != this.Equation.length - 1) {
            output.write(System.lineSeparator());

          }
        }
      }
      // Tambahkan kalimat parametrix solution
      if (countParametrix > 0) {
        char params = 'a';
        sentence = "Dengan ";
        for (i = countParametrix; i > 0; i--) {
          sentence += String.valueOf(params);
          params++;
          if (i > 1) {
            sentence += ", ";
          }
        }
        sentence += " anggota bilangan real";

        output.write(sentence + "\n");
      }

      output.write("\nEstimasi nilai yk = " + this.estimate() + "\n");
      output.close();
      System.out.println(
          "Berhasil menyimpan Solusi Regresi pada folder test, file \"" + filePath + "\".");
    } catch (IOException e) {
      System.out.println("Something went wrong.");
      e.printStackTrace();
    }
  }

  public void regressionDriver() {
    int pilihanInput, pilihanOutput;
    Regression reg = new Regression(0, 0);
    Scanner scan = new Scanner(System.in);

    System.out.println("Metode Input: ");
    System.out.println("1. Keyboard (melalui terminal)");
    System.out.println("2. File");
    System.out.println("Katakan pilihanmu: ");
    pilihanInput = Integer.parseInt(scan.nextLine());

    System.out.println("Metode Output: ");
    System.out.println("1. Terminal");
    System.out.println("2. File Output");
    System.out.println("Katakan pilihanmu: ");
    pilihanOutput = Integer.parseInt(scan.nextLine());

    switch (pilihanInput) {
      case 1:
        int n = 0, m = 0;
        System.out.print("Masukkan jumlah peubah x : ");
        n = Integer.parseInt(scan.nextLine());

        System.out.print("Masukkan banyak sampel : ");
        m = Integer.parseInt(scan.nextLine());

        reg = new Regression(n + 1, n + 2);
        reg.readRegressionKeyboard(m, n);
        reg.compileMatrix();
        reg.gaussMethodSPL();
        break;
      case 2:
        n = 0;
        m = 0;
        File file = new File("test/data/testRegresi2.txt");
        try {
          Scanner scanFile = new Scanner(file);
          if (scanFile.hasNextInt()) {
            n = scanFile.nextInt();
          }
          if (scanFile.hasNextInt()) {
            m = scanFile.nextInt();
          }
          reg = new Regression(n + 1, n + 2);
          reg.readRegressionFile("test/data/testRegresi2.txt", n, m);
          reg.compileMatrix();
          reg.gaussMethodSPL();

          scanFile.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
        break;
    }

    switch (pilihanOutput) {
      case 1:
        reg.displayRegression();
        break;
      case 2:
        reg.writeFileRegression("./test/output/hasilRegresi.txt");
        break;
    }

    scan.close();
  }
}
