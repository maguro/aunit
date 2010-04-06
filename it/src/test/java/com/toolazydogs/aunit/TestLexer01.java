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
import static com.toolazydogs.aunit.Work.scan;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.toolazydogs.aunit.tests.Antlr01Lexer;
import com.toolazydogs.aunit.tests.Antlr01Parser;


/**
 * @version $Revision: $ $Date: $
 */
@RunWith(AntlrTestRunner.class)
public class TestLexer01
{
    @Configuration
    public static Option[] configure()
    {
        return options(
                lexer(Antlr01Lexer.class),
                parser(Antlr01Parser.class)
        );
    }

    @Test
    public void test() throws Exception
    {
        assertToken(Antlr01Lexer.NAME, "abc", scan("abc"));
        assertTree(Antlr01Parser.STATEMENT, "(+ 1 2)", parse("statement", "1 + 2"));
    }
}
