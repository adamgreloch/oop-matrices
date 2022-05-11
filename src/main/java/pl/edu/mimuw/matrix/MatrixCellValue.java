package pl.edu.mimuw.matrix;

import java.util.Objects;

public final class MatrixCellValue {

  public final int row;
  public final int column;
  public final double value;

  public MatrixCellValue(int row, int column, double value) {
    this.column = column;
    this.row = row;
    this.value = value;
  }

  public static MatrixCellValue cell(int row, int column, double value) {
    return new MatrixCellValue(row, column, value);
  }

  @Override
  public String toString() {
    return "{" + value + " @[" + row + ", " + column + "]}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MatrixCellValue cell = (MatrixCellValue) o;
    return row == cell.row && column == cell.column && value == cell.value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, column, value);
  }

  public int row() {
    return this.row;
  }

  public int column() {
    return this.column;
  }

  public double value() {
    return this.value;
  }
}
