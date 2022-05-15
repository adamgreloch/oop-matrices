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
    Shape.assertProduct(this, other);
    /*
      Product of ConstantMatrix times any IDoubleMatrix is a ColumnMatrix
     */
    double[] rows = new double[this.shape().columns];
    double row = 0;

    for (int i = 0; i < this.shape().columns; i++) {
      for (int j = 0; j < other.shape().columns; j++)
        row += other.get(i, j);
      rows[i] = row * this.value;
      row = 0;
    }

    return new ColumnMatrix(Shape.product(other, this), rows);
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

  @Override
  public String toString() {
    return "ConstantMatrix " + this.shape() + ": every cell = " + this.value;
  }
}
