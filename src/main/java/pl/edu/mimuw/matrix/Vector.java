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
    // TODO
    return new double[0][];
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

  public double getColumn(int column) {
    return 0;
  }

  public double getRow(int row) {
    return 0;
  }

  public String matrixType() {
    return "vector";
  }
}
