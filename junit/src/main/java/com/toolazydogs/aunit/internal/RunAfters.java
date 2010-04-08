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

import java.util.ArrayList;
import java.util.List;

import org.junit.internal.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;


/**
 * @version $Revision: $ $Date: $
 */
public class RunAfters extends Statement
{
    private final Statement fNext;
    private final List<? extends Statement> afters;

    public RunAfters(Statement next, List<? extends Statement> afters)
    {
        fNext = next;
        this.afters = afters;
    }

    @Override
    public void evaluate() throws Throwable
    {
        List<Throwable> fErrors = new ArrayList<Throwable>();
        fErrors.clear();
        try
        {
            fNext.evaluate();
        }
        catch (Throwable e)
        {
            fErrors.add(e);
        }
        finally
        {
            for (Statement each : afters)
                try
                {
                    each.evaluate();
                }
                catch (Throwable e)
                {
                    fErrors.add(e);
                }
        }
        if (fErrors.isEmpty())
            return;
        if (fErrors.size() == 1)
            throw fErrors.get(0);
        throw new MultipleFailureException(fErrors);
    }
}
