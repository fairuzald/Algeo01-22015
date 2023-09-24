package tools;

public interface MatrixInterface {
    int getRowEff();

    int getColEff();

    float getElmt(int i, int j);

    int getLastIdxRow();

    int getLastIdxCol();

    int getFirstIdxRow();

    int getFirstIdxCol();

    float getElmtDiagonal(int i);

    int getSize();

    boolean isSquare();

    boolean isSymmetric();

    boolean isIdentity();

    void setElmt(int i, int j, float value);

    boolean isMatrixIdxValid(int i, int j);

    float[][] copyElmtMatrix();

    Matrix copyMatrix();

    void readMatrix();

    void readFileMatrix(String fileName);

    void displayMatrix();

    void writeFileMatrix(String fileName);

    Matrix addMatrix(Matrix m1, Matrix m2);

    Matrix subtractMatrix(Matrix m1, Matrix m2);

    Matrix multiplyMatrix(Matrix m1, Matrix m2);

    void multiplyByConst(float k);

    void negation();

    Matrix transpose();

    Matrix createIdentityMatrix(int rows, int cols);

    void OBEPlusRow(int idxRowOrigin, int idxRowTarget, float factor);

    void OBESwapRow(int idxRowOrigin, int idxRowTarget);

    void OBEDivisionFactor(int i, float factor);

    void gaussElimination();

    void gJordanElimination();

    float determinantCofactor();

    float determinantUpperTriangle();

    Matrix cofactor();

    Matrix adjoin();

    Matrix inversAdjoin();

    Matrix inversGJordan();
}
