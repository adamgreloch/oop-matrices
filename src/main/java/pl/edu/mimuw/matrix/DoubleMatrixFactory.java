package pl.edu.mimuw.matrix;

public class DoubleMatrixFactory {

  private DoubleMatrixFactory() {
  }

  public static IDoubleMatrix sparse(Shape shape, MatrixCellValue... values) {
    return new IrregularMatrix(shape, values);
  }

  public static IDoubleMatrix full(double[][] values) {
    assert values != null && values.length > 0;
    return new FullMatrix(values);
  }

  public static IDoubleMatrix identity(int size) {
    return new DiagonalMatrix(Shape.matrix(size, size), 1, size);
  }

  public static IDoubleMatrix diagonal(double... diagonalValues) {
    int n = diagonalValues.length;
    return new DiagonalMatrix(Shape.matrix(n, n), diagonalValues);
  }

  public static IDoubleMatrix antiDiagonal(double... antiDiagonalValues) {
    int n = antiDiagonalValues.length;
    return new AntiDiagonalMatrix(Shape.matrix(n, n), antiDiagonalValues);
  }

  public static IDoubleMatrix vector(double... values) {
    return new Vector(Shape.matrix(values.length, 1), values); // Tu trzeba wpisać właściwą instrukcję
  }

  public static IDoubleMatrix zero(Shape shape) {
    return new ConstantMatrix(shape, 0);
  }
}
