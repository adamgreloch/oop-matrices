package pl.edu.mimuw;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import pl.edu.mimuw.matrix.DoubleMatrixFactory;
import pl.edu.mimuw.matrix.IDoubleMatrix;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.edu.mimuw.matrix.DoubleMatrixFactory.zero;
import static pl.edu.mimuw.matrix.MatrixCellValue.cell;
import static pl.edu.mimuw.matrix.Shape.matrix;

public class MatrixBinaryOperationTest {

  @ParameterizedTest
  @ArgumentsSource(TestMatrixSameArgumentProvider.class)
  void testPlusMatrices(IDoubleMatrix l, IDoubleMatrix r) {
    final var result = l.plus(r).data();

    final var expectedResult = new double[][]{
      new double[]{2, 4, 6},
      new double[]{8, 10, 12},
    };

    assertArrayEquals(expectedResult, result);
  }

  @ParameterizedTest
  @ArgumentsSource(TestMatrixSameArgumentProvider.class)
  void testMinusMatrices(IDoubleMatrix l, IDoubleMatrix r) {
    final var result = l.minus(r).data();

    final var expectedResult = new double[][]{
      new double[]{0, 0, 0},
      new double[]{0, 0, 0},
    };

    assertArrayEquals(expectedResult, result);
  }

  @ParameterizedTest
  @ArgumentsSource(TestMatrixTransposedShapeArgumentProvider.class)
  void testTimesMatrices(IDoubleMatrix l, IDoubleMatrix r) {
    final var result = l.times(r).data();

    final var expectedResult = new double[][]{
      new double[]{22, 28},
      new double[]{49, 64},
    };

    assertArrayEquals(expectedResult, result);
  }

  @ParameterizedTest
  @ArgumentsSource(TestMatrixArgumentProvider.class)
  void testTimesScalar(IDoubleMatrix m) {
    final var result = m.times(2).minus(m).data();
    final var expectedResult = m.data();

    assertArrayEquals(expectedResult, result);
  }

  @ParameterizedTest
  @ArgumentsSource(TestMatrixArgumentProvider.class)
  void testTimesMinusScalar(IDoubleMatrix m) {
    final var result = m.times(-2).plus(m).data();
    final var expectedResult = m.times(-1).data();

    assertArrayEquals(expectedResult, result);
  }

  @ParameterizedTest
  @ArgumentsSource(TestMatrixArgumentProvider.class)
  void testPlusMinusScalar(IDoubleMatrix m) {
    final var result = m.plus(42).minus(42).data();
    final var expectedResult = m.data();

    assertArrayEquals(expectedResult, result);
  }

  @ParameterizedTest
  @ArgumentsSource(TestMatrixArgumentProvider.class)
  void testMinusPlusScalar(IDoubleMatrix m) {
    final var result = m.plus(42).minus(42).data();
    final var expectedResult = m.data();

    assertArrayEquals(expectedResult, result);
  }

  @Test
  void testPlusSparseMatrices() {
    final var l = DoubleMatrixFactory.sparse(
      matrix(1_000_000, 1_000_000_000),
      cell(0, 0, 42),
      cell(767, 123_123, 24),
      cell(999_999, 999_999_999, 66)
    );
    final var r = DoubleMatrixFactory.sparse(
      matrix(1_000_000, 1_000_000_000),
      cell(0, 0, 24),
      cell(767, 123_123, 42)
    );
    final var result = l.plus(r);

    assertEquals(66, result.get(0, 0));
    assertEquals(66, result.get(767, 123_123));
    assertEquals(66, result.get(999_999, 999_999_999));
  }

  @Test
  void testMinusSparseMatrices() {
    final var l = DoubleMatrixFactory.sparse(
      matrix(1_000_000, 1_000_000_000),
      cell(0, 0, 42),
      cell(767, 123_123, 24),
      cell(999_999, 999_999_999, 66)
    );
    final var r = DoubleMatrixFactory.sparse(
      matrix(1_000_000, 1_000_000_000),
      cell(0, 0, 24),
      cell(767, 123_123, 42)
    );
    final var result = l.minus(r);

    assertEquals(18, result.get(0, 0));
    assertEquals(-18, result.get(767, 123_123));
    assertEquals(66, result.get(999_999, 999_999_999));
  }

  @Test
  void testTimesSparseMatrices() {
    final var l = DoubleMatrixFactory.sparse(
      matrix(1_000_000, 1_000_000_000),
      cell(0, 0, 3),
      cell(0, 213, 2),
      cell(0, 555_555, 66),

      cell(456_456, 1, 7),
      cell(456_456, 321, 8),
      cell(456_456, 444_444, 66)

    );
    final var r = DoubleMatrixFactory.sparse(
      matrix(1_000_000_000, 1_000_000),
      cell(0, 0, 4),
      cell(213, 0, 5),
      cell(666_666, 0, 66),

      cell(1, 456_456, 9),
      cell(321, 456_456, 10),
      cell(444_445, 456_456, 66)
    );
    final var result = l.times(r);

    assertEquals(22, result.get(0, 0));
    assertEquals(143, result.get(456_456, 456_456));
    assertEquals(0, result.get(42, 42));
  }

  @Test
  void testConstantTimesSparse() {
    final var l = DoubleMatrixFactory.constant(
      matrix(3, 3), 3
    );

    final var r = DoubleMatrixFactory.sparse(
      matrix(3, 3),
      cell(0, 0, 2),
      cell(2, 1, 1)
    );

    final var result = l.times(r);

    for (int i = 0; i < 2; i++) {
      assertEquals(6, result.get(i, 0));
      assertEquals(3, result.get(i, 1));
      assertEquals(0, result.get(i, 2));
    }
  }

  @Test
  void testColumnTimesRow() {
    final var l = DoubleMatrixFactory.column(
      3, 1, 2, 3
    );

    final var r = DoubleMatrixFactory.row(
      3, 1, 2, 3, 4, 5
    );

    final var result = l.times(r);

    for (int i = 0; i < 2; i++)
      for (int j = 0; j < 5; j++)
        assertEquals(3 * (i + 1) * (j + 1), result.get(i, j));
  }

  @Test
  void testLinearTransformation() {
    final var l = DoubleMatrixFactory.row(
      4, 7, 3, 3, 1
    );

    final var r = DoubleMatrixFactory.vector(
      1, 3, 3, 7
    );

    final var result = l.times(r);

    for (int i = 0; i < 4; i++)
      assertEquals(32, result.get(i, 0));
  }

  @ParameterizedTest
  @ArgumentsSource(TestMatrixSameArgumentProvider.class)
  void testZeroMatrixTimes(IDoubleMatrix l, IDoubleMatrix r) {
    final var z = zero(matrix(3, 2));
    final var result = l.times(z).times(r).data();
    final var expectedResult = new double[][]{
      new double[]{0, 0, 0},
      new double[]{0, 0, 0},
    };
    assertArrayEquals(expectedResult, result);
  }

  @ParameterizedTest
  @ArgumentsSource(TestMatrixArgumentProvider.class)
  void testZeroMatrixTimes(IDoubleMatrix m) {
    final var shape = m.shape();
    final var z = zero(matrix(shape.rows, shape.columns));
    final var expectedResult = m.data();
    assertArrayEquals(expectedResult, z.plus(m).data());
    assertArrayEquals(expectedResult, m.plus(z).data());
  }
}
