package pl.edu.mimuw.matrix;

public class ConstantMatrix extends SparseMatrix {
  private double value;

  public ConstantMatrix(Shape shape, double value) {
    super(shape);
    this.value = value;
  }

  public IDoubleMatrix plusSparse(SparseMatrix other) {
    return other.plus(this.value);
  }

  public IDoubleMatrix rHMinusSparse(SparseMatrix other) {
    return other.minus(this.value);
  }

  public IDoubleMatrix rHTimesSparse(SparseMatrix other) {
    /**
     * Product of ConstantMatrix times any SparseMatrix is a ColumnMatrix
     */
    double[] rows = new double[this.shape().columns];
    for (int i = 0; i < this.shape().columns; i++)
      rows[i] = other.getRow(i) * this.value;
    return new ColumnMatrix(Shape.product(other, this), rows);
  }

  @Override
  public IDoubleMatrix plusFull(FullMatrix other) {
    return null;
  }

  @Override
  public IDoubleMatrix rHMinusFull(FullMatrix other) {
    return null;
  }

  @Override
  public IDoubleMatrix rHTimesFull(FullMatrix other) {
    return null;
  }

  public IDoubleMatrix times(double scalar) {
    return new ConstantMatrix(this.shape(), value * scalar);
  }

  public IDoubleMatrix plus(double scalar) {
    return new ConstantMatrix(this.shape(), value + scalar);
  }

  public double get(int row, int column) {
    this.assertInMatrix(row, column);
    return value;
  }

  public double[][] data() {
    return new double[0][];
  }

  public double normOne() {
    return 0;
  }

  public double normInfinity() {
    return 0;
  }

  public double frobeniusNorm() {
    return 0;
  }

  public double getColumn(int column) {
    this.assertInMatrix(0, column);
    return value * shape().rows;
  }

  public double getRow(int row) {
    this.assertInMatrix(row, 0);
    return value * shape().columns;
  }

  public String sparseType() {
    return "constant";
  }

  @Override
  public String toString() {
    // TODO deredund
    int columns = this.shape().columns;
    StringBuilder res = new StringBuilder();
    if (this.shape().rows < 3) {
      for (int i = 0; i < columns; i++) {
        res.append(" ");
        for (int j = 0; j < columns; j++)
          res.append(this.value).append(" ");
        res.append("\n");
      }
      return res.toString();
    }

    for (int i = 0; i < columns; i++) {
      for (int j = 0; j < columns; j++) {
        if (j == 0 || j == columns - 1)
          res.append(this.value);
        if (j == (columns - 1)/2)
          res.append("...");
        res.append("   ");
      }
      res.append("\n");
    }
    return res.toString();
  }
}
