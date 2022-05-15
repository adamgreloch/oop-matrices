package pl.edu.mimuw;

import pl.edu.mimuw.matrix.DoubleMatrixFactory;
import pl.edu.mimuw.matrix.IDoubleMatrix;
import pl.edu.mimuw.matrix.Shape;

public class Main {

  public static void main(String[] args) {
    // TODO Tu trzeba wpisać kod testujący toString dla poszczególnych macierzy i wyników operacji
    IDoubleMatrix xd = DoubleMatrixFactory.zero(Shape.matrix(5, 5));
    System.out.println(xd);
  }
}
