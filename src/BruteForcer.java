import java.util.ArrayList;
import java.util.List;

public class BruteForcer {
  static double[][] box = { { 0, 10 }, { 10, 0 }, { 5, 5 }, { 2, 8 }, { 1, 9 },
      { 1, 16 }, { 1, 25 }, { 5, 5 }, { 8, 2 }, { 9, 1 }, { 16, 1 }, { 25, 1 },
      { 6, 3 }, { 3, 6 }, { 6, 4 }, { 4, 6 } };
  static double[] targ = { 0, 0, 25, 16, 9, 16, 25, 25, 16, 9, 16, 25, 18, 18,
      24, 24 };
  static double[] consts = { 0.5, 1, 2 };

  static ArrayList<Subcircuit> opts = new ArrayList<Subcircuit>();
  static ArrayList<Subcircuit> circuit = new ArrayList<Subcircuit>();

  public static void main(String[] args) {
    addOpt(new Series());
    addOpt(new Parallel());
    // addOpt(new H());
    for (int i = 0; i < consts.length; i++) {
      addOpt(new Const(consts[i]));
    }
    for (int i = 0; i < box[0].length; i++) {
      Input in = new Input(i);
      in.quota = 10;
      addOpt(in);
    }
    for (int i = 1; i <= 50; i++) {
      System.out.println(i);
      recurse(i);
    }
  }

  static void addOpt(Subcircuit s) {
    s.id = opts.size();
    opts.add(s);
  }

  static void recurse(int depth) {
    // printCircuit();
    // System.out.println("test");
    int height = circuit.size();
    if (height == 1 && depth == 0) {
      // System.out.println("test2");
      register();
    }
    opt: for (Subcircuit s : opts) {
      if (s.weight <= depth && s.quota > 0) {
        for (int i = s.arityMin; i <= height && i <= s.arityMax; i++) {
          List<Subcircuit> children = circuit.subList(0, i);
          if (s.wouldBeValid(children)) {
            Subcircuit cur = s.clone();
            for (int r = 0; r < i; r++) {
              cur.children.add(circuit.remove(0));
            }
            circuit.add(0, cur);
            s.quota--;
            recurse(depth - s.weight);
            s.quota++;
            circuit.remove(0);
            circuit.addAll(0, cur.children);
          } else {
            continue opt;
          }
          // Subcircuit cur = s.clone();
          // for (int r = 0; r < i; r++) {
          // cur.children.add(circuit.remove(0));
          // }
          // boolean valid = cur.isValid();
          // if (valid) {
          // circuit.add(0, cur);
          // recurse(depth - s.weight);
          // circuit.remove(0);
          // }
          // circuit.addAll(0, cur.children);
          // if (!valid) {
          // continue opt;
          // }
        }
      }
    }
  }

  static double best = Double.MAX_VALUE;

  static void register() {
    double fitness = 0;
    for (int i = 0; i < box.length; i++) {
      double max = circuit.get(0).eval(box[i]);
      double dif = max - targ[i];
      if (targ[i] > max) {
        max = targ[i];
      }
      if (dif != 0) {
        dif /= max;
      }
      double err = dif * dif;
      fitness += err;
    }
    if (fitness < best) {
      best = fitness;
      System.out.print(fitness + "  ");
      printCircuit();
      System.out.print("  ");
      printCircuitEq();
      System.out.println();
    }
  }

  static void printCircuit() {
    for (int i = 0; i < circuit.size(); i++) {
      System.out.print(circuit.get(i));
    }
  }

  static void printCircuitEq() {
    for (int i = 0; i < circuit.size(); i++) {
      System.out.print(circuit.get(i).toEq());
    }
  }

  // static boolean validateCircuit() {
  // for (int i = 0; i < circuit.size(); i++) {
  // if (!circuit.get(i).isValid()) {
  // return false;
  // }
  // }
  // return true;
  // }

}
