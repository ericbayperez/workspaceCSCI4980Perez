package simpleVisitorPattern.visitor;

import simpleVisitorPattern.part.Body;
import simpleVisitorPattern.part.Engine;
import simpleVisitorPattern.part.Wheel;
import simpleVisitorPattern.part.Brake;

public class MyReverseVisitor extends CarPartVisitor {

	@Override
	public void visit(Wheel wheel) {
		// TODO Auto-generated method stub
		wheel.setName(reverse(wheel.getName()));
		wheel.setModelNumberWheel(reverse(wheel.getModelNumberWheel()));
		wheel.setModelYearWheel(reverse(wheel.getModelYearWheel()));
	}

	@Override
	public void visit(Engine engine) {
		// TODO Auto-generated method stub
		engine.setName(reverse(engine.getName()));
		engine.setModelNumberEngine(reverse(engine.getModelNumberEngine()));
		engine.setModelYearEngine(reverse(engine.getModelYearEngine()));
	}

	@Override
	public void visit(Body body) {
		// TODO Auto-generated method stub
		body.setName(reverse(body.getName()));
		body.setModelNumberBody(reverse(body.getModelNumberBody()));
		body.setModelYearBody(reverse(body.getModelYearBody()));
	}

	@Override
	public void visit(Brake brake) {
		// TODO Auto-generated method stub
		brake.setName(reverse(brake.getName()));
		brake.setModelNumberBrake(reverse(brake.getModelNumberBrake()));
		brake.setModelYearBrake(reverse(brake.getModelYearBrake()));
	}
	
	private String reverse(String source) {
		String reversed = "";
		for (String part : source.split(" ")) {
		    reversed += new StringBuilder(part).reverse().toString();
		    reversed += " ";
		}
		return reversed;
	}
}
