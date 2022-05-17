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

  protected double calculateNormOne() {
    return this.maxOneAbsValueProduct();
  }

  protected double calculateNormInfinity() {
    return this.absTableSum();
  }

  protected int periods() {
    return shape().rows;
  }

  public String matrixType() {
    return "row";
  }
}
