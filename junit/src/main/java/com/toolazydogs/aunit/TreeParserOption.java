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

import com.toolazydogs.aunit.internal.TreeParserFactory;
import org.antlr.runtime.tree.TreeParser;
import org.junit.runners.model.Statement;

/**
 * TreeParser options to be used for configuring tests.
 */
public class TreeParserOption implements Option {
    private final Class<? extends TreeParser> treeParserClass;
    private boolean failOnError = true;

    TreeParserOption(Class<? extends TreeParser> parserClass) {
        assert parserClass != null;

        this.treeParserClass = parserClass;
    }

    Class<? extends TreeParser> getTreeParserClass() {
        return treeParserClass;
    }

    /**
     * Determine if the tree parser will throw an exception if there is an error
     * during a parse.
     *
     * @return <code>true</code> if the tree parser will throw an exception if there
     *         is an error during a parse, <code>false</code> otherwise
     */
    boolean isFailOnError() {
        return failOnError;
    }

    /**
     * Direct that a tree parser is to throw an exception if there is an error
     * during a parse.
     *
     * @return this tree parser option for more configuration
     */
    public TreeParserOption failOnError() {
        failOnError = true;
        return this;
    }

    /**
     * Direct that a tree parser is to throw an exception if there is an error
     * during a parse.
     *
     * @param fail <code>true</code> if the tree parser is to throw an exception if
     *             there is an error during a tree parse, <code>false</code>
     *             otherwise
     * @return this tree parser option for more configuration
     */
    public TreeParserOption failOnError(boolean fail) {
        failOnError = fail;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public Statement generateSetupStatement() {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                AunitRuntime.setTreeParserFactory(new TreeParserFactory(treeParserClass, failOnError));
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    public Statement generateTeardownStatement() {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                AunitRuntime.setTreeParserFactory(null);
            }
        };
    }
}
