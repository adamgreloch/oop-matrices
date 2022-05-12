package pl.edu.mimuw.matrix;

public class AntiDiagonalMatrix extends SkewTableMatrix {

  public AntiDiagonalMatrix(Shape shape, double[] antiDiagonalValues) {
    super(shape, antiDiagonalValues);
  }

  public OneTableMatrix newMatrix(double[] newValues) {
    return new AntiDiagonalMatrix(this.shape(), newValues);
  }

  public int indexRow(int index) {
    return bound - index - 1;
  }

  public int indexColumn(int index) {
    return index;
  }

  public String sparseType() {
    return "antiDiagonal";
  }
}
