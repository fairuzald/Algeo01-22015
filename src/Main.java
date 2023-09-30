import java.util.Scanner;

import tools.Matrix;
import tools.Regression;

public class Main {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.println("Selamat datang di TUBES ALGEO 1");
    System.out.println("by: ucup tampan");
    System.out.println("Anggota:");
    System.out.println("1. (13522015) Yusuf Ardian Sandi ");
    System.out.println("2. (13522032) Tazkia Nizami");
    System.out.println("3. (13522057) Moh Fairuz Alauddin Yahya");
    System.out.println("===================================================================");

    while (true) {
      System.out.println("MENU");
      System.out.println("1. Sistem Persamaan Linier");
      System.out.println("2. Determinan");
      System.out.println("3. Matriks Balikan");
      System.out.println("4. Interpolasi Polinom");
      System.out.println("5. Interpolasi Bicubic Spline");
      System.out.println("6. Regresi Linier Balikan");
      System.out.println("7. Keluar");
      System.out.println("Katakan Pilihanmu:");
      int pilihan = scan.nextInt();
      switch (pilihan) {
        case 1:
          LinearEquationMenu tes = new LinearEquationMenu();
          tes.LinearEquation();
          break;
        case 2:
          DeterminantMenu determinan = new DeterminantMenu();
          determinan.calculatingDeterminant();
          break;
        case 3:
          InversMenu inversMatrix = new InversMenu();
          inversMatrix.calculatingInversMatrix();
          break;
        case 4:
          InterpolationMenu interpolasiPolinomial = new InterpolationMenu();
          interpolasiPolinomial.calculatingPolinomialInterpolation();
          break;
        case 5:
          BicubicMenu bicubicAsikJos = new BicubicMenu();
          bicubicAsikJos.BicubicSpline(scan);
          break;
        case 6:
          Regression regresiEuy = new Regression(0, 0);
          regresiEuy.regressionDriver();
          break;
        case 7:
          System.out.println("Progam ditutup...");
          scan.close();
          return;
      }
    }

  }
}
