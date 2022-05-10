package pl.edu.mimuw.matrix;

public class ColumnMatrix extends OneTableMatrix {
  public ColumnMatrix(Shape shape, double[] diagonalValues) {
    super(shape, 0, diagonalValues, shape.rows);
  }

  public double get(int row, int column) {
    return this.values[row];
  }

  public double[][] data() {
    // TODO
    return new double[0][];
  }

  public IDoubleMatrix plusSparse(SparseMatrix other) {
    return null;
  }

  public IDoubleMatrix rHMinusSparse(SparseMatrix other) {
    return null;
  }

  public IDoubleMatrix plusFull(FullMatrix other) {
    return null;
  }

  public IDoubleMatrix rHMinusFull(FullMatrix other) {
    return null;
  }

  public double getColumn(int column) {
    return 0;
  }

  public double getRow(int row) {
    return 0;
  }

  public String sparseType() {
    return "column";
  }

  @Override
  public OneTableMatrix newMatrix(double[] newValues) {
    return new ColumnMatrix(this.shape(), newValues);
  }
}
