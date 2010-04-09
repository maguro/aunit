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
import org.antlr.runtime.TokenSource;


/**
 * @version $Revision: $ $Date: $
 */
public class LexerResults
{
    private final TokenSource tokenSource;
    private final Token token;

    public LexerResults(TokenSource tokenSource)
    {
        this.tokenSource = tokenSource;
        this.token = tokenSource.nextToken();
    }

    public TokenSource getTokenSource()
    {
        return tokenSource;
    }

    public Token getToken()
    {
        return token;
    }

    public ParseResults parseAs(String production)
    {
        return null;  //Todo change body of created methods use File | Settings | File Templates.
    }

}
