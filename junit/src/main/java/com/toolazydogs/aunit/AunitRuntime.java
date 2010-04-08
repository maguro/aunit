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

import org.antlr.runtime.Lexer;
import org.antlr.runtime.Parser;
import org.antlr.runtime.tree.TreeParser;

import com.toolazydogs.aunit.internal.LexerFactory;
import com.toolazydogs.aunit.internal.ParserFactory;
import com.toolazydogs.aunit.internal.TreeParserFactory;


/**
 * @version $Revision: $ $Date: $
 */
public class AunitRuntime
{
    private final static ThreadLocal<LexerFactory> LEXER_FACTORY = new ThreadLocal<LexerFactory>();
    private final static ThreadLocal<ParserFactory> PARSER_FACTORY = new ThreadLocal<ParserFactory>();
    private final static ThreadLocal<TreeParserFactory> WALKER_FACTORY = new ThreadLocal<TreeParserFactory>();
    private final static ThreadLocal<Lexer> LEXER = new ThreadLocal<Lexer>();
    private final static ThreadLocal<Parser> PARSER = new ThreadLocal<Parser>();
    private final static ThreadLocal<TreeParser> WALKER = new ThreadLocal<TreeParser>();

    public static LexerFactory getLexerFactory()
    {
        return LEXER_FACTORY.get();
    }

    public static void setLexerFactory(LexerFactory factory)
    {
        LEXER_FACTORY.set(factory);
    }

    public static ParserFactory getParserFactory()
    {
        return PARSER_FACTORY.get();
    }

    public static void setParserFactory(ParserFactory factory)
    {
        PARSER_FACTORY.set(factory);
    }

    public static TreeParserFactory getTreeParserFactory()
    {
        return WALKER_FACTORY.get();
    }

    public static void setTreeParserFactory(TreeParserFactory factory)
    {
        WALKER_FACTORY.set(factory);
    }

    public static Lexer getLexer()
    {
        return LEXER.get();
    }

    public static void setLexer(Lexer lexer)
    {
        LEXER.set(lexer);
    }

    public static Parser getParser()
    {
        return PARSER.get();
    }

    public static void setParser(Parser parser)
    {
        PARSER.set(parser);
    }

    public static TreeParser getTreeParser()
    {
        return WALKER.get();
    }

    public static void setTreeParser(TreeParser treeParser)
    {
        WALKER.set(treeParser);
    }

    public static void clear()
    {
        LEXER_FACTORY.set(null);
        PARSER_FACTORY.set(null);
        WALKER_FACTORY.set(null);
        LEXER.set(null);
        PARSER.set(null);
        WALKER.set(null);
    }
}
