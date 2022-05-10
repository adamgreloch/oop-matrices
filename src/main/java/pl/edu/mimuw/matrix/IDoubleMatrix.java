package pl.edu.mimuw.matrix;

public interface IDoubleMatrix {

  IDoubleMatrix times(IDoubleMatrix other);

  IDoubleMatrix times(double scalar);

  IDoubleMatrix plus(IDoubleMatrix other);

  /**
   * Performs scalar to matrix addition following a convention chosen by Wolfram
   * i.e. matrix + constant matrix with all cells of scalar value.
   */
  IDoubleMatrix plus(double scalar);

  IDoubleMatrix minus(IDoubleMatrix other);

  IDoubleMatrix minus(double scalar);

  double get(int row, int column);

  double[][] data();

  double normOne();

  double normInfinity();

  double frobeniusNorm();

  String toString();

  Shape shape();

  IDoubleMatrix plusSparse(SparseMatrix other);

  IDoubleMatrix rHMinusSparse(SparseMatrix other);

  IDoubleMatrix plusFull(FullMatrix other);

  IDoubleMatrix rHMinusFull(FullMatrix other);

  IDoubleMatrix rHTimesFull(FullMatrix other);

  IDoubleMatrix lHMinusFull(FullMatrix other);

  double getColumn(int column);

  double getRow(int row);

  double getAbsColumn(int column);

  double getAbsRow(int row);
}
