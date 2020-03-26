package com.wymm._25visitor.computer;

import com.wymm._25visitor.visitor.ComputerPartVisitor;

public class Computer implements ComputerPart {
    
    ComputerPart[] part;
    
    public Computer() {
        part = new ComputerPart[]{new KeyBoard(), new Monitor(), new Mouse()};
    }
    
    @Override
    public void accept(ComputerPartVisitor computerPartVisitor) {
        for (int i = 0; i < part.length; i++) {
            part[i].accept(computerPartVisitor);
        }
        computerPartVisitor.visit(this);
    }
}
