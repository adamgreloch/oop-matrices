package pl.edu.mimuw.matrix;

public class FullMatrix implements IDoubleMatrix {
  private final double[][] values;
  private final Shape shape;

  public FullMatrix(double[][] values) {
    this.shape = Shape.matrix(values.length, values[0].length);
    this.values = copy2DTable(values);
  }

  private static double[][] copy2DTable(double[][] table) {
    double[][] res = new double[table.length][];
    for (int i = 0; i < table.length; i++)
      res[i] = table[i].clone();
    return res;
  }

  public IDoubleMatrix times(IDoubleMatrix other) {
    Shape.assertProduct(this, other);
    return other.rHTimesFull(this);
  }

  public IDoubleMatrix times(double scalar) {
    double[][] res = copy2DTable(this.values);

    for (int i = 0; i < this.shape().rows; i++)
      for (int j = 0; j < this.shape().columns; j++)
        res[i][j] *= scalar;
    return new FullMatrix(res);
  }

  public IDoubleMatrix plus(IDoubleMatrix other) {
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
    return other.lHMinusFull(this);
  }

  public IDoubleMatrix minus(double scalar) {
    return plus(-scalar);
  }

  public double get(int row, int column) {
    return this.values[row][column];
  }

  public double[][] data() {
    return copy2DTable(this.values);
  }

  public double normOne() {
    double max = 0;

    for (int i = 0; i < this.shape().columns; i++)
      max = Math.max(this.getAbsColumn(i), max);

    return max;
  }

  public double normInfinity() {
    double max = 0;

    for (int i = 0; i < this.shape().rows; i++)
        max = Math.max(this.getAbsRow(i), max);

    return max;
  }

  public double frobeniusNorm() {
    double frob = 0;

    for (int i = 0; i < this.shape().rows; i++)
      for (int j = 0; j < this.shape().columns; j++)
        frob += Math.pow(this.get(i, j), 2);

    return Math.sqrt(frob);
  }

  public Shape shape() {
    return this.shape;
  }

  public IDoubleMatrix plusSparse(SparseMatrix other) {
    return other.plusFull(this);
  }

  public IDoubleMatrix rHMinusSparse(SparseMatrix other) {
    return other.lHMinusFull(this);
  }

  public IDoubleMatrix rHTimesSparse(SparseMatrix other) {
    return other.lHTimesFull(this);
  }

  public IDoubleMatrix plusFull(FullMatrix other) {
    assert this.shape.equals(other.shape);
    double[][] res = new double[this.values.length][];

    for (int i = 0; i < this.shape().rows; i++)
      for (int j = 0; j < this.shape().columns; j++)
        res[i][j] = this.get(i, j) + other.get(i, j);
    return new FullMatrix(res);
  }

  public IDoubleMatrix rHMinusFull(FullMatrix other) {
    assert this.shape.equals(other.shape);
    double[][] res = new double[this.values.length][];

    for (int i = 0; i < this.shape().rows; i++)
      for (int j = 0; j < this.shape().columns; j++)
        res[i][j] = other.get(i, j) - this.get(i, j);
    return new FullMatrix(res);
  }

  @Override
  public IDoubleMatrix rHTimesFull(FullMatrix other) {
    Shape.assertProduct(other, this);
    double[][] res = new double[this.values.length][];

    for (int i = 0; i < this.shape().rows; i++)
      for (int j = 0; j < this.shape().columns; j++)
        for (int k = 0; k < this.shape().columns; k++)
          res[i][j] = other.get(i, k) * this.get(k, j);

    return new FullMatrix(res);
  }

  public IDoubleMatrix lHMinusFull(FullMatrix other) {
    return other.rHMinusFull(this);
  }

  public IDoubleMatrix lHTimesFull(FullMatrix other) {
    return other.rHTimesFull(this);
  }

  public double getColumn(int column) {
    double sum = 0;
    for (int i = 0; i < this.shape().rows; i++)
      sum += this.get(i, column);
    return sum;
  }

  public double getRow(int row) {
    double sum = 0;
    for (int i = 0; i < this.shape().rows; i++)
      sum += this.get(row, i);
    return sum;
  }

  public double getAbsColumn(int column) {
    double sum = 0;
    for (int i = 0; i < this.shape().rows; i++)
      sum += Math.abs(this.get(i, column));
    return sum;
  }

  public double getAbsRow(int row) {
    double sum = 0;
    for (int i = 0; i < this.shape().rows; i++)
      sum += Math.abs(this.get(row, i));
    return sum;
  }
}