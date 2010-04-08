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

import java.lang.reflect.Method;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RuleReturnScope;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.Tree;


/**
 * @version $Revision: $ $Date: $
 */
public class Work
{
    public static Token scan(String characters) throws Exception
    {
        if (characters == null) throw new IllegalArgumentException("Characters cannot be null");

        Lexer lexer = AunitRuntime.getLexerFactory().generate(new ANTLRStringStream(characters));
        return lexer.nextToken();
    }

    public static Tree parse(String stream, String production, Object... arguments) throws Exception
    {
        if (stream == null) throw new IllegalArgumentException("Stream cannot be null");
        if (production == null) throw new IllegalArgumentException("Production name cannot be null");

        Lexer lexer = AunitRuntime.getLexerFactory().generate(new ANTLRStringStream(stream));
        Parser parser = AunitRuntime.getParserFactory().generate(new CommonTokenStream(lexer));

        Method m = parser.getClass().getMethod(production);
        return (Tree)((RuleReturnScope)m.invoke(parser)).getTree();
    }
}
