package pl.edu.mimuw.matrix;

public abstract class DoubleMatrix implements IDoubleMatrix {
  private final Shape shape;

  public DoubleMatrix(Shape shape) {
    this.shape = shape;
  }

  public static Shape product(IDoubleMatrix A, IDoubleMatrix B) {
    DoubleMatrix.assertProduct(A, B);
    return Shape.matrix(A.shape().rows, B.shape().columns);
  }

  public static void assertProduct(IDoubleMatrix A, IDoubleMatrix B) {
    assert A.shape().columns == B.shape().rows;
  }

  public IDoubleMatrix times(IDoubleMatrix other) {
    return dotProduct(this, other);
  }

  private IDoubleMatrix rHTimes(IDoubleMatrix other) {
    return dotProduct(other, this);
  }

  private IDoubleMatrix dotProduct(IDoubleMatrix a, IDoubleMatrix b) {
    DoubleMatrix.assertProduct(a, b);
    int m = a.shape().rows, n = b.shape().columns;
    double[][] res = new double[m][n];

    for (int i = 0; i < m; i++)
      for (int j = 0; j < n; j++)
        for (int k = 0; k < a.shape().columns; k++)
          res[i][j] += a.get(i, k) * b.get(k, j);

    return new FullMatrix(res);
  }

  public IDoubleMatrix rHTimesIrregular(IrregularMatrix other) {
    return this.rHTimes(other);
  }

  public IDoubleMatrix plusFull(FullMatrix other) {
    double[][] res = this.data();

    for (int i = 0; i < this.shape().rows; i++)
      for (int j = 0; j < this.shape().columns; j++)
        res[i][j] += other.get(i, j);

    return new FullMatrix(res);
  }

  public IDoubleMatrix plusIrregular(IrregularMatrix other) {
    return this.plusSparse(other);
  }

  public Shape shape() {
    return this.shape;
  }

  public void assertInMatrix(int row, int column) {
    this.shape().assertInShape(row, column);
  }

  public boolean typeEquals(IDoubleMatrix other) {
    return this.matrixType().equals(other.matrixType());
  }

  public abstract String printMatrix();

  protected abstract int getCellMaxWidth();

  @Override
  public String toString() {
    return this.matrixType() + "(" + this.shape.rows + "x" + this.shape.columns + ")\n" + this.printMatrix();
  }
}
