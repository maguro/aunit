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

import java.util.List;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;


/**
 * @version $Revision: $ $Date: $
 */
public class RunBefores extends Statement
{
    private final Statement next;

    private final Object target;

    private final List<? extends FrameworkMethod> befores;

    public RunBefores(Statement next, List<? extends FrameworkMethod> befores, Object target)
    {
        this.next = next;
        this.befores = befores;
        this.target = target;
    }

    @Override
    public void evaluate() throws Throwable
    {
        for (FrameworkMethod before : befores) before.invokeExplosively(target);

        next.evaluate();
    }
}
