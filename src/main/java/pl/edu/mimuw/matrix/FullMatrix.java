package pl.edu.mimuw.matrix;

public class FullMatrix extends DoubleMatrix {
  private final double[][] values;

  public FullMatrix(double[][] values) {
    super(Shape.matrix(values.length, values[0].length));
    this.values = copy2DTable(values);
  }

  private static double[][] copy2DTable(double[][] table) {
    double[][] res = new double[table.length][];
    int rows = table[0].length;

    for (int i = 0; i < table.length; i++) {
      assert table[i].length == rows;
      res[i] = table[i].clone();
    }

    return res;
  }

  public IDoubleMatrix times(double scalar) {
    double[][] res = copy2DTable(this.values);

    for (int i = 0; i < this.shape().rows; i++)
      for (int j = 0; j < this.shape().columns; j++)
        res[i][j] *= scalar;
    return new FullMatrix(res);
  }

  public IDoubleMatrix plus(IDoubleMatrix other) {
    assert this.shape().equals(other.shape());
    return other.plusFull(this);
  }

  public IDoubleMatrix plus(double scalar) {
    double[][] res = copy2DTable(this.values);

    for (int i = 0; i < this.shape().rows; i++)
      for (int j = 0; j < this.shape().columns; j++)
        res[i][j] += scalar;
    return new FullMatrix(res);
  }

  public IDoubleMatrix minus(IDoubleMatrix other) {
    assert this.shape().equals(other.shape());
    return other.rHMinusFull(this);
  }

  public IDoubleMatrix minus(double scalar) {
    return plus(-scalar);
  }

  public double get(int row, int column) {
    this.assertInMatrix(row, column);
    return this.values[row][column];
  }

  public double[][] data() {
    return copy2DTable(this.values);
  }

  protected double calculateNormOne() {
    double max = 0;

    for (int i = 0; i < this.shape().columns; i++)
      max = Math.max(this.getAbsColumn(i), max);
    return max;
  }

  protected double calculateNormInfinity() {
    double max = 0;

    for (int i = 0; i < this.shape().rows; i++)
      max = Math.max(this.getAbsRow(i), max);
    return max;
  }

  protected double calculateFrobeniusNorm() {
    double res = 0;

    for (int i = 0; i < this.shape().rows; i++)
      for (int j = 0; j < this.shape().columns; j++)
        res += Math.pow(this.get(i, j), 2);
    return Math.sqrt(res);
  }

  public IDoubleMatrix plusSparse(SparseMatrix other) {
    return other.plusFull(this);
  }

  public IDoubleMatrix rHMinusSparse(SparseMatrix other) {
    return other.lHMinusFull(this);
  }

  public IDoubleMatrix plusFull(FullMatrix other) {
    double[][] res = copy2DTable(this.values);

    for (int i = 0; i < this.shape().rows; i++)
      for (int j = 0; j < this.shape().columns; j++)
        res[i][j] += other.get(i, j);
    return new FullMatrix(res);
  }

  public IDoubleMatrix rHMinusFull(FullMatrix other) {
    double[][] res = copy2DTable(other.values);

    for (int i = 0; i < this.shape().rows; i++)
      for (int j = 0; j < this.shape().columns; j++)
        res[i][j] -= this.get(i, j);
    return new FullMatrix(res);
  }

  public IDoubleMatrix lHMinusFull(FullMatrix other) {
    return other.rHMinusFull(this);
  }

  public double getAbsColumn(int column) {
    double sum = 0;
    for (int i = 0; i < this.shape().rows; i++)
      sum += Math.abs(this.get(i, column));
    return sum;
  }

  public double getAbsRow(int row) {
    double sum = 0;
    for (int i = 0; i < this.shape().columns; i++)
      sum += Math.abs(this.get(row, i));
    return sum;
  }

  public String matrixType() {
    return "full";
  }

  protected int getCellMaxWidth() {
    int max = 0;
    for (int i = 0; i < this.shape().rows; i++)
      for (int j = 0; j < this.shape().columns; j++)
        max = Math.max(String.format("%.1f", this.values[i][j]).length(), max);
    return max;
  }

  public String printMatrix() {
    StringBuilder res = new StringBuilder();
    int maxLength = getCellMaxWidth();

    for (int i = 0; i < this.shape().rows; i++) {
      res.append("| ");
      for (int j = 0; j < this.shape().columns; j++)
        res.append(String.format("%-" + maxLength + ".1f ", this.values[i][j]));
      res.append("|\n");
    }
    return res.toString();
  }
}
