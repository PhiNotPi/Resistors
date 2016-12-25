import java.util.List;

public class Series extends Subcircuit {

  public Series() {
    super(0, 2, 100, false);
  }

  @Override
  double eval(double[] box) {
    double sum = 0;
    for (int i = 0; i < children.size(); i++) {
      sum += children.get(i).eval(box);
    }
    return sum;
  }

  @Override
  public String toString() {
    String res = "<";
    for (Subcircuit s : children) {
      res += s.toString();
    }
    return res + ">";
  }

  @Override
  public String toEq() {
    String res = "(";
    for (int i = 0; i < children.size(); i++) {
      if (i > 0) {
        res += "+";
      }
      res += children.get(i).toEq();
    }
    return res + ")";
  }

  @Override
  protected Subcircuit clone() {
    return new Series();
  }

  @Override
  boolean wouldBeValid(List<Subcircuit> children) {
    if (children.size() < 2) {
      return false;
    }
    for (int i = 0; i < children.size(); i++) {
      Subcircuit c = children.get(i);
      if (c instanceof Series) {
        return false;
      }
      // if (!c.isValid()) {
      // return false;
      // }
      if (i > 0) {
        if (c.id < children.get(i - 1).id) {
          return false;
        }
      }
    }
    return true;
  }
}
