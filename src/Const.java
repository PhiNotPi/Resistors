import java.util.List;

public class Const extends Subcircuit {

  double type;

  public Const(double type) {
    super(1, 0, 0, false);
    this.type = type;
  }

  @Override
  double eval(double[] box) {
    return type;
  }

  @Override
  public String toString() {
    if (type == 1) {
      return "1";
    } else if (type == 2) {
      return "2";
    } else if (type == 0.5) {
      return "5";
    }
    return "?";
  }

  @Override
  public String toEq() {
    return "" + type;
  }

  @Override
  protected Subcircuit clone() {
    return new Const(type);
  }

  @Override
  boolean wouldBeValid(List<Subcircuit> children) {
    return true;
  }

}
