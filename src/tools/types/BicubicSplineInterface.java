package tools.types;

import tools.Matrix;

public interface BicubicSplineInterface {
  void readBicubicSpline();

  void readFileBicubicSpline(final String filePath);

  Matrix getBicubicSplineXMatrix();

  Matrix getCoefficientA(Matrix X, Matrix Y);

  void predictBicubicSplineValue();
}
