package tools;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import tools.types.ImageScalingInterface;

public class ImageScaling extends BicubicSpline implements ImageScalingInterface {
  public ImageScaling(int nRows, int nCols) {
    super(nRows, nCols);
  }

  public Matrix getMatrixColor(final String color, final String filePath) {
    Matrix colorMatrix;
    int width = 0, height = 0, shifting;
    BufferedImage image = null;
    File inputFile = null;
    // Mengambil jumlah pixel dari gambar
    try {
      inputFile = new File(filePath);
      image = ImageIO.read(inputFile);
      width = image.getWidth();
      height = image.getHeight();

    } catch (IOException e) {
      System.out.println(e);
    }

    colorMatrix = new Matrix(height, width);

    // Melakukan formatting untuk pengambilan biner value dari pixel dengan shifting bitwise
    switch (color.toLowerCase()) {
      // Mengambil byte kedua dari int
      case "red":
        shifting = 16;
        break;
      // Mengambil byte ketigadari int
      case "green":
        shifting = 8;
        break;
      // Mengambil byte terakhir dari int
      case "blue":
        shifting = 0;
        break;
      // Mengambil byte pertama dari int
      default:
        shifting = 24;
        break;
    }


    // Melakukan pengambilan biner value dari pixel
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int pixel = image.getRGB(j, i);
        // Meelakukan shifting bitwise untuk mendapatkan biner value dari pixel dan memastikan
        // bernilai [0..255]
        int intValue = (pixel >> shifting) & 0xff;
        colorMatrix.setElmt(i, j, (double) intValue);
      }
    }

    return colorMatrix;
  }

  public int getImageFormatType(final String filePath) {
    File inputFile = null;
    BufferedImage image = null;
    try {
      inputFile = new File(filePath);
      image = ImageIO.read(inputFile);
    } catch (IOException e) {
      System.out.println(e);
    }
    // Mengembalikan type file yang direpresentasikan di library buffered image
    return image.getType();
  }

  public Matrix getXImageMatrix() {
    Matrix mKoef = new Matrix(16, 16);
    int x = 0;
    int y = 0;
    double insert = 0;
    int i, j, k;

    for (k = 0; k < 16; k++) {
      x = (k % 2);
      y = (k % 4 > 1) ? 1 : 0;
      int col = 0;
      for (i = -1; i <= 2; i++) {
        for (j = -1; j <= 2; j++) {
          col = (i + 1) + (j + 1) * 4;
          switch (k / 4) {
            // fungsi
            case 0:
              insert = (x == i && y == j) ? 1 : 0;

              mKoef.setElmt(k, col, insert);
              break;
            // Turunan terhadap x
            case 1:
              if (i + 1 == x && j == y) {
                insert = -0.5;
              } else if (i - 1 == x && j == y) {
                insert = 0.5;
              } else {
                insert = 0;
              }
              mKoef.setElmt(k, col, insert);
              break;
            // Turunan terhadap y
            case 2:
              if (i == x && j == y + 1) {
                insert = 0.5;
              } else if (i == x && j == y - 1) {
                insert = -0.5;
              } else {
                insert = 0;
              }
              mKoef.setElmt(k, col, insert);
              break;
            // Turunan x terhadap y
            default:
              if (i - 1 == x && j - 1 == y || (i == x && j == y)) {
                insert = 0.25;
              } else if (i == x - 1 && j == y || (i == x && j == y - 1)) {
                insert = -0.25;
              } else {
                insert = 0;
              }
              mKoef.setElmt(k, col, insert);
              break;
          }
        }
      }
    }
    return mKoef;
  }

  public Matrix getCoeffImageMatrix(final Matrix X, final Matrix D, final Matrix I) {
    Matrix aCoef, xInv;
    xInv = X.inversGJordan();
    aCoef = this.multiplyMatrix(xInv, D);
    aCoef = this.multiplyMatrix(aCoef, I);
    return aCoef;
  }

  public Matrix getPaddingImageMatrix(final Matrix m) {
    // Menyiapkan border matix dengan menambahkan padding sebanyak 2 baris y dan 2 kolom x
    Matrix borderM = new Matrix(m.getRowEff() + 4, m.getColEff() + 4);

    // Mengambil nilai dari matriks asli dan memasukkannya dalam gabungan border matrix
    for (int i = 0; i < m.getRowEff() + 4; i++) {
      for (int j = 0; j < m.getColEff() + 4; j++) {
        int rowIndex = Math.min(Math.max(i - 2, 0), m.getRowEff() - 1);
        int colIndex = Math.min(Math.max(j - 2, 0), m.getColEff() - 1);
        borderM.setElmt(i, j, m.getElmt(rowIndex, colIndex));
      }
    }
    return borderM;
  }

  public Matrix getInterpolaterImageMatrix(final Matrix borderedMatrix, final int x, final int y) {
    Matrix I = new Matrix(16, 1);
    // Melakukan interpolasi menggunakan bordered matrix dengan x,y [-1..2] sesuai mekanisme bicubic
    // spline atau dalam hal ini [0..3]]
    int row = 0;
    for (int i = x; i < x + 4; i++) {
      for (int j = y; j < y + 4; j++) {
        I.setElmt(row, 0, borderedMatrix.getElmt(i, j));
        row += 1;
      }
    }

    return I;
  }

  public Matrix getScaledImageMatrix(final Matrix borderedMatrix, final double n) {
    int i, j;
    // Mengambil matrix asli tanpa mengambil nilai padding dan ass
    int rowEff = (int) Math.floor((borderedMatrix.getRowEff() - 4) * n);
    int colEff = (int) Math.floor((borderedMatrix.getColEff() - 4) * n);
    Matrix scaledMatrix = new Matrix(rowEff, colEff);

    // Mengambil nilai matrix X pada metode bicubic spline
    Matrix X = this.getBicubicSplineXMatrix();
    Matrix koef;

    for (i = 0; i < scaledMatrix.getRowEff(); i++) {
      for (j = 0; j < scaledMatrix.getColEff(); j++) {
        // Mendapatkan index pada image asli
        int rowOriginIdx = (int) Math.floor(i / (double) n);
        int colOriginIdx = (int) Math.floor(j / (double) n);
        // Menyiapkan untuk melakukan predict value coordinate dengan bicubic spline
        double x = (i % n) / (double) n;
        double y = (j % n) / (double) n;

        // Mencari nilai koefisien dengan korelasi persamaan image dan persamaan umum bicubic spline
        koef = this.getCoeffImageMatrix(X, getXImageMatrix(),
            getInterpolaterImageMatrix(borderedMatrix, rowOriginIdx, colOriginIdx));

        // Assign value pada scaled matrix dengan predict value dari bicubic spline
        double value = this.predictBicubicSplineImage(y, x, koef);
        scaledMatrix.setElmt(i, j, value);
      }
    }
    return scaledMatrix;
  }

  public void saveImageToFile(final Matrix alphaMatrix, final Matrix redMatrix,
      final Matrix greenMatrix, final Matrix blueMatrix, String filePath, int imageType,
      String imgExtension) {
    int rowEff = alphaMatrix.getRowEff();
    int colEff = alphaMatrix.getColEff();
    BufferedImage image = new BufferedImage(colEff, rowEff, imageType);

    try {
      int[] pixelData = new int[colEff * rowEff];

      for (int i = 0; i < rowEff; i++) {
        for (int j = 0; j < colEff; j++) {
          int alpha = ((int) alphaMatrix.getElmt(i, j)) << 24;
          int red = ((int) redMatrix.getElmt(i, j)) << 16;
          int green = ((int) greenMatrix.getElmt(i, j)) << 8;
          int blue = ((int) blueMatrix.getElmt(i, j));
          pixelData[i * colEff + j] = alpha | red | green | blue;
        }
      }

      image.setRGB(0, 0, colEff, rowEff, pixelData, 0, colEff);
      File outputFile = new File(filePath);
      ImageIO.write(image, imgExtension, outputFile);
      System.out.println("\nGambar berhasil diperbesar dan disimpan di " + filePath);
    } catch (IOException e) {
      System.out.println("Error saving image: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
