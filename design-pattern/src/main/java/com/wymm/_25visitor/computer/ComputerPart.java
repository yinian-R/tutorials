package com.wymm._25visitor.computer;

import com.wymm._25visitor.visitor.ComputerPartVisitor;

public interface ComputerPart {
    void accept(ComputerPartVisitor computerPartVisitor);
}
