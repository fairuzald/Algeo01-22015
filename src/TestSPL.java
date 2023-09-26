import tools.SPL;


public class TestSPL {

  public static void main(String[] args) {
    // Membuat matriks SPL
    SPL spl = new SPL(4, 5); // Ganti dengan ukuran matriks SPL Anda

    // Mengisi matriks dengan koefisien SPL
    spl.setElmt(0, 0, 1);
    spl.setElmt(0, 1, 1);
    spl.setElmt(0, 2, -1);
    spl.setElmt(0, 3, -1);
    spl.setElmt(0, 4, 1);

    spl.setElmt(1, 0, 2);
    spl.setElmt(1, 1, 5);
    spl.setElmt(1, 2, -7);
    spl.setElmt(1, 3, -5);
    spl.setElmt(1, 4, -2);

    spl.setElmt(2, 0, 2);
    spl.setElmt(2, 1, -1);
    spl.setElmt(2, 2, 1);
    spl.setElmt(2, 3, 3);
    spl.setElmt(2, 4, 4);
    spl.setElmt(3, 0, 5);
    spl.setElmt(3, 1, 2);
    spl.setElmt(3, 2, -4);
    spl.setElmt(3, 3, 2);
    spl.setElmt(3, 4, 6);
    SPL testcase2 = new SPL(0, 0);
    testcase2.readFileSPL("testcase2a.txt");

    // Menyelesaikan SPL menggunakan eliminasi Gauss
    // spl.cramerMethodSPL();
    // testcase2.gJordanMethodSPL();
    testcase2.gaussMethodSPL();

    // Menampilkan solusi SPL
    System.out.println("\nSolusi SPL:");
    testcase2.displaySPL();
    testcase2.writeFileSPL("testcase2aGauss.txt");
  }

}
