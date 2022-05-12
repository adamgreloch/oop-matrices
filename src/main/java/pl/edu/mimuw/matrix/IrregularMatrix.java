package pl.edu.mimuw.matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IrregularMatrix extends SparseMatrix {
  private final ArrayList<MatrixCellValue> valuesList;
  private final ArrayList<ArrayList<MatrixCellValue>> values;

  public IrregularMatrix(Shape shape, MatrixCellValue... values) {
    super(shape);

    for (MatrixCellValue cell : values)
      shape.assertInShape(cell.row, cell.column);

    lexicalOrder(values);

    this.valuesList = new ArrayList<>(List.of(values));
    this.values = new ArrayList<>();
    this.values.add(new ArrayList<>());

    MatrixCellValue prev = null;
    int i = 0;

    for (MatrixCellValue cell : values) {
      if (prev != null && cell.row != prev.row) {
        this.values.add(new ArrayList<>());
        i++;
      }
      assert cell != prev;
      this.values.get(i).add(cell);
      prev = cell;
    }
  }

  private void lexicalOrder(MatrixCellValue... values) {
    Arrays.sort(values, (cell1, cell2) -> {
      int cmp = Integer.compare(cell1.row, cell2.row);
      if (cmp == 0)
        return Integer.compare(cell1.column, cell2.column);
      else
        return cmp;
    });
  }

  public IDoubleMatrix plusFull(FullMatrix other) {
    double[][] res = other.data();

    for (MatrixCellValue cell : this.valuesList)
      res[cell.row][cell.column] += cell.value;

    return new FullMatrix(res);
  }

  public IDoubleMatrix rHMinusFull(FullMatrix other) {
    double[][] res = other.data();

    for (MatrixCellValue cell : this.valuesList)
      res[cell.row][cell.column] -= cell.value;

    return new FullMatrix(res);
  }

  public IDoubleMatrix lHMinusFull(FullMatrix other) {
    double[][] res = other.data();

    for (MatrixCellValue cell : this.valuesList)
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

    for (MatrixCellValue cell : this.valuesList)
      res.add(new MatrixCellValue(cell.row, cell.column, mode ? cell.value + scalar : cell.value * scalar));

    return new IrregularMatrix(this.shape(), res.toArray(MatrixCellValue[]::new));
  }

  public double get(int row, int column) {
    this.assertInMatrix(row, column);
    ArrayList<MatrixCellValue> rowCells = this.values.get(row);

    if (rowCells != null)
      for (MatrixCellValue cell : rowCells)
        if (cell.column == column)
          return cell.value;

    return 0;
  }

  public double[][] data() {
    double[][] res = new double[this.shape().rows][];
    for (int i = 0; i < this.shape().rows; i++)
      res[i] = new double[this.shape().columns];

    for (ArrayList<MatrixCellValue> rows : this.values)
      for (MatrixCellValue cell : rows)
        res[cell.row][cell.column] = cell.value;

    return res;
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

  public IDoubleMatrix plusSparse(SparseMatrix other) {
    return other.plusIrregular(this);
  }

  @Override
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

  public String sparseType() {
    return "irregular";
  }
}
