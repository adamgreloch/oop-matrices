package pl.edu.mimuw.matrix;

import java.util.Arrays;

public abstract class PeriodicTableMatrix extends OneTableMatrix {

  public PeriodicTableMatrix(Shape shape, double[] periodicValues, int bound) {
    super(shape, periodicValues, bound);
  }

  public double get(int row, int column) {
    this.assertInMatrix(row, column);
    return this.values[choose(row, column)];
  }

  public double[][] data() {
    int m = this.shape().rows, n = this.shape().columns;
    double[][] res = new double[m][];
    for (int i = 0; i < m; i++) {
      res[i] = new double[n];
      for (int j = 0; j < n; j++)
        res[i][j] = this.get(i, j);
    }
    return res;
  }

  public IDoubleMatrix plus(double scalar) {
    double[] res = Arrays.copyOf(this.values, bound);

    for (int i = 0; i < bound; i++)
      res[i] += scalar;

    return this.newMatrix(res);
  }

  public abstract OneTableMatrix newMatrix(double[] newValues);

  protected IDoubleMatrix arithmeticOperatorSparse(SparseMatrix other, boolean isSubtraction) {
    if (this.typeEquals(other)) {
      double[] newValues = Arrays.copyOf(this.values, bound);

      for (int i = 0; i < bound; i++)
        newValues[i] = other.get(i, 0) + newValues[i] * (isSubtraction ? -1 : 1);

      return this.newMatrix(newValues);
    }
    double[][] newValues = other.data();

    for (int i = 0; i < this.shape().rows; i++)
      for (int j = 0; j < this.shape().columns; j++)
        newValues[i][j] += this.values[choose(i, j)] * (isSubtraction ? -1 : 1);

    return new FullMatrix(newValues);
  }

  public IDoubleMatrix rHMinusFull(FullMatrix other) {
    double[][] newValues = other.data();

    for (int i = 0; i < this.shape().rows; i++)
      for (int j = 0; j < this.shape().columns; j++)
        newValues[i][j] -= this.values[choose(i, j)];

    return new FullMatrix(newValues);
  }

  public IDoubleMatrix lHMinusFull(FullMatrix other) {
    double[][] newValues = other.data();

    for (int i = 0; i < this.shape().rows; i++)
      for (int j = 0; j < this.shape().columns; j++)
        newValues[i][j] = this.values[choose(i, j)] - newValues[i][j];

    return new FullMatrix(newValues);
  }

  protected abstract int choose(int row, int column);

  protected abstract int periods();

  public abstract String matrixType();

  protected double maxOneAbsValueProduct() {
    return Math.abs(Arrays.stream(this.values).map(Math::abs).max().orElseThrow()) * periods();
  }

  protected double absTableSum() {
    return Arrays.stream(this.values).map(Math::abs).sum();
  }

  @Override
  public double frobeniusNorm() {
    double sum = 0;
    for (int i = 0; i < bound; i++)
      sum += Math.pow(this.values[i], 2);

    return Math.sqrt(sum * periods());
  }

  public String printMatrix() {
    StringBuilder res = new StringBuilder();
    int m = this.shape().rows, n = this.shape().columns;
    int maxLength = getCellMaxWidth() + 1;

    for (int i = 0; i < m; i++) {
      res.append("| ");
      for (int j = 0; j < n; j++) {
        if (i < 2 || j < 2 || m - i - 1 < 2 || n - j - 1 < 2)
          res.append(String.format("%-" + maxLength + ".1f ", get(i, j)));
        else if (i == j) res.append("...").append(" ".repeat(Math.max(1, maxLength - 2)));
        else res.append(" ".repeat(maxLength + 1));
      }
      res.append("|\n");
    }
    return res.toString();
  }
}
