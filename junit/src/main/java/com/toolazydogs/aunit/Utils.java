/**
 *
 * Copyright 2011 (C) The original author or authors
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

import java.io.OutputStream;
import java.io.PrintStream;

import org.antlr.runtime.tree.Tree;


/**
 * Handy ANTLR utilities that may have nothing to do with unit testing.
 */
public class Utils
{
    /**
     * Pretty print the contents of a {@link Tree} to an {@link OutputStream}.
     *
     * @param tree the tree to be pretty printed
     * @param out  the stream to receive the pretty printing
     */
    public static void prettyPrint(Tree tree, OutputStream out)
    {
        doPrettyPrint(tree, new PrintStream(out), 0);
    }

    private static void doPrettyPrint(Tree tree, PrintStream out, int indent)
    {
        for (int i = 0; i < indent; i++) out.print("  ");
        out.print("(");
        out.print(tree.toString());
        for (int i = 0; i < tree.getChildCount(); i++)
        {
            Tree child = tree.getChild(i);
            if (child.getChildCount() > 0)
            {
                out.println();
                doPrettyPrint(child, out, indent + 1);
            }
            else
            {
                out.print(" ");
                out.print(child.toString());
            }
        }
        out.print(")");
    }

    private Utils() {}
}
