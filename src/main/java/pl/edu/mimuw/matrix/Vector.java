package pl.edu.mimuw.matrix;

import java.util.Arrays;

public class Vector extends OneTableMatrix {
  public Vector(Shape shape, double[] values) {
    super(shape, values, shape.rows);
  }

  public double get(int row, int column) {
    this.assertInMatrix(row, column);
    return this.values[row];
  }

  public double[][] data() {
    double[][] res = new double[this.shape().rows][1];
    for (int i = 0; i < bound; i++)
      res[i][0] = this.values[i];
    return res;
  }

  public OneTableMatrix newMatrix(double[] newValues) {
    return new Vector(this.shape(), this.values);
  }

  protected IDoubleMatrix arithmeticOperatorSparse(SparseMatrix other, boolean isSubtraction) {
    double[] newValues = Arrays.copyOf(this.values, bound);

    for (int i = 0; i < bound; i++)
      newValues[i] = other.get(i, 0) + newValues[i] * (isSubtraction ? -1 : 1);

    return this.newMatrix(newValues);
  }

  public IDoubleMatrix rHMinusFull(FullMatrix other) {
    double[][] newValues = other.data();

    for (int i = 0; i < bound; i++)
      newValues[i][0] -= this.values[i];

    return new FullMatrix(newValues);
  }

  public IDoubleMatrix lHMinusFull(FullMatrix other) {
    double[][] newValues = other.data();

    for (int i = 0; i < bound; i++)
      newValues[i][0] = this.values[i] - newValues[i][0];

    return new FullMatrix(newValues);
  }

  public String matrixType() {
    return "vector";
  }

  @Override
  public double normOne() {
    double sum = 0;
    for (int i = 0; i < bound; i++)
      sum += Math.abs(this.values[i]);
    return sum;
  }

  public double frobeniusNorm() {
    double sum = 0;
    for (int i = 0; i < bound; i++)
      sum += Math.pow(this.values[i], 2);
    return Math.sqrt(sum);
  }
}
