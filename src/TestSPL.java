import tools.BicubicSpline;
import tools.Matrix;
import tools.SPL;


public class TestSPL {


  public static void main(String[] args) {
    // Create an instance of BicubicSpline
    BicubicSpline spline = new BicubicSpline(16, 16);

    // Create a Matrix instance
    Matrix tes = new Matrix(0, 0);
    tes.readFileMatrix("./test/data/testcase2b.txt");
    tes.gaussElimination();

    // Call the generatePartial method or other necessary methods here
    // ...

    // Display the Matrix contents
    tes.displayMatrix();
  }

}
