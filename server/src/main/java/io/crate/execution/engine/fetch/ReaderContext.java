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

package io.crate.execution.engine.fetch;

import java.io.IOException;

import org.apache.lucene.index.LeafReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.StoredFieldVisitor;

public class ReaderContext {

    private final LeafReaderContext leafReaderContext;

    interface DocumentFieldsReader {

        void document(int docId, StoredFieldVisitor visitor) throws IOException;
    }

    private final DocumentFieldsReader storedFieldsReader;

    public ReaderContext(LeafReaderContext leafReaderContext, DocumentFieldsReader storedFieldsReader) {
        this.leafReaderContext = leafReaderContext;
        this.storedFieldsReader = storedFieldsReader;
    }

    public ReaderContext(LeafReaderContext leafReaderContext) {
        LeafReader reader = leafReaderContext.reader();
        this.leafReaderContext = leafReaderContext;
        this.storedFieldsReader = reader::document;
    }

    public void visitDocument(int docId, StoredFieldVisitor visitor) throws IOException {
        storedFieldsReader.document(docId, visitor);
    }

    public LeafReader reader() {
        return leafReaderContext.reader();
    }

    public int docBase() {
        return leafReaderContext.docBase;
    }

}
