package pl.edu.mimuw.matrix;

import java.util.Arrays;

public class ColumnMatrix extends SparseMatrix {
  private double[] rowValues;

  public ColumnMatrix(Shape shape, double[] rowValues) {
    super(shape);
    this.rowValues = Arrays.copyOf(rowValues, rowValues.length);
  }

  public IDoubleMatrix times(double scalar) {
    for (int i = 0; i < shape().rows; i++)
      this.rowValues[i] *= scalar;

    return new ColumnMatrix(this.shape(), this.rowValues);
  }

  public IDoubleMatrix plus(double scalar) {
    for (int i = 0; i < shape().rows; i++)
      this.rowValues[i] += scalar;

    return new ColumnMatrix(this.shape(), this.rowValues);
  }

  public double get(int row, int column) {
    return rowValues[row];
  }

  @Override
  public double[][] data() {
    // TODO
    return new double[0][];
  }

  @Override
  public double normOne() {
    // TODO
    return 0;
  }

  @Override
  public double normInfinity() {
    // TODO
    return 0;
  }

  @Override
  public double frobeniusNorm() {
    // TODO
    return 0;
  }

  @Override
  public IDoubleMatrix plusSparse(SparseMatrix other) {
    return null;
  }

  @Override
  public IDoubleMatrix rHMinusSparse(SparseMatrix other) {
    return null;
  }

  @Override
  public IDoubleMatrix rHTimesSparse(SparseMatrix other) {
    return null;
  }

  @Override
  public double getColumn(int column) {
    return 0;
  }

  @Override
  public double getRow(int row) {
    return 0;
  }
}
