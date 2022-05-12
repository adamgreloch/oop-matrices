package pl.edu.mimuw.matrix;

public class ColumnMatrix extends PeriodicTableMatrix {
  public ColumnMatrix(Shape shape, double[] columnValues) {
    super(shape, columnValues);
  }

  public OneTableMatrix newMatrix(double[] newValues) {
    return new ColumnMatrix(this.shape(), newValues);
  }

  protected int choose(int row, int column) {
    return row;
  }

  public double getColumn(int column) {
    return 0;
  }

  public double getRow(int row) {
    return 0;
  }

  public String sparseType() {
    return "column";
  }
}
