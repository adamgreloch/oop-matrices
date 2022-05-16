package pl.edu.mimuw.matrix;

import java.util.Arrays;

public class ConstantMatrix extends SparseMatrix {
  private final double value;

  public ConstantMatrix(Shape shape, double value) {
    super(shape);
    this.value = value;
  }

  public IDoubleMatrix plusSparse(SparseMatrix other) {
    return other.plus(this.value);
  }

  public IDoubleMatrix rHMinusSparse(SparseMatrix other) {
    return other.minus(this.value);
  }

  @Override
  public IDoubleMatrix times(IDoubleMatrix other) {
    DoubleMatrix.assertProduct(this, other);
    /*
      Product of ConstantMatrix times any IDoubleMatrix is a RowMatrix
     */
    double[] rowValues = new double[this.shape().columns];
    double row = 0;

    for (int i = 0; i < this.shape().columns; i++) {
      for (int k = 0; k < other.shape().rows; k++)
        row += other.get(k, i);
      rowValues[i] = row * this.value;
      row = 0;
    }

    return new RowMatrix(DoubleMatrix.product(other, this), rowValues);
  }

  @Override
  public IDoubleMatrix rHMinusFull(FullMatrix other) {
    return other.minus(this.value);
  }

  public IDoubleMatrix lHMinusFull(FullMatrix other) {
    return other.times(-1).plus(this.value);
  }

  public IDoubleMatrix times(double scalar) {
    return new ConstantMatrix(this.shape(), this.value * scalar);
  }

  public IDoubleMatrix plus(double scalar) {
    return new ConstantMatrix(this.shape(), this.value + scalar);
  }

  public double get(int row, int column) {
    this.assertInMatrix(row, column);
    return this.value;
  }

  public double[][] data() {
    double[][] res = new double[this.shape().rows][];
    for (int i = 0; i < this.shape().rows; i++) {
      res[i] = new double[this.shape().columns];
      Arrays.fill(res[i], this.value);
    }
    return res;
  }

  public double normOne() {
    return Math.abs(this.value);
  }

  public double normInfinity() {
    return Math.abs(this.value);
  }

  public double frobeniusNorm() {
    return Math.sqrt(Math.pow(this.value, 2) * this.shape().rows * this.shape().columns);
  }

  public String matrixType() {
    return "constant";
  }

  protected int getCellMaxWidth() {
    return 0;
  }

  public String printMatrix() {
    return "| every cell = " + this.value + " |\n";
  }
}
