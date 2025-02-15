/*
 * Licensed to Crate.io GmbH ("Crate") under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.  Crate licenses
 * this file to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.  You may
 * obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * However, if you have executed another commercial license agreement
 * with Crate these terms will supersede the license and you may use the
 * software solely pursuant to the terms of the relevant commercial agreement.
 */

package io.crate.sql.tree;

import java.util.Objects;

import javax.annotation.Nullable;

public final class Close extends Statement {

    private final String cursorName;

    /**
     * @param cursorName name of cursor to close; null implies ALL
     */
    public Close(@Nullable String cursorName) {
        this.cursorName = cursorName;
    }

    @Nullable
    public String cursorName() {
        return cursorName;
    }

    @Override
    public <R, C> R accept(AstVisitor<R, C> visitor, C context) {
        return visitor.visitClose(this, context);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cursorName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Close other = (Close) obj;
        return Objects.equals(cursorName, other.cursorName);
    }

    @Override
    public String toString() {
        return "Close{cursorName=" + cursorName + "}";
    }
}
