package pl.edu.mimuw.matrix;

public class ZeroMatrix extends ConstantMatrix {

  public ZeroMatrix(Shape shape) {
    super(shape, 0);
  }

  @Override
  public IDoubleMatrix plusSparse(SparseMatrix other) {
    return other;
  }

  @Override
  public IDoubleMatrix rHMinusSparse(SparseMatrix other) {
    return other.times(-1);
  }

  @Override
  public IDoubleMatrix times(IDoubleMatrix other) {
    return new ZeroMatrix(DoubleMatrix.product(other, this));
  }

  @Override
  public IDoubleMatrix rHMinusFull(FullMatrix other) {
    return other.times(-1);
  }

  @Override
  public IDoubleMatrix lHMinusFull(FullMatrix other) {
    return other;
  }

  @Override
  public IDoubleMatrix times(double scalar) {
    return this;
  }

  @Override
  public IDoubleMatrix plus(double scalar) {
    return new ConstantMatrix(this.shape(), scalar);
  }

  @Override
  public double get(int row, int column) {
    this.assertInMatrix(row, column);
    return 0;
  }

  @Override
  public double[][] data() {
    double[][] res = new double[this.shape().rows][];
    for (int i = 0; i < this.shape().rows; i++)
      res[i] = new double[this.shape().columns];
    return res;
  }

  @Override
  protected double calculateNormOne() {
    return 0;
  }

  @Override
  protected double calculateNormInfinity() {
    return 0;
  }

  @Override
  protected double calculateFrobeniusNorm() {
    return 0;
  }

  @Override
  public String matrixType() {
    return "zero";
  }
}
