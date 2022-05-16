package pl.edu.mimuw.matrix;

public abstract class SparseMatrix extends DoubleMatrix {

  public SparseMatrix(Shape shape) {
    super(shape);
  }

  public abstract IDoubleMatrix times(double scalar);

  public IDoubleMatrix plus(IDoubleMatrix other) {
    assert this.shape().equals(other.shape());
    return other.plusSparse(this);
  }

  public abstract IDoubleMatrix plus(double scalar);

  public IDoubleMatrix minus(IDoubleMatrix other) {
    assert this.shape().equals(other.shape());
    return other.rHMinusSparse(this);
  }

  public IDoubleMatrix minus(double scalar) {
    return this.plus(-scalar);
  }
}
