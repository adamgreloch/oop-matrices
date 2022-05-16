/*
 * @author Adam Greloch
 * JetBrains IntelliJ IDEA 2022.1 (Ultimate Edition)
 * Build #IU-221.5080.210, built on April 12, 2022
 */

package pl.edu.mimuw;

import pl.edu.mimuw.matrix.*;

import static pl.edu.mimuw.matrix.DoubleMatrixFactory.*;
import static pl.edu.mimuw.matrix.MatrixCellValue.cell;
import static pl.edu.mimuw.matrix.Shape.matrix;

public class Main {

  private static void demo(IDoubleMatrix matrix, String demoTitle) {
    System.out.println("###   " + demoTitle + "   ###\n" + matrix);
    System.out.println("normOne: " + matrix.normOne());
    System.out.println("normInfinity: " + matrix.normInfinity());
    System.out.println("frobeniusNorm: " + matrix.frobeniusNorm() + "\n");
  }

  public static void main(String[] args) {
    IDoubleMatrix constant = constant(Shape.matrix(10, 10), 2);
    IDoubleMatrix zero = zero(Shape.matrix(10, 10));
    IDoubleMatrix column = column(10, 1, 2136, 3, 4, 5, 10, 2, 2, 5, 6);
    IDoubleMatrix row = row(10, 1, 2000, 3, 4, 5, 13, 13, 13, 13, 13);
    IDoubleMatrix diagonal = diagonal(1, 2000, 3, 4, 5, 5, 6, 7, 8, 10);
    IDoubleMatrix antiDiagonal = antiDiagonal(21, 21, 31, 14, 25, 10, 10, 11, 42, 3);
    IDoubleMatrix vector = vector(1, 3, 4, 1, 1, 2, 3, 5, 10, 2000);
    IDoubleMatrix SPARSE_3X2 = sparse(matrix(10, 10),
      cell(0, 0, 1),
      cell(0, 1, 2),
      cell(1, 0, 3),
      cell(1, 1, 4),
      cell(2, 0, 5),
      cell(2, 1, 6)
    );

    IDoubleMatrix sparse = DoubleMatrixFactory.sparse(
      matrix(10, 10),
      cell(0, 0, 24),
      cell(9, 3, 42),
      cell(5, 5, 11)
    );

    demo(zero, "Trivial");
    demo(constant, "Less trivial but still");
    demo(sparse, "Example sparse matrix");
    demo(sparse.times(SPARSE_3X2), "My sparse times SPARSE_3X2");
    demo(SPARSE_3X2.times(vector), "Transforming a vector with SPARSE_3X2");
    demo(column.times(row), "Column times Row");
    demo(constant.times(sparse), "Look! Constant times Sparse is a RowMatrix!");
    demo(diagonal.plus(42), "Adding 42 to a DiagonalMatrix gives lots of 42's");
    demo(column.plus(42), "But adding 42 to my ColumnMatrix gives only one 42. Can you spot it?");
    demo(diagonal, "DiagonalMatrix");
    demo(antiDiagonal, "AntiDiagonalMatrix");
    demo(sparse.minus(sparse), "We can subtract the same matrix to get void and darkness again");
    demo(sparse.minus(sparse).minus(sparse), "Subtracting same matrix twice from itself gives negative void and darkness");
    demo(identity(10)
      .times(constant) .minus(sparse)
      .minus(diagonal).times(2)
      .minus(sparse)
      .times(vector(1,2,3,4,5,6,7,8,9,10)
      .times(row(1,9,9,9,9,1,1,1,1,2,5))), "Identity, Constant, Sparse, Vector and Row combined");
  }
}
