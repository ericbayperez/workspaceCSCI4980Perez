package simpleVisitorPattern.part;

import simpleVisitorPattern.visitor.CarPartVisitor;

public class Brake implements ICarElement {
   String name;
   String modelNumberBrake;
   String modelYearBrake;

   public Brake(String n) {
      this.name = n;
   }

   public void accept(CarPartVisitor visitor) {
      visitor.visit(this);
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getModelNumberBrake() {
      return modelNumberBrake;
   }

   public void setModelNumberBrake(String modelNumberWheel) {
      this.modelNumberBrake = modelNumberWheel;
   }

   public String getModelYearBrake() {
      return modelYearBrake;
   }

   public void setModelYearBrake(String modelYearWheel) {
      this.modelYearBrake = modelYearWheel;
   }
}