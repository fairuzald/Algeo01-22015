import tools.BicubicSpline;
import tools.Matrix;
import tools.SPL;

public class TestSPL {

  public static void main(String[] args) {
    // Create an instance of BicubicSpline
    BicubicSpline spline = new BicubicSpline(16, 16);


    spline.readBicubicSpline();
    spline.predictBicubicSplineValue();
    spline.displayBicubicSpline();
  }

}
