package pl.edu.mimuw.matrix;

import java.util.ArrayList;
import java.util.LinkedList;

public class IrregularMatrix extends SparseMatrix {
  private final IrregularValues values;

  public IrregularMatrix(Shape shape, MatrixCellValue... values) {
    super(shape);

    for (MatrixCellValue cell : values)
      shape.assertInShape(cell.row, cell.column);

    this.values = new IrregularValues(values);
  }

  public IDoubleMatrix plusFull(FullMatrix other) {
    double[][] res = other.data();

    for (MatrixCellValue cell : this.values.getValuesList())
      res[cell.row][cell.column] += cell.value;

    return new FullMatrix(res);
  }

  public IDoubleMatrix rHMinusFull(FullMatrix other) {
    double[][] res = other.data();

    for (MatrixCellValue cell : this.values.getValuesList())
      res[cell.row][cell.column] -= cell.value;

    return new FullMatrix(res);
  }

  public IDoubleMatrix lHMinusFull(FullMatrix other) {
    double[][] res = other.data();

    for (MatrixCellValue cell : this.values.getValuesList())
      res[cell.row][cell.column] = cell.value - res[cell.row][cell.column];

    return new FullMatrix(res);
  }

  public IDoubleMatrix times(double scalar) {
    return this.scalarOperator(scalar, false);
  }

  public IDoubleMatrix plus(double scalar) {
    return this.scalarOperator(scalar, true);
  }

  private IDoubleMatrix scalarOperator(double scalar, boolean mode) {
    ArrayList<MatrixCellValue> res = new ArrayList<>();

    for (MatrixCellValue cell : this.values.getValuesList())
      res.add(new MatrixCellValue(cell.row, cell.column, mode ? cell.value + scalar : cell.value * scalar));

    return new IrregularMatrix(this.shape(), res.toArray(MatrixCellValue[]::new));
  }

  public double get(int row, int column) {
    MatrixCellValue res = this.values.get(row, column);
    if (res == null) return 0;
    return res.value;
  }

  public double[][] data() {
    return this.values.generateMatrix(this.shape());
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

  @Override
  public IDoubleMatrix times(IDoubleMatrix other) {
    Shape.assertProduct(this, other);
    return other.rHTimesIrregular(this);
  }

  @Override
  public IDoubleMatrix rHTimesIrregular(IrregularMatrix other) {
    LinkedList<MatrixCellValue> res = new LinkedList<>();
    LinkedList<LinkedList<MatrixCellValue>> toSum = new LinkedList<>();
    LinkedList<MatrixCellValue> sum = new LinkedList<>();

    for (LinkedList<MatrixCellValue> row : other.values.getValuesColRow()) {
      for (MatrixCellValue cell : row)
        toSum.add(IrregularValues.multiplyRow(this.values.getRow(cell.column), cell.value));
      for (LinkedList<MatrixCellValue> element : toSum)
        IrregularValues.addRows(sum, element);
      res.addAll(sum);
      sum.clear();
    }

    return new IrregularMatrix(Shape.product(other, this), res.toArray(MatrixCellValue[]::new));
  }

  public IDoubleMatrix plusSparse(SparseMatrix other) {
    return other.plusIrregular(this);
  }

  public IDoubleMatrix plusIrregular(IrregularMatrix other) {
    return null;
  }

  public IDoubleMatrix rHMinusSparse(SparseMatrix other) {
    return null;
  }

  public double getColumn(int column) {
    return 0;
  }

  public double getRow(int row) {
    return 0;
  }

  private IrregularValues getValues() {
    return this.values;
  }

  public String matrixType() {
    return "irregular";
  }
}
