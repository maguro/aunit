/**
 *
 * Copyright 2010 (C) The original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.toolazydogs.aunit;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.Tree;


/**
 * @author Jeremy D. Frens (jdfrens@users.sourceforge.net)
 * @version $Revision: $ $Date: $
 */
public class Assert
{
    static void assertToken(int expectedChannel, int expectedType, String expectedText, ScanResults scanResults)
    {
    }

    static void assertToken(int expectedChannel, int expectedType, String expectedText, Token token)
    {
    }

    static void assertToken(int expectedType, String expectedText, ScanResults scanResults)
    {
    }

    static void assertToken(int expectedType, String expectedText, org.antlr.runtime.Token token)
    {
    }

    static void assertToken(String message, int expectedChannel, int expectedType, String expectedText, ScanResults scanResults)
    {
    }

    static void assertToken(String message, int expectedChannel, int expectedType, String expectedText, Token token)
    {
    }

    static void assertToken(String message, int expectedType, String expectedText, ScanResults scanResults)
    {
    }

    static void assertToken(String message, int expectedType, String expectedText, Token token)
    {
    }

    static void assertTree(int rootType, String preorder, ParseResults postParse) {}

    static void assertTree(int rootType, String preorder, Tree tree) {}

    static void assertTree(String message, int rootType, String preorder, ParseResults postParse) {}

    static void assertTree(String message, int rootType, String preorder, Tree tree) {}

    static String preorder(Tree tree) {return null;}

    static void refuteToken(int refutedType, ParseResults postScan) {}
}
