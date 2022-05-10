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
    return new double[0][];
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

  public IDoubleMatrix rHTimesSparse(SparseMatrix other) {
    return null;
  }

  public IDoubleMatrix plusFull(FullMatrix other) {
    return null;
  }

  public IDoubleMatrix rHMinusFull(FullMatrix other) {
    return null;
  }

  public IDoubleMatrix rHTimesFull(FullMatrix other) {
    return null;
  }

  public double getColumn(int column) {
    return 0;
  }

  public double getRow(int row) {
    return 0;
  }

  public String sparseType() {
    return "antiDiagonal";
  }
}
