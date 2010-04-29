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

import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RuleReturnScope;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.antlr.runtime.tree.Tree;

import com.toolazydogs.aunit.internal.LexerWrapper;
import com.toolazydogs.aunit.internal.ParserWrapper;


/**
 * @version $Revision: $ $Date: $
 */
class LexerResults
{
    private final TokenSource tokenSource;
    private final Token token;

    LexerResults(TokenSource tokenSource)
    {
        this.tokenSource = tokenSource;
        this.token = tokenSource.nextToken();

        LexerWrapper wrapper = (LexerWrapper)tokenSource;
        if (wrapper.isFailOnError() && !wrapper.getErrors().isEmpty())
        {
            throw new LexerException(wrapper.getErrors());
        }
    }

    TokenSource getTokenSource()
    {
        return tokenSource;
    }

    Token getToken()
    {
        return token;
    }

    ParseResults parseAs(SelectedProduction selectedProduction) throws Exception
    {
        if (selectedProduction == null) throw new IllegalArgumentException("SelectedProduction cannot be null, please use production()");
        if (AunitRuntime.getParserFactory() == null) throw new IllegalStateException("Parser factory not set by configuration");

        Parser parser = AunitRuntime.getParserFactory().generate(new CommonTokenStream(tokenSource));

        RuleReturnScope rs = selectedProduction.invoke(parser);

        ParserWrapper wrapper = (ParserWrapper)parser;
        if (wrapper.isFailOnError() && !wrapper.getErrors().isEmpty())
        {
            throw new ParserException(wrapper.getErrors());
        }

        return new ParseResults((Tree)rs.getTree());
    }

}
