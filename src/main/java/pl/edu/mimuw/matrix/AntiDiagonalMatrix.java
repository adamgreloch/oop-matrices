package pl.edu.mimuw.matrix;

public class AntiDiagonalMatrix extends OneTableMatrix {

  public AntiDiagonalMatrix(Shape shape, double dominantValue, double[] antiDiagonalValues) {
    super(shape, dominantValue, antiDiagonalValues, Math.min(shape.rows, shape.columns));
  }

  public double get(int row, int column) {
    this.assertInMatrix(row, column);
    if (row == column)
      return values[row];
    else
      return this.dominantValue();
  }

  public OneTableMatrix newMatrix(double[] newValues) {
    return new AntiDiagonalMatrix(this.shape(), this.dominantValue(), newValues);
  }

  public double[][] data() {
    int m = this.shape().rows;
    int n = this.shape().columns;
    double[][] res = new double[m][n];

    for (int i = 0; i < bound; i++)
      res[n - i - 1][i] = values[i];

    return res;
  }

  public double normOne() {
    return 0;
  }

  public double normInfinity() {
    return 0;
  }

  public double frobeniusNorm() {
    return 0;
  }

  public IDoubleMatrix plusSparse(SparseMatrix other) {
    return null;
  }

  public IDoubleMatrix rHMinusSparse(SparseMatrix other) {
    return null;
  }

  public IDoubleMatrix rHMinusFull(FullMatrix other) {
    return null;
  }

  public IDoubleMatrix lHMinusFull(FullMatrix other) {
    return null;
  }

  @Override
  public double getColumn(int column) {
    return 0;
  }

  @Override
  public double getRow(int row) {
    return 0;
  }

  public String sparseType() {
    return "antiDiagonal";
  }
}
