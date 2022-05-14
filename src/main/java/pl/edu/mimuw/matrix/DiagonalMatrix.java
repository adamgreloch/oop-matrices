package pl.edu.mimuw.matrix;

public class DiagonalMatrix extends SkewTableMatrix {

  public DiagonalMatrix(Shape shape, double[] diagonalValues) {
    super(shape, diagonalValues);
  }

  public DiagonalMatrix(Shape shape, double diagonalValue, int bound) {
    super(shape, diagonalValue, bound);
  }

  public OneTableMatrix newMatrix(double[] newValues) {
    return new DiagonalMatrix(this.shape(), newValues);
  }

  public int indexRow(int index) {
    return index;
  }

  public int indexColumn(int index) {
    return index;
  }

  public String matrixType() {
    return "diagonal";
  }
}
