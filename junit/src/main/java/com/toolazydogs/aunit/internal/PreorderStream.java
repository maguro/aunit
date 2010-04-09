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
package com.toolazydogs.aunit.internal;

/**
 * @version $Revision: $ $Date: $
 */
public class PreorderStream
{
    private final String characters;
    private int ptr = 0;

    public PreorderStream(String characters)
    {
        this.characters = characters;
    }


    public String token()
    {
        while (ptr < characters.length() && Character.isWhitespace(characters.charAt(ptr))) ptr++;
        int start = ptr;
        while (ptr < characters.length()
               && !Character.isWhitespace(characters.charAt(ptr))
               && characters.charAt(ptr) != '('
               && characters.charAt(ptr) != ')')
        {
            ptr++;
        }
        return characters.substring(start, ptr);
    }

    public void leftparen() throws PreorderException
    {
        while (ptr < characters.length() && Character.isWhitespace(characters.charAt(ptr)) && characters.charAt(ptr) != '(') ptr++;
        if (characters.charAt(ptr) != '(') throw new PreorderException("Should have consumed a '('");
        ptr++;
    }

    public void rightparen() throws PreorderException
    {
        while (ptr < characters.length() && Character.isWhitespace(characters.charAt(ptr)) && characters.charAt(ptr) != ')') ptr++;
        if (characters.charAt(ptr) != ')') throw new PreorderException("Should have consumed a ')'");
        ptr++;
    }

    @Override
    public String toString()
    {
        return characters.substring(ptr);
    }

    public void done() throws PreorderException
    {
        while (ptr < characters.length() && Character.isWhitespace(characters.charAt(ptr))) ptr++;
        if (ptr < characters.length()) throw new PreorderException("Should have nothing left to consume");
    }
}
