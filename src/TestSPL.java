import tools.ImageScaling;
import tools.Matrix;


public class TestSPL {


  public static void main(String[] args) {
    // Replace 'your_image_path_here' with the path to your image file.
    String filePath = "./test/data/tes.png";
    String outputPath = "./test/output/teseaaas2.png";

    ImageScaling test = new ImageScaling(1, 1);
    // Get the color matrix for the 'red' channel.
    Matrix redMatrix = test.getMatrixColor("red", filePath);
    Matrix blueM = test.getMatrixColor("blue", filePath);
    Matrix greenM = test.getMatrixColor("green", filePath);
    Matrix alphaM = test.getMatrixColor("alpha", filePath);

    // Get the image format type.
    int imageType = test.getImageFormatType(filePath);

    // Create a sample bordered matrix (you can replace this with your own data).
    Matrix borderedImageMatrixRed = test.getPaddingImageMatrix(redMatrix);
    Matrix borderedImageMatrixBlue = test.getPaddingImageMatrix(blueM);
    Matrix borderedImageMatrixGreen = test.getPaddingImageMatrix(greenM);
    Matrix borderedImageMatrixAlpha = test.getPaddingImageMatrix(alphaM);

    // Get the scaled matrix.
    Matrix scaledImageMatrixAlpha = test.getScaledImageMatrix(borderedImageMatrixAlpha, 2);
    Matrix scaledImageMatrixRed = test.getScaledImageMatrix(borderedImageMatrixRed, 2);
    Matrix scaledImageMatrixGreen = test.getScaledImageMatrix(borderedImageMatrixGreen, 2);
    Matrix scaledImageMatrixBlue = test.getScaledImageMatrix(borderedImageMatrixBlue, 2);
    test.convertMatrixImage(scaledImageMatrixAlpha, scaledImageMatrixRed, scaledImageMatrixGreen,
        scaledImageMatrixBlue, outputPath, imageType, "png");

  }

}
