/**
 * Copyright © 2007 Terence Parr.
 * All rights reserved.
 */
// START:header
tree grammar CMinusWalker;

options {
  tokenVocab=CMinus; // import tokens from CMinus.g
  ASTLabelType=CommonTree;
}
@header {
package com.toolazydogs.aunit.tests;
}
// END:header

// START:decl
program
    :   declaration+
    ;

declaration
    :   variable
    |   function
    ;

variable
    :   ^(VAR type ID)
        {System.out.println("define "+$type.text+" "+$ID.text);}
    ;

type:   'int' 
    |   'char'
    ;

function
    :   ^(FUNC type ID formalParameter* block)
        {System.out.println("define "+$type.text+" "+$ID.text+"()");}
    ;

formalParameter
    :   ^(ARG type ID)
    ;
// END:decl

// START:stat
block
    :   ^(SLIST variable* stat*)
    ;

stat: forStat
    | expr
    | block
    | assignStat
    ;

forStat
    :   ^('for' assignStat expr assignStat block)
    ;

assignStat
    :   ^('=' ID expr)
    ;
// END:stat

// START:expr
expr:   ^('==' expr expr)
    |   ^('!=' expr expr)
    |   ^('+' expr expr)
    |   ^('*' expr expr)
    |   ID
    |   INT
    ;
// END:expr
