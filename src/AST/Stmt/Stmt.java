package AST.Stmt;

import AST.Node;

public abstract class Stmt extends Node {
    public abstract Stmt simplify();
}
