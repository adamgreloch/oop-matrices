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
    if (column == indexRow(row))
      return values[indexRow(row)];
    else
      return 0;
  }

  public IDoubleMatrix plus(double scalar) {
    double[][] res = new double[this.shape().rows][];

    for (int i = 0; i < bound; i++) {
      res[indexRow(i)] = new double[this.shape().columns];
      Arrays.fill(res[indexRow(i)], scalar);
      res[indexRow(i)][indexColumn(i)] += this.values[i];
    }

    return new FullMatrix(res);
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

  public abstract int indexRow(int index);

  public abstract int indexColumn(int index);

  protected IDoubleMatrix arithmeticOperatorSparse(SparseMatrix other, boolean isRHSubtraction) {
    if (this.typeEquals(other)) {
      double[] newValues = Arrays.copyOf(this.values, bound);

      for (int i = 0; i < bound; i++)
        newValues[i] = other.get(indexRow(i), i) + newValues[i] * (isRHSubtraction ? -1 : 1);

      return this.newMatrix(newValues);
    }
    double[][] newValues = other.data();

    for (int i = 0; i < bound; i++)
      newValues[indexRow(i)][i] += this.values[i] * (isRHSubtraction ? -1 : 1);

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

  protected double calculateNormOne() {
    return calculateNormInfinity();
  }

  protected double calculateNormInfinity() {
    double max = 0;
    for (int i = 0; i < bound; i++)
      max = Math.max(max, Math.abs(this.values[i]));
    return max;
  }

  public abstract String matrixType();

  public String printMatrix() {
    StringBuilder res = new StringBuilder();
    int m = this.shape().rows, n = this.shape().columns;
    int maxLength = getCellMaxWidth() + 1;

    for (int i = 0; i < m; i++) {
      res.append("| ");
      for (int j = 0; j < n; j++) {
        if (Math.abs(indexRow(i) - j) < 2)
          res.append(String.format("%-" + maxLength + ".1f ", get(i, j)));
        else if (Math.abs(indexRow(i) - j) == 2) res.append("...").append(" ".repeat(Math.max(1, maxLength - 2)));
        else res.append(" ".repeat(maxLength + 1));
      }
      res.append("|\n");
    }
    return res.toString();
  }
}
