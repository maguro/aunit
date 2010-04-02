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
import java.util.Arrays;
import java.util.List;

import com.toolazydogs.aunit.CompositeOption;
import com.toolazydogs.aunit.Option;


/**
 * @version $Revision: $ $Date: $
 */
public class DefaultCompositeOption
        implements CompositeOption
{

    /**
     * Composite options (cannot be null).
     */
    private final List<Option> m_options;

    /**
     * Constructor.
     *
     * @param options composite options (can be null or no option specified)
     */
    public DefaultCompositeOption(final Option... options)
    {
        m_options = new ArrayList<Option>();
        add(options);
    }

    /**
     * Constructor.
     */
    public DefaultCompositeOption()
    {
        this(new Option[0]);
    }

    /**
     * {@inheritDoc}
     */
    public Option[] getOptions()
    {
        return OptionUtils.expand(m_options.toArray(new Option[m_options.size()]));
    }

    /**
     * Adds options.
     *
     * @param options composite options to be added (can be null or no options specified)
     * @return itself, for fluent api usage
     */
    public DefaultCompositeOption add(final Option... options)
    {
        if (options != null)
        {
            m_options.addAll(Arrays.asList(options));
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append("DefaultCompositeOption");
        sb.append("{options=").append(m_options);
        sb.append('}');
        return sb.toString();
    }

}
