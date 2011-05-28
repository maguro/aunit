/**
 *
 * Copyright 2010-2011 (C) The original author or authors
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

import com.toolazydogs.aunit.tests.CMinusLexer;
import com.toolazydogs.aunit.tests.CMinusParser;
import com.toolazydogs.aunit.tests.CMinusWalker;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.toolazydogs.aunit.Assert.assertToken;
import static com.toolazydogs.aunit.Assert.assertTree;
import static com.toolazydogs.aunit.CoreOptions.*;
import static com.toolazydogs.aunit.Work.*;
import static org.junit.Assert.fail;


/**
 *
 */
@RunWith(AntlrTestRunner.class)
public class CMinusTest
{
    /**
     * Configuration for tests whose name starts with "test".
     * <p/>
     * This is a simple example of how to assign a different configuration to
     * tests whose name starts with "test".
     *
     * @return a configuration for tests whose name starts with "test"
     */
    @Configuration
    @AppliesTo("test.*")
    public static Option[] configureTest()
    {
        return options(
                lexer(CMinusLexer.class),
                parser(CMinusParser.class),
                walker(CMinusWalker.class)
        );
    }

    /**
     * Configuration for tests that will not fail on lexer/parse errors.
     * <p/>
     * This is a simple example of how to assign a different configuration to
     * a set of tests.
     *
     * @return a configuration for tests that will not fail on lexer/parse errors
     */
    @Configuration
    @AppliesTo("oddballTest")
    public static Option[] configureSpecial()
    {
        return options(
                lexer(CMinusLexer.class).failOnError(false),
                parser(CMinusParser.class).failOnError(false),
                walker(CMinusWalker.class).failOnError(false)
        );
    }

    @Test
    public void test() throws Exception
    {
        try
        {
            parse("+ 1 2", rule("expression", args(15)));
            fail("Should have failed parsing");
        }
        catch (ParserException e)
        {
        }
    }

    @Test
    public void testOne() throws Exception
    {
        /**
         * Here's an example of specifying token type to be returned by the lexer
         */
        assertToken(CMinusLexer.ID, "abc", scan("abc"));
    }

    @Test
    public void testTwo() throws Exception
    {
        /**
         * Here's an example of specifying the text of the root token
         */
        assertTree("=", "(= a (EXPR (+ 1 2)))   ", parse(" a = 1 + 2; ", rule("statement")));

        /**
         * Here's an example of specifying an imaginary token that should be at the root of the tree
         */
        assertTree(CMinusParser.BLOCK, "(BLOCK  (= a (EXPR (+ 1 2)))  ) ", parse("{ a = 1 + 2; }", rule("block")));

        /**
         * Here's an example of invoking a rule that takes parameters
         */
        assertTree(CMinusParser.EXPR, "(EXPR (+ 1 2))", parse("1 + 2", rule("expression", args(15))));
    }

    @Test
    public void oddballTest() throws Exception
    {
        try
        {
            parse("+ 1 2", rule("expression", args(15)));
        }
        catch (ParserException e)
        {
            fail("This configuration should not have thrown an exception");
        }
    }

    @Test
    public void testTreeTest() throws Exception
    {
        try
        {
            walk(withRule("testExpr"), resultOf(parse("1 + 2", rule("expression", args(15)))));
        }
        catch (ParserException e)
        {
            fail("This configuration should not have thrown an exception");
        }
    }


    /**
     * This test does not have a name of "oddballTest" or that begins with
     * "test" and so using the static assert methods will throw
     * {@link IllegalStateException} to indicate that the test was not
     * configured.
     *
     * @throws Exception if there is an error scanning or parsing
     */
    @Test
    public void unconfiguredTest() throws Exception
    {
        try
        {
            assertToken(CMinusLexer.ID, "abc", scan("abc"));
            fail("Unconfigured test should have failed");
        }
        catch (IllegalStateException e)
        {
        }

        try
        {
            assertTree(CMinusParser.EXPR, "(EXPR (+ 1 2))", parse("1 + 2", rule("expression", args(15))));
            fail("Unconfigured test should have failed");
        }
        catch (IllegalStateException e)
        {
        }
    }
}
