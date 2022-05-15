package pl.edu.mimuw;

import pl.edu.mimuw.matrix.*;

import static pl.edu.mimuw.matrix.DoubleMatrixFactory.sparse;
import static pl.edu.mimuw.matrix.MatrixCellValue.cell;
import static pl.edu.mimuw.matrix.Shape.matrix;

public class Main {

  public static void main(String[] args) {
    IDoubleMatrix constant = new ConstantMatrix(Shape.matrix(10, 10), 2);
    System.out.println(constant);

    IDoubleMatrix zero = DoubleMatrixFactory.zero(Shape.matrix(10, 10));
    System.out.println(zero);

    IDoubleMatrix column = new ColumnMatrix(Shape.matrix(10, 10), new double[]{1, 2000, 3, 4, 5});
    System.out.println(column);

    IDoubleMatrix row = new RowMatrix(Shape.matrix(10, 10), new double[]{1, 2000, 3, 4, 5});
    System.out.println(row);

    IDoubleMatrix diagonal = new DiagonalMatrix(Shape.matrix(10, 10), new double[]{1, 2000, 3, 4, 5});
    System.out.println(diagonal);

    IDoubleMatrix vector = DoubleMatrixFactory.vector(1, 1, 2, 3, 4);
    System.out.println(vector);

    IDoubleMatrix SPARSE_3X2 = sparse(matrix(10, 10),
      cell(0, 0, 1),
      cell(0, 1, 2),
      cell(1, 0, 3),
      cell(1, 1, 4),
      cell(2, 0, 5),
      cell(2, 1, 6)
    );
    System.out.println(SPARSE_3X2);

    IDoubleMatrix r = DoubleMatrixFactory.sparse(
      matrix(10, 10),
      cell(0, 0, 24),
      cell(10, 3, 42)
    );
    System.out.println(r);
  }
}
