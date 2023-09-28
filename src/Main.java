import java.util.Scanner;
import tools.LinearEquationMenu;

public class Main {
  public static void main(String[] args) {
    Scanner globalScan = new Scanner(System.in);
    System.out.println("Selamat datang di TUBES ALGEO 1");
    System.out.println("by: ucup tampan");
    System.out.println("Anggota:");
    System.out.println("1. (13522015) Yusuf Ardian Sandi ");
    System.out.println("2. (13522032) Tazkia Nizami");
    System.out.println("3. (13522057) Moh Fairuz Alauddin Yahya");
    System.out.println("===================================================================");

    try {
      System.out.println("MENU");
      System.out.println("1. Sistem Persamaan Linier");
      System.out.println("2. Determinan");
      System.out.println("3. Matriks Balikan");
      System.out.println("4. Interpolasi Polinom");
      System.out.println("5. Interpolasi Bicubic Spline");
      System.out.println("6. Regresi Linier Balikan");
      System.out.println("7. Keluar");
      System.out.println("Katakan Pilihanmu:");
      int pilihan = globalScan.nextInt();
      switch (pilihan) {
        case 1:
          LinearEquationMenu tes = new LinearEquationMenu();
          tes.LinearEquation();
          break;
        case 2:
          break;
        case 3:
          break;
        case 4:
          break;
        case 5:
          break;
        case 6:
          System.out.print("Masukkan jumlah peubah x : ");
          int n = globalScan.nextInt();

          System.out.print("Masukkan banyak sampel : ");
          int m = globalScan.nextInt();
          Regression regresi = new Regression(m, n + 1);
          regresi.readRegressionKeyboard(m, n);
          regresi.compileMatrix();
          regresi.displayMatrix();
          regresi.inversMethodSPL();
          System.out.println(regresi.Equation[0]);
          break;
        case 7:
          System.out.println("Progam ditutup...");
          globalScan.close();
          return;
      }
    } catch (Exception e) {
      System.err.println("Error saatnya.");

    }

  }
}
