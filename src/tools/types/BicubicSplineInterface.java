package tools.types;

import tools.Matrix;

public interface BicubicSplineInterface {
  void readBicubicSpline();

  void readFileBicubicSpline(final String filePath);

  Matrix getBicubicSplineXMatrix();

  Matrix getCoefficientA(Matrix X, Matrix Y);

  double predictBicubicSplineValue(double x, double y, Matrix aCoefficient);
}
