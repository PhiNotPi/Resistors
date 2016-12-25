import java.util.ArrayList;
import java.util.List;

public abstract class Subcircuit {

  int weight;
  int arityMin;
  int arityMax;
  boolean ordered;
  int id;
  int quota = 100;

  public Subcircuit(int weight, int arityMin, int arityMax, boolean ordered) {
    super();
    this.weight = weight;
    this.arityMin = arityMin;
    this.arityMax = arityMax;
    this.ordered = ordered;
  }

  ArrayList<Subcircuit> children = new ArrayList<Subcircuit>();

  abstract double eval(double[] box);

  public abstract String toString();

  public abstract String toEq();

  boolean isValid() {
    return wouldBeValid(children);
  };

  abstract boolean wouldBeValid(List<Subcircuit> children);

  protected abstract Subcircuit clone();
}
