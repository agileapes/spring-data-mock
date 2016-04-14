package com.mmnaseri.utils.spring.data.domain.impl;

import com.mmnaseri.utils.spring.data.domain.KeyGenerator;
import com.mmnaseri.utils.spring.data.domain.impl.key.UUIDKeyGenerator;

/**
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (10/8/15)
 */
public class UUIDKeyGeneratorTest extends BaseKeyGeneratorTest<String> {

    @Override
    protected KeyGenerator<String> getKeyGenerator() {
        return new UUIDKeyGenerator();
    }

}