package pl.edu.mimuw.matrix;

public abstract class DoubleMatrix implements IDoubleMatrix {
  public IDoubleMatrix times(IDoubleMatrix other) {
    Shape.assertProduct(this, other);
    double[][] res = new double[this.shape().rows][other.shape().columns];

    for (int i = 0; i < this.shape().rows; i++)
      for (int j = 0; j < this.shape().columns; j++)
        for (int k = 0; k < this.shape().columns; k++)
          res[i][j] = this.get(i, k) * other.get(k, j);

    return new FullMatrix(res);
  }
}
