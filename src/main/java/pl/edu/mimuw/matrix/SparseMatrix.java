package pl.edu.mimuw.matrix;

public abstract class SparseMatrix implements IDoubleMatrix {
  private Shape shape;
  private double dominantValue;

  public SparseMatrix(Shape shape, double dominantValue) {
    this.shape = shape;
    this.dominantValue = dominantValue;
  }

  public SparseMatrix(Shape shape) {
    this(shape, 0);
  }

  public IDoubleMatrix times(IDoubleMatrix other) {
    return other.rHTimesSparse(this);
  }

  public abstract IDoubleMatrix times(double scalar);

  public IDoubleMatrix plus(IDoubleMatrix other) {
    return other.plusSparse(this);
  }

  public abstract IDoubleMatrix plus(double scalar);

  public IDoubleMatrix minus(IDoubleMatrix other) {
    return null;
  }

  public IDoubleMatrix minus(double scalar) {
    return this.plus(-scalar);
  }

  public Shape shape() {
    return shape;
  }

  public abstract double get(int row, int column);

  public abstract double[][] data();

  public abstract double normOne();

  public abstract double normInfinity();

  public abstract double frobeniusNorm();

  public abstract IDoubleMatrix plusSparse(SparseMatrix other);

  public abstract IDoubleMatrix rHMinusSparse(SparseMatrix other);

  public abstract IDoubleMatrix rHTimesSparse(SparseMatrix other);

  public abstract double getColumn(int column);

  public abstract double getRow(int row);

  public void assertInMatrix(int row, int column) {
    this.shape().assertInShape(row, column);
  }

  public double dominantValue() {
    return dominantValue;
  }
}
