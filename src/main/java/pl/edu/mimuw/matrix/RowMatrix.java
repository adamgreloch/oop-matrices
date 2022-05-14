package pl.edu.mimuw.matrix;

public class RowMatrix extends PeriodicTableMatrix {
  public RowMatrix(Shape shape, double[] rowValues) {
    super(shape, rowValues, shape.columns);
  }

  public OneTableMatrix newMatrix(double[] newValues) {
    return new RowMatrix(this.shape(), newValues);
  }

  protected int choose(int row, int column) {
    return column;
  }

  public double getColumn(int column) {
    return 0;
  }

  public double getRow(int row) {
    return 0;
  }

  protected int periods() {
    return shape().rows;
  }

  public String matrixType() {
    return "row";
  }
}
