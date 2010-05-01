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

import static com.toolazydogs.aunit.Assert.assertToken;
import static com.toolazydogs.aunit.Assert.assertTree;
import static com.toolazydogs.aunit.CoreOptions.lexer;
import static com.toolazydogs.aunit.CoreOptions.options;
import static com.toolazydogs.aunit.CoreOptions.parser;
import static com.toolazydogs.aunit.Work.parse;
import static com.toolazydogs.aunit.Work.rule;
import static com.toolazydogs.aunit.Work.scan;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.toolazydogs.aunit.tests.CMinusLexer;
import com.toolazydogs.aunit.tests.CMinusParser;


/**
 * @version $Revision: $ $Date: $
 */
@RunWith(AntlrTestRunner.class)
public class CMinusTest
{
    @Configuration
    @AppliesTo("test.*")
    public static Option[] configureTest()
    {
        return options(
                lexer(CMinusLexer.class).failOnError(),
                parser(CMinusParser.class).failOnError()
        );
    }

    @Configuration
    @AppliesTo("specialTest")
    public static Option[] configureSpecial()
    {
        return options(
                lexer(CMinusLexer.class).failOnError(),
                parser(CMinusParser.class).failOnError()
        );
    }

    @Test
    public void test() throws Exception
    {
        assertToken(CMinusLexer.ID, "abc", scan("abc"));

        assertTree(CMinusParser.BLOCK, "(BLOCK  (= a (EXPR (+ 1 2)))  ) ", parse("{ a = 1 + 2; }", rule("block")));
        assertTree(CMinusParser.EXPR, "(EXPR (+ 1 2))", parse("1 + 2", rule("expression", 15)));
    }

    @Test
    public void specialTest() throws Exception
    {
        assertToken(CMinusLexer.ID, "abc", scan("abc"));

        assertTree(CMinusParser.EXPR, "(EXPR (+ 1 2))", parse("1 + 2", rule("expression", 15)));
    }

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
            assertTree(CMinusParser.EXPR, "(EXPR (+ 1 2))", parse("1 + 2", rule("expression", 15)));
            fail("Unconfigured test should have failed");
        }
        catch (IllegalStateException e)
        {
        }
    }

}
