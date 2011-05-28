/**
 * Copyright - 2007 Terence Parr.
 * All rights reserved.
 */
grammar CMinus;
options {
  output = AST;              // build trees
  ASTLabelType = CommonTree; // use standard tree nodes
}

tokens {
  METHOD_DECL; // function definition
  ARG_DECL;    // parameter
  BLOCK;
  VAR_DECL;
  CALL;
  ELIST;       // expression list
  EXPR; 	   // root of an expression
}

@header {
package com.toolazydogs.aunit.tests;
}
@lexer::header {
package com.toolazydogs.aunit.tests;
}

compilationUnit
    :   (methodDeclaration | varDeclaration)+
    ;

// START: method
methodDeclaration
    :   type ID '(' formalParameters? ')' block
        -> ^(METHOD_DECL type ID formalParameters? block)
    ;
// END: method

formalParameters
    :   type ID (',' type ID)* -> ^(ARG_DECL type ID)+
    ;

type:   'float'
    |   'int'
    |	'void'
    ;

// START: block
block
    :   '{' statement* '}' -> ^(BLOCK statement*)
    ;
// END: block

// START: var
varDeclaration
    :   type ID ('=' expression[0])? ';' -> ^(VAR_DECL type ID expression?)
    ;
// END: var

statement
    :   block
    |	varDeclaration
    |   'return' expression[0]? ';' -> ^('return' expression?)
    |   postfixExpression // handles function calls like f(i);
        (   '=' expression[0] -> ^('=' postfixExpression expression)
        |   -> ^(EXPR postfixExpression)
        )
        ';'       
    ;

expressionList
    :   expression[0] (',' expression[0])* -> ^(ELIST expression+)
    |   -> ELIST
    ;

// We add the foo parameter as a trumped up example of a rule that takes a
// parameter
expression[int foo]
    :   addExpression -> ^(EXPR addExpression)
    ;
    
addExpression
	:	postfixExpression ('+'^ postfixExpression)*
	;

// START: call
postfixExpression
    :   primary ( lp='('^ expressionList ')'! {$lp.setType(CALL);} )*
    ;
// END: call

primary
    :   ID
    |   INT
    |   '(' expression[0] ')' -> expression
    ;

// LEXER RULES

ID  :   LETTER (LETTER | '0'..'9')*
    ;

fragment
LETTER  :   ('a'..'z' | 'A'..'Z')
    ;

INT :   '0'..'9'+
    ;

WS  :   (' '|'\r'|'\t'|'\n') {$channel=HIDDEN;}
    ;

SL_COMMENT
    :   '//' ~('\r'|'\n')* '\r'? '\n' {$channel=HIDDEN;}
    ;
