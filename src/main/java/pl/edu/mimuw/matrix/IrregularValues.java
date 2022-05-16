package pl.edu.mimuw.matrix;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class IrregularValues {
  private final LinkedList<MatrixCellValue> valuesList;
  private final LinkedList<LinkedList<MatrixCellValue>> valuesAsRows;
  private final LinkedList<LinkedList<MatrixCellValue>> valuesAsCols;

  public IrregularValues(MatrixCellValue... values) {
    this.valuesList = new LinkedList<>(List.of(values));

    /* Keeping the same matrix as two differently organized lists of lists
    allow row-wise write out of cells to cost approximately the same as
    its column-wise counterpart. */
    this.valuesAsRows = initAsRows(values);
    this.valuesAsCols = initAsCols(values);
  }

  private static MatrixCellValue[] lexicalOrder(MatrixCellValue[] values, boolean isRowFocused) {
    MatrixCellValue[] res = Arrays.copyOf(values, values.length);

    Arrays.sort(res, (cell1, cell2) -> {
      int cmp = Integer.compare(cell1.row, cell2.row);
      if (cmp == 0)
        cmp = Integer.compare(cell1.column, cell2.column);
      return cmp * (isRowFocused ? 1 : -1);
    });

    return res;
  }

  public static LinkedList<MatrixCellValue> addRows(LinkedList<MatrixCellValue> row1,
                                                    LinkedList<MatrixCellValue> row2) {
    return arithmeticRowOperator(row1, row2, false);
  }

  public static LinkedList<MatrixCellValue> minusRows(LinkedList<MatrixCellValue> row1,
                                                      LinkedList<MatrixCellValue> row2) {
    return arithmeticRowOperator(row1, row2, true);
  }

  private static LinkedList<MatrixCellValue> arithmeticRowOperator(LinkedList<MatrixCellValue> row1,
                                                                   LinkedList<MatrixCellValue> row2,
                                                                   boolean isSubtraction) {
    LinkedList<MatrixCellValue> longer = new LinkedList<>(row1), shorter = new LinkedList<>(row2);
    if (longer.size() < shorter.size()) {
      LinkedList<MatrixCellValue> buf = longer;
      longer = shorter;
      shorter = buf;
    }

    LinkedList<MatrixCellValue> res = new LinkedList<>();
    MatrixCellValue curr;

    while (!longer.isEmpty()) {
      curr = longer.pop();
      if (shorter.peek() != null && curr.column == shorter.peek().column)
        res.add(new MatrixCellValue(curr.row, curr.column,
          curr.value + (isSubtraction ? -1 : 1) * shorter.pop().value));
      else
        res.add(curr);
    }

    return res;
  }

  public static LinkedList<MatrixCellValue> multiplyRow(LinkedList<MatrixCellValue> row, double scalar, int newRow) {
    LinkedList<MatrixCellValue> res = new LinkedList<>();
    for (MatrixCellValue cell : row)
      res.add(new MatrixCellValue(newRow, cell.column, cell.value * scalar));

    return res;
  }

  public static LinkedList<MatrixCellValue> mergeCells(LinkedList<MatrixCellValue> list) {
    LinkedList<MatrixCellValue> res = new LinkedList<>();
    MatrixCellValue[] sorted = lexicalOrder(list.toArray(MatrixCellValue[]::new), true);
    MatrixCellValue prev = null;
    double sum = 0;

    for (MatrixCellValue cell : sorted) {
      if (prev == null)
        sum = cell.value;
      else if (cell.row == prev.row && cell.column == prev.column)
        sum += cell.value;
      else {
        res.add(new MatrixCellValue(prev.row, prev.column, sum));
        sum = cell.value;
      }
      prev = cell;
    }

    if (prev != null)
      res.add(new MatrixCellValue(prev.row, prev.column, sum));

    return res;
  }

  private LinkedList<LinkedList<MatrixCellValue>> initAsRows(MatrixCellValue[] values) {
    return initAs(values, true);
  }

  private LinkedList<LinkedList<MatrixCellValue>> initAsCols(MatrixCellValue[] values) {
    return initAs(values, false);
  }

  private LinkedList<LinkedList<MatrixCellValue>> initAs(MatrixCellValue[] values, boolean isRowFocued) {
    MatrixCellValue[] sorted = lexicalOrder(values, isRowFocued);
    LinkedList<LinkedList<MatrixCellValue>> res = new LinkedList<>();
    int i = -1;
    MatrixCellValue prev = null;
    for (MatrixCellValue value : sorted) {
      assert !value.equals(prev);
      if ((isRowFocued ? value.row : value.column) > i) {
        res.add(new LinkedList<>());
        i++;
      }
      res.getLast().add(value);
      prev = value;
    }
    return res;
  }

  public MatrixCellValue get(int i, int j) {
    for (LinkedList<MatrixCellValue> row : valuesAsRows)
      if (row.peekFirst() != null && row.peekFirst().row == i)
        for (MatrixCellValue cell : row)
          if (cell.column == j)
            return cell;
    return null;
  }

  LinkedList<MatrixCellValue> getValuesList() {
    return this.valuesList;
  }

  LinkedList<LinkedList<MatrixCellValue>> getValuesAsRows() {
    return this.valuesAsRows;
  }

  LinkedList<LinkedList<MatrixCellValue>> getValuesAsCols() {
    return this.valuesAsCols;
  }

  public LinkedList<MatrixCellValue> getRow(int index) {
    for (LinkedList<MatrixCellValue> row : this.valuesAsRows)
      if (row.peek() != null && row.peek().row == index)
        return row;
    return null;
  }

  public double[][] generateMatrix(Shape shape) {
    double[][] res = new double[shape.rows][];
    for (int i = 0; i < shape.rows; i++)
      res[i] = new double[shape.columns];

    for (LinkedList<MatrixCellValue> row : this.valuesAsRows)
      for (MatrixCellValue cell : row)
        res[cell.row][cell.column] = cell.value;

    return res;
  }
}
