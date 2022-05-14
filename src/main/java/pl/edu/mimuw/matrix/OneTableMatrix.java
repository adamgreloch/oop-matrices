package pl.edu.mimuw.matrix;

import java.util.Arrays;

public abstract class OneTableMatrix extends SparseMatrix {
  protected final double[] values;
  protected final int bound;

  public OneTableMatrix(Shape shape, double[] values, int bound) {
    super(shape);
    this.values = Arrays.copyOf(values, values.length);
    this.bound = bound;
  }

  public OneTableMatrix(Shape shape, double value, int bound) {
    super(shape);
    this.values = new double[bound];
    this.bound = bound;
    Arrays.fill(this.values, value);
  }

  public IDoubleMatrix plus(double scalar) {
    double[] res = Arrays.copyOf(this.values, bound);

    for (int i = 0; i < bound; i++)
      res[i] += scalar;

    return this.newMatrix(res);
  }

  public IDoubleMatrix times(double scalar) {
    double[] res = Arrays.copyOf(this.values, bound);

    for (int i = 0; i < bound; i++)
      res[i] *= scalar;

    return this.newMatrix(res);
  }

  public abstract OneTableMatrix newMatrix(double[] newValues);

  protected abstract IDoubleMatrix arithmeticOperatorSparse(SparseMatrix other, boolean isSubtraction);

  public IDoubleMatrix plusSparse(SparseMatrix other) {
    return this.arithmeticOperatorSparse(other, false);
  }

  public IDoubleMatrix rHMinusSparse(SparseMatrix other) {
    return this.arithmeticOperatorSparse(other, true);
  }

  public double normOne() {
    return normInfinity();
  }

  public double normInfinity() {
    double max = 0;
    for (int i = 0; i < bound; i++)
      max = Math.max(max, Math.abs(this.values[i]));
    return max;
  }

  public double frobeniusNorm() {
    double sum = 0;
    for (int i = 0; i < bound; i++)
      sum += Math.pow(this.values[i], 2);
    return Math.sqrt(sum);
  }
}
