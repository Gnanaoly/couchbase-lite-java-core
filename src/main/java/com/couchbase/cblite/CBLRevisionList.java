/**
 * Original iOS version by  Jens Alfke
 * Ported to Android by Marty Schoch
 *
 * Copyright (c) 2012 Couchbase, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package com.couchbase.cblite;

import com.couchbase.cblite.internal.CBLRevisionInternal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * An ordered list of TDRevisions
 */
@SuppressWarnings("serial")
public class CBLRevisionList extends ArrayList<CBLRevisionInternal> {

    public CBLRevisionList() {
        super();
    }

    /**
     * Allow converting to CBLRevisionList from List<CBLRevisionInternal>
     * @param list
     */
    public CBLRevisionList(List<CBLRevisionInternal> list) {
        super(list);
    }

    public CBLRevisionInternal revWithDocIdAndRevId(String docId, String revId) {
        Iterator<CBLRevisionInternal> iterator = iterator();
        while(iterator.hasNext()) {
            CBLRevisionInternal rev = iterator.next();
            if(docId.equals(rev.getDocId()) && revId.equals(rev.getRevId())) {
                return rev;
            }
        }
        return null;
    }

    public List<String> getAllDocIds() {
        List<String> result = new ArrayList<String>();

        Iterator<CBLRevisionInternal> iterator = iterator();
        while(iterator.hasNext()) {
            CBLRevisionInternal rev = iterator.next();
            result.add(rev.getDocId());
        }

        return result;
    }

    public List<String> getAllRevIds() {
        List<String> result = new ArrayList<String>();

        Iterator<CBLRevisionInternal> iterator = iterator();
        while(iterator.hasNext()) {
            CBLRevisionInternal rev = iterator.next();
            result.add(rev.getRevId());
        }

        return result;
    }

    public void sortBySequence() {
        Collections.sort(this, new Comparator<CBLRevisionInternal>() {

            public int compare(CBLRevisionInternal rev1, CBLRevisionInternal rev2) {
                return CBLMisc.TDSequenceCompare(rev1.getSequence(), rev2.getSequence());
            }

        });
    }

    public void limit(int limit) {
        if(size() > limit) {
            removeRange(limit, size());
        }
    }

}
