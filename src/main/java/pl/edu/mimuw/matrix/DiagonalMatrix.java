package pl.edu.mimuw.matrix;

import java.util.Arrays;

public class DiagonalMatrix extends SparseMatrix {
  private double[] diagonalValues;

  public DiagonalMatrix(Shape shape, double dominantValue, double[] diagonalValues) {
    super(shape, dominantValue);
    this.diagonalValues = Arrays.copyOf(diagonalValues, diagonalValues.length);
  }

  public IDoubleMatrix times(double scalar) {
    return null;
  }

  public IDoubleMatrix plus(double scalar) {
    return null;
  }

  public double get(int row, int column) {
    this.assertInMatrix(row, column);
    if (row == column)
      return diagonalValues[row];
    else
      return this.dominantValue();
  }

  public double[][] data() {
    return new double[0][];
  }

  public double normOne() {
    return 0;
  }

  public double normInfinity() {
    return 0;
  }

  public double frobeniusNorm() {
    return 0;
  }

  public IDoubleMatrix plusSparse(SparseMatrix other) {
    return null;
  }

  public IDoubleMatrix rHMinusSparse(SparseMatrix other) {
    return null;
  }

  public IDoubleMatrix rHTimesSparse(SparseMatrix other) {
    return null;
  }

  public double getColumn(int column) {
    return 0;
  }

  public double getRow(int row) {
    return 0;
  }
}
