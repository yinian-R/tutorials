package com.wymm._25visitor;

import com.wymm._25visitor.computer.Computer;
import com.wymm._25visitor.computer.ComputerPart;
import com.wymm._25visitor.visitor.ComputerPartDisplayVisitor;

public class MainDemo {
    public static void main(String[] args) {
        ComputerPart computer = new Computer();
        computer.accept(new ComputerPartDisplayVisitor());
    }
}
