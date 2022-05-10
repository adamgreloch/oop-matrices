package pl.edu.mimuw.matrix;

public abstract class SparseMatrix extends DoubleMatrix {
  private final double dominantValue;
  private final Shape shape;

  public SparseMatrix(Shape shape, double dominantValue) {
    this.shape = shape;
    this.dominantValue = dominantValue;
  }

  public SparseMatrix(Shape shape) {
    this(shape, 0);
  }

  public abstract IDoubleMatrix times(double scalar);

  public IDoubleMatrix plus(IDoubleMatrix other) {
    return other.plusSparse(this);
  }

  public abstract IDoubleMatrix plus(double scalar);

  public IDoubleMatrix minus(double scalar) { return this.plus(-scalar); }

  public Shape shape() { return shape; }

  public void assertInMatrix(int row, int column) {
    this.shape().assertInShape(row, column);
  }

  public double dominantValue() {
    return dominantValue;
  }

  public abstract String sparseType();

  public boolean typeEquals(SparseMatrix other) {
    return this.sparseType().equals(other.sparseType());
  }
}
