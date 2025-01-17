/*
 * Copyright 2004-2014 H2 Group. Multiple-Licensed under the MPL 2.0,
 * and the EPL 1.0 (http://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package org.lealone.db.value;

import org.lealone.common.util.StringUtils;
import org.lealone.db.SysProperties;

/**
 * Implementation of the CHAR data type.
 */
public class ValueStringFixed extends ValueString {

    private static final ValueStringFixed EMPTY = new ValueStringFixed("");

    protected ValueStringFixed(String value) {
        super(value);
    }

    private static String trimRight(String s) {
        int endIndex = s.length() - 1;
        int i = endIndex;
        while (i >= 0 && s.charAt(i) == ' ') {
            i--;
        }
        s = i == endIndex ? s : s.substring(0, i + 1);
        return s;
    }

    @Override
    public int getType() {
        return Value.STRING_FIXED;
    }

    /**
     * Get or create a fixed length string value for the given string.
     * Spaces at the end of the string will be removed.
     *
     * @param s the string
     * @return the value
     */
    public static ValueStringFixed get(String s) {
        s = trimRight(s);
        if (s.length() == 0) {
            return EMPTY;
        }
        ValueStringFixed obj = new ValueStringFixed(StringUtils.cache(s));
        if (s.length() > SysProperties.OBJECT_CACHE_MAX_PER_ELEMENT_SIZE) {
            return obj;
        }
        return (ValueStringFixed) Value.cache(obj);
    }

    @Override
    protected ValueString getNew(String s) {
        return ValueStringFixed.get(s);
    }
}
