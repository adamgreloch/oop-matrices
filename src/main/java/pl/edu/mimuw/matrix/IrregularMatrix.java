package pl.edu.mimuw.matrix;

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
    LinkedList<MatrixCellValue> res = new LinkedList<>();

    for (MatrixCellValue cell : this.values.getValuesList())
      res.add(new MatrixCellValue(cell.row, cell.column, mode ? cell.value + scalar : cell.value * scalar));

    return new IrregularMatrix(this.shape(), res.toArray(MatrixCellValue[]::new));
  }

  public double get(int row, int column) {
    this.shape().assertInShape(row, column);
    MatrixCellValue res = this.values.get(row, column);
    if (res == null) return 0;
    return res.value;
  }

  public double[][] data() {
    return this.values.generateMatrix(this.shape());
  }

  public double normOne() {
    double max = 0, sum = 0;
    for (LinkedList<MatrixCellValue> column : this.values.getValuesAsCols()) {
      for (MatrixCellValue cell : column)
        sum += Math.abs(cell.value);
      max = Math.max(sum, max);
      sum = 0;
    }
    return max;
  }

  public double normInfinity() {
    double max = 0, sum = 0;
    for (LinkedList<MatrixCellValue> row : this.values.getValuesAsRows()) {
      for (MatrixCellValue cell : row)
        sum += Math.abs(cell.value);
      max = Math.max(sum, max);
      sum = 0;
    }
    return max;
  }

  public double frobeniusNorm() {
    double sum = 0;
    for (MatrixCellValue cell : this.values.getValuesList())
      sum += Math.pow(cell.value, 2);
    return Math.sqrt(sum);
  }

  @Override
  public IDoubleMatrix times(IDoubleMatrix other) {
    Shape.assertProduct(this, other);
    return other.rHTimesIrregular(this);
  }

  @Override
  public IDoubleMatrix rHTimesIrregular(IrregularMatrix other) {
    LinkedList<MatrixCellValue> res = new LinkedList<>(), sum = new LinkedList<>(), rowToAdd;
    LinkedList<LinkedList<MatrixCellValue>> toSum = new LinkedList<>();

    for (LinkedList<MatrixCellValue> row : other.values.getValuesAsRows()) {
      for (MatrixCellValue cell : row) {
        rowToAdd = this.values.getRow(cell.column);
        if (rowToAdd != null)
          toSum.add(IrregularValues.multiplyRow(rowToAdd, cell.value, cell.row));
      }
      for (LinkedList<MatrixCellValue> element : toSum)
        sum = IrregularValues.addRows(sum, element);
      toSum.clear();
      res.addAll(sum);
      sum.clear();
    }

    res = IrregularValues.mergeCells(res);

    return new IrregularMatrix(Shape.product(other, this), res.toArray(MatrixCellValue[]::new));
  }

  public IDoubleMatrix plusSparse(SparseMatrix other) {
    return other.plusIrregular(this);
  }

  public IDoubleMatrix plusIrregular(IrregularMatrix other) {
    return this.arithmeticIrregularOperator(other, false);
  }

  public IDoubleMatrix lHMinusIrregular(IrregularMatrix other) {
    return this.arithmeticIrregularOperator(other, true);
  }

  private IDoubleMatrix arithmeticIrregularOperator(IrregularMatrix other, boolean isLHReduction) {
    LinkedList<LinkedList<MatrixCellValue>> toOperate = new LinkedList<>(other.values.getValuesAsRows());
    LinkedList<MatrixCellValue> res = new LinkedList<>();
    for (LinkedList<MatrixCellValue> row : this.values.getValuesAsRows()) {
      if (row.peek() != null && toOperate.peek() != null && toOperate.peek().peek() != null) {
        if (row.peek().row == toOperate.peek().peek().row) {
          if (isLHReduction)
            res.addAll(IrregularValues.minusRows(row, toOperate.poll()));
          else
            res.addAll(IrregularValues.addRows(row, toOperate.poll()));
        }
      }
      else
        res.addAll(row);
    }
    return new IrregularMatrix(this.shape(), res.toArray(MatrixCellValue[]::new));
  }

  public IDoubleMatrix rHMinusSparse(SparseMatrix other) {
    return other.lHMinusIrregular(this);
  }

  public String matrixType() {
    return "irregular";
  }
}
