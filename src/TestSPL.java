import tools.ImageScaling;
import tools.Matrix;


public class TestSPL {


  public static void main(String[] args) {
    // Replace 'your_image_path_here' with the path to your image file.
    String filePath = "./test/data/riil.png";
    String outputPath = "./test/output/riil2.png";

    // Get the color matrix for the 'red' channel.
    Matrix redMatrix = ImageScaling.getMatrixColor("red", filePath);
    Matrix blueM = ImageScaling.getMatrixColor("blue", filePath);
    Matrix greenM = ImageScaling.getMatrixColor("green", filePath);
    Matrix alphaM = ImageScaling.getMatrixColor("alpha", filePath);

    // Get the image format type.
    int imageType = ImageScaling.ImageFormatType(filePath);

    // Create a sample bordered matrix (you can replace this with your own data).
    Matrix borderedImageMatrixRed = ImageScaling.getPaddingMatrix(redMatrix);
    Matrix borderedImageMatrixBlue = ImageScaling.getPaddingMatrix(blueM);
    Matrix borderedImageMatrixGreen = ImageScaling.getPaddingMatrix(greenM);
    Matrix borderedImageMatrixAlpha = ImageScaling.getPaddingMatrix(alphaM);

    // Get the scaled matrix.
    ImageScaling test = new ImageScaling(1, 1);
    Matrix scaledImageMatrixAlpha = test.getScaledMatrix(borderedImageMatrixAlpha, 10);
    Matrix scaledImageMatrixRed = test.getScaledMatrix(borderedImageMatrixRed, 10);
    Matrix scaledImageMatrixGreen = test.getScaledMatrix(borderedImageMatrixGreen, 10);
    Matrix scaledImageMatrixBlue = test.getScaledMatrix(borderedImageMatrixBlue, 10);
    ImageScaling.convertMatrix(scaledImageMatrixAlpha, scaledImageMatrixRed, scaledImageMatrixGreen,
        scaledImageMatrixBlue, outputPath, imageType, "png");

  }

}
