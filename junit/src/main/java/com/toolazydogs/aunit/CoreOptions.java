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

import static com.toolazydogs.aunit.internal.OptionUtils.expand;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.Parser;

import com.toolazydogs.aunit.internal.DefaultCompositeOption;


/**
 * Factory methods for core options.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @author Toni Menzel (toni@okidokiteam.com)
 * @author Alan D. Cabrera (adc@toolazydogs.com)
 * @version $Revision: $ $Date: $
 */
public class CoreOptions
{

    private static final String DEFAULT_CONFIGURATION = "META-INF/maven/paxexam-config.args";

    /**
     * Utility class. Meant to be used via the static factory methods.
     */
    private CoreOptions()
    {
        // utility class
    }

    /**
     * Convenience method (more to be used for a nice fluent api) for creating an array of options.
     * It also expands the composite options.
     *
     * @param options options
     * @return provided options, expanded
     * @see com.toolazydogs.aunit.internal.OptionUtils#expand(Option...)
     */
    public static Option[] options(final Option... options)
    {
        return expand(options);
    }

    /**
     * Convenience method (more to be used for a nice fluent api) for creating a composite option.
     *
     * @param options options
     * @return provided options
     */
    public static Option composite(final Option... options)
    {
        return new DefaultCompositeOption(options);
    }

    /**
     * Create a lexer option instance that indicates the lexer class used for testing.
     *
     * @param lexerClass the lexer class used for testing
     * @return the lexer option instance that indicates the lexer class used for testing
     */
    public static LexerOption lexer(final Class<? extends Lexer> lexerClass)
    {
        return new LexerOption(lexerClass);
    }

    /**
     * Create a parser option instance that indicates the parser class used for testing.
     *
     * @param parserClass the parser class used for testing
     * @return the parser option instance that indicates the parser class used for testing
     */
    public static ParserOption parser(final Class<? extends Parser> parserClass)
    {
        return new ParserOption(parserClass);
    }
}
