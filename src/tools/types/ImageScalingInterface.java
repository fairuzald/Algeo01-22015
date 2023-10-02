package tools.types;

import java.awt.image.BufferedImage;
import tools.Matrix;

public interface ImageScalingInterface {
    Matrix getMatrixColor(String color, String filePath);
    int getImageFormatType(String filePath);
    Matrix getXImageMatrix();
    Matrix getCoeffImageMatrix(Matrix X, Matrix D, Matrix I);
    Matrix getPaddingImageMatrix(Matrix m);
    Matrix getInterpolaterImageMatrix(Matrix borderedMatrix, int x, int y);
    Matrix getScaledImageMatrix(Matrix borderedMatrix, double n);
    void saveImageToFile(Matrix alphaMatrix, Matrix redMatrix, Matrix greenMatrix, Matrix blueMatrix, String filePath, int imageType, String imgExtension);
}

