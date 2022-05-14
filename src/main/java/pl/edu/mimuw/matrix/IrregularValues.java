package pl.edu.mimuw.matrix;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class IrregularValues {
  private final LinkedList<MatrixCellValue> valuesList;
  private final LinkedList<LinkedList<MatrixCellValue>> valuesRowCol;
  private final LinkedList<LinkedList<MatrixCellValue>> valuesColRow;

  public IrregularValues(MatrixCellValue... values) {
    this.valuesList = new LinkedList<>(List.of(values));

    /* These two structures represent the same matrix, but the order of
    cells is mutually inverted. That way, row-wise write out of cells
    costs approximately the same as its column-wise counterpart. */
    this.valuesRowCol = initRowCol(values);
    this.valuesColRow = initColRow(values);
  }

  /**
   *
   * @param values array of cells representing a matrix.
   * @return List of lists of MatrixCellValue elements where first list level
   * represents matrix rows and second columns.
   */
  private LinkedList<LinkedList<MatrixCellValue>> initRowCol(MatrixCellValue[] values) {
    MatrixCellValue[] sorted = lexicalRowOrder(values);
    LinkedList<LinkedList<MatrixCellValue>> res = new LinkedList<>();
    int row = -1;
    MatrixCellValue prev = null;
    for (MatrixCellValue value : sorted) {
      assert value != prev;
      if (value.row > row) {
        res.add(new LinkedList<>());
        row++;
      }
      res.getLast().add(value);
      prev = value;
    }
    return res;
  }

  /**
   *
   * @param values array of cells representing a matrix.
   * @return List of lists of MatrixCellValue elements where first list level
   * represents matrix rows and second columns.
   */
  private LinkedList<LinkedList<MatrixCellValue>> initColRow(MatrixCellValue[] values) {
    MatrixCellValue[] sorted = lexicalColOrder(values);
    LinkedList<LinkedList<MatrixCellValue>> res = new LinkedList<>();
    int col = -1;
    MatrixCellValue prev = null;
    for (MatrixCellValue value : sorted) {
      assert value != prev;
      if (value.column > col) {
        res.add(new LinkedList<>());
        col++;
      }
      res.getLast().add(value);
      prev = value;
    }
    return res;
  }

  private MatrixCellValue[] lexicalRowOrder(MatrixCellValue[] values) {
    MatrixCellValue[] res = Arrays.copyOf(values, values.length);

    Arrays.sort(res, (cell1, cell2) -> {
      int cmp = Integer.compare(cell1.row, cell2.row);
      if (cmp == 0)
        return Integer.compare(cell1.column, cell2.column);
      else
        return cmp;
    });

    return res;
  }

  private MatrixCellValue[] lexicalColOrder(MatrixCellValue[] values) {
    MatrixCellValue[] res = Arrays.copyOf(values, values.length);

    Arrays.sort(res, (cell1, cell2) -> {
      int cmp = Integer.compare(cell1.column, cell2.column);
      if (cmp == 0)
        return Integer.compare(cell1.row, cell2.row);
      else
        return cmp;
    });

    return res;
  }

  /**
   * Finds a cell at given coordinates
   * @return MatrixCellValue in (row, column) or null if such
   * non-zero cell doesn't exist.
   */
  public MatrixCellValue get(int row, int column) {
    for (LinkedList<MatrixCellValue> col : valuesRowCol)
      if (col.peekFirst() != null && col.peekFirst().row == row)
        for (MatrixCellValue cell : col)
          if (cell.column == column)
            return cell;
    return null;
  }

  LinkedList<MatrixCellValue> getValuesList() {
    return this.valuesList; // TODO non-copy intended?
  }

  LinkedList<LinkedList<MatrixCellValue>> getValuesRowCol() {
    return this.valuesRowCol; // TODO non-copy intended?
  }

  LinkedList<LinkedList<MatrixCellValue>> getValuesColRow() {
    return this.valuesColRow; // TODO non-copy intended?
  }

  LinkedList<MatrixCellValue> getRow(int index) {
    for (LinkedList<MatrixCellValue> column : this.valuesColRow)
      if (column.peek() != null && column.peek().column == index)
        return column;
    return null;
  }

  public double[][] generateMatrix(Shape shape) {
    double[][] res = new double[shape.rows][];
    for (int i = 0; i < shape.rows; i++)
      res[i] = new double[shape.columns];

    for (LinkedList<MatrixCellValue> col : this.valuesRowCol)
      for (MatrixCellValue cell : col)
        res[cell.row][cell.column] = cell.value;

    return res;
  }

  /**
   * Treats two rows as vectors and adds their values. Assumes that rows are sorted by column number of cells.
   * @param row1
   * @param row2
   * @return
   */
  public static LinkedList<MatrixCellValue> addRows(LinkedList<MatrixCellValue> row1, LinkedList<MatrixCellValue> row2) {
    LinkedList<MatrixCellValue> copy1 = new LinkedList<>(row1), copy2 = new LinkedList<>(row2);
    LinkedList<MatrixCellValue> longer = copy1, shorter = copy2;
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
        res.add(new MatrixCellValue(curr.row, curr.column, curr.value + shorter.pop().value));
      else
        res.add(curr);
    }

    return res;
  }

  public static LinkedList<MatrixCellValue> multiplyRow(LinkedList<MatrixCellValue> row, double scalar) {
    LinkedList<MatrixCellValue> res = new LinkedList<>();
    for (MatrixCellValue cell : row)
      res.add(new MatrixCellValue(cell.row, cell.column, cell.value * scalar));

    return res;
  }

}
