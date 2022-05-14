package pl.edu.mimuw.matrix;

public abstract class DoubleMatrix implements IDoubleMatrix {
  private final Shape shape;

  public DoubleMatrix(Shape shape) {
    this.shape = shape;
  }

  public IDoubleMatrix times(IDoubleMatrix other) {
    Shape.assertProduct(this, other);
    double[][] res = new double[this.shape().rows][other.shape().columns];

    for (int i = 0; i < this.shape().rows; i++)
      for (int j = 0; j < this.shape().columns; j++)
        for (int k = 0; k < this.shape().columns; k++)
          res[i][j] = this.get(i, k) * other.get(k, j);

    return new FullMatrix(res);
  }

  public IDoubleMatrix rHTimesIrregular(IrregularMatrix other) {
    return other.times(this);
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

  public IDoubleMatrix lHMinusIrregular(IrregularMatrix other) {
    return this.minus(other);
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
}
