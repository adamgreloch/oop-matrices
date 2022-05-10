package pl.edu.mimuw.matrix;

import java.util.HashSet;
import java.util.List;

public class IrregularMatrix extends SparseMatrix {
  private final HashSet<MatrixCellValue> values;
  private final Shape shape;

  public IrregularMatrix(Shape shape, MatrixCellValue... values) {
    super(shape);
    this.values = new HashSet<>(List.of(values));
    this.shape = shape;
  }

  public IDoubleMatrix plusFull(FullMatrix other) {
    return null;
  }

  public IDoubleMatrix rHMinusFull(FullMatrix other) {
    return null;
  }

  public IDoubleMatrix rHTimesFull(FullMatrix other) {
    return null;
  }

  public IDoubleMatrix times(double scalar) {
    return null;
  }

  public IDoubleMatrix plus(double scalar) {
    return null;
  }

  public double get(int row, int column) {
    return 0;
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

  public IDoubleMatrix plusSparse(SparseMatrix other) {
    return null;
  }

  public IDoubleMatrix rHMinusSparse(SparseMatrix other) {
    return null;
  }

  public IDoubleMatrix rHTimesSparse(SparseMatrix other) {
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
