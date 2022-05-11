package pl.edu.mimuw.matrix;

public abstract class SparseMatrix extends DoubleMatrix {
  private final double dominantValue;

  public SparseMatrix(Shape shape, double dominantValue) {
    super(shape);
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

  public IDoubleMatrix minus(IDoubleMatrix other) {
    return other.rHMinusSparse(this);
  }

  public IDoubleMatrix minus(double scalar) { return this.plus(-scalar); }

  public double dominantValue() {
    return dominantValue;
  }

  public abstract String sparseType();

  public boolean typeEquals(SparseMatrix other) {
    return this.sparseType().equals(other.sparseType());
  }

  public boolean dominantEquals(SparseMatrix other) {
    return this.dominantValue() == other.dominantValue();
  }
}
