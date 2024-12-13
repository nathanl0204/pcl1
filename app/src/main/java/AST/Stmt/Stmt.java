package AST.Stmt;

import AST.Node;

public interface Stmt extends Node {
    public abstract Stmt simplify();
}
