package com.wymm._25visitor.computer;

import com.wymm._25visitor.visitor.ComputerPartVisitor;

public class KeyBoard implements ComputerPart {
    @Override
    public void accept(ComputerPartVisitor computerPartVisitor) {
        computerPartVisitor.visit(this);
    }
}
