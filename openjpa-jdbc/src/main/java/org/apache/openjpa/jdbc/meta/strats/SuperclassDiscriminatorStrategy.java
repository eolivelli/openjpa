/*
 * Copyright 2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;

import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;

/**
 * Discriminator strategy that delegates to superclass discriminator.
 *
 * @author Abe White
 * @nojavadoc
 */
public class SuperclassDiscriminatorStrategy
    extends AbstractDiscriminatorStrategy {

    public void map(boolean adapt) {
        // if the superclass maps the discriminator value, so should we
        if (disc.getClassMapping().getJoinablePCSuperclassMapping().
            getDiscriminator().getValue() != null)
            disc.setValue(disc.getMappingInfo().getValue(disc, adapt));
    }

    public void loadSubclasses(JDBCStore store)
        throws SQLException, ClassNotFoundException {
        disc.getClassMapping().getPCSuperclassMapping().
            getDiscriminator().loadSubclasses(store);
        disc.setSubclassesLoaded(true);
    }

    public Class getClass(JDBCStore store, ClassMapping base, Result res)
        throws SQLException, ClassNotFoundException {
        return disc.getClassMapping().getPCSuperclassMapping().
            getDiscriminator().getClass(store, base, res);
    }

    public SQLBuffer getClassConditions(JDBCStore store, Select sel,
        Joins joins, ClassMapping base, boolean subclasses) {
        return disc.getClassMapping().getPCSuperclassMapping().
            getDiscriminator().getClassConditions(store, sel, joins, base,
            subclasses);
    }
}
