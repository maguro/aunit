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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import junit.framework.AssertionFailedError;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RuleReturnScope;
import org.antlr.runtime.tree.Tree;

import com.toolazydogs.aunit.internal.ParserWrapper;


/**
 * @version $Revision: $ $Date: $
 */
public class Work
{
    public static LexerResults scan(String characters) throws Exception
    {
        if (characters == null) throw new IllegalArgumentException("Characters cannot be null");
        if (AunitRuntime.getLexerFactory() == null) throw new AssertionFailedError("Lexer factory not set by configuration");

        Lexer lexer = AunitRuntime.getLexerFactory().generate(new ANTLRStringStream(characters));
        return new LexerResults(lexer);
    }

    public static Tree parse(String stream, String production, Object... arguments) throws Exception
    {
        if (stream == null) throw new IllegalArgumentException("Stream cannot be null");
        if (production == null) throw new IllegalArgumentException("Production name cannot be null");
        if (AunitRuntime.getLexerFactory() == null) throw new AssertionFailedError("Lexer factory not set by configuration");
        if (AunitRuntime.getParserFactory() == null) throw new AssertionFailedError("Parser factory not set by configuration");

        Lexer lexer = AunitRuntime.getLexerFactory().generate(new ANTLRStringStream(stream));
        Parser parser = AunitRuntime.getParserFactory().generate(new CommonTokenStream(lexer));

        for (Method method : collectMethods(parser.getClass()))
        {
            if (method.getName().equals(production))
            {
                RuleReturnScope rs = (RuleReturnScope)method.invoke(parser, arguments);

                ParserWrapper wrapper = (ParserWrapper)parser;
                if (wrapper.isFailOnError() && !wrapper.getErrors().isEmpty())
                {
                    throw new AssertionFailedError(wrapper.getErrors().get(0));
                }

                return (Tree)rs.getTree();
            }
        }

        throw new Exception("Production " + production + " not found");
    }

    private static Set<Method> collectMethods(Class clazz)
    {
        if (clazz == null) return Collections.emptySet();

        Set<Method> s = new HashSet<Method>();

        s.addAll(Arrays.asList(clazz.getDeclaredMethods()));
        s.addAll(collectMethods(clazz.getSuperclass()));

        return s;
    }
}
