package pl.edu.mimuw.matrix;

import java.util.Arrays;

public abstract class OneTableMatrix extends SparseMatrix {
  protected final double[] values;
  protected final int bound;

  public OneTableMatrix(Shape shape, double dominantValue, double[] values, int bound) {
    super(shape, dominantValue);
    this.values = Arrays.copyOf(values, values.length);
    this.bound = bound;
  }

  public OneTableMatrix(Shape shape, double dominantValue, double value, int bound) {
    super(shape, dominantValue);
    this.values = new double[bound];
    this.bound = bound;
    Arrays.fill(this.values, value);
  }

  public IDoubleMatrix times(double scalar) {
    double[] res = Arrays.copyOf(this.values, bound);

    for (int i = 0; i < bound; i++)
      res[i] *= scalar;

    return this.newMatrix(res);
  }

  public IDoubleMatrix plus(double scalar) {
    double[] res = Arrays.copyOf(this.values, bound);

    for (int i = 0; i < bound; i++)
      res[i] += scalar;

    return this.newMatrix(res);
  }

  public abstract OneTableMatrix newMatrix(double[] newValues);

  public double normOne() {
    return 0;
  }

  public double normInfinity() {
    return 0;
  }

  public double frobeniusNorm() {
    return 0;
  }

}