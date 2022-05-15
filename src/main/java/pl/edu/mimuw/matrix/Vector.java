package pl.edu.mimuw.matrix;

public class Vector extends FullMatrix {
  public Vector(double[] values) {
    super(convertTo2D(values));
  }

  private static double[][] convertTo2D(double[] values) {
    double[][] res = new double[values.length][];
    for (int i = 0; i < values.length; i++) {
      res[i] = new double[1];
      res[i][0] = values[i];
    }
    return res;
  }

  @Override
  public String matrixType() {
    return "vector";
  }
}
