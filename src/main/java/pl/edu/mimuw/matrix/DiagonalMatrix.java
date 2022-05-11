package pl.edu.mimuw.matrix;

public class DiagonalMatrix extends OneTableMatrix {

  public DiagonalMatrix(Shape shape, double dominantValue, double[] diagonalValues) {
    super(shape, dominantValue, diagonalValues, Math.min(shape.rows, shape.columns));
  }

  public DiagonalMatrix(Shape shape, double dominantValue, double diagonalValue, int bound) {
    super(shape, dominantValue, diagonalValue, bound);
  }

  public double get(int row, int column) {
    this.assertInMatrix(row, column);
    if (row == column)
      return values[row];
    else
      return this.dominantValue();
  }

  public OneTableMatrix newMatrix(double[] newValues) {
    return new DiagonalMatrix(this.shape(), this.dominantValue(), newValues);
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

  public IDoubleMatrix rHMinusFull(FullMatrix other) {
    return null;
  }

  public IDoubleMatrix lHMinusFull(FullMatrix other) {
    return null;
  }

  public double getColumn(int column) {
    return 0;
  }

  public double getRow(int row) {
    return 0;
  }

  public String sparseType() {
    return "diagonal";
  }
}
