package com.wymm._25visitor.visitor;

import com.wymm._25visitor.computer.Computer;
import com.wymm._25visitor.computer.KeyBoard;
import com.wymm._25visitor.computer.Monitor;
import com.wymm._25visitor.computer.Mouse;

public interface ComputerPartVisitor {
    
    void visit(KeyBoard keyBoard);
    
    void visit(Monitor monitor);
    
    void visit(Mouse mouse);
    
    void visit(Computer computer);
}
