package pl.edu.mimuw.matrix;

public class IdentityMatrix extends DiagonalMatrix {

  public IdentityMatrix(Shape shape) {
    super(shape, 1, shape.rows);
    assert shape.rows == shape.columns;
  }

  @Override
  public double get(int row, int column) {
    this.assertInMatrix(row, column);
    return row == column ? 1 : 0;
  }

  @Override
  public IDoubleMatrix times(IDoubleMatrix other) {
    return other;
  }

  @Override
  public String matrixType() {
    return "identity";
  }
}
