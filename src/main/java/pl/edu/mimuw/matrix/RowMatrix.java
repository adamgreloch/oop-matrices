package pl.edu.mimuw.matrix;

public class RowMatrix extends OneTableMatrix {

  public RowMatrix(Shape shape, double[] diagonalValues) {
    super(shape, 0, diagonalValues, shape.columns);
  }

  public IDoubleMatrix minus(IDoubleMatrix other) {
    return null;
  }

  public double get(int row, int column) {
    return this.values[column];
  }

  public RowMatrix newMatrix(double[] newValues) {
    return new RowMatrix(this.shape(), newValues);
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

  public IDoubleMatrix rHTimesSparse(SparseMatrix other) {
    return null;
  }

  @Override
  public IDoubleMatrix plusFull(FullMatrix other) {
    return null;
  }

  @Override
  public IDoubleMatrix rHMinusFull(FullMatrix other) {
    return null;
  }

  @Override
  public IDoubleMatrix rHTimesFull(FullMatrix other) {
    return null;
  }

  public IDoubleMatrix lHMinusFull(FullMatrix other) {
    return null;
  }

  public IDoubleMatrix lHTimesFull(FullMatrix other) {
    return null;
  }

  public double getColumn(int column) {
    return 0;
  }

  public double getRow(int row) {
    return 0;
  }

  public String sparseType() {
    return "row";
  }
}
