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

import org.antlr.runtime.tree.Tree;


/**
 * A wrapper to an instance of a {@link Tree}.  Used to provide type safety for
 * method arguments of {@link Assert}.
 *
 * @see Assert
 */
class ParseResults
{
    private Tree tree;

    ParseResults(Tree tree)
    {
        assert tree != null;

        this.tree = tree;
    }

    Tree getTree()
    {
        return tree;
    }
}
