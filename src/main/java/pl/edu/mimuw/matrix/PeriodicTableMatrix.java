package pl.edu.mimuw.matrix;

import java.util.Arrays;

public abstract class PeriodicTableMatrix extends OneTableMatrix {
  public PeriodicTableMatrix(Shape shape, double[] periodicValues) {
    super(shape, periodicValues, shape.rows);
  }

  public double get(int row, int column) {
    this.assertInMatrix(row, column);
    return this.values[choose(row, column)];
  }

  public double[][] data() {
    // TODO
    return new double[0][];
  }

  public abstract OneTableMatrix newMatrix(double[] newValues);

  protected IDoubleMatrix arithmeticOperatorSparse(SparseMatrix other, boolean isReduction) {
    if (this.typeEquals(other)) {
      double[] newValues = Arrays.copyOf(this.values, bound);

      for (int i = 0; i < bound; i++)
        newValues[i] = other.get(i, 0) + newValues[i] * (isReduction ? -1 : 1);

      return this.newMatrix(newValues);
    }
    double[][] newValues = other.data();

    for (int i = 0; i < bound; i++)
      for (int j = 0; j < bound; j++)
        newValues[i][j] += this.values[choose(i, j)] * (isReduction ? -1 : 1);

    return new FullMatrix(newValues);
  }

  public IDoubleMatrix rHMinusFull(FullMatrix other) {
    double[][] newValues = other.data();

    for (int i = 0; i < bound; i++)
      for (int j = 0; j < bound; j++)
        newValues[i][j] -= this.values[choose(i, j)];

    return new FullMatrix(newValues);
  }

  public IDoubleMatrix lHMinusFull(FullMatrix other) {
    double[][] newValues = other.data();

    for (int i = 0; i < bound; i++)
      for (int j = 0; j < bound; j++)
        newValues[i][j] = this.values[choose(i, j)] - newValues[i][j];

    return new FullMatrix(newValues);
  }

  protected abstract int choose(int row, int column);

  public double getColumn(int column) {
    return 0;
  }

  public double getRow(int row) {
    return 0;
  }

  public abstract String matrixType();
}
