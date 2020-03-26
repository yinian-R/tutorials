package com.wymm._25visitor.visitor;

import com.wymm._25visitor.computer.Computer;
import com.wymm._25visitor.computer.KeyBoard;
import com.wymm._25visitor.computer.Monitor;
import com.wymm._25visitor.computer.Mouse;

public class ComputerPartDisplayVisitor implements ComputerPartVisitor {
    @Override
    public void visit(KeyBoard keyBoard) {
        System.out.println("Displaying KeyBoard");
    }
    
    @Override
    public void visit(Monitor monitor) {
        System.out.println("Displaying Monitor");
    }
    
    @Override
    public void visit(Mouse mouse) {
        System.out.println("Displaying Mouse");
    }
    
    @Override
    public void visit(Computer computer) {
        System.out.println("Displaying Computer");
    }
}
