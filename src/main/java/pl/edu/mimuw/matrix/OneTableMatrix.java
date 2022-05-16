package pl.edu.mimuw.matrix;

import java.util.Arrays;

public abstract class OneTableMatrix extends SparseMatrix {
  protected final double[] values;
  protected final int bound;

  public OneTableMatrix(Shape shape, double[] values, int bound) {
    super(shape);
    this.values = Arrays.copyOf(values, bound);
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

  public double frobeniusNorm() {
    return Math.sqrt(Arrays.stream(this.values).map(v -> v * v).sum());
  }

  protected int getCellMaxWidth() {
    int max = 0;
    for (int i = 0; i < bound; i++)
      max = Math.max(Double.toString(this.values[i]).length(), max);
    return max;
  }
}
