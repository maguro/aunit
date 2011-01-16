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

import java.util.Collections;
import java.util.List;


/**
 * Instances of <code>ParserException</code> are used to indicate that an error
 * occurred during parsing of a character stream.
 */
public class ParserException extends RuntimeException
{
    private List<String> messages;

    /**
     * {@inheritDoc}
     */
    public ParserException(String message)
    {
        super(message);
        this.messages = Collections.singletonList(message);
    }

    /**
     * Constructs a parser exception with a list of error messages.
     *
     * @param messages the list of error messages to associate with the
     *                 exception, usually explaining the cause of the exception
     */
    public ParserException(List<String> messages)
    {
        this.messages = messages;
    }

    /**
     * Return the list of error messages.
     *
     * @return the list of error messages
     */
    public List<String> getMessages()
    {
        return messages;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return "ParserException{" +
               "messages=" + messages +
               '}';
    }
}
