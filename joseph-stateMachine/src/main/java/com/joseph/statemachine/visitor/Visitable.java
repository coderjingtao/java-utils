package com.joseph.statemachine.visitor;

/**
 * @author Joseph.Liu
 */
public interface Visitable {
    String visit(final Visitor visitor);
}
