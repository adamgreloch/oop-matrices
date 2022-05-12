package pl.edu.mimuw.matrix;

import java.util.Arrays;

public abstract class SkewTableMatrix extends OneTableMatrix {

  public SkewTableMatrix(Shape shape, double[] skewValues) {
    super(shape, skewValues, Math.min(shape.rows, shape.columns));
  }

  public SkewTableMatrix(Shape shape, double diagonalValue, int bound) {
    super(shape, diagonalValue, bound);
  }

  public double get(int row, int column) {
    this.assertInMatrix(row, column);
    if (row == indexRow(row) && column == indexColumn(column))
      return values[row];
    else
      return 0;
  }

  public abstract OneTableMatrix newMatrix(double[] newValues);

  public double[][] data() {
    int m = this.shape().rows;
    int n = this.shape().columns;
    double[][] res = new double[m][n];

    for (int i = 0; i < bound; i++)
      res[indexRow(i)][indexColumn(i)] = values[i];

    return res;
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

  public abstract int indexRow(int index);

  public abstract int indexColumn(int index);

  protected IDoubleMatrix arithmeticOperatorSparse(SparseMatrix other, boolean isReduction) {
    if (this.typeEquals(other)) {
      double[] newValues = Arrays.copyOf(this.values, bound);

      for (int i = 0; i < bound; i++)
        newValues[i] = other.get(indexRow(i), i) + newValues[i] * (isReduction ? -1 : 1);

      return this.newMatrix(newValues);
    }
    double[][] newValues = other.data();

    for (int i = 0; i < bound; i++)
      newValues[indexRow(i)][i] += this.values[i] * (isReduction ? -1 : 1);

    return new FullMatrix(newValues);
  }

  public IDoubleMatrix rHMinusFull(FullMatrix other) {
    double[][] newValues = other.data();

    for (int i = 0; i < bound; i++)
      newValues[indexRow(i)][i] -= this.values[i];

    return new FullMatrix(newValues);
  }

  public IDoubleMatrix lHMinusFull(FullMatrix other) {
    double[][] newValues = other.data();

    for (int i = 0; i < bound; i++)
      newValues[indexRow(i)][i] = this.values[i] - newValues[indexRow(i)][i];

    return new FullMatrix(newValues);
  }

  public double getColumn(int column) {
    return 0;
  }

  public double getRow(int row) {
    return 0;
  }


  public abstract String sparseType();
}
