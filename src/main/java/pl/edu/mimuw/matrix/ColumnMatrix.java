package pl.edu.mimuw.matrix;

public class ColumnMatrix extends PeriodicTableMatrix {
  public ColumnMatrix(Shape shape, double[] columnValues) {
    super(shape, columnValues, shape.rows);
  }

  public OneTableMatrix newMatrix(double[] newValues) {
    return new ColumnMatrix(this.shape(), newValues);
  }

  protected int choose(int row, int column) {
    return row;
  }

  protected int periods() {
    return shape().columns;
  }

  public String matrixType() {
    return "column";
  }
}
