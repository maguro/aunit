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
import static com.toolazydogs.aunit.CoreOptions.options;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.toolazydogs.aunit.tests.Test01Lexer;


/**
 * @version $Revision: $ $Date: $
 */
@RunWith(JUnit4TestRunner.class)
public class TestLexer01
{
    @Inject(lexerClass = Test01Lexer.class)
    private LexerWrapper lexer = null;

    @Configuration
    public static Option[] configure()
    {
        return options(
//                equinox(),
//                felix(),
//                knopflerfish(),
////                papoose(),
//                compendiumProfile(),
//                provision(
//                        mavenBundle().groupId("org.livetribe.slp").artifactId("livetribe-slp-osgi").version(asInProject())
//                )
//                // vmOption("-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"),
//                // this is necessary to let junit runner not timout the remote process before attaching debugger
//                // setting timeout to 0 means wait as long as the remote service comes available.
//                // starting with version 0.5.0 of PAx Exam this is no longer required as by default the framework tests
//                // will not be triggered till the framework is not started
//                // waitForFrameworkStartup()
        );
    }

    @Test
    public void test() throws Exception
    {
        assertToken(Test01Lexer.NAME, "abc", lexer.scan("abc"));
    }
}
